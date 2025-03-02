import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

public class Sun extends Interactable{
    private static BufferedImage sunSprite = null;
    private float timer;
    private float initialX,initialY;
    private int finalY;
    private float angle;
    private float initialVelocity;
    private boolean shouldMove;
    private boolean collected = false;
    public int totalSteps = 2000;
    private int counter = 0;
    private int aliveCounter = 0;

    private int animationCounter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame = SunManager.sunAnimation.getFrame(0);

    private float collectedX =  0,collectedY = 0;

    Sun(float initialX,float initialY,int finalY, float angle,float initialVelocity)
    {
        if(sunSprite == null){
            try {
                sunSprite = ImageIO.read(getClass().getResourceAsStream("/assets/sun.png"));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        this.shouldMove = true;
        this.finalY = finalY;
        this.angle = angle;
        this.initialVelocity = initialVelocity;
        this.timer = 0.0f;
        this.initialX = initialX;
        this.initialY = initialY;
        setPosition(initialX,initialY);
        setDimensions(100,100);
    }

    private float calculateNextY() {
        return (getX() - 45) * collectedY / (collectedX - 45);
    }
    public boolean getCollected() { return collected; }

    public void trajectoryMove(int angle) {
        if(GamePanel.lost)
            return;
        if(angle == 270 && getY() < finalY) {
            setPosition(getX(), getY() + 0.0075f);
            return;
        }
        else if(getY() >= finalY)
            return;
        if(shouldMove)
        {
            float nextX = (float)(initialX + initialVelocity*timer*Math.cos(Math.toRadians(angle)));
            float nextY = (float)(initialY - initialVelocity*timer*Math.sin(Math.toRadians(angle)) + 0.5f*0.005f*timer*timer);
            setPosition(nextX,nextY);
            timer += 0.016f;
        }

        if((int)getY() == (int)finalY)
            shouldMove = false;

    }
    public void lineMoveToCollection(int x1,int y1, int x2, int y2) {
        if(GamePanel.lost)
            return;
        counter++;
        if((int)getX() > SunManager.collectionX || (int)getY() > SunManager.collectionY) {
            setX(x1 + (x2 - x1) * counter / totalSteps);
            setY(y1 + (y2 - y1) * counter / totalSteps);
        }

    }
    public void setCollected(boolean collected) { this.collected = collected; }
    public void setCollectedPos(float x, float y) { this.collectedX = x; this.collectedY = y; }

    public static BufferedImage getSunSprite() { return sunSprite; }
    public float getCollectedX() { return collectedX; }
    public float getCollectedY() { return collectedY; }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        tick();
        animationCounter++;
        if (animationCounter >= 5) {
            frameIndex = (frameIndex + 1) % 22;
            currentFrame = SunManager.sunAnimation.getFrame(frameIndex);
            animationCounter = 0;
        }
    }
    public void tick() { aliveCounter++;}
    public void drawFrame(int frameIndex, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(SunManager.sunAnimation.getFrame(frameIndex),(int)getX(),(int)getY(),getWidth(),getHeight(),null);
    }
    public float getAngle() { return angle; }
    public int getAliveCounter() { return aliveCounter; }
    public void resetAliveCounter() { aliveCounter = 0; }
    public void drawAnimation(Graphics g) {
        drawFrame(frameIndex,g);
        updateFrame();
    }
}
