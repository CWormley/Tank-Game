package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    private float x,y;
    private List<BufferedImage> frames;
    private int delay = 40;
    private boolean isRunning = false;
    private long timeSinceLastUpdate = 0;

    private int currentFrame;

    public Animation(float x, float y, List<BufferedImage> frames){
        this.x = x;
        this.y =y;
        this.frames = frames;
        this.isRunning = true;
        this.currentFrame =0;
    }

    public void update(){
        long currentTime = System.currentTimeMillis();
        if(timeSinceLastUpdate + delay <= currentTime){
            this.currentFrame++;
            if(this.frames.size() == currentFrame){isRunning =false;}
            this.timeSinceLastUpdate = currentTime;
        }
    }

    public void render(Graphics g){
        if(this.isRunning){
            g.drawImage(this.frames.get(currentFrame), (int)x, (int)y, null);
        }
    }

}

