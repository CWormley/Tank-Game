package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Poolable {
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float angle;

    private float R = 4;
    private float ROTATIONSPEED = 2.0f;

    private BufferedImage img;

    public boolean colision = false;

    Bullet(float x, float y, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.img = img;
        this.angle = angle;
    }

    public Bullet(BufferedImage img) {
        this.img = img;
        this.vx=0;
        this.vy=0;
        this.x = 0;
        this.y = 0;
        this.angle = 0;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void checkBorder() {
        if (x < 40) {colision = true;}
        if (x >= GameConstants.GAME_WORLD_WIDTH - 40) {colision = true;}
        if (y < 40) {colision = true;}
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 40) {colision = true;}
    }


    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    @Override
    public void draw(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.scale(.5,.5);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }

    @Override
    public void initObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void initObject(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @Override
    public void resetObject() {
        this.x = -5;
        this.y = -5;
    }
}
