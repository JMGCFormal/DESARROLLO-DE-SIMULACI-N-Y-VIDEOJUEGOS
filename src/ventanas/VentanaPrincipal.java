package ventanas;

import javax.swing.*;
public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal(){
        this.setSize(800, 600);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SextoSol");
        this.setLocationRelativeTo(null);

        PanelJuego panelJuego = new PanelJuego();
        this.getContentPane().add(panelJuego);
    }
}
