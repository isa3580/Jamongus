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

public class RolImpostor extends Rol {
    public RolImpostor() {
        super("IMPOSTOR", Color.RED);
    }

    @Override
    public void dibujarHabilidades(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.drawString("[K] Matar - [C] Congelar - [E] Ducto", 20, 50);
    }

    @Override
    public String getInstrucciones() {
        return "Elimina a la tripulación sin ser visto.";
    }
}
