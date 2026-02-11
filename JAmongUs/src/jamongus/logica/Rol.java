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

public abstract class Rol {
    protected String nombre;
    protected Color colorHUD;

    public Rol(String nombre, Color color) {
        this.nombre = nombre;
        this.colorHUD = color;
    }

    // Métodos que cada rol implementará a su manera
    public abstract void dibujarHabilidades(Graphics2D g2);
    public abstract String getInstrucciones();
    
    public String getNombre() { return nombre; }
    public Color getColorHUD() { return colorHUD; }
}
