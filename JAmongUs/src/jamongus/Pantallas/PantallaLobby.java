/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

/**
 *
 * @author 9isab
 */

import jamongus.Mapas.MapaCancha;
import jamongus.Mapas.MapaLab;
import jamongus.Mapas.Mapas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;


public class PantallaLobby extends JPanel {
    private JComboBox<String> selectorMapa;
    private JLabel lblCodigo;
    private JTextArea areaListaJugadores;
    private JButton btnStart;
    private Image imagenFondo;
    private String codigoActual = "";
    private boolean esHost = false;

    public PantallaLobby(ActionListener iniciarJuego, ActionListener volverAlPreLobby) {
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(null);
        
        try {
            this.imagenFondo = new ImageIcon("assets/imagenLobby.png").getImage();
        } catch (Exception e) {
            System.err.println("Imagen de fondo no encontrada en assets/");
        }

        JLabel titulo = new JLabel("LOBBY DE ESPERA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 35));
        titulo.setForeground(Color.CYAN);
        titulo.setBounds(0, 30, 800, 50);
        add(titulo);

        // --- LISTA DE JUGADORES ---
        areaListaJugadores = new JTextArea();
        areaListaJugadores.setFont(new Font("Monospaced", Font.BOLD, 16));
        areaListaJugadores.setForeground(Color.GREEN);
        areaListaJugadores.setOpaque(false);
        areaListaJugadores.setEditable(false);
        
        JScrollPane scroll = new JScrollPane(areaListaJugadores);
        scroll.setBounds(80, 150, 200, 300);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        add(scroll);

        // --- SECCIÓN DE CÓDIGO ---
        lblCodigo = new JLabel("CÓDIGO: -----", SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Serif", Font.BOLD, 28));
        lblCodigo.setForeground(Color.YELLOW);
        lblCodigo.setBounds(450, 120, 250, 40);
        add(lblCodigo);

        // --- SELECTOR MAPA ---
        JLabel lblMapa = new JLabel("SELECCIONAR MAPA:");
        lblMapa.setForeground(Color.WHITE);
        lblMapa.setBounds(450, 200, 200, 20);
        add(lblMapa);

        String[] mapas = {"UNEG Módulo A", "The Skeld (Espacio)"};
        selectorMapa = new JComboBox<>(mapas);
        selectorMapa.setBounds(450, 230, 250, 35);
        add(selectorMapa);

        // --- BOTONES ---
        btnStart = new JButton("COMENZAR PARTIDA");
        btnStart.setBounds(450, 320, 250, 60);
        btnStart.setBackground(new Color(46, 204, 113));
        btnStart.setForeground(Color.WHITE);
        btnStart.setFont(new Font("Arial", Font.BOLD, 18));
        btnStart.setFocusPainted(false);
        btnStart.addActionListener(iniciarJuego);
        add(btnStart);

        JButton btnSalir = new JButton("SALIR AL MENU");
        btnSalir.setBounds(450, 400, 250, 40);
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(volverAlPreLobby);
        add(btnSalir);
    }

    /**
     * Se llama al entrar al lobby para definir si es Host o Invitado.
     */
    public void configurarLobby(String codigo, boolean esHost) {
        this.codigoActual = codigo;
        this.esHost = esHost;
        lblCodigo.setText("CÓDIGO: " + codigo);
        
        btnStart.setVisible(esHost);
        selectorMapa.setEnabled(esHost);
        
        // Reiniciamos la lista con el usuario actual
        if (esHost) {
            areaListaJugadores.setText("👤 Tú (Host)\n");
        } else {
            areaListaJugadores.setText("👤 Jugador 1 (Host)\n👤 Tú (Invitado)\n");
        }
    }
    
    public void actualizarListaVisual(String[] nombres) {
    // 1. Limpiamos el cuadro de texto
    areaListaJugadores.setText("");
    
    // 2. Nos aseguramos de que el color sea blanco
    areaListaJugadores.setForeground(Color.WHITE);
    
    // 3. Recorremos el arreglo de nombres que nos mandó el servidor
    for (int i = 0; i < nombres.length; i++) {
        String decoracion = (i == 0) ? " [HOST]" : "";
        
        // Escribimos cada nombre en una línea nueva
        areaListaJugadores.append("👤 " + nombres[i] + decoracion + "\n");
    }
    
    // Refrescamos visualmente el componente
    areaListaJugadores.revalidate();
    areaListaJugadores.repaint();
}

    /**
     * MÉTODO NUEVO: Permite que JAmongUs agregue nombres cuando 
     * el servidor notifica una nueva conexión.
     */
    public void agregarJugadorALista(String nombre) {
        areaListaJugadores.append("👤 " + nombre + "\n");
    }

    public String getCodigoActual() { return codigoActual; }
    public boolean esHost() { return esHost; }
    
public int getMapaSeleccionadoIndex() {
    return selectorMapa.getSelectedIndex();
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), null);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(60, 100, 250, 400, 20, 20);
        g2.fillRoundRect(420, 100, 310, 400, 20, 20);
    }
}