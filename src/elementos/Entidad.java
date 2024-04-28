package elementos;

import fisicas.Hitbox;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entidad {
    public BufferedImage image;
    public Hitbox hitbox;
    private int xPosition;
    private int yPosition;
    private final int ejeRotation;
    private int rotationAngle = 0;
    private int score = 0;
    public static final int
            CENTER=2,
            IMAGE=1;
    private double angleInRadians;

    public Entidad(String imagePath, int xPosition, int yPosition, int ejeRotation) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ejeRotation = ejeRotation;
        try {
            String directorioRaiz = System.getProperty("user.dir");
            image = ImageIO.read(new File(directorioRaiz + "/assets/" + imagePath));
            hitbox = new Hitbox(xPosition, yPosition, image.getWidth(), image.getHeight());
            Path2D path = new Path2D.Double();
            path.moveTo(xPosition, yPosition);
            path.lineTo(xPosition + image.getWidth(), yPosition);
            path.lineTo(xPosition + image.getWidth(), yPosition + image.getHeight());
            path.closePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void updatePath() {
//        path.reset();
//        path.moveTo(xPosition, yPosition);
//        path.lineTo(xPosition + image.getWidth(), yPosition);
//        path.lineTo(xPosition + image.getWidth(), yPosition + image.getHeight());
//        path.closePath();
//    }

//    public void drawHitbox(Graphics2D g2d) {
//        g2d.setColor(Color.GREEN);
//        g2d.drawRect(hitbox.getXPosition(), hitbox.getYPosition(), hitbox.getWidth(), hitbox.getHeight());
//    }

    public void draw(Graphics2D g2d) {
        AffineTransform originalTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        if (this.ejeRotation == 1) {
            transform.rotate(Math.toRadians(rotationAngle), xPosition + image.getWidth() / 2.0, yPosition + image.getHeight() / 2.0);
        } else {
            transform.rotate(Math.toRadians(rotationAngle), 400, 300);
        }
        AffineTransform savedTransform = g2d.getTransform();
        g2d.transform(transform);
        g2d.drawImage(image, xPosition, yPosition, null);
        g2d.setTransform(savedTransform);
        //drawHitbox(g2d);
        g2d.setTransform(originalTransform);
    }
    public void setMovementAngle(double angleInRadians) {
        this.angleInRadians = angleInRadians;
    }

    public double getMovementAngle() {
        return this.angleInRadians;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setXPosicion(int i) {
        this.xPosition = i;
        this.hitbox.setXPosition(i);
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public void setYPosicion(int i) {
        this.yPosition = i;
        this.hitbox.setYPosition(i);
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public int getRotationAngle() {
        return this.rotationAngle;
    }
}
