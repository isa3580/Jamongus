/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.logica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author 9isab
 */


public class Ducto {
    public int x, y;
    public Ducto destino; // El ducto conectado

    public Ducto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Conecta este ducto con otro para permitir el viaje
     */
    public void conectarCon(Ducto otro) {
        this.destino = otro;
    }

    /**
     * Dibuja la rejilla del ducto en el mapa.
     * Se usa un diseño metálico circular/ovalado.
     */
    public void dibujar(Graphics2D g2) {
        // 1. Sombra o borde exterior (Efecto de profundidad en el suelo)
        g2.setColor(new Color(30, 30, 30));
        g2.fillOval(x - 16, y - 11, 32, 22);

        // 2. Tapa metálica principal
        g2.setColor(new Color(70, 75, 80)); // Gris metálico
        g2.fillOval(x - 15, y - 10, 30, 20);

        // 3. Detalles de la rejilla (Líneas negras)
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        // Líneas horizontales para que parezca una rejilla
        g2.drawLine(x - 10, y - 4, x + 10, y - 4);
        g2.drawLine(x - 12, y, x + 12, y);
        g2.drawLine(x - 10, y + 4, x + 10, y + 4);

        // 4. Borde de la tapa
        g2.drawOval(x - 15, y - 10, 30, 20);
        
        // Opcional: Un pequeño brillo para efecto metálico
        g2.setColor(new Color(200, 200, 200, 50));
        g2.fillArc(x - 12, y - 8, 15, 10, 0, 180);
    }
}