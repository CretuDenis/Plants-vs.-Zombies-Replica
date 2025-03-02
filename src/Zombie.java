import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Zombie extends Entity{
    private int health;
    private final Random randGenerator = new Random();
    private int animationCounter = 0;
    private BufferedImage currentFrame;
    private int frameIndex = 0;
    private int row,column;
    private int id;
    private int eatCounter = 0;
    private float zombieSpeed = 0.002f;
    private int speedCounter = 0;
    private boolean slowed = false;
    boolean eating = false;
    private float zombieRealX;
    Animation currentAnimation;
    public boolean hit = false;
    public boolean alive = true;
    private int groanCounter = 0;

    Zombie(int id) {
        this.column = 9;
        this.health = 100;
        this.id = id;
        currentFrame = ZombieManager.animationBuffer[0].getFrame(0);
        setAnimation(ZombieManager.animationBuffer[0]);
        currentAnimation = ZombieManager.animationBuffer[0];
        setDimensions(200,200);
        row = randGenerator.nextInt(5);
        switch(row) {
            case 0:
                setPosition(1300.0f,25.0f);
                break;
            case 1:
                setPosition(1300.0f,150.0f);
                break;
            case 2:
                setPosition(1300.0f,250.0f);
                break;
            case 3:
                setPosition(1300.0f,375.0f);
                break;
            case 4:
                setPosition(1300.0f,500.0f);
                break;
            default:
                break;
        }
        zombieRealX = getX()+getWidth()/2;
    }


    public void behave() {
        groanCounter++;
        if(groanCounter >= 200000) {
            Random rand = new Random();
            int random = rand.nextInt(6);
            if(random == 0)
                SoundManager.playAudio(Sounds.GROAN);
            else if(random == 1)
                SoundManager.playAudio(Sounds.GROAN2);
            else if(random == 2)
                SoundManager.playAudio(Sounds.GROAN3);
            else if(random == 3)
                SoundManager.playAudio(Sounds.GROAN4);
            else if(random == 4)
                SoundManager.playAudio(Sounds.GROAN5);
            else
                SoundManager.playAudio(Sounds.GROAN6);
            groanCounter = 0;
        }

        if(zombieRealX < LawnManager.grassGrid[0][8].getX() + LawnManager.grassGrid[0][8].getWidth() && zombieRealX > LawnManager.grassGrid[0][8].getX()) {
            ZombieManager.lawnZombies[row][9].remove(id);
            ZombieManager.lawnZombies[row][8].put(id,this);
            column = 8;
        }else if(zombieRealX < LawnManager.grassGrid[0][7].getX() + LawnManager.grassGrid[0][7].getWidth() && zombieRealX > LawnManager.grassGrid[0][7].getX()) {
            ZombieManager.lawnZombies[row][8].remove(id);
            ZombieManager.lawnZombies[row][7].put(id,this);
            column = 7;
        }else if(zombieRealX < LawnManager.grassGrid[0][6].getX() + LawnManager.grassGrid[0][6].getWidth() && zombieRealX > LawnManager.grassGrid[0][6].getX()) {
            ZombieManager.lawnZombies[row][7].remove(id);
            ZombieManager.lawnZombies[row][6].put(id,this);
            column = 6;
        }else if(zombieRealX < LawnManager.grassGrid[0][5].getX() + LawnManager.grassGrid[0][5].getWidth() && zombieRealX > LawnManager.grassGrid[0][5].getX()) {
            ZombieManager.lawnZombies[row][6].remove(id);
            ZombieManager.lawnZombies[row][5].put(id,this);
            column = 5;
        }else if(zombieRealX < LawnManager.grassGrid[0][4].getX() + LawnManager.grassGrid[0][4].getWidth() && zombieRealX > LawnManager.grassGrid[0][4].getX()) {
            ZombieManager.lawnZombies[row][5].remove(id);
            ZombieManager.lawnZombies[row][4].put(id,this);
            column = 4;
        }else if(zombieRealX < LawnManager.grassGrid[0][3].getX() + LawnManager.grassGrid[0][3].getWidth() && zombieRealX > LawnManager.grassGrid[0][3].getX()) {
            ZombieManager.lawnZombies[row][4].remove(id);
            ZombieManager.lawnZombies[row][3].put(id,this);
            column = 3;
        }else if(zombieRealX < LawnManager.grassGrid[0][2].getX() + LawnManager.grassGrid[0][2].getWidth() && zombieRealX > LawnManager.grassGrid[0][2].getX()) {
            ZombieManager.lawnZombies[row][3].remove(id);
            ZombieManager.lawnZombies[row][2].put(id,this);
            column = 2;
        }else if(zombieRealX < LawnManager.grassGrid[0][1].getX() + LawnManager.grassGrid[0][1].getWidth() && zombieRealX > LawnManager.grassGrid[0][1].getX()) {
            ZombieManager.lawnZombies[row][2].remove(id);
            ZombieManager.lawnZombies[row][1].put(id,this);
            column = 1;
        }else if(zombieRealX < LawnManager.grassGrid[0][0].getX() + LawnManager.grassGrid[0][0].getWidth() && zombieRealX > LawnManager.grassGrid[0][0].getX()) {
            ZombieManager.lawnZombies[row][1].remove(id);
            ZombieManager.lawnZombies[row][0].put(id,this);
            column = 0;
        }

        if(column <= 8)
            if(PlantManager.plantGrid[row][column] != null && zombieRealX <= LawnManager.grassGrid[row][column].getX()+LawnManager.grassGrid[row][column].getWidth()/2 + 30) {
                eating = true;
                setAnimation(ZombieManager.animationBuffer[1]);
            }
            else
                eating = false;

        if(!eating) {
            currentAnimation = ZombieManager.animationBuffer[0];
            setPosition(getX() - zombieSpeed,getY());
            zombieRealX = getX()+getWidth()/2;
        }
        else {
            currentAnimation = ZombieManager.animationBuffer[1];
            eatCounter++;
            if(eatCounter >= 5000) {
                Random rand = new Random();
                int randomInt = rand.nextInt(3);
                if(randomInt == 0)
                    SoundManager.playAudio(Sounds.CHOMP_SOFT);
                else if(randomInt == 1)
                    SoundManager.playAudio(Sounds.CHOMP);
                else
                    SoundManager.playAudio(Sounds.CHOMP2);

                if(PlantManager.plantGrid[row][column] != null)
                    PlantManager.plantGrid[row][column].takeDamage();
                eatCounter = 0;
                if(PlantManager.plantGrid[row][column] != null && PlantManager.plantGrid[row][column].getHealth() <= 0) {
                    PlantManager.removePlant(row, column);
                    SoundManager.playAudio(Sounds.GULP);
                    eating = false;
                }

            }
        }

        if(slowed)
        {
            zombieSpeed = 0.00125f/2.0f;
            speedCounter++;
            if(speedCounter >= 1000) {
                slowed = false;
                zombieSpeed = 0.001f;
                speedCounter = 0;
            }
        }
    }

    public void updateFrame() {
        if(GamePanel.lost)
            return;
        if(!eating)
            currentAnimation = ZombieManager.animationBuffer[0];
        else
            currentAnimation = ZombieManager.animationBuffer[1];

        animationCounter++;
        if (animationCounter >= currentAnimation.getAnimationTime()) {
            frameIndex = (frameIndex + 1) % currentAnimation.getNumOfFrames();
            currentFrame = currentAnimation.getFrame(frameIndex);
            animationCounter = 0;
        }
    }

    public void drawFrame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(currentAnimation.getFrame(frameIndex),(int)getX(),(int)getY(),getWidth(),getHeight(),null);
    }

    public void drawZombieAnimation(Graphics g) {
        drawFrame(g);
        updateFrame();
    }

    public void die() {
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public float getRealX() { return zombieRealX; }
    public int getID() {return id;}
    public int getHealth() { return health; }
    public void takeDamage(int damage) {
        health = health - damage;
        if(health <= 0)
            alive = false;
    }
    public void setHit(boolean hit) { this.hit = hit; }
    public void setSlowed(boolean slowed) { this.slowed = slowed; }
}
