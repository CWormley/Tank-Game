package tankrotationexample.game;

import tankrotationexample.GameConstants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// Bullet class that extends GameObject and implements Poolable
// This class is used to create a bullet object that is shot by the tank
public class Bullet extends GameObject implements Poolable {
    // Variables for the bullet object
    private float vx;
    private float vy;
    private float angle;
    private float R = 4;
    private float ROTATIONSPEED = 2.0f;
    public boolean collision = false;
    public int tankID;

    // Constructor
    Bullet(float x, float y, float angle, BufferedImage img, int tankID) {
        super(x, y, img);
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
        this.tankID = tankID;
    }
    // Default Constructor
    public Bullet(BufferedImage img) {
        super(0, 0, img);
        this.vx=0;
        this.vy=0;
        this.angle = 0;
        this.tankID = -1;
    }

    //check if the bullet has hit the border of the game world
    private void checkBorder() {
        if (x < 40) {collision = true;}
        if (x >= GameConstants.GAME_WORLD_WIDTH - 40) {collision = true;}
        if (y < 40) {collision = true;}
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 40) {collision = true;}
    }


    // move bullet and associated assets, for boarder collision
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation((int)x, (int)y);
    }

    // draw bullet to frame
    @Override
    public void draw(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(.5,.5);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }

    // poolable methods
    @Override
    public void initObject(float x, float y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void initObject(float x, float y, float angle, int tankID) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.tankID = tankID;
    }
    @Override
    public void resetObject() {
        this.x = -5;
        this.y = -5;
    }

    // handle collisions
    public boolean hit(GameObject obj){
        if(obj instanceof Tank){
            if(((Tank)obj).getId() != this.tankID){
                collision = true;
                return true;
            }else{
                return false;
            }
        }else{
            collision = true;// damage wall or other object
            return true;
        }
    }
}
