package GameObjects;

import GameObjects.PowerUps.ExtraLife;
import GameObjects.PowerUps.PowerUp;
import GameObjects.PowerUps.Speed;
import GameObjects.Walls.BreakWall;
import GameObjects.Walls.UnBreakWall;
import GameObjects.Walls.Wall;
import TankGameCore.GameConstants;
import TankGameCore.GameWindows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


import static javax.imageio.ImageIO.read;

public class GameWorld extends JPanel implements Runnable {

    private Graphics2D buffer;
    private Tank t1;
    private Tank t2;
    private GameWindows lf;
    static long tickCount = 0;
    private static ExtraLife life;
    private BufferedImage world;
    private static BufferedImage player1WinImg;
    private static BufferedImage player2WinImg;
    public static ArrayList<Wall> walls = new ArrayList<>();
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    public static HashMap<String, Tank> tanks = new HashMap<>();


    public GameWorld(GameWindows lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            gameInitialize();
            while (true) {
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                // tickCount++;
                if (t1.getHealth() <= 0 || t2.getHealth() <= 0) {
                    this.lf.setFrame("end");
                    break;
                }
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
            }
        } catch (InterruptedException | IOException ignored) {
            System.out.println(ignored);
        }
    }


    /**
     * Reset game to its initial state.
     */
    /*public void resetGame(){
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(300);
        this.t2.setY(300);
    }*/


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() throws IOException {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null, backGroundImg = null;

        try {
            //Tank Images
            t1img = read(GameWorld.class.getClassLoader().getResource("resources/tank1.png"));


            //Player1 & Player2 Won Image
            player1WinImg = ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/player1WinsImg.PNG"));
            player2WinImg = ImageIO.read(GameWorld.class.getClassLoader().getResource("resources/player1WinsImg.PNG"));


            //Map File
            InputStreamReader isr = new InputStreamReader(GameWorld.class.getClassLoader().getResourceAsStream("resources/maps/map1.txt"));
            BufferedReader mapReader = new BufferedReader(isr);

            //Reading the map
            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("No data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    switch (mapInfo[curCol]) {
                        case "2":
                            this.walls.add(new BreakWall(curCol * 30, curRow * 30));
                            break;
                        case "3":
                            this.powerUps.add(new Speed(curCol * 30, curRow * 30));
                            break;
                        case "4":
                            this.powerUps.add(new ExtraLife(curCol * 30, curRow * 30));
                            break;
                        case "9":
                            this.walls.add(new UnBreakWall(curCol * 30, curRow * 30));
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //Creating the tanks
        t1 = new Tank(300, 300, 0, 0, 0, t1img, "t1");
        t2 = new Tank(300, 300, 0, 0, 0, t1img, "t2");
        tanks.put("t1", t1);
        tanks.put("t2", t2);

        //Tanks key control
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_Z, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);

    }


    //Split screen for the x coordinate of the first Tank
    private int getX1Coordinate(Tank t1) {
        int x1 = t1.getX();
        if (x1 < GameConstants.GAME_SCREEN_WIDTH / 4)
            x1 = GameConstants.GAME_SCREEN_WIDTH / 4;
        if (x1 > GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4)
            x1 = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4;
        return x1;
    }

    //Split screen for the x coordinate of the second Tank
    private int getX2Coordinate(Tank t2) {
        int x2 = t2.getX();
        if (x2 < GameConstants.GAME_SCREEN_WIDTH / 4)
            x2 = GameConstants.GAME_SCREEN_WIDTH / 4;
        if (x2 > GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4)
            x2 = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 4;
        return x2;
    }

    //Split screen for the y coordinate of the first Tank
    private int getY1Coordinate(Tank t2) {
        int y1 = t2.getY();
        if (y1 < GameConstants.GAME_SCREEN_HEIGHT / 2)
            y1 = GameConstants.GAME_SCREEN_HEIGHT / 2;
        if (y1 > GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2)
            y1 = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2;
        return y1;
    }

    //Split screen for the x coordinate of the second Tank
    private int getY2Coordinate(Tank t2) {
        int y2 = t2.getY();
        if (y2 < GameConstants.GAME_SCREEN_HEIGHT / 2)
            y2 = GameConstants.GAME_SCREEN_HEIGHT / 2;
        if (y2 > GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2)
            y2 = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT / 2;
        return y2;
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        buffer = world.createGraphics();
        buffer.setColor(Color.black);
        buffer.fillRect(0, 0, GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);

        //Drawing the walls
        try {
              this.walls.forEach(wall -> wall.drawImage(buffer));
            } catch (Exception e){
             }

        //Drawing the tanks
           this.t1.drawImage(buffer);
           this.t2.drawImage(buffer);


           //Power up
           this.powerUps.forEach(powerUp -> powerUp.drawImage(buffer));


           //Split Screen
           BufferedImage lefScreen = world.getSubimage(getX1Coordinate(t1) - GameConstants.GAME_SCREEN_WIDTH / 4, getY1Coordinate(t1) - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);
           BufferedImage rightScreen = world.getSubimage(getX2Coordinate(t2) - GameConstants.GAME_SCREEN_WIDTH / 4, getY2Coordinate(t2) - GameConstants.GAME_SCREEN_HEIGHT / 2, GameConstants.GAME_SCREEN_WIDTH / 2, GameConstants.GAME_SCREEN_HEIGHT);


           //Draw Split Screen
           g2.drawImage(lefScreen, 0, 0, null);
           g2.drawImage(rightScreen, GameConstants.GAME_SCREEN_WIDTH / 2 + 5, 0, null);

           //Health bars
           g2.setFont(new Font("Purisa", Font.BOLD, 25));
           g2.setColor(Color.white);
           g2.setColor(Color.green);
           g2.fillRect(25, 40, 2 * t1.getHealth(), 25);
           g2.setColor(Color.green);
           g2.fillRect(680, 40, 2 * t2.getHealth(), 25);



           //Draw Mini Map
           //For x coordinate, width of game screen divide by 2 - width of game world dived by 12
           //For y coordinate, height of game screen - height of game world divided by 6
           g2.drawImage(world, 352, 352, GameConstants.GAME_WORLD_WIDTH / 6, GameConstants.GAME_WORLD_HEIGHT / 6, null);

    }
}