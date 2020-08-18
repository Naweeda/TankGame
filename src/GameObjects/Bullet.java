package GameObjects;
import GameObjects.Walls.BreakWall;
import GameObjects.Walls.UnBreakWall;
import GameObjects.Walls.Wall;

import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class Bullet extends MovableObjects {
    private Tank opposingTank;
    private int alive;
    private int bulletLive = 400;


    public Bullet(int x, int y, float angle, String opposingTank) throws IOException {
        super(x, y, (int) Math.round(3 * Math.cos(Math.toRadians(angle))),
                (int) Math.round(3 * Math.sin(Math.toRadians(angle))), angle,
                read(GameWorld.class.getClassLoader().getResource("resources/Weapon.gif")));
        this.opposingTank = GameWorld.tanks.get(opposingTank);
        this.alive = bulletLive;
    }

    public boolean stillAlive(){
        this.alive--;
        return this.alive>=0;
    }

    //Check Collision For the breakable wall
    public void checkCollision()
    {
        ArrayList<BreakWall> breakWalls = new ArrayList<>();
        for(Wall bw: GameWorld.walls) {
            //Checking collision in direction of x and y
            if(this.getX() - 40 < bw.getX() && bw.getX() < this.getX() + 40
                    && this.getY() - 40 < bw.getY() && bw.getY() < this.getY() + 40) {
                //Accessing the BreakWall class
                if (bw instanceof BreakWall) {
                    breakWalls.add((BreakWall) bw);
                }
            }
        }
        for (BreakWall bw: breakWalls){
            GameWorld.walls.remove(bw);
        }

    }

    public void update()
    {
        this.setVx( (int) Math.round(3 * Math.cos(Math.toRadians(angle))));
        this.setVy((int) Math.round(3 * Math.sin(Math.toRadians(angle))));
        this.setX(this.getX() + this.getVx());
        this.setY(this.getY() + this.getVy());
        this.checkCollision();
        if(x - 40 < this.opposingTank.getX() && this.opposingTank.getX() < x + 40
               && y - 40 < this.opposingTank.getY() && this.opposingTank.getY() < y + 40) {
            this.opposingTank.setHealth(this.opposingTank.getHealth() -15);
            this.alive = 0;
        }

    }

}


