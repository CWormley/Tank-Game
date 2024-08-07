package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Star class for the stars in the game
//Power up type object
public class PowerUp extends GameObject {
    public enum Types {FLOWER, POWER, STAR};
    private long powerUpTime = 0;

    Types t;
    public PowerUp(float x, float y, BufferedImage img, Types t){
        super(x, y, img);
        this.t = t;
    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }

    protected void damage(GameWorld gm) {
        gm.removeGameObject(this);
    }

    public void usePower(Tank tank){
        switch (t){
            case POWER -> tank.powerMode();
            case FLOWER -> tank.flowerMode();
            case STAR -> tank.starMode();
        }
    }



}
