import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    private float x,y;
    private float velocityX,velocityY;
    private float accelerationX,accelerationY;
    private int width,height;
    private BufferedImage sprite;
    Animation animation;

    Entity(float x, float y,int width,int height,String sprite) {
        this.x = x;
        this.y = y;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.width = width;
        this.height = height;
        loadImage(sprite);
    }
    Entity(float x, float y,int width,int height) {
        this.x = x;
        this.y = y;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.width = width;
        this.height = height;
    }
    Entity(float x, float y,int width,int height,BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
    Entity()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.width = 0;
        this.height = 0;
    }

    Entity(float x, float y,int width,int height,Animation animation) {
        this.x = x;
        this.y = y;
        this.velocityX = 0.0f;
        this.velocityY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.width = width;
        this.height = height;
        this.animation = animation;
    }

    public void loadImage(String spriteName) {
        sprite = null;
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(spriteName));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setVelocity(float velX, float velY) { this.velocityX = velX; this.velocityY = velY; }
    public void setVelocityX(float velX) { this.velocityX = velX; }
    public void setVelocityY(float velY) { this.velocityY = velY; }
    public void setAcceleration(float accX,float accY) { this.accelerationX = accX; this.accelerationY = accY; }
    public void setDimensions(int width,int height) { this.width = width; this.height = height; }
    public void setPosition(float x,float y) { this.x = x; this.y = y; }
    public void setX(float x) { this.x = x;}
    public void setY(float y) { this.y = y;}
    public void setAccelerationX(float acX) { this.accelerationX = acX; }
    public void setAccelerationY(float acY) { this.accelerationY = acY; }
    public void setSprite(String spriteName) { loadImage(spriteName); }
    public void setSprite(BufferedImage sprite) { this.sprite = sprite; }
    public void setAnimation(Animation animation) { this.animation = animation; }
    public void decrementAccelerationX(int dec) { this.accelerationX -= dec;}
    public void decrementAccelerationY(int dec) { this.accelerationY -= dec;}

    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }
    public float getAccelerationX() { return accelerationX; }
    public float getAccelerationY() { return accelerationY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getX() { return x; }
    public float getY() { return y; }

    public void moveX(float dec) { x += dec; }
    public void moveY(float dec) { y += dec; }

    public void updateVelocity() { velocityX += accelerationX; velocityY += accelerationY; }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite,(int)x,(int)y,width,height,null);
    }
    public void draw(Graphics g,BufferedImage image) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,(int)x,(int)y,width,height,null);
    }

    public void updateAnimation()
    {
        animation.updateFrame();
    }

    public void drawAnimation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(animation.getCurrentFrame(),(int)x,(int)y,width,height,null);
        updateAnimation();
    }
}
