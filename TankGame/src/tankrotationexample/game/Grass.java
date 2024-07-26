package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grass extends GameObject {


    public Grass(float x, float y, BufferedImage img){
        super(x, y, img);

    }

    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }


}
