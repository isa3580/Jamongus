/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Servidor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 9isab
*/


public class Servidor extends Thread {
    private static final int PUERTO = 12345;
    
    // Lista sincronizada de clientes
    public static List<ManejadorCliente> hilosClientes = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void run() {
        try (ServerSocket servidorSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket socketCliente = servidorSocket.accept();
                System.out.println("Nuevo jugador conectado desde: " + socketCliente.getInetAddress());

                ManejadorCliente manejador = new ManejadorCliente(socketCliente);
                hilosClientes.add(manejador);
                manejador.start();

                // Al entrar un nuevo jugador, notificamos la lista actualizada a todos
                difundirListaCompleta();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Envía la lista de todos los nombres conectados a todos los clientes.
     */
    public static void difundirListaCompleta() {
        StringBuilder sb = new StringBuilder("LOBBY_LIST:");
        
        synchronized (hilosClientes) {
            for (int i = 0; i < hilosClientes.size(); i++) {
                sb.append("Jugador ").append(i + 1);
                // Si no es el último, añadimos una coma para separar
                if (i < hilosClientes.size() - 1) {
                    sb.append(",");
                }
            }
        }

        String mensaje = sb.toString();
        // Enviamos la lista construida a todos los hilos
        synchronized (hilosClientes) {
            for (ManejadorCliente cliente : hilosClientes) {
                cliente.enviar(mensaje);
            }
        }
    }

    /**
     * Difunde mensajes generales (movimientos, acciones, etc.)
     */
    public static void difundir(String mensaje, ManejadorCliente emisor) {
        synchronized (hilosClientes) {
            for (int i = hilosClientes.size() - 1; i >= 0; i--) {
                ManejadorCliente cliente = hilosClientes.get(i);
                try {
                    // Si es un mensaje de inicio, lo reciben todos
                    if (mensaje.startsWith("START_GAME:")) {
                        cliente.enviar(mensaje);
                    } else if (cliente != emisor) {
                        // Movimientos y otros datos solo a los demás
                        cliente.enviar(mensaje);
                    }
                } catch (Exception e) {
                    hilosClientes.remove(i);
                    // Si alguien se desconectó durante la difusión, actualizamos la lista
                    difundirListaCompleta();
                }
            }
        }
    }
}