package elementos;

import comportamientos.EnemiComportamiento;
import ventanas.Fase;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Enemi {
    private final String enemiFs1 = "sprites/1enemigo.png";
    private final String enemiFs2 = "sprites/2enemigo.png";
    private final String enemiFs3 = "sprites/3enemigo.png";
    private final Fase fase;
    private int x, y;

    public Enemi(Fase fase, int fsx) {
        this.fase = fase;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (fase.enemis.isEmpty()){
                System.gc();
                enemiSpawn(fsx);
            }
        }, 0, 4, TimeUnit.SECONDS);
    }

    private void createEnemi(String enemiFsx) {
        Entidad enemi;
        fase.setEnemi(enemi = new Entidad(enemiFsx, x, y, Entidad.CENTER));
        new EnemiComportamiento(enemi, fase, enemiFsx);
        fase.revalidate();
    }

    private void randomSpawn() {
        Random random = new Random();
        int corner = random.nextInt(8);
        switch (corner) {
            case 0: // Arriba izquierda
                x = 0;
                y = 0;
                break;
            case 1: // Arriba centro
                x = 400;
                y = 0;
                break;
            case 2: // Arriba derecha
                x = 752;
                y = 0;
                break;
            case 3: // Medio derecha
                x = 752;
                y = 300;
                break;
            case 4: // Abajo derecha
                x = 752;
                y = 530;
                break;
            case 5: // Abajo centro
                x = 400;
                y = 530;
                break;
            case 6: // Abajo izquierda
                x = 0;
                y = 530;
                break;
            case 7: // Medio izquierda
                x = 0;
                y = 300;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + corner);
        }
    }

    private void enemiSpawn(int fsx){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int totalIterations = 4;
        AtomicInteger iterationCount = new AtomicInteger(0);
        scheduler.scheduleAtFixedRate(() -> {
            switch (fsx){
                case 0:
                    randomSpawn();
                    createEnemi(enemiFs1);
                    break;
                case 1:
                    randomSpawn();
                    createEnemi(enemiFs1);
                    createEnemi(enemiFs2);
                    break;
                case 2:
                    randomSpawn();
                    createEnemi(enemiFs1);
                    createEnemi(enemiFs2);
                    createEnemi(enemiFs3);
                    break;
            }
            int currentIteration = iterationCount.incrementAndGet();
            if (currentIteration >= totalIterations) {
                scheduler.shutdown();
            }
        }, 2, 1, TimeUnit.SECONDS);
    }
}
