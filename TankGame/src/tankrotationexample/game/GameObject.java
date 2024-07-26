package tankrotationexample.game;

import tankrotationexample.ResourceManager;
import java.awt.*;
import java.awt.image.BufferedImage;

// GameObject class
// This class is used to create a game object
// This class is abstract and is extended by other classes
public abstract class GameObject {
    // Variables for the game object
    protected float x;
    protected float y;
    protected BufferedImage img;
    protected Rectangle hitBox;

    // Constructor
    public GameObject(float x, float y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int)x, (int)y, (int)(img.getWidth()*.5), (int)(img.getHeight()*.5));
    }

    //Interpret map file and create objects
    public static GameObject newInstance(String type, float x, float y){
        return switch (type) {
            case "9" -> new Wall(x, y, ResourceManager.getSprite("wall1"));
            case "8" -> new Wall(x, y, ResourceManager.getSprite("wall2"));
            case "7" -> new Wall(x, y, ResourceManager.getSprite("wall3"));
            case "4" -> new BWall (x, y, ResourceManager.getSprite("bwall"));
            case "5" -> new Flower(x, y, ResourceManager.getSprite("flower"));
            case "6" -> new Star(x, y, ResourceManager.getSprite("star"));
            default -> throw new IllegalArgumentException("Unsupported type _> %s\n".formatted(type));
        };
    }

    // draw the game object to the frame
    public abstract void draw (Graphics g);

    public Rectangle getHitBox(){
        return hitBox.getBounds();
    }
    // damage method for the game object
    protected void damage(){}
}
