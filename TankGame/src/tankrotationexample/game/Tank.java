package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.ResourceManager;
import tankrotationexample.ResourcePools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author anthony-pc
 */
// Tank class that extends GameObject
// Contains the tank's movement and shooting logic
// Also contains the tank's collision logic
public class Tank extends GameObject{

    private float screen_x;
    private float screen_y;
    private float vx;
    private float vy;
    private float angle;
    private float R = 2.5f;
    private float ROTATIONSPEED = 2.0f;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    public int tankID;
    private long coolDown  = 400;
    private long lastShot = 0;
    private int hitCount=0;

    public int loses = 0;

    private float last_X;
    private float last_Y;

    private long powerUpTime = 0;
    // List of bullets
    List<Bullet> ammo = new ArrayList<Bullet>();



    // Constructor for the Tank class
    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        super(x, y, img);
        this.screen_x = x;
        this.screen_y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankID = new Random().nextInt(1000);
        this.hitBox = new Rectangle((int)x, (int)y, img.getWidth()/4,img.getHeight()/4);

    }

    public float getScreen_x() {
        return screen_x;
    }

    public float getScreen_y() {
        return screen_y;
    }

    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

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

    void toggleShoot() {
        this.shootPressed = true;
    }

    void unToggleShoot() {
        this.shootPressed = false;
    }


    // Update method for the Tank class
    // Contains the tank's movement and shooting logic
    void update(GameWorld gw) {
        this.last_X = this.x;
        this.last_Y = this.y;
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

        long currentTime = System.currentTimeMillis();
        if (this.shootPressed && currentTime > this.coolDown + this.lastShot) {
            Sound s = ResourceManager.getSound("shoot");
            s.setVolume(.2f);
            s.play();
            this.lastShot = currentTime;
            var p = ResourcePools.getPooledInstance("bullet");
            p.initObject(x +this.img.getWidth()/4f,y +this.img.getHeight()/4f,angle, tankID);
            this.ammo.add((Bullet)p);
            gw.addGameObject((Bullet)p);
        }

        for(int i = 0; i < ammo.size(); i++){
            if(ammo.get(i).collision) {
                gw.playAnimation(new Animation(ammo.get(i).x, ammo.get(i).y, ResourceManager.getAnimation("explosion_sm")));
                gw.removeBullet(ammo.get(i));
                ammo.remove(ammo.get(i));

                break;
            }
            ammo.get(i).update();
        }

        centerScreen();

        this.hitBox.setLocation((int)this.x, (int)this.y);

        if(powerUpTime !=0){
            if(System.currentTimeMillis() - powerUpTime > 5000){
                powerUpTime = 0;
                coolDown = 400;
                System.out.println("Power up over");
            }
        }
    }


    //movement methods
    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
    }

    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    //center the screen on the tank
    private void centerScreen(){
        this.screen_x = this.x - GameConstants.GAME_SCREEN_WIDTH/4f;
        this.screen_y = this.y - GameConstants.GAME_SCREEN_HEIGHT/2f;


        if(this.screen_x < 0) {screen_x = 0;}
        if(this.screen_y < 0) {screen_y = 0;}


        if(this.screen_x >GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2) {
            this.screen_x = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH / 2;
        }
        if(this.screen_y > GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) {
            this.screen_y = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }


    }

    //check collisoin with the border
    private void checkBorder() {
        if (x < 40) {
            x = 40;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 110) {
            x = GameConstants.GAME_WORLD_WIDTH - 110;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 130) {
            y = GameConstants.GAME_WORLD_HEIGHT - 130;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    //draw the tank
    @Override
    public void draw(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(.5,.5);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < ammo.size(); i++){
            ammo.get(i).draw(g);
        }
        g2d.drawImage(this.img, rotation, null);
    }

    //handle collision
    public void collision(){
        this.x= last_X;
        this.y = last_Y;
    }

    protected void damage(GameWorld gm){
        hitCount++;
        if(hitCount == 3){
            loses++;
            if(loses == 3) {
                gm.GameOver(this);
            }
            else {
                gm.newGame();
            }
        }
    }


    public int getId() {
        return this.tankID;
    }

    public void resetTank(int x, int y, int angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hitCount = 0;
        }

    public void flowerMode() {
        this.coolDown = 200;
        this.powerUpTime = System.currentTimeMillis();
    }

    public void starMode() {
        this.hitCount--;
    }

    public BufferedImage getHealth (){
        List <BufferedImage> healthBar = new ArrayList<>(Arrays.asList(ResourceManager.getSprite("full"), ResourceManager.getSprite("oneHit"),ResourceManager.getSprite("twoHit"),ResourceManager.getSprite("threeHit")));
        return healthBar.get(hitCount);
    }

    public BufferedImage getHeart (int place){
        if(loses > place){
            return ResourceManager.getSprite("emptyHeart");
        }
        return ResourceManager.getSprite("heart");

    }
}



