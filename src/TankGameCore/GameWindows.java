/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGameCore;


import GameObjects.GameWorld;
import TankGameCore.menus.StartMenuPanel;
import TankGameCore.menus.EndGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;


import static javax.imageio.ImageIO.read;

public class GameWindows extends JPanel implements Runnable {


    private JPanel mainPanel;
    private JPanel startPanel;
    private GameWorld gamePanel;
    private JPanel endPanel;
    private JFrame jf;
    private CardLayout cl;

    public GameWindows(){
        this.jf = new JFrame();             // creating a new JFrame object
        this.jf.setTitle("Tank Wars Game"); // setting the title of the JFrame window.
        this.jf.setLocationRelativeTo(null); // this will open the window in the center of the screen.
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when the GUI is closed, this will also shutdown the VM
        this.setBackground(Color.black);
    }

    private void initUIComponents() throws IOException {
        this.mainPanel = new JPanel(); // create a new main panel
        this.startPanel = new StartMenuPanel(this); // create a new start panel
        this.gamePanel = new GameWorld(this); // create a new game panel
        this.gamePanel.gameInitialize(); // initialize game, but DO NOT start game
        this.endPanel = new EndGamePanel(this); // create a new end game pane;
        cl = new CardLayout(); // creating a new CardLayout Panel
        this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH + 20, GameConstants.GAME_SCREEN_HEIGHT + 40);
        this.jf.setResizable(false); //make the JFrame not resizable
        this.mainPanel.setLayout(cl); // set the layout of the main panel to our card layout
        this.mainPanel.add(startPanel, "start"); //add the start panel to the main panel
        this.mainPanel.add(gamePanel, "game");   //add the game panel to the main panel
        this.mainPanel.add(endPanel, "end");    // add the end game panel to the main panel
        this.jf.add(mainPanel); // add the main panel to the JFrame
        this.setFrame("start"); // set the current panel to start panel
    }


    public void setFrame(String type){
        this.jf.setVisible(false);
        switch(type){
            case "start":
                this.jf.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                this.jf.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
                break;
            case "end":
                this.jf.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
        }
        this.cl.show(mainPanel, type);
        this.jf.setVisible(true);
    }
    public JFrame getJf() {
        return jf;
    }

    public void closeGame(){
        this.jf.dispatchEvent(new WindowEvent(this.jf, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void run() {

    }


    public static void main(String[] args) throws IOException {
        GameWindows game = new GameWindows();
        game.initUIComponents();
    }


}
