package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author anthony-pc
 */
public class GameWorld extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;

    ArrayList<GameObject> gObjs = new ArrayList<>();
    private BufferedImage background;

    /**
     *
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update(); // update tank
                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);


        int row = 0;
        InputStreamReader isr = new InputStreamReader
                (Objects.requireNonNull(
                        GameWorld.class.getClassLoader().getResourceAsStream("map1.csv")
                ));
        try(BufferedReader mapReader = new BufferedReader(isr)){
            while(mapReader.ready()){
                String line = mapReader.readLine();
                String[] obj = line.split(",");
                for(int col = 0; col < obj.length; col++){
                    String gameItem = obj[col];
                    if(gameItem.equals("0")){continue;}
                    this.gObjs.add(GameObject.newInstance(gameItem, col * 40, row * 40));
                }
                row++;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }


        t1 = new Tank(30, 182, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);

        t2 = new Tank(690, 558, 0, 0, (short) 180, ResourceManager.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc2);

        background = ResourceManager.getSprite("background");
    }

    private void displayMiniMap(Graphics2D onScreenPanel) {
        BufferedImage miniMap = this.world.getSubimage(0,0, GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        AffineTransform scaler = AffineTransform.getTranslateInstance(GameConstants.GAME_SCREEN_WIDTH/2-(GameConstants.GAME_WORLD_WIDTH*.15)/2, 0);
        scaler.scale(0.15, 0.15);
        onScreenPanel.drawImage(miniMap, scaler,null);
    }

    private void displaySplitScreen(Graphics2D onScreenPanel){
        BufferedImage lh = this.world.getSubimage((int)this.t1.x, (int)this.t1.y, GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        onScreenPanel.drawImage(lh, 0, 0, null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        super.paintComponent(g2);
        buffer.drawImage(background, 0, 0, null);
        for(int i = 0; i < this.gObjs.size(); i++){
            this.gObjs.get(i).draw(buffer);
        }
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        //this.displaySplitScreen(g2);
        this.displayMiniMap(g2);
    }




}
