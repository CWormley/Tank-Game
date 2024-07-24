package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.ResourceManager;
import tankrotationexample.ResourcePools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank{

    private float x;
    private float y;
    private float screen_x;
    private float screen_y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 3;
    private float ROTATIONSPEED = 2.0f;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;

    private long coolDown  = 500;
    private long lastShot = 0;
    List<Bullet> ammo = new ArrayList<Bullet>();


    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.screen_x = x;
        this.screen_y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
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



    void update() {
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
            this.lastShot = currentTime;
            var p = ResourcePools.getPooledInstance("bullet");
            p.initObject(x +this.img.getWidth()/4f,y +this.img.getHeight()/4f,angle);
            this.ammo.add((Bullet)p);
        }

        for(int i = 0; i < ammo.size(); i++){
            if(ammo.get(i).colision) {
                ammo.remove(ammo.get(i));
                break;
            }
            ammo.get(i).update();
        }

        centerScreen();
    }



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


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(.5,.5);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < ammo.size(); i++){
            ammo.get(i).draw(g);
        }
        g2d.drawImage(this.img, rotation, null);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);

    }
}
