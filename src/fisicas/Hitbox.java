package fisicas;
public class Hitbox {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private double angle;

    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Hitbox other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    public int getXPosition() {
        return x;
    }

    public int getYPosition() {
        return y;
    }

    public void setXPosition(int x) {
        this.x = x;
    }

    public void setYPosition(int y) {
        this.y = y;
    }

    public double getAngle(){
        return this.angle;
    }

    public void setCircufetence(int radio, double angle){
        this.angle = angle;
        this.x = (int) (400 + radio * Math.cos(Math.toRadians(this.angle)) - this.width / 2);
        this.y = (int) (300 + radio * Math.sin(Math.toRadians(this.angle)) - this.height / 2);
    }
}