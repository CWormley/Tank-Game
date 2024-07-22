package tankrotationexample;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();
    //ResourcePool<Wall> wallPool = new ResourcePool<>();

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(
                ResourceManager.class.getClassLoader().getResource(path),
                "Resource %s was not found: ".formatted(path)));
    }
    private static void initSprites() throws IOException {
        ResourceManager.sprites.put("tank1", loadSprite("tank1.png"));
        ResourceManager.sprites.put("tank2", loadSprite("tank2.png"));
        ResourceManager.sprites.put("title", loadSprite("title.png"));
        ResourceManager.sprites.put("background", loadSprite("background.png"));
        ResourceManager.sprites.put("wall1", loadSprite("wall1.png"));
        ResourceManager.sprites.put("wall2", loadSprite("wall2.png"));
        ResourceManager.sprites.put("wall3", loadSprite("wall3.png"));
        ResourceManager.sprites.put("flower", loadSprite("flower.png"));
        ResourceManager.sprites.put("star", loadSprite("star.png"));
        ResourceManager.sprites.put("grass", loadSprite("grass.png"));

    }

    public static BufferedImage getSprite(String key){
        if(!ResourceManager.sprites.containsKey(key)){
            throw new IllegalArgumentException("No sprite found with key " + key);
        }
        return ResourceManager.sprites.get(key);
    }

    public static void loadAssets(){
        // load all assets
        try {
            initSprites();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load assets", e);
        }
    }



}