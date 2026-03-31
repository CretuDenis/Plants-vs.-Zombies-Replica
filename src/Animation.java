import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrameIndex;
    private int numOfFrames;
    private int animationTime; // in miliseconds
    private BufferedImage currentFrame;
    private int[][] counters = new int[45][2]; // 45 0 ii counteru 45 [1] frame indexu
    private int counter = 0;

    Animation(String baseAnimationName, int numOfFrames, int animationTime) {
        this.numOfFrames = numOfFrames;
        this.animationTime = animationTime;
        this.currentFrameIndex = 0;
        frames = new BufferedImage[numOfFrames];
        loadAnimation(baseAnimationName);
        currentFrame = frames[0];
    }

    private void loadImage(String spriteName, int frameIndex) {
        frames[frameIndex] = null;

        try {
            frames[frameIndex] = ImageIO.read(new File("../resources/animations/" + spriteName + "/" + spriteName +  " (" + (frameIndex+1) + ")" + ".png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAnimation(String baseAnimationName) {
        for(int i = 0; i < numOfFrames; i++) {
            loadImage(baseAnimationName,i);
        }
    }

    public void updateFrame() {
        counter++;
        if (counter >= animationTime) {
            currentFrameIndex = (currentFrameIndex + 1) % numOfFrames;
            currentFrame = frames[currentFrameIndex];
            counter = 0;  // Reset counter
        }
    }

    public void setNumOfFrames(int num) { this.numOfFrames = num; }
    public void setAnimationTime(int animationTime) { this.animationTime = animationTime; }

    public BufferedImage getCurrentFrame() { return currentFrame; }
    public int getCurrentFrameIndex() { return currentFrameIndex; }
    public int getNumOfFrames() { return numOfFrames; }
    public int getAnimationTime() { return animationTime;}
    public BufferedImage getFrame(int i) { return frames[i]; }

}
