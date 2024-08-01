package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;
import tankrotationexample.ResourceManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author anthony-pc
 */
// GameWorld class
// This class is used to create the game world

public class GameWorld extends JPanel implements Runnable {
// Variables for the game world
    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private final Launcher lf;
    private long tick = 0;

    private int winner;

    public boolean gameOver = false;

    // Array list of game objects
    ArrayList<GameObject> gObjs = new ArrayList<>();
    ArrayList<GameObject> gObjsRefined = new ArrayList<>();
    ArrayList<GameObject> gObjsMovable = new ArrayList<>();
    private BufferedImage background;
    int x=0;
    Sound bg = ResourceManager.getSound("background");

    // Constructor
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    // run method for the game world
    //update game objects and check for collisions
    //redraw game
    @Override
    public void run() {
        bg = ResourceManager.getSound("background");
        bg.setVolume(0.2f);
        bg.loop();
        bg.play();
        try {
            while (true){
                this.tick++;
                this.t1.update(this); // update tank
                this.t2.update(this); // update tank
                this.checkCollision();
                this.repaint();   // redraw game
                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our 
                 * loop run at a fixed rate per/sec. 
                */
                Thread.sleep(1000 / 144);

                if(this.gameOver){
                    if(winner == 1){
                        ResourceManager.setSprite("end", ResourceManager.getSprite("blue"));
                    } else{
                        ResourceManager.setSprite("end", ResourceManager.getSprite("green"));
                    }
                    bg.stop();
                    bg = ResourceManager.getSound("start_background");
                    bg.setVolume(0.2f);
                    bg.loop();
                    bg.play();
                    this.lf.setFrame("end");;

                }
            }



        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    public void resetMain(){
        this.resetGame();
        this.t2.loses = 0;
        this.t1.loses = 0;
        bg.stop();
        bg = ResourceManager.getSound("background");
        bg.setVolume(0.2f);
        bg.loop();
        bg.play();

    }

    // check for collisions between game objects
    private void checkCollision() {
        for(int i = 0; i < this.gObjsRefined.size(); i++) {
            GameObject obj = this.gObjsRefined.get(i);
            for(int j = 0; j < this.gObjsMovable.size(); j++){
                GameObject obj2 = this.gObjsMovable.get(j);
                if(obj == obj2){continue;}
                if(obj.getHitBox().intersects(obj2.getHitBox())){
                    if(obj2 instanceof Bullet){
                        if(((Bullet)obj2).hit(obj)){
                            obj.damage(this);
                        }
                        if(obj instanceof Flower){
                            flower((Bullet)obj2);
                        }
                        if(obj instanceof Star){
                            star((Bullet)obj2);
                        }
                        continue;
                    }
                    ((Tank)obj2).collision();
                }
            }
        }
    }


    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.resetTank(45,80,0);
        this.t2.resetTank(1875,80,180);

    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        // Load map from file
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
                    if(row !=0 && col != 0 && row != 24 && col != 32){
                        this.gObjsRefined.add(this.gObjs.get(this.gObjs.size()-1));
                    }
                }
                row++;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    // Create tanks
        t1 = new Tank(45, 80, 0, 0, (short) 0, ResourceManager.getSprite("tank1"));
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc1);
        this.gObjsRefined.add(t1);
        this.gObjsMovable.add(t1);


        t2 = new Tank(1875, 80, 0, 0, (short) 180, ResourceManager.getSprite("tank2"));
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SLASH);
        this.lf.getJf().addKeyListener(tc2);
        this.gObjsRefined.add(t2);
        this.gObjsMovable.add(t2);

        background = ResourceManager.getSprite("background");
    }

    // display mini map
    private void displayMiniMap(Graphics2D onScreenPanel) {
        BufferedImage miniMap = this.world.getSubimage(0,0, GameConstants.GAME_WORLD_WIDTH, GameConstants.GAME_WORLD_HEIGHT);
        AffineTransform scaler = AffineTransform.getTranslateInstance(GameConstants.GAME_SCREEN_WIDTH/2-(GameConstants.GAME_WORLD_WIDTH*.15)/2, 0);
        scaler.scale(0.15, 0.15);
        onScreenPanel.drawImage(miniMap, scaler,null);
    }

    // display split screen
    private void displaySplitScreen(Graphics2D onScreenPanel){
        BufferedImage lh = this.world.getSubimage((int)this.t1.getScreen_x(), (int)this.t1.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        onScreenPanel.drawImage(lh, 0, 0, null);
        BufferedImage rh = this.world.getSubimage((int)this.t2.getScreen_x(), (int)this.t2.getScreen_y(), GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        onScreenPanel.drawImage(rh, GameConstants.GAME_SCREEN_WIDTH/2+4, 0, null);
    }

    // paint component method for the game world
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        super.paintComponent(g2);
        buffer.drawImage(background, 0, 0, null);
        for(int i = 0; i < this.gObjs.size(); i++){
            this.gObjs.get(i).draw(buffer);
        }
        this.t1.draw(buffer);
        this.t2.draw(buffer);

        this.displaySplitScreen(g2);
        this.displayMiniMap(g2);


    }

    //add bullets to movable objects from tank class
    public void addGameObject(GameObject g) {
        this.gObjsMovable.add(g);
    }

    public void removeBullet(GameObject g) {
        this.gObjsMovable.remove(g);
    }
    public void removeGameObject(GameObject g) {
        this.gObjsRefined.remove(g);
        this.gObjs.remove(g);
    }

    public void GameOver(Tank t) {
        this.gameOver = true;
        if(t == t1){
            System.out.println("winner is player 2");
            winner = 2;
        } else{
            System.out.println("winner is player 1");
            winner = 1;
        }
    }

    public void newGame() {
        Sound gameover = ResourceManager.getSound("gameover");
        gameover.setVolume(0.2f);
        gameover.play();
        this.resetGame();
    }

    public void flower(Bullet b) {
        if(b.tankID == t1.tankID){
            t1.flowerMode();
        }
        else{
            t2.flowerMode();
        }
    }

    public void star(Bullet b) {
        if(b.tankID == t1.tankID){
            t1.starMode();
        }
        else{
            t2.starMode();
        }
    }






}
