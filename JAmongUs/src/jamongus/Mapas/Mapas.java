/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Mapas;

/**
 *
 * @author 9isab
 */

import java.awt.*;


import java.util.ArrayList;


public abstract class Mapas {
    protected ArrayList<Rectangle> paredes;
    protected Image fondo;

    public Mapas() {
        paredes = new ArrayList<>();
        // El constructor del hijo llamará a cargarRecursos automáticamente
    }

    // Obliga a los hijos (MapaLab, MapaCancha) a cargar su imagen y sus rectángulos
    public abstract void cargarRecursos();

    public void dibujar(Graphics2D g2) {
        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, null);
        }
        
        // MODO DEBUG: Descomenta esto para ver las paredes en rojo dentro del juego
        /*
        g2.setColor(new Color(255, 0, 0, 100)); 
        for (Rectangle r : paredes) {
            g2.fill(r);
        }
        */
    }

    public boolean hayColision(int siguienteX, int siguienteY) {
        // Creamos un rectángulo en los pies del jugador (hitbox)
        Rectangle hitboxFutura = new Rectangle(siguienteX + 15, siguienteY + 35, 20, 15);
        
        for (Rectangle pared : paredes) {
            if (hitboxFutura.intersects(pared)) {
                return true; 
            }
        }
        return false;
    }
}