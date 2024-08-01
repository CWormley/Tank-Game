package tankrotationexample.menus;


import tankrotationexample.Launcher;
import tankrotationexample.ResourceManager;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;
    Sound bg;

    public StartMenuPanel(Launcher lf) {

        bg = ResourceManager.getSound("start_background");
        bg.setVolume(0.2f);
        bg.loop();
        bg.play();

        this.lf = lf;
        menuBackground = ResourceManager.getSprite("title");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 610, 150, 50);
        start.addActionListener(actionEvent -> setGame());

        JButton exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(407, 610, 150, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(start);
        this.add(exit);
    }

    public void setGame(){
        bg.stop();
        this.lf.setFrame("game");
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
