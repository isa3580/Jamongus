/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.logica;

/**
 *
 * @author 9isab
 */


import java.awt.*;

public class Tarea {
    public String nombre;
    public int x, y;
    public int progreso = 0;
    public boolean completada = false;
    private float anguloBrillo = 0; // Para una pequeña animación

    public Tarea(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    /**
     * Lógica para progresar en la tarea
     * @param px Posición X del jugador
     * @param py Posición Y del jugador
     * @param estaQuieto Si el jugador no está presionando teclas de movimiento
     */
    public void actualizar(int px, int py, boolean estaQuieto) {
        if (completada) return;

        // Calcular distancia al punto de la tarea
        double dist = Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));

        // Solo progresa si está cerca (radio de 40px) y NO se mueve
        if (dist < 40 && estaQuieto) {
            if (progreso < 100) {
                progreso++;
            } else {
                completada = true;
            }
        } else {
            // Si se aleja o se mueve, el progreso se pierde (estilo Among Us)
            progreso = 0;
        }
        
        // Animación del brillo
        anguloBrillo += 0.1f;
    }

    /**
     * Dibuja el punto de interés en el mapa
     */
    public void dibujar(Graphics2D g2) {
        if (completada) {
            // Si está lista, dibujamos un check verde o algo discreto
            g2.setColor(new Color(0, 255, 0, 150));
            g2.fillOval(x - 10, y - 10, 20, 20);
            return;
        }

        // 1. Aura exterior brillante (parpadeante)
        int alpha = (int) (100 + 50 * Math.sin(anguloBrillo));
        g2.setColor(new Color(255, 255, 0, alpha));
        g2.fillOval(x - 25, y - 25, 50, 50);

        // 2. Círculo central (Icono de tarea)
        g2.setColor(Color.YELLOW);
        g2.fillOval(x - 15, y - 15, 30, 30);
        
        // 3. Símbolo de admiración o borde
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x - 15, y - 15, 30, 30);
        
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.drawString("!", x - 2, y + 4);
    }
}