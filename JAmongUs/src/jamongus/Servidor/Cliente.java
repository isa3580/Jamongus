/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Servidor;

import jamongus.JAmongUs;
import jamongus.Pantallas.Escenario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author 9isab
 */



public class Cliente extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private Escenario pJuego;

    public Cliente(String ip, Escenario juego) {
        this.pJuego = juego;
        try {
            Socket s = new Socket(ip, 12345);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            System.err.println("Sin servidor");
        }
    }

    public void enviarDatos(String m) {
        if (out != null) out.println(m);
    }

    @Override
    public void run() {
        try {
            String linea;
            while ((linea = in.readLine()) != null) {
                
                // 1. LISTA DE NOMBRES LOBBY
                if (linea.startsWith("LOBBY_LIST:")) {
                    String[] n = linea.split(":")[1].split(",");
                    SwingUtilities.invokeLater(() -> {
                        JAmongUs v = (JAmongUs) SwingUtilities.getWindowAncestor(pJuego);
                        if (v != null) v.actualizarNombresLobby(n);
                    });
                }
                
                // 2. ASIGNACIÓN DE COLOR (Cambiado a setMiColorLocal para que no salga rojo)
                else if (linea.startsWith("TU_COLOR:")) {
                    pJuego.setMiColorLocal(linea.split(":")[1]);
                }
                
                // 3. ASIGNACIÓN DE ROL
                else if (linea.startsWith("TU_ROL:")) {
                    pJuego.setRol(linea.split(":")[1].equals("IMPOSTOR"));
                }
                
                // 4. INICIO DE JUEGO
                else if (linea.startsWith("START_GAME:")) {
                    int mIdx = Integer.parseInt(linea.split(":")[1]);
                    SwingUtilities.invokeLater(() -> {
                        JAmongUs v = (JAmongUs) SwingUtilities.getWindowAncestor(pJuego);
                        if (v != null) v.iniciarSecuenciaDeJuego(mIdx);
                    });
                }
                
                // 5. CONGELAR
                else if (linea.startsWith("CONGELAR:")) {
                    pJuego.aplicarCongelamiento(linea.split(":")[1]);
                }
                
                // 6. MOVIMIENTO (Sincronización de los 7 datos)
                else if (linea.contains(",") && !linea.contains(":")) {
                    String[] d = linea.split(",");
                    if (d.length >= 7) {
                        pJuego.actualizarRemoto(
                            d[0],                          // ID
                            Integer.parseInt(d[1]),        // X
                            Integer.parseInt(d[2]),        // Y
                            Integer.parseInt(d[3]),        // Fila
                            Integer.parseInt(d[4]),        // Col
                            d[5],                          // Sprite
                            d[6].equals("1")               // Congelado
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en hilo de cliente");
        }
    }
}