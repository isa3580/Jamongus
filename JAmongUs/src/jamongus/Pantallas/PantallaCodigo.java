/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

import jamongus.Mapas.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author 9isab
 */


public class PantallaCodigo extends JPanel {
    private JTextField txtCodigo;

    public PantallaCodigo(ActionListener accionEntrar, ActionListener volver) {
        setLayout(null);
        setBackground(new Color(10, 10, 20));

        JLabel lbl = new JLabel("INGRESA EL CÓDIGO DE LA SALA:", SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(0, 150, 800, 30);
        add(lbl);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(300, 200, 200, 50);
        txtCodigo.setFont(new Font("Monospaced", Font.BOLD, 24));
        txtCodigo.setHorizontalAlignment(JTextField.CENTER);
        add(txtCodigo);

        JButton btnEntrar = new JButton("ENTRAR AL LOBBY");
        btnEntrar.setBounds(300, 280, 200, 50);
        btnEntrar.addActionListener(accionEntrar);
        add(btnEntrar);

        JButton btnV = new JButton("CANCELAR");
        btnV.setBounds(350, 400, 100, 30);
        btnV.addActionListener(volver);
        add(btnV);
    }

    public String getCodigoIngresado() {
        return txtCodigo.getText().toUpperCase();
    }
}
