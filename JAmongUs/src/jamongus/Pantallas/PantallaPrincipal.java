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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import jamongus.logica.Reproductor;



public class PantallaPrincipal extends JPanel {
    private Image imagenFondo;

    public PantallaPrincipal(ActionListener irLobby, ActionListener irAyuda, ActionListener irAcercaDe) {
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(null);

        // 1. Cargar imagen de fondo
        this.imagenFondo = new ImageIcon("assets/introAmongUs.png").getImage();

        // 2. Crear y Estilizar Botones
        JButton btnJugar = new JButton("EN LINEA");
        btnJugar.setBounds(250, 220, 300, 60);
        estilizarBoton(btnJugar, new Color(46, 204, 113)); // Verde Among Us
        btnJugar.addActionListener(irLobby);
        add(btnJugar);

        JButton btnAyuda = new JButton("COMO JUGAR");
        btnAyuda.setBounds(250, 300, 300, 60);
        estilizarBoton(btnAyuda, new Color(52, 152, 219)); // Azul
        btnAyuda.addActionListener(irAyuda);
        add(btnAyuda);

        JButton btnAcerca = new JButton("ACERCA DE");
        btnAcerca.setBounds(250, 380, 300, 60);
        estilizarBoton(btnAcerca, new Color(155, 89, 182)); // Morado
        btnAcerca.addActionListener(irAcercaDe);
        add(btnAcerca);

        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(250, 460, 300, 60);
        estilizarBoton(btnSalir, new Color(231, 76, 60)); // Rojo
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    private void estilizarBoton(JButton btn, Color colorBase) {
        btn.setFont(new Font("Arial Black", Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorBase);
        
        // Quitar el estilo feo por defecto de Java
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(Color.WHITE, 4, true)); // Borde blanco grueso redondeado
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto "Hover" (brillo al pasar el mouse)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorBase.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorBase);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dibujar fondo
        if (imagenFondo != null) {
            g2.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}