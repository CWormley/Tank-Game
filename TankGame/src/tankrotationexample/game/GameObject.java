package tankrotationexample.game;

import tankrotationexample.ResourceManager;

import java.awt.*;

public abstract class GameObject {

    public static GameObject newInstance(String type, float x, float y){
        return switch (type) {
            case "3", "9" -> new Wall(x, y, ResourceManager.getSprite("wall1"));
            case "8" -> new Wall(x, y, ResourceManager.getSprite("wall2"));
            default -> throw new IllegalArgumentException("Unsupported type _> %s\n".formatted(type));

        };
    }

    public abstract void draw (Graphics g);
}
