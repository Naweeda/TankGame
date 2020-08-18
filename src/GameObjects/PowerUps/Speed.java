package GameObjects.PowerUps;
import GameObjects.GameWorld;
import GameObjects.Tank;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Speed extends PowerUp {

    public Speed(int x, int y) throws IOException {
        super(x, y, read(GameWorld.class.getClassLoader().getResource("resources/speed-boost.png")));
    }

    public void applyEffect(Tank t){
        t.setR(t.getR() + 2);
    }

}