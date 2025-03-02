import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends Entity{
    private int animationCounter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame;

    Explosion(int x,int y) {
        setPosition(x,y);
        setAnimation(PlantManager.cherryExplosion);
        setDimensions(350,350);
        currentFrame = PlantManager.cherryExplosion.getFrame(0);
    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        animationCounter++;
        if (animationCounter >= PlantManager.cherryExplosion.getAnimationTime()) {
            frameIndex = (frameIndex + 1) % PlantManager.cherryExplosion.getNumOfFrames();
            currentFrame = PlantManager.cherryExplosion.getFrame(frameIndex);
            animationCounter = 0;
        }
    }
    public void drawFrame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(PlantManager.cherryExplosion.getFrame(frameIndex),(int)getX(),(int)getY(),getWidth(),getHeight(),null);
    }
    public void drawExplosionAnimation(Graphics g) {
        drawFrame(g);
        updateFrame();
    }

    public int getFrameIndex() { return frameIndex; }
}
