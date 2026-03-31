import java.awt.*;
import java.awt.image.BufferedImage;

public class Repeater extends Plant {
    private int counter = 0;
    private int frameIndex = 0;
    private BufferedImage currentFrame = PlantManager.animationBuffer[6].getFrame(0);
    private int projectileCounter = 0;
    boolean shooting = false;
    Repeater(int x, int y,int row, int column) {
        setPosition(x,y);
        setDimensions(100,100);
        setRow(row);
        setColumn(column);
        setHealth(100);
        setRange(8);
        setAnimation(animation);
        setType(6);
    }

    public void shootPea() {
        Projectile projectile1 = new Projectile(getX()+getWidth()/2 + 25, getY()+getHeight()/2 - 35,25,25,"assets/pea.png",ProjectileType.NORMAL_PEA,getRow(),getColumn());
        Projectile projectile2 = new Projectile(getX()+getWidth()/2 + 100, getY()+getHeight()/2 - 35,25,25,"assets/pea.png",ProjectileType.NORMAL_PEA,getRow(),getColumn());
        PlantManager.projectileList.add(projectile1);
        PlantManager.projectileList.add(projectile2);
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
        if (projectileCounter >= 50000) {
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
        counter++;
        if (counter >= 3) {
            frameIndex = (frameIndex + 1) % 49;
            currentFrame = PlantManager.animationBuffer[6].getFrame(frameIndex);
            counter = 0;
        }
    }
    @Override
    public void drawAnimation(Graphics g) {
        drawFrame(6,frameIndex,g);
        updateFrame();
    }
}
