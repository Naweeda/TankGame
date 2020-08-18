package GameObjects.Walls;

import GameObjects.GameWorld;

import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class UnBreakWall extends Wall{

    public UnBreakWall(int xPosition, int yPosition) throws IOException {
        super(xPosition, yPosition,
                read(GameWorld.class.getClassLoader().getResource("resources/unBreakWall.png"))
        );
    }


}
