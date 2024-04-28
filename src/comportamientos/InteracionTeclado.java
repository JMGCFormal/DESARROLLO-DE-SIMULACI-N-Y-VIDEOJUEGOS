package comportamientos;

import elementos.Entidad;
import ventanas.Fase;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InteracionTeclado extends KeyAdapter {
    public static int ROTATION_SPEED = 100;
    private final Fase fase;
    private final int radio = 93;

    public InteracionTeclado(Fase fase) {
        this.fase = fase;
        createShield();
    }

    private void createShield() {
        fase.setFocusable(true);
        fase.requestFocusInWindow();
    }

    public void incrementAngle() {
        for (Entidad shields : fase.shields) {
            shields.setRotationAngle(shields.getRotationAngle() + ROTATION_SPEED);
            shields.hitbox.setCircufetence(radio, shields.hitbox.getAngle() + ROTATION_SPEED);
        }
    }

    public void decrementAngle() {
        for (Entidad shields : fase.shields) {
            shields.setRotationAngle(shields.getRotationAngle() - ROTATION_SPEED);
            shields.hitbox.setCircufetence(radio, shields.hitbox.getAngle() - ROTATION_SPEED);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT) {
            incrementAngle();
        } else if (keyCode == KeyEvent.VK_LEFT) {
            decrementAngle();
        }
    }
}
