package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// BWall class that extends GameObject
// This class is used to create a breakable wall object
public class BWall extends GameObject {
    // Constructor
    public BWall(float x, float y, BufferedImage img){
        super(x, y, img);
    }

    // draw the breakable wall object to the frame
    public void draw (Graphics g){
        g.drawImage(this.img, (int)this.x, (int)this.y, null);
    }

    // damage method for the breakable wall object
    protected void damage() {
        System.out.println("Damage detected");
    }



}
