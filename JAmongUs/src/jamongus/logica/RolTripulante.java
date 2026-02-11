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

public class RolTripulante extends Rol {
    public RolTripulante() {
        super("TRIPULANTE", Color.CYAN);
    }

    @Override
    public void dibujarHabilidades(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString("Tareas pendientes: 4", 20, 50);
    }

    @Override
    public String getInstrucciones() {
        return "Completa tus tareas para ganar.";
    }
}