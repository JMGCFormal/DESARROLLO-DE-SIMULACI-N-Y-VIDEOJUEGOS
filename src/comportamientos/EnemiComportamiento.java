package comportamientos;

import elementos.Entidad;
import ventanas.Fase;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EnemiComportamiento {
    public static final int ROTATION_SPEED = 2;
    private static final int SPEED = 2;
    private final ScheduledExecutorService executorRotation;
    private final ScheduledExecutorService executorMovement;
    private final Entidad enemi;
    private final Fase fase;

    public EnemiComportamiento(Entidad entidad, Fase fase, String enemiFsx) {
        this. enemi = entidad;
        this.fase = fase;
        executorRotation = Executors.newSingleThreadScheduledExecutor();
        executorMovement = Executors.newSingleThreadScheduledExecutor();
        Atack(enemiFsx);
    }

    private void Atack(String enemiFsx){
        String enemiFs1 = "sprites/1enemigo.png";
        String enemiFs2 = "sprites/2enemigo.png";
        String enemiFs3 = "sprites/3enemigo.png";
        if (enemiFsx.equals(enemiFs1)) {
            initializeMovement();
            enemi.setScore(10);
        } else if (enemiFsx.equals(enemiFs2)) {
            initializeRotation();
            enemi.setScore(20);
        } else if (enemiFsx.equals(enemiFs3)) {
            initializeRotation();
            enemi.setScore(30);
        }
    }

    private void initializeMovement() {
        enemi.setRotationAngle(0);
        enemi.setYPosicion(enemi.hitbox.getYPosition());
        enemi.setXPosicion(enemi.hitbox.getXPosition());
        executorMovement.scheduleAtFixedRate(() -> {
            int frameCenterX = fase.getWidth() / 2;
            int frameCenterY = fase.getHeight() / 2;
            double deltaX = frameCenterX - enemi.getXPosition();
            double deltaY = frameCenterY - enemi.getYPosition();
            double angleToCenter = Math.atan2(deltaY, deltaX);
            int moveX = (int) (SPEED * Math.cos(angleToCenter));
            int moveY = (int) (SPEED * Math.sin(angleToCenter));
            enemi.setXPosicion(enemi.getXPosition() + moveX);
            enemi.setYPosicion(enemi.getYPosition() + moveY);
        },0, 10, TimeUnit.MILLISECONDS);
    }

    private void initializeRotation() {
        int radio = getRadio();
        int totalIterations = 50;
        AtomicInteger iterationCount = new AtomicInteger(0);
        executorRotation.scheduleAtFixedRate(() -> {
            enemi.setRotationAngle(enemi.getRotationAngle() + ROTATION_SPEED);
            enemi.hitbox.setCircufetence(radio,getAngle() + ROTATION_SPEED);
            int currentIteration = iterationCount.incrementAndGet();
            if (currentIteration >= totalIterations) {
                executorRotation.shutdown();
                initializeMovement();
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    private int getRadio(){
        int deltaX = enemi.getXPosition() - 384;
        int deltaY = enemi.getYPosition() - 285;
        return Math.toIntExact((long) Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }

    private double getAngle(){
        int deltaX = enemi.hitbox.getXPosition() - 384;
        int deltaY = enemi.hitbox.getYPosition() - 285;
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }
}
