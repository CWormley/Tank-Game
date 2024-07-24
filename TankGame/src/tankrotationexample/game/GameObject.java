package tankrotationexample.game;

import tankrotationexample.ResourceManager;

import java.awt.*;

public abstract class GameObject {

    public static GameObject newInstance(String type, float x, float y){
        return switch (type) {
            case "9" -> new Wall(x, y, ResourceManager.getSprite("wall1"));
            case "8" -> new Wall(x, y, ResourceManager.getSprite("wall2"));
            case "7" -> new Wall(x, y, ResourceManager.getSprite("wall3"));
            case "4" -> new UBWall (x, y, ResourceManager.getSprite("ub_wall"));
            case "5" -> new Flower(x, y, ResourceManager.getSprite("flower"));
            case "6" -> new Star(x, y, ResourceManager.getSprite("star"));
            default -> throw new IllegalArgumentException("Unsupported type _> %s\n".formatted(type));
        };
    }

    public abstract void draw (Graphics g);
}
