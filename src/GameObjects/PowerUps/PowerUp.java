package GameObjects.PowerUps;
import GameObjects.Tank;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp {
    private int x;
    private int y;
    private BufferedImage img;
    public PowerUp(int x, int y, BufferedImage extraLifeImg)
    {
        this.x = x;
        this.y = y;
        this.img = extraLifeImg;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, this.x, this.y, null);
    }

    public abstract void applyEffect(Tank t);
}
