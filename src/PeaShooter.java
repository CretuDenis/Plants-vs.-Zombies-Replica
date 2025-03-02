import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PeaShooter extends Plant{
    private int counter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame = PlantManager.animationBuffer[0].getFrame(0);
    int projectileCounter = 0;
    boolean shooting = false;

    PeaShooter(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(100);
        setRange(8);
        setAnimation(animation);
        setType(0);
    }

    void shootPea() {
        Projectile projectile = new Projectile(getX() + getWidth() / 2 + 25, getY() + getHeight() / 2 - 35, 25, 25, "assets/pea.png", ProjectileType.NORMAL_PEA, getRow(), getColumn());
        projectile.setParentPlant(this);
        PlantManager.projectileList.add(projectile);
    }

    @Override
    public void behave() {
        if(!shooting) {
            for(int i = getColumn(); i < 9; i++)
                if(!ZombieManager.lawnZombies[getRow()][i].isEmpty())
                    shooting = true;
            return;
        }

        projectileCounter++;
        if (projectileCounter >= 25000) {
            projectileCounter = 0;
            shootPea();
        }
        int contor = 0;
        for(int i = getColumn(); i < 9; i++) {
            contor += ZombieManager.lawnZombies[getRow()][i].size();
        }
        if(contor == 0) {
            shooting = false;

        }
    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        synchronized(this) {
            counter++;
            if (counter >= 2) {
                frameIndex = (frameIndex + 1) % 77;
                currentFrame = PlantManager.animationBuffer[0].getFrame(frameIndex);
                counter = 0;
            }
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(0,frameIndex,g);
        updateFrame();
    }
}
