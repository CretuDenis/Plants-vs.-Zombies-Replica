import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class PotatoMine extends Plant{
    private int counter = 0;
    private int frameIndex = 0;
    private int activeCounter = 0;
    private boolean active = false;
    private BufferedImage currentFrame = PlantManager.animationBuffer[4].getFrame(0);

    PotatoMine(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(100);
        setRange(1);
        setAnimation(animation);
        setSunCost(25);
    }

    private void inflictExplosionDamage(int row, int column) {
        if(ZombieManager.lawnZombies[row][column].isEmpty())
            return;
        for(Map.Entry<Integer,Zombie> entry : ZombieManager.lawnZombies[row][column].entrySet()) {
            Zombie zombie = entry.getValue();
            zombie.takeDamage(300);
        }
    }

    @Override
    public void behave() {
        if(!active) {
            activeCounter++;
            if(activeCounter >= 275000) {
                active = true;
                activeCounter = 0;
            }
        }

        if(!active)
            return;

        int row = getRow();
        int column = getColumn();
        if(ZombieManager.lawnZombies[row][column].isEmpty())
            return;

        for(Map.Entry<Integer,Zombie> entry : ZombieManager.lawnZombies[row][column].entrySet()) {
            Zombie zombie = entry.getValue();
            if(getX() >= zombie.getRealX() - 90) {
                Explosion explosion = new Explosion((int)getX() - 125,(int)getY()-125);
                PlantManager.removePlant(row,column);
                PlantManager.explosionList.add(explosion);
                SoundManager.playAudio(Sounds.POTATO_MINE);
                inflictExplosionDamage(row,column);
            }
        }
    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        counter++;
        if (counter >= 4) {
            frameIndex = (frameIndex + 1) % 29;
            currentFrame = PlantManager.animationBuffer[4].getFrame(frameIndex);
            counter = 0;
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(4,frameIndex,g);
        updateFrame();
    }
}
