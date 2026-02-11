/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jamongus;
/**
 *
 * @author 9isab
 */

import jamongus.Mapas.MapaCancha;
import jamongus.Mapas.MapaLab;
import jamongus.Mapas.Mapas;
import jamongus.Servidor.Servidor;
import javax.swing.JFrame;
import jamongus.Pantallas.PantallaAcercaDe;
import jamongus.Pantallas.PantallaAyuda;
import jamongus.Pantallas.PantallaLobby;
import jamongus.Pantallas.PantallaPreLobby;
import jamongus.Pantallas.PantallaPrincipal;
import jamongus.Pantallas.PantallaRol;
import jamongus.Pantallas.Escenario;
import jamongus.Pantallas.PantallaCodigo;
import jamongus.logica.Reproductor;


// Importamos todas las clases del paquete de interfaz 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class JAmongUs extends JFrame {
    private CardLayout navegador = new CardLayout();
    private JPanel contenedor = new JPanel(navegador);
    
    // Pantallas principales
    private Escenario pantallaJuego;
    private PantallaLobby pantallaLobby;
    private PantallaPreLobby pantallaPreLobby;
    private PantallaCodigo pantallaIngresarCod;

    public JAmongUs() {
        setTitle("JAmongUs Multiplayer - Kill & Freeze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Inicializamos el escenario (el motor del juego)
        Reproductor.iniciarMusicaMenu("espacio.wav");
        pantallaJuego = new Escenario(new MapaLab());

        // --- ACCIÓN: CREAR PARTIDA (HOST) ---
        ActionListener accionCrear = e -> {
            new Servidor().start(); // Inicia el servidor localmente
            String cod = generarCodigoAleatorio();
            pantallaLobby.configurarLobby(cod, true); // Es host
            pantallaJuego.conectarRed("localhost");
            
            Timer t = new Timer(200, ev -> {
                if (pantallaJuego.getCliente() != null) {
                    pantallaJuego.getCliente().enviarDatos("SALA:" + cod);
                    navegador.show(contenedor, "LOBBY");
                }
            });
            t.setRepeats(false); t.start();
        };

        // --- ACCIÓN: UNIRSE A PARTIDA (INVITADO) ---
        ActionListener accionUnir = e -> {
            String cod = pantallaIngresarCod.getCodigoIngresado();
            if (!cod.isEmpty()) {
                pantallaLobby.configurarLobby(cod, false); // No es host
                pantallaJuego.conectarRed("localhost"); 
                
                Timer t = new Timer(200, ev -> {
                    if (pantallaJuego.getCliente() != null) {
                        pantallaJuego.getCliente().enviarDatos("SALA:" + cod);
                        navegador.show(contenedor, "LOBBY");
                    }
                });
                t.setRepeats(false); t.start();
            }
        };

        // --- ACCIÓN: COMENZAR (SOLO HOST) ---
        ActionListener accionComenzar = e -> {
            if (pantallaLobby.esHost()) {
                int mapaIdx = pantallaLobby.getMapaSeleccionadoIndex();
                
                Reproductor.detenerMusicaMenu();
                // Avisamos a todos los clientes que inicien
                if (pantallaJuego.getCliente() != null) {
                    pantallaJuego.getCliente().enviarDatos("START_GAME:" + mapaIdx);
                }
                iniciarSecuenciaDeJuego(mapaIdx);
            }
        };

        // Inicialización de Pantallas de Menú
        pantallaPreLobby = new PantallaPreLobby(accionCrear, 
            e -> navegador.show(contenedor, "CODIGO"), 
            e -> navegador.show(contenedor, "MENU"));
            
        pantallaIngresarCod = new PantallaCodigo(accionUnir, 
            e -> navegador.show(contenedor, "PRELOBBY"));
            
        pantallaLobby = new PantallaLobby(accionComenzar, 
            e -> navegador.show(contenedor, "PRELOBBY"));

        // Agregamos todo al contenedor principal
        contenedor.add(new PantallaPrincipal(
            e -> navegador.show(contenedor, "PRELOBBY"), null, null), "MENU");
        contenedor.add(pantallaPreLobby, "PRELOBBY");
        contenedor.add(pantallaIngresarCod, "CODIGO");
        contenedor.add(pantallaLobby, "LOBBY");
        contenedor.add(pantallaJuego, "JUEGO");

        add(contenedor);
        pack();
        setLocationRelativeTo(null);
        navegador.show(contenedor, "MENU");
        setVisible(true);
    }

    /**
     * Este método gestiona la transición: 
     * Lobby -> PantallaRol (Shhh) -> Escenario
     */
    public void iniciarSecuenciaDeJuego(int mapaIndex) {
        // 1. Configuramos el mapa elegido
        Mapas mapaElegido = (mapaIndex == 0) ? new MapaLab() : new MapaCancha();
        pantallaJuego.setMapa(mapaElegido);
        
        // 2. Creamos la pantalla de Rol (Shhh)
        // Usamos pantallaJuego.getSoyImpostor() que ya debe estar seteado por el Cliente
        PantallaRol pRol = new PantallaRol(pantallaJuego.getSoyImpostor(), () -> {
            // Acción que ocurre tras los 4 segundos de la pantalla de rol:
            navegador.show(contenedor, "JUEGO");
            pantallaJuego.requestFocusInWindow(); // Enfocamos teclado para mover al jugador
        });
        
        // 3. Mostramos la presentación
        contenedor.add(pRol, "MOSTRAR_ROL");
        navegador.show(contenedor, "MOSTRAR_ROL");
    }

    public void actualizarNombresLobby(String[] nombres) {
        if (this.pantallaLobby != null) {
            this.pantallaLobby.actualizarListaVisual(nombres);
        }
    }

    private String generarCodigoAleatorio() {
        return "ABCDE".charAt((int)(Math.random()*5)) + "" + (int)(Math.random()*900 + 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JAmongUs::new);
    }
}