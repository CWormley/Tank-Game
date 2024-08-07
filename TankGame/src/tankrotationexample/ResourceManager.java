package tankrotationexample;

import tankrotationexample.game.Animation;
import tankrotationexample.game.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

// This class is responsible for loading and storing all the resources used in the game
//links to the resources are stored in the resources folder
//like resources are stored in map, key is used to grab the resource
public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, Sound> sounds = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animations = new HashMap<>();

    private final static Map<String, Integer> animInfo = new HashMap<>(){{
        put("explosion_sm", 6);
        put("explosion_sm2", 6);


    }};

    //construct a new ResourceManager object


    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(
                ResourceManager.class.getClassLoader().getResource(path),
                "Resource %s was not found: ".formatted(path)));
    }
    private static Sound loadSound(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(
                    ResourceManager.class.getClassLoader().getResource(path),
                    "Sound Resource %s was not found: ".formatted(path)));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            Sound sound = new Sound(clip);
            ResourceManager.sounds.put(path, sound);
            return sound;
    }

    private static void initSounds (){
        try {
            ResourceManager.sounds.put("background", loadSound("bg.wav"));
            ResourceManager.sounds.put("start_background", loadSound("bg_start.wav"));
            ResourceManager.sounds.put("shoot", loadSound("shoot.wav"));
            ResourceManager.sounds.put("gameover", loadSound("gameover.wav"));

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
    private static void initAnims(){
        String baseFormat = "%s/%s_%04d.png";
        ResourceManager.animInfo.forEach((animationName, frameCount) ->{
            List<BufferedImage> f = new ArrayList<>(frameCount);
            try {
            for(int i =1; i <= frameCount; i++){
                String spritePath = String.format(baseFormat, animationName , animationName, i);

                    f.add(loadSprite(spritePath));
            }
            ResourceManager.animations.put(animationName, f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //load all the sprites
    private static void initSprites() throws IOException {
        ResourceManager.sprites.put("tank1", loadSprite("tank1.png"));
        ResourceManager.sprites.put("tank2", loadSprite("tank2.png"));
        ResourceManager.sprites.put("title", loadSprite("title.png"));
        ResourceManager.sprites.put("background", loadSprite("background.png"));
        ResourceManager.sprites.put("blue", loadSprite("blueEnd.png"));
        ResourceManager.sprites.put("green", loadSprite("greenEnd.png"));
        ResourceManager.sprites.put("end", loadSprite("blueEnd.png"));
        ResourceManager.sprites.put("wall1", loadSprite("wall1.png"));
        ResourceManager.sprites.put("wall2", loadSprite("wall2.png"));
        ResourceManager.sprites.put("wall3", loadSprite("wall3.png"));
        ResourceManager.sprites.put("flower", loadSprite("flower.png"));
        ResourceManager.sprites.put("star", loadSprite("star.png"));
        ResourceManager.sprites.put("bwall", loadSprite("bwall.png"));
        ResourceManager.sprites.put("bullet", loadSprite("bullet.png"));
        ResourceManager.sprites.put("heart", loadSprite("heart.png"));
        ResourceManager.sprites.put("emptyHeart", loadSprite("emptyHeart.png"));
        ResourceManager.sprites.put("full", loadSprite("full.png"));
        ResourceManager.sprites.put("oneHit", loadSprite("oneHit.png"));
        ResourceManager.sprites.put("twoHit", loadSprite("twoHit.png"));
        ResourceManager.sprites.put("threeHit", loadSprite("threeHit.png"));
        ResourceManager.sprites.put("power", loadSprite("power.png"));
    }



    public static BufferedImage getSprite(String key){
        if(!ResourceManager.sprites.containsKey(key)){
            throw new IllegalArgumentException("No sprite found with key " + key);
        }
        return ResourceManager.sprites.get(key);
    }

    public static Sound getSound(String key){
        if(!ResourceManager.sounds.containsKey(key)){
            throw new IllegalArgumentException("No sound found with key " + key);
        }
        return (ResourceManager.sounds.get(key));
    }

    public static List<BufferedImage> getAnimation(String key){
        if(!ResourceManager.animations.containsKey(key)){
            throw new IllegalArgumentException("No animation found with key " + key);
        }
        return ResourceManager.animations.get(key);
    }

    public static void setSprite(String key, BufferedImage sprite) {
        ResourceManager.sprites.put(key, sprite);
    }

    //called in launcher
    public static void loadAssets(){
        // load all assets
        try {
            initSprites();
            initSounds();
            initAnims();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load assets", e);
        }
    }




}