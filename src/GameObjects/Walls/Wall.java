package GameObjects.Walls;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Wall {
    private BufferedImage wallImg;
    private int xPosition, yPosition;

    public Wall(int xPosition, int yPosition, BufferedImage wallImg) throws IOException {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.wallImg = wallImg;
    }
    public void drawImage(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.wallImg,this.xPosition,this.yPosition,null);
    }
    public int getX() {
        return this.xPosition;
    }
    public int getY() {
        return this.yPosition;
    }


}
