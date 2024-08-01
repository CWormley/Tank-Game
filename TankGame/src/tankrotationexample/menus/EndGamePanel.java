package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.ResourceManager;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;
    private Sound bg;

    public EndGamePanel(Launcher lf) {
        this.lf = lf;
        menuBackground = ResourceManager.getSprite("end");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton start = new JButton("Restart Game");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(150, 600, 200, 50);
        start.addActionListener((actionEvent -> this.lf.resetGame()));


        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(357, 600, 200, 50);
        exit.addActionListener((actionEvent -> this.lf.closeGame()));

        this.add(start);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.menuBackground = ResourceManager.getSprite("end");
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

}
