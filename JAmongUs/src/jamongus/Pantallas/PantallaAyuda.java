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

public class PantallaAyuda extends JPanel {

    public PantallaAyuda(ActionListener volverAlMenu) {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(new Color(20, 20, 40)); // Azul oscuro
        this.setLayout(null);

        JLabel titulo = new JLabel("GUÍA DE TRIPULANTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 50, 800, 50);
        add(titulo);

        JTextArea texto = new JTextArea("1. Muévete con WASD.\n2. Busca el cuadro amarillo.\n3. Presiona ESPACIO para reparar.");
        texto.setFont(new Font("Arial", Font.PLAIN, 20));
        texto.setForeground(Color.LIGHT_GRAY);
        texto.setBackground(null);
        texto.setEditable(false);
        texto.setBounds(200, 150, 400, 200);
        add(texto);

        JButton botonVolver = new JButton("VOLVER");
        botonVolver.setBounds(350, 450, 100, 40);
        botonVolver.addActionListener(volverAlMenu); // Usamos el evento que viene del Main
        add(botonVolver);
    }
}