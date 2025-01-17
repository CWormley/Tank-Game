package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Wall class that extends GameObject
// This class is used to create a wall object
public class Wall extends GameObject {
    public Wall(float x, float y, BufferedImage img){
        super(x, y, img);
    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }


}
