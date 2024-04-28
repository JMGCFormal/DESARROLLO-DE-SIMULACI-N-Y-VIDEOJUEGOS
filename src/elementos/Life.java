package elementos;

import ventanas.Fase;

public class Life {
    public Life (Fase fase, int num){
        Entidad life1 = new Entidad("sprites/ring1.png", 370, 270, Entidad.IMAGE);
        Entidad life2 = new Entidad("sprites/ring2.png", 365, 265, Entidad.IMAGE);
        Entidad life3 = new Entidad("sprites/ring3.png", 362, 262, Entidad.IMAGE);
        switch (num){
            case 0:
                fase.setLifes(life1);
                break;
            case 1:
                fase.setLifes(life2);
                break;
            case 2:
                fase.setLifes(life3);
                break;
        }
    }
}
