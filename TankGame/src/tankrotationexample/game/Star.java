package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Star extends GameObject {
    float x, y;
    BufferedImage img;

    public Star(float x, float y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }


}
