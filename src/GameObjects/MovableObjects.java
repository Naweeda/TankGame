package GameObjects;

import GameObjects.Walls.BreakWall;
import GameObjects.Walls.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class MovableObjects {

    int x;
    int y;
    int vx;
    int vy;
    float angle;
    BufferedImage img;
    Rectangle bounds;

    public MovableObjects(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.img = img;
        this.bounds = new Rectangle(x,y, img.getWidth(), img.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int _x) {x = _x;}

    public void setY(int _y) {y = _y;}

    public int getVx(){
        return vx;
    }

    public int getVy(){
        return vy;
    }

    public void setVx(int _vx){
        vx = _vx;
    }

    public void setVy(int _vy){
        vy = _vy;
    }

    public float getAngle(){
        return angle;
    }

    public void setAngle(float an){
        this.angle = an;
    }

    public abstract void checkCollision();

    public void drawImage(Graphics g){
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    public abstract void update() throws IOException;
}
