/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus;

/**
 *
 * @author 9isab
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Jugador {
    public int x, y;
    public int ancho = 45, alto = 55; 
    private BufferedImage[][] animaciones; 
    private int direccion = 0, frameActual = 0, contadorFrames = 0;
    private String nombreSprite;
    
    // NUEVO: Estado para que los demás vean que estoy congelado
    public boolean estaCongeladoVisual = false;
    public boolean esImpostorRemoto = false;

    public Jugador(int x, int y, String nombreArchivo) {
        this.x = x; this.y = y;
        this.nombreSprite = nombreArchivo;
        cargarSprites(nombreArchivo);
    }

    public void setNombreSprite(String nuevo) {
        if (nuevo != null && !nuevo.equals(this.nombreSprite)) {
            this.nombreSprite = nuevo;
            cargarSprites(nuevo);
        }
    }

    public void cargarSprites(String archivo) {
    // ESTA LÍNEA ES LA QUE FALTA:
    this.nombreSprite = archivo; 

    try {
        BufferedImage hoja = ImageIO.read(new File("assets/" + archivo));
        int w = hoja.getWidth() / 3; 
        int h = hoja.getHeight() / 4; 
        animaciones = new BufferedImage[4][3];
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 3; c++) {
                animaciones[f][c] = hoja.getSubimage(c * w, f * h, w, h);
            }
        }
       // System.out.println("Sprite cargado con éxito: " + archivo); // Para que veas en consola si funciona
    } catch (Exception e) { 
        System.err.println("Error sprite: " + archivo); 
    }
}

    public void actualizar(int vx, int vy) {
        if (vx == 0 && vy == 0) { frameActual = 1; } 
        else {
            if (vy > 0) direccion = 0; else if (vy < 0) direccion = 3; 
            else if (vx < 0) direccion = 1; else if (vx > 0) direccion = 2; 
            if (++contadorFrames > 7) { frameActual = (frameActual + 1) % 3; contadorFrames = 0; }
        }
    }

    public void actualizarFrame(int col, int fila) { this.frameActual = col; this.direccion = fila; }
    public int getFilaActual() { return direccion; }
    public int getColActual() { return frameActual; }
    public String getNombreSprite() { return nombreSprite; }

    public void dibujar(Graphics2D g2) {
        if (animaciones != null) {
            g2.drawImage(animaciones[direccion][frameActual], x, y, ancho, alto, null);
            // Efecto de bloque de hielo permanente
            if (estaCongeladoVisual) {
                g2.setColor(new Color(150, 220, 255, 140));
                g2.fillRect(x, y, ancho, alto);
                g2.setColor(Color.WHITE);
                g2.drawRect(x, y, ancho, alto);
            }
        }
    }
}