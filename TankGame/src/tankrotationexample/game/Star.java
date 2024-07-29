package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Star class for the stars in the game
//Power up type object
public class Star extends GameObject {
    public Star(float x, float y, BufferedImage img){
        super(x, y, img);
    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }

    protected void damage(GameWorld gm) {
        gm.removeGameObject(this);
    }

}
