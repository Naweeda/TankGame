package TankGameCore.menus;

import TankGameCore.GameWindows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private GameWindows lf;

    public EndGamePanel(GameWindows lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("resources/title.png"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Restart Game");
        start.setFont(new Font("arial", Font.BOLD ,20));
        start.setBounds(150,300,180,50);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));


        exit = new JButton("Exit");
        exit.setFont(new Font("arial", Font.BOLD ,20));
        exit.setBounds(150,400,180,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
