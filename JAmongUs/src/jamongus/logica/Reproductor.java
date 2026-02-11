/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.logica;

/**
 *
 * @author 9isab
 */

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public class Reproductor {
    // Guardamos el clip de música aquí para poder detenerlo luego
    private static Clip musicaMenu;

    public static void iniciarMusicaMenu(String nombreArchivo) {
        try {
            if (musicaMenu != null && musicaMenu.isRunning()) return; // Evita duplicar la música

            File ruta = new File("assets/" + nombreArchivo);
            AudioInputStream ai = AudioSystem.getAudioInputStream(ruta);
            musicaMenu = AudioSystem.getClip();
            musicaMenu.open(ai);
            
            // Bajar un poco el volumen (opcional)
            FloatControl gainControl = (FloatControl) musicaMenu.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); 

            musicaMenu.loop(Clip.LOOP_CONTINUOUSLY); 
            musicaMenu.start();
        } catch (Exception e) {
            System.err.println("Error música: " + e.getMessage());
        }
    }

    public static void detenerMusicaMenu() {
        if (musicaMenu != null) {
            musicaMenu.stop();
            musicaMenu.close();
        }
    }

    // El método para efectos cortos (shhh, pasos, etc.) se queda igual
    public static void jugar(String nombreArchivo) {
        new Thread(() -> {
            try {
                File ruta = new File("assets/" + nombreArchivo);
                AudioInputStream ai = AudioSystem.getAudioInputStream(ruta);
                Clip clip = AudioSystem.getClip();
                clip.open(ai);
                clip.start();
            } catch (Exception e) {
                System.err.println("Error efecto: " + e.getMessage());
            }
        }).start();
    }
}