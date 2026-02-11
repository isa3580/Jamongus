/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Mapas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author 9isab
 */

  public class MapaCancha extends Mapas {
    public MapaCancha() {
        super(); // Llama al constructor de Mapas para inicializar la lista de paredes
        cargarRecursos();
    }

    @Override
    public void cargarRecursos() {
        this.fondo = new ImageIcon("assets/MapaCancha.png").getImage();
        // Definimos las paredes de este mapa específico
        paredes.add(new Rectangle(0, 0, 800, 50));   // Pared superior
        paredes.add(new Rectangle(100, 100, 50, 50)); // Una mesa de ejemplo
    }
}