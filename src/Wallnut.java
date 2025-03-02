import java.awt.*;
import java.awt.image.BufferedImage;

public class Wallnut extends Plant{
    private int counter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame = PlantManager.animationBuffer[3].getFrame(0);

    Wallnut(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(1000);
        setRange(0);
        setAnimation(animation);
    }

    @Override
    public void behave() {

    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        counter++;
        if (counter >= 5) {
            frameIndex = (frameIndex + 1) % 44;
            currentFrame = PlantManager.animationBuffer[0].getFrame(frameIndex);
            counter = 0;
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(3,frameIndex,g);
        updateFrame();
    }

}
