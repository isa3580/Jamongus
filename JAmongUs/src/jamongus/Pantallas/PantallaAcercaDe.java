/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

/**
 *
 * @author 9isab
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PantallaAcercaDe extends JPanel {

    public PantallaAcercaDe(ActionListener volverAlMenu) {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(new Color(40, 20, 20)); // Rojo oscuro
        this.setLayout(new GridBagLayout()); // Centra todo automáticamente

        JLabel info = new JLabel("<html><div style='text-align: center;'>"
            + "<h1>ACERCA DE</h1>"
            + "<p>Versión 1.0 - Java Swing Edition</p>"
            + "<p>Inspirado en InnerSloth</p>"
            + "</div></html>");
        info.setForeground(Color.WHITE);
        
        JButton botonVolver = new JButton("ENTENDIDO");
        botonVolver.addActionListener(volverAlMenu);

        add(info);
        add(botonVolver);
    }
}
