/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author 9isab
 */



public class PantallaPreLobby extends JPanel {
    public PantallaPreLobby(ActionListener crear, ActionListener unirse, ActionListener volver) {
        setLayout(null);
        setBackground(new Color(15, 15, 30));

        JButton btnCrear = new JButton("CREAR PARTIDA");
        btnCrear.setBounds(250, 200, 300, 60);
        btnCrear.addActionListener(crear);
        add(btnCrear);

        JButton btnUnirse = new JButton("UNIRSE A PARTIDA");
        btnUnirse.setBounds(250, 300, 300, 60);
        btnUnirse.addActionListener(unirse);
        add(btnUnirse);

        JButton btnVolver = new JButton("VOLVER");
        btnVolver.setBounds(350, 450, 100, 40);
        btnVolver.addActionListener(volver);
        add(btnVolver);
    }
}