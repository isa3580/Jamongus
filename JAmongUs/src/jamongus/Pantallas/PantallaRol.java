/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;
import jamongus.logica.Reproductor;
/**
 *
 * @author 9isab
 */


public class PantallaRol extends JPanel {
    private BufferedImage imagenShh;
    private boolean mostrarTexto = false;
    private String rolMensaje;
    private Color rolColor;
    private boolean soyImpostor;

    public PantallaRol(boolean esImpostor, Runnable alTerminar) {
        this.soyImpostor = esImpostor; 
        this.setBackground(Color.BLACK);
        
        // --- 1. LLAMADA AL SONIDO (Dentro del constructor) ---
        Reproductor.jugar("shh.wav");

        try {
            imagenShh = ImageIO.read(new File("assets/shh.png"));
        } catch (Exception e) {
            System.err.println("Error: No se encontró assets/shh.png");
        }

        this.rolMensaje = esImpostor ? "IMPOSTOR" : "TRIPULANTE";
        this.rolColor = esImpostor ? Color.RED : new Color(0, 255, 255);

        // Timer para mostrar el texto después de 1.5 segundos
        Timer timerTexto = new Timer(1500, e -> {
            mostrarTexto = true;
            repaint();
        });
        timerTexto.setRepeats(false);
        timerTexto.start();

        // Timer para cerrar la pantalla después de 4 segundos
        Timer timerSalida = new Timer(4000, e -> alTerminar.run());
        timerSalida.setRepeats(false);
        timerSalida.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int midX = getWidth() / 2;

        // --- DIBUJAR IMAGEN ---
        if (imagenShh != null) {
            int iw = 300; 
            int ih = 300; 
            g2.drawImage(imagenShh, midX - (iw / 2), 100, iw, ih, null);
        }

        // --- DIBUJAR TEXTO SECUENCIAL ---
        if (mostrarTexto) {
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            g2.setColor(rolColor);
            FontMetrics fm = g2.getFontMetrics();
            int tx = midX - (fm.stringWidth(rolMensaje) / 2);
            g2.drawString(rolMensaje, tx, 500);

            g2.setFont(new Font("Arial", Font.PLAIN, 22));
            g2.setColor(Color.WHITE);
            
            String sub = soyImpostor ? 
                "Congela a todos sin que te descubran." : 
                "Completa tus tareas y encuentra al impostor.";

            int sx = midX - (g2.getFontMetrics().stringWidth(sub) / 2);
            g2.drawString(sub, sx, 550);
        }
    }
}