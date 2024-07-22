package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UBWall extends GameObject {
    float x, y;
    BufferedImage img;

    public UBWall(float x, float y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }


}
