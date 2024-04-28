package ventanas;

import elementos.Enemi;
import elementos.Fondo;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PanelJuego extends JLayeredPane {
    private final JLabel scoreJlab = new JLabel();
    private final Timer timer;
    private final Fase fase;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int fsx = 0;
    private int scoreInt;
    private Fondo fondo;

    public PanelJuego() {
        this.setLayout(new OverlayLayout(this));
        String imagPath1 = "screen/fase1.gif";
        fondo = new Fondo(imagPath1, 800, 600);
        this.add(fondo, Integer.valueOf(0));
        fase = new Fase();
        this.add(fase, Integer.valueOf(1));
        JPanel interfaz = new JPanel();
        interfaz.setOpaque(false);
        interfaz.setLayout(null);
        this.add(interfaz, Integer.valueOf(2));
        this.scoreJlab.setBounds(385,0,150,30);
        interfaz.add(scoreJlab);
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreJlab.setText(String.format("%04d", fase.score));
                if (fase.score >= 200 && fsx == 0){
                    scoreInt = fase.score;
                    fsx ++;
                    changeBackground(fsx);
                    timer.stop();
                }if (fase.score >= 500 && fsx == 1){
                    scoreInt = fase.score;
                    fsx ++;
                    changeBackground(fsx);
                    timer.stop();
                }
            }
        }); timer.start();
        executorService.scheduleAtFixedRate(() ->{
            if (!timer.isRunning()){
                System.gc();
                fase.holeada = new Enemi(fase,fsx);
                fase.starLife();
                timer.start();
            }
            if (fase.life == 0){
                scoreInt = fase.score;
                fsx = 3;
                changeBackground(fsx);
                this.remove(fase);
                System.gc();
                JOptionPane.showMessageDialog(null,
                        "your score: " + String.format("%04d", scoreInt),
                        "GAME OVER",
                        JOptionPane.PLAIN_MESSAGE);
                executorService.shutdown();
            }
        },0, 1000, TimeUnit.MILLISECONDS);
    }

    private void changeBackground(int fsx){
        this.remove(fondo);
        String imagPath2 = "screen/fase2.gif";
        String imagPath3 = "screen/fase3.gif";
        String imagPath4 = "screen/error.gif";
        switch (fsx){
            case 1:
                fondo = new Fondo(imagPath2, 800, 600);
                this.add(fondo, Integer.valueOf(0));
                break;
            case 2:
                fondo = new Fondo(imagPath3, 800, 600);
                this.add(fondo, Integer.valueOf(0));
                break;
            case 3:
                fondo = new Fondo(imagPath4, 800, 600);
                this.add(fondo, Integer.valueOf(0));
                break;
        }
    }
}
