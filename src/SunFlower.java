import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class SunFlower extends Plant {
    private int counter = 0;
    private long limit = 500000;
    private int frameIndex = 0;
    private long sunTimer = 0;
    private BufferedImage currentFrame = PlantManager.animationBuffer[0].getFrame(0);

    private boolean spawnSun = false;

    SunFlower(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(100);
        setRange(0);
        setType(1);
    }

    @Override
    public void behave() {
        sunTimer++;
        if(sunTimer > limit) {
            sunTimer = 0;
            SunManager.spawnSunSunflower((int)getX(),(int)getY());
        }
    }

    public void setSpawn(boolean spawn) { this.spawnSun = spawn; }

    public boolean getSpawn() { return spawnSun; }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        counter++;
        if (counter >= 5) {
            frameIndex = (frameIndex + 1) % 54;
            currentFrame = PlantManager.animationBuffer[0].getFrame(frameIndex);
            counter = 0;
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(1,frameIndex,g);
        updateFrame();
    }
}
