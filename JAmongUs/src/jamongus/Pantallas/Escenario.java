/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jamongus.Pantallas;

/**
 *
 * @author 9isab
 */
import jamongus.JAmongUs;
import jamongus.Jugador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jamongus.Mapas.MapaLab;
import jamongus.Mapas.Mapas;
import jamongus.Servidor.Cliente;
import jamongus.logica.Ducto;
import jamongus.logica.Rol;
import jamongus.logica.RolImpostor;
import jamongus.logica.RolTripulante;
import jamongus.logica.Tarea;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;



public class Escenario extends JPanel implements ActionListener, KeyListener {
    // --- LÓGICA Y RED ---
    private Mapas mapaActual;
    private Jugador miJugador;
    private String miID = "P" + (int)(Math.random() * 1000);
    private Cliente clienteRed;
    private ConcurrentHashMap<String, Jugador> otrosJugadores = new ConcurrentHashMap<>();
    private Timer gameLoop;
    
    // --- ESTADOS ---
    private boolean w, a, s, d, vivo = true, estoyCongelado = false;
    private Rol miRol;
    private boolean juegoTerminado = false;

    // --- ELEMENTOS DEL MAPA ---
    private ArrayList<Tarea> listaTareas = new ArrayList<>();
    private ArrayList<Ducto> listaDuctos = new ArrayList<>();

    public Escenario(Mapas m) {
        this.mapaActual = m;
        // Asumiendo tu constructor original (x, y, sprite)
        this.miJugador = new Jugador(512, 512, "rojo.png");
        this.setFocusable(true);
        this.addKeyListener(this);

        // --- CARGAR TAREAS INICIALES ---
        listaTareas.add(new Tarea("Motores", 300, 300));
        listaTareas.add(new Tarea("Oxigeno", 700, 500));
        listaTareas.add(new Tarea("Navegacion", 800, 200));

        // --- CARGAR DUCTOS INICIALES ---
        Ducto d1 = new Ducto(400, 400);
        Ducto d2 = new Ducto(900, 300);
        d1.conectarCon(d2); d2.conectarCon(d1);
        listaDuctos.add(d1); listaDuctos.add(d2);

        this.gameLoop = new Timer(16, this);
        this.gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!vivo || juegoTerminado) return;

        int vx = 0, vy = 0;
        if (!estoyCongelado) {
            if (w) vy = -5; if (s) vy = 5; if (a) vx = -5; if (d) vx = 5;
        }

        // Colisiones
        if (!mapaActual.hayColision(miJugador.x + vx, miJugador.y)) miJugador.x += vx;
        if (!mapaActual.hayColision(miJugador.x, miJugador.y + vy)) miJugador.y += vy;
        
        miJugador.actualizar(vx, vy);

        // Lógica de Tareas para Tripulantes
        if (miRol instanceof RolTripulante) {
            for (Tarea t : listaTareas) {
                t.actualizar(miJugador.x, miJugador.y, (vx == 0 && vy == 0));
            }
        }

        // ENVIAR DATOS (Usando tus nombres largos de Jugador)
        if (clienteRed != null) {
            clienteRed.enviarDatos(miID + "," + miJugador.x + "," + miJugador.y + "," + 
            miJugador.getFilaActual() + "," + miJugador.getColActual() + "," + 
            miJugador.getNombreSprite() + "," + (estoyCongelado ? 1 : 0));
        }
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. GUARDAR EL ESTADO LIMPIO (Antes de la cámara)
        AffineTransform transformacionLimpia = g2.getTransform();

        // 2. APLICAR CÁMARA (Mover el mundo)
        g2.translate(400 - miJugador.x * 2, 300 - miJugador.y * 2);
        g2.scale(2, 2);
        
        if (mapaActual != null) mapaActual.dibujar(g2);
        for (Ducto d : listaDuctos) d.dibujar(g2);
        
        if (miRol instanceof RolTripulante) {
            for (Tarea t : listaTareas) t.dibujar(g2);
        }

        for (Jugador j : otrosJugadores.values()) j.dibujar(g2);
        if (vivo) miJugador.dibujar(g2);

        // 3. RESETEAR CÁMARA PARA EL HUD (Texto fijo)
        g2.setTransform(transformacionLimpia);
        
        dibujarHUD(g2);
    }

    private void dibujarHUD(Graphics2D g2) {
        if (miRol == null) return;

        // Fondo para el HUD
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(15, 15, 260, 140, 15, 15);

        g2.setFont(new Font("Arial", Font.BOLD, 18));
        
        if (miRol instanceof RolImpostor) {
            g2.setColor(Color.RED);
            g2.drawString("ROL: IMPOSTOR", 30, 45);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.setColor(Color.WHITE);
            g2.drawString("[C] Congelar  |  [E] Ducto", 30, 75);
            if (estoyCongelado) {
                g2.setColor(Color.CYAN);
                g2.drawString("ESTADO: CONGELADO", 30, 100);
            }
        } else {
            g2.setColor(new Color(0, 255, 255));
            g2.drawString("ROL: TRIPULANTE", 30, 45);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(Color.WHITE);
            g2.drawString("TAREAS:", 30, 75);
            
            int y = 95;
            for (Tarea t : listaTareas) {
                g2.setFont(new Font("Arial", Font.PLAIN, 13));
                if (t.completada) {
                    g2.setColor(Color.GREEN);
                    g2.drawString("✔ " + t.nombre, 40, y);
                } else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.drawString("○ " + t.nombre, 40, y);
                }
                y += 20;
            }
        }
    }

    // --- MÉTODOS DE SOPORTE ---
    public boolean getSoyImpostor() { return (this.miRol instanceof RolImpostor); }
    public void setRol(boolean imp) { this.miRol = imp ? new RolImpostor() : new RolTripulante(); }
    public void setMapa(Mapas m) { this.mapaActual = m; }
    public Cliente getCliente() { return clienteRed; }
    public void setMiColorLocal(String color) { miJugador.cargarSprites(color); }

    public void conectarRed(String ip) {
        clienteRed = new Cliente(ip, this);
        clienteRed.start();
    }

    public void aplicarCongelamiento(String id) {
        if (id.equals(miID)) estoyCongelado = true;
        else if (otrosJugadores.containsKey(id)) otrosJugadores.get(id).estaCongeladoVisual = true;
    }

    public void procesarMuerte(String id) {
        if (id.equals(miID)) vivo = false;
        else otrosJugadores.remove(id);
    }

public void actualizarRemoto(String id, int x, int y, int f, int c, String s, boolean conge) {
    if (!id.equals(miID)) {
        // Si el jugador no existe, lo creamos con el sprite que viene del servidor
        if (!otrosJugadores.containsKey(id)) {
            Jugador nuevoJugador = new Jugador(x, y, s);
            otrosJugadores.put(id, nuevoJugador);
        }
        
        Jugador j = otrosJugadores.get(id);
        j.x = x; 
        j.y = y; 
        j.actualizarFrame(c, f);
        j.estaCongeladoVisual = conge;
        
        // --- CORRECCIÓN DE COLOR PARA EL HOST ---
        // Si el sprite que recibimos (s) es diferente al que tiene el objeto (j), lo cambiamos.
        if (s != null && !s.equals(j.getNombreSprite())) {
            j.cargarSprites(s); 
        }
    }
}

    private void realizarCongelar() {
        for (String id : otrosJugadores.keySet()) {
            Jugador v = otrosJugadores.get(id);
            if (Math.sqrt(Math.pow(miJugador.x - v.x, 2) + Math.pow(miJugador.y - v.y, 2)) < 80) {
                clienteRed.enviarDatos("CONGELAR:" + id);
                break;
            }
        }
    }

    private void usarDucto() {
        for (Ducto d : listaDuctos) {
            if (Math.sqrt(Math.pow(miJugador.x - d.x, 2) + Math.pow(miJugador.y - d.y, 2)) < 50) {
                miJugador.x = d.destino.x; miJugador.y = d.destino.y;
                break;
            }
        }
    }

    // --- TECLADO ---
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (miRol instanceof RolImpostor && !estoyCongelado) {
            if (k == KeyEvent.VK_C) realizarCongelar();
            if (k == KeyEvent.VK_E) usarDucto();
        }
        if (k == KeyEvent.VK_W) w = true; if (k == KeyEvent.VK_S) s = true;
        if (k == KeyEvent.VK_A) a = true; if (k == KeyEvent.VK_D) d = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) w = false; if (k == KeyEvent.VK_S) s = false;
        if (k == KeyEvent.VK_A) a = false; if (k == KeyEvent.VK_D) d = false;
    }
    @Override public void keyTyped(KeyEvent e) {}
}