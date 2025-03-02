import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class CherryBomb extends Plant{
    private int counter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame = PlantManager.animationBuffer[2].getFrame(0);

    CherryBomb(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(9999);
        setRange(3);
        setAnimation(animation);
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
        if(frameIndex == PlantManager.animationBuffer[2].getNumOfFrames() - 1) {
            SoundManager.playAudio(Sounds.CHERRYBOMB_EXPLOSION);
            PlantManager.removePlant(this.getRow(),this.getColumn());
            Explosion explosion = new Explosion((int)getX()- 125,(int)getY()-125);
            PlantManager.explosionList.add(explosion);
            int row = getRow();
            int column = getColumn();
            if(row == 0 && column == 0) {
                inflictExplosionDamage(0,0);
                inflictExplosionDamage(1,1);
                inflictExplosionDamage(0,1);
                inflictExplosionDamage(1,0);
                return;
            }

            if(row == 4 && column == 0) {
                inflictExplosionDamage(4,0);
                inflictExplosionDamage(3,0);
                inflictExplosionDamage(4,1);
                inflictExplosionDamage(1,1);
                return;
            }

            if(row == 0 && column == 8) {
                inflictExplosionDamage(0,8);
                inflictExplosionDamage(0,7);
                inflictExplosionDamage(1,8);
                inflictExplosionDamage(1,7);
                return;
            }

            if(row == 4 && column == 8) {
                inflictExplosionDamage(4,8);
                inflictExplosionDamage(4,7);
                inflictExplosionDamage(3,8);
                inflictExplosionDamage(3,7);
                return;
            }

            if(row == 0) {
                inflictExplosionDamage(0,column);
                inflictExplosionDamage(0,column+1);
                inflictExplosionDamage(0,column-1);

                inflictExplosionDamage(1,column);
                inflictExplosionDamage(1,column+1);
                inflictExplosionDamage(1,column-1);
                return;
            }

            if(row == 4) {
                inflictExplosionDamage(4,column);
                inflictExplosionDamage(4,column+1);
                inflictExplosionDamage(4,column-1);

                inflictExplosionDamage(3,column);
                inflictExplosionDamage(3,column+1);
                inflictExplosionDamage(3,column-1);
                return;
            }

            if(column == 0) {
                inflictExplosionDamage(row+1,0);
                inflictExplosionDamage(row,0);
                inflictExplosionDamage(row-1,0);

                inflictExplosionDamage(row,1);
                inflictExplosionDamage(row+1,1);
                inflictExplosionDamage(row-1,1);
                return;
            }

            if(column == 8) {
                inflictExplosionDamage(row+1,8);
                inflictExplosionDamage(row,8);
                inflictExplosionDamage(row-1,8);

                inflictExplosionDamage(row,7);
                inflictExplosionDamage(row+1,7);
                inflictExplosionDamage(row-1,7);
                return;
            }

            inflictExplosionDamage(row,column);
            inflictExplosionDamage(row,column+1);
            inflictExplosionDamage(row,column-1);

            inflictExplosionDamage(row+1,column);
            inflictExplosionDamage(row+1,column+1);
            inflictExplosionDamage(row+1,column-1);

            inflictExplosionDamage(row-1,column);
            inflictExplosionDamage(row-1,column+1);
            inflictExplosionDamage(row-1,column-1);
        }
    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        counter++;
        if (counter >= 5) {
            frameIndex = (frameIndex + 1) % 7;
            currentFrame = PlantManager.animationBuffer[2].getFrame(frameIndex);
            counter = 0;
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(2,frameIndex,g);
        updateFrame();
    }
}
