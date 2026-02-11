/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author 9isab
*/
public class ManejadorCliente extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    // Colores disponibles en orden
    private static final String[] COLORES = {"rojo.png", "azulClaro.png", "verde.png", "rosa.png",
        "azulOscuro.png","morado.png","naranja.png","marron.png","negro.png","amarillo.png"};
    private static int contadorColor = 0;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void enviar(String mensaje) { 
        if (out != null) out.println(mensaje); 
    }

    @Override
    public void run() {
        try {
            // 1. Asignar color único al entrar
            String colorAsignado;
            synchronized (COLORES) {
                colorAsignado = COLORES[contadorColor % COLORES.length];
                contadorColor++;
            }
            enviar("TU_COLOR:" + colorAsignado);

            // 2. Notificar a todos que la lista de jugadores cambió (alguien entró)
            Servidor.difundirListaCompleta();

            String linea;
            while ((linea = in.readLine()) != null) {
                // Si el mensaje es de SALA, refrescamos la lista
                if (linea.startsWith("SALA:")) {
                    Servidor.difundirListaCompleta();
                } 
                else if (linea.startsWith("CONGELAR:")) {
                    Servidor.difundir(linea, this);
                } 
                else {
                    // Reenvía movimientos, START_GAME, etc.
                    Servidor.difundir(linea, this);
                }
            }
        } catch (IOException e) {
            System.out.println("Conexión perdida con un jugador.");
        } finally {
            // 3. LIMPIEZA AL SALIR:
            // Quitamos al jugador de la lista del servidor
            Servidor.hilosClientes.remove(this);
            
            // Avisamos a todos los que quedan que reconstruyan su lista (alguien salió)
            Servidor.difundirListaCompleta();
            
            try { 
                socket.close(); 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}