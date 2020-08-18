package GameObjects;
import GameObjects.PowerUps.PowerUp;
import GameObjects.Walls.BreakWall;
import GameObjects.Walls.UnBreakWall;
import GameObjects.Walls.Wall;
import TankGameCore.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;


public class Tank extends MovableObjects{


    private int R = 3;
    private final float ROTATIONSPEED = 4;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private int health = 100;
    private int lives = 2;
    private Bullet bullet;
    private String identity;



    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, String identity) {
        super(x, y, vx, vy, angle, img);
        this.bullet = null;
        this.identity = identity;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    public void update() throws IOException {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && GameWorld.tickCount % 20 ==0)
        {
            if(this.bullet == null){
                this.bullet = new Bullet(this.getX(), this.getY(), this.getAngle(), "t2".equals(this.getIdentity()) ? "t1" : "t2");
            }
        }
        checkBorder();
        checkCollision();
        if(this.bullet != null && this.bullet.stillAlive()){
            this.bullet.update();
        } else {
            this.bullet = null;
        }
    }


    private void rotateLeft() {
        this.setAngle(this.getAngle() - this.ROTATIONSPEED);
    }

    public String getIdentity(){
        return this.identity;
    }

    private void rotateRight() {
        this.setAngle(this.getAngle() + this.ROTATIONSPEED);
    }

    private void moveBackwards() {
        this.setVx((int) Math.round(R * Math.cos(Math.toRadians(angle))));
        this.setVy((int) Math.round(R * Math.sin(Math.toRadians(angle))));
        if(checkWalls(this.getX() - this.getVx(), this.getY() - this.getVy())) {
            this.setX(this.getX() - this.getVx());
            this.setY(this.getY() - this.getVy());
            this.bounds.setLocation(this.getX(), this.getY());
        }
    }

    private void moveForwards() {
        this.setVx((int) Math.round(R * Math.cos(Math.toRadians(angle))));
        this.setVy((int) Math.round(R * Math.sin(Math.toRadians(angle))));
        if(checkWalls(this.getX() + this.getVx(), this.getY() + this.getVy())) {
            this.setX(this.getX() + this.getVx());
            this.setY(this.getY() + this.getVy());
            this.bounds.setLocation(this.getX(), this.getY());
        }
    }


   //Check Collisions for the walls
    public boolean checkWalls(int new_x, int new_y){
        for(Wall w: GameWorld.walls) {
            if(new_x - 40 < w.getX() && w.getX() < new_x + 40
                    && new_y - 40 < w.getY() && w.getY() < new_y + 40) {
                if (w instanceof Wall) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setR(int _R){
        R = _R;
    }

    public int getR(){
      return R;
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }



    public int getHealth() {
        return health;
    }

    public void setHealth(int _health) {health = _health;}


    //Check collisions for the power up
    @Override
    public void checkCollision()
    {
        ArrayList<PowerUp> toRemove = new ArrayList<>();
        for(PowerUp p: GameWorld.powerUps){
            if(this.getX() - 40 < p.getX() && p.getX() < this.getX() + 40
                    && this.getY() - 40 < p.getY() && p.getY() < this.getY() + 40) {
                p.applyEffect(this);
                toRemove.add(p);
            }
        }
        for(PowerUp p: toRemove){
            GameWorld.powerUps.remove(p);
        }
    }

    @Override
    public String toString() {
        return "x=" + this.getX() + ", y=" + this.getY() + ", angle=" + this.getAngle();
    }

    @Override
    public void drawImage(Graphics g) {
        super.drawImage(g);
        if(this.bullet != null){
            this.bullet.drawImage(g);

        }
    }



}