package GameObjects.PowerUps;
import GameObjects.GameWorld;
import GameObjects.Tank;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class ExtraLife extends PowerUp {

    public ExtraLife(int x, int y) throws IOException {
        super(x, y, read(GameWorld.class.getClassLoader().getResource("resources/extraLife.png")));
    }

    public void applyEffect(Tank t){

            t.setHealth(t.getHealth() + 10);
    }

}
