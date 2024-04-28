package elementos;

import ventanas.Fase;

public class Shield {
    private final String imagePath = "sprites/shield.png";
    private final int xPosition = 369;
    private final int yPosition = 370;

    public Entidad
            shi1 = new Entidad(imagePath, xPosition, yPosition, Entidad.CENTER),
            shi2 = new Entidad(imagePath, xPosition, yPosition, Entidad.CENTER),
            shi3 = new Entidad(imagePath, xPosition, yPosition, Entidad.CENTER),
            shi4 = new Entidad(imagePath, xPosition, yPosition, Entidad.CENTER);

    public Shield(Fase fase){
        shi1.setRotationAngle(0);
        int radio = 93;
        shi1.hitbox.setCircufetence(
                radio,
                90
        );
        fase.setShield(shi1);
        shi2.setRotationAngle(90);
        shi2.hitbox.setCircufetence(
                radio,
                180
        );
        fase.setShield(shi2);
        shi3.setRotationAngle(180);
        shi3.hitbox.setCircufetence(
                radio,
                270
        );
        fase.setShield(shi3);
        shi4.setRotationAngle(270);
        shi4.hitbox.setCircufetence(
                radio,
                0
        );
        fase.setShield(shi4);
    }
}
