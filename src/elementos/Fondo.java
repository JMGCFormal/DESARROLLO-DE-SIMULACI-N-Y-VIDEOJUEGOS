package elementos;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class Fondo extends JPanel{
    private final Image image;

    public Fondo(String imagePath, int width, int height) {
        String directorioRaiz = System.getProperty("user.dir");
        ImageIcon imageIcon = new ImageIcon(directorioRaiz + "/assets/" + imagePath);
        this.image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_FAST);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
