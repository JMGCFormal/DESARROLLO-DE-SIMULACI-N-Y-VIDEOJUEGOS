package comportamientos;

import elementos.Entidad;
import ventanas.Fase;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InteraccionMouse extends MouseAdapter {
    public static final int ROTATION_SPEED = 30;
    private static final int SPEED = 4;
    private final Fase fase;
    private double angleInRadians;
    private ScheduledExecutorService executor;

    public InteraccionMouse(Fase fase) {
        this.fase = fase;
    }

    private void createBullet() {
        fase.setBullet(new Entidad("sprites/bullet.png", 400 - (19 / 2), 300 - (19 / 2), Entidad.IMAGE));
        fase.bullets.get(fase.bullets.size()-1).setMovementAngle(angleInRadians);
    }

    public void stopRotationMovement() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    private void initializeMovement() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (!fase.bullets.isEmpty()) {
                Iterator<Entidad> iterator = fase.bullets.iterator();
                while (iterator.hasNext()) {
                    Entidad bullets = iterator.next();
                    bullets.setRotationAngle(bullets.getRotationAngle() + ROTATION_SPEED);
                    double deltaX = SPEED * Math.cos(bullets.getMovementAngle());
                    double deltaY = SPEED * Math.sin(bullets.getMovementAngle());
                    int newX = (int) (bullets.getXPosition() + deltaX);
                    int newY = (int) (bullets.getYPosition() + deltaY);
                    if (newX <= 0 || newX >= 800 || newY <= 0 || newY >= 600) {
                        stopRotationMovement();
                        iterator.remove();
                    } else {
                        bullets.setXPosicion(newX);
                        bullets.setYPosicion(newY);
                    }
                }
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        int mouseX = e.getX();
        int mouseY = e.getY();
        int imageCenterX = fase.mainCharacter.getXPosition() + fase.mainCharacter.image.getWidth() / 2;
        int imageCenterY = fase.mainCharacter.getYPosition() + fase.mainCharacter.image.getHeight() / 2;
        int dx = mouseX - imageCenterX;
        int dy = mouseY - imageCenterY;
        int rotationAngle = (int) Math.toDegrees(Math.atan2(dy, dx));
        fase.mainCharacter.setRotationAngle(rotationAngle);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        double frameCenterX = (double) fase.getWidth() / 2;
        double frameCenterY = (double) fase.getTopLevelAncestor().getHeight() / 2;
        double mouseX = e.getX();
        double mouseY = e.getY();
        angleInRadians = Math.atan2(mouseY - frameCenterY, mouseX - frameCenterX);
        createBullet();
        initializeMovement();
    }
}
