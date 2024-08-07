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
        this.hitBox = new Rectangle((int)x, (int)y, img.getWidth(),img.getHeight());
    }

    //Interpret map file and create objects
    public static GameObject newInstance(String type, float x, float y){
        return switch (type) {
            case "9" -> new Wall(x, y, ResourceManager.getSprite("wall1"));
            case "8" -> new Wall(x, y, ResourceManager.getSprite("wall2"));
            case "7" -> new Wall(x, y, ResourceManager.getSprite("wall3"));
            case "4" -> new BWall (x, y, ResourceManager.getSprite("bwall"));
            case "3" -> new PowerUp (x, y, ResourceManager.getSprite("power"), PowerUp.Types.POWER);
            case "5" -> new PowerUp(x, y, ResourceManager.getSprite("flower"), PowerUp.Types.FLOWER);
            case "6" -> new PowerUp(x, y, ResourceManager.getSprite("star"), PowerUp.Types.STAR);
            default -> throw new IllegalArgumentException("Unsupported type _> %s\n".formatted(type));
        };
    }

    // draw the game object to the frame
    public abstract void draw (Graphics g);

    public Rectangle getHitBox(){return hitBox.getBounds();}

    // damage method for the game object
    protected void damage(GameWorld gm){}
}
