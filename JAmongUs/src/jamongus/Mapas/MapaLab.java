/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Mapas;

/**
 *
 * @author 9isab
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class MapaLab extends Mapas {
    public MapaLab() {
        super(); // Llama al constructor de Mapas para inicializar la lista de paredes
        cargarRecursos();
    }

    @Override
    public void cargarRecursos() {
        this.fondo = new ImageIcon("assets/MapaLab.png").getImage();
        // Definimos las paredes de este mapa específico
        paredes.add(new Rectangle(0, 0, 800, 50));   // Pared superior
        paredes.add(new Rectangle(100, 100, 50, 50)); // Una mesa de ejemplo
    }
}