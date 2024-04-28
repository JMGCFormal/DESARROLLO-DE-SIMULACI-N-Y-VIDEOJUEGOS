package ventanas;

import comportamientos.InteraccionMouse;
import comportamientos.InteracionTeclado;
import elementos.Enemi;
import elementos.Entidad;
import elementos.Life;
import elementos.Shield;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fase extends JPanel {
    public Entidad mainCharacter = new Entidad("sprites/impacto.png", 400-19, 300-19, Entidad.IMAGE);
    public List<Entidad> enemis = new ArrayList<>();
    public List<Entidad> bullets = new ArrayList<>();
    public List<Entidad> shields = new ArrayList<>();
    public List<Entidad> lifes = new ArrayList<>();
    public Enemi holeada;
    public int life, fsx;
    public int score = 0;

    public Fase(){
        this.setOpaque(false);
        this.addMouseListener(new InteraccionMouse(this));
        this.addMouseMotionListener(new InteraccionMouse(this));
        this.addKeyListener(new InteracionTeclado(this));
        starLife();
        holeada = new Enemi(this, fsx);
        new Shield(this);
        verificarColciones();
    }

    private void verificarColciones(){
        Timer timer = new Timer(10, e -> {
            verificarColisionesShield();
            verificarColisionesBullet();
        });
        timer.start();
    }

    private void verificarColisionesShield() {
        Iterator<Entidad> enemisIterator = enemis.iterator();
        while (enemisIterator.hasNext()) {
            Entidad enemi = enemisIterator.next();
            for (int j = shields.size() - 1; j >= 0; j--) {
                if (enemi.hitbox.intersects(shields.get(j).hitbox)) {
                    score += enemi.getScore();
                    enemisIterator.remove();
                    break;
                }
            }
            if (enemi.hitbox.intersects(mainCharacter.hitbox)) {
                enemisIterator.remove();
                removeLifes(lifes.get(lifes.size() - 1));
                life--;
            }
        }
    }

    private void verificarColisionesBullet() {
        Iterator<Entidad> enemisIterator = enemis.iterator();
        Iterator<Entidad> bulletsIterator = bullets.iterator();
        while (enemisIterator.hasNext()) {
            Entidad enemi = enemisIterator.next();
            while (bulletsIterator.hasNext()) {
                Entidad bullet = bulletsIterator.next();
                if (enemi.hitbox.intersects(bullet.hitbox)) {
                    score += enemi.getScore();
                    enemisIterator.remove();
                    bulletsIterator.remove();
                    break;
                }
            }
            if (enemi.hitbox.intersects(mainCharacter.hitbox)) {
                enemisIterator.remove();
                removeLifes(lifes.get(lifes.size() - 1));
                life--;
            }
        }
    }

    public void starLife(){
        for (int i = lifes.size(); i <= 2; i ++){
            new Life(this, i);
            life++;
        }
        Timer timer = new Timer(50, e -> {
            if (life == 3){
                lifes.get(0).setRotationAngle(lifes.get(0).getRotationAngle() + 1);
                lifes.get(1).setRotationAngle(lifes.get(1).getRotationAngle() - 1);
                lifes.get(2).setRotationAngle(lifes.get(2).getRotationAngle() + 1);
            }else if (life == 2){
                lifes.get(1).setRotationAngle(lifes.get(1).getRotationAngle() - 1);
                lifes.get(0).setRotationAngle(lifes.get(0).getRotationAngle() + 1);
            }else if (life == 1){
                lifes.get(0).setRotationAngle(lifes.get(0).getRotationAngle() + 1);
            }
        });
        timer.start();
    }

    public void setBullet(Entidad entidad) {
        bullets.add(entidad);
    }

    public void setEnemi(Entidad entidad){
        enemis.add(entidad);
    }

    public void setShield(Entidad entidad){
        shields.add(entidad);
    }

    public void setLifes(Entidad entidad){
        lifes.add(entidad);
    }

    public void removeLifes(Entidad entidad){
        lifes.remove(entidad);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        mainCharacter.draw(g2d);
        for (Entidad shields : shields){
            shields.draw(g2d);
        }
        for (Entidad bullets : bullets) {
            bullets.draw(g2d);
        }
        for (Entidad enemis : enemis){
            enemis.draw(g2d);
        }
        for (Entidad lifes : lifes){
            lifes.draw(g2d);
        }
        g2d.dispose();
    }
}
