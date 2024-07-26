package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// Flower class that extends GameObject
//Power up type
public class Flower extends GameObject {
    // Constructor
    public Flower(float x, float y, BufferedImage img){
        super(x, y, img);
    }
    // draw the flower object to the frame
    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }


}
