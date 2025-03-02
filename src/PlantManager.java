import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlantManager {
    public static final int plantSpriteWidth = 100;
    public static Entity currentCursorPlant = new Entity(-200, -200, plantSpriteWidth, plantSpriteWidth, "animations/sunflower/sunflower (1).png");
    private static CopyOnWriteArrayList<Plant> placedPlants = new CopyOnWriteArrayList<>();
    public static Plant[][] plantGrid = new Plant[5][9];
    static int currentPlantState = -1;
    public static int currentPanel = SeedsPlacer.getCurrentSelectedPanel();
    public static Animation[] animationBuffer = new Animation[7];
    public static BufferedImage[] projectiles = new BufferedImage[2];
    public static Animation cherryExplosion;
    public static CopyOnWriteArrayList<Projectile> projectileList = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Explosion> explosionList = new CopyOnWriteArrayList<>();

    PlantManager() {
        try {
            projectiles[0] = ImageIO.read(getClass().getResourceAsStream("./assets/pea.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            projectiles[1] = ImageIO.read(getClass().getResourceAsStream("./assets/snow_pea.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAnimations() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                plantGrid[i][j] = null;
            }
        }

        cherryExplosion = new Animation("cherry_explosion",7,6);
        animationBuffer[0] = new Animation("peashooter", 77, 3);
        animationBuffer[1] = new Animation("sunflower", 54, 5);
        animationBuffer[2] = new Animation("cherrybomb", 7, 5);
        animationBuffer[3] = new Animation("wallnut", 44, 5);
        animationBuffer[4] = new Animation("potatomine", 29, 4);
        animationBuffer[5] = new Animation("snowpea", 25, 5);
        animationBuffer[6] = new Animation("repeater", 49, 3);
    }

    private static int getSunCostOfSelectedPlant(int plantID) {
        return switch (plantID) {
            case 0 -> 100;
            case 1 -> 50;
            case 2 -> 150;
            case 3 -> 50;
            case 4 -> 25;
            case 5 -> 175;
            case 6 -> 200;
            default -> -1;
        };
    }
    public static void getCurrentCursorPlant() {
        if(GamePanel.lost)
            return;
        currentPanel = SeedsPlacer.getCurrentSelectedPanel();
        int sunCost = getSunCostOfSelectedPlant(currentPanel);

        if (currentPlantState == -1 && SeedsPlacer.mouseInPanels() && MouseHandler.mouseClicked && sunCost <= SunManager.sunCount) {
            SoundManager.playAudio(Sounds.SEED_LIFT);
            SunManager.sunCount -= sunCost;
            switch (currentPanel) {
                case 0:
                    currentCursorPlant.setSprite("animations/peashooter/peashooter (1).png");
                    currentPlantState = 0;
                    break;
                case 1:
                    currentCursorPlant.setSprite("animations/sunflower/sunflower (1).png");
                    currentPlantState = 1;
                    break;
                case 2:
                    currentCursorPlant.setSprite("animations/cherrybomb/cherrybomb (1).png");
                    currentPlantState = 2;
                    break;
                case 3:
                    currentCursorPlant.setSprite("animations/wallnut/wallnut (1).png");
                    currentPlantState = 3;
                    break;
                case 4:
                    currentCursorPlant.setSprite("animations/potatomine/potatomine (1).png");
                    currentPlantState = 4;
                    break;
                case 5:
                    currentCursorPlant.setSprite("animations/snowpea/snowpea (1).png");
                    currentPlantState = 5;
                    break;
                case 6:
                    currentCursorPlant.setSprite("animations/repeater/repeater (1).png");
                    currentPlantState = 6;
                    break;
                default:
                    break;
            }
            currentPlantState = currentPanel;
            MouseHandler.mouseClicked = false;
        } else if (currentPlantState != -1 && SeedsPlacer.mouseInPanels() && MouseHandler.mouseClicked) {
            currentCursorPlant.setPosition(-200, -200); // OFFSCREEN
            currentPlantState = -1;
            MouseHandler.mouseClicked = false;
        }
    }

    public static void addPlantInList(Plant plant) {
        if (!LawnManager.lawnLayout[plant.getRow()][plant.getColumn()]) {
            LawnManager.lawnLayout[plant.getRow()][plant.getColumn()] = true;
            plantGrid[plant.getRow()][plant.getColumn()] = plant;
            placedPlants.add(plant);
        }
    }

    public void removePlantInList(int row, int column) {
        for (Plant plant : placedPlants) {
            if (plant.getRow() == row && plant.getColumn() == column) {
                LawnManager.lawnLayout[plant.getRow()][plant.getColumn()] = false;
                plantGrid[row][column] = null;
                placedPlants.remove(plant);
            }
        }
    }

    public static void updateCursorPlant() {
        if(GamePanel.lost)
            return;
        if (currentPlantState == -1) {
            currentCursorPlant.setPosition(-200, -200);
        } else {
            currentCursorPlant.setPosition(MouseHandler.mouseX - currentCursorPlant.getWidth() / 2, MouseHandler.mouseY - currentCursorPlant.getHeight() / 2);
        }

    }

    public static void addPlant(int row,int column, int type) {
        Point plantPos = LawnManager.getPlacedPlantPosition(row, column);
        switch (type) {
            case 0:
                addPlantInList(new PeaShooter((int) plantPos.getX(), (int) plantPos.getY(), row, column));
                break;
            default:
                break;
        }
    }

    public static void placePlant() {
        Point currentInLawnPos = LawnManager.getCursorPosInGrid();
        synchronized(placedPlants) {
            if (currentInLawnPos != null) {
                if (currentPlantState != -1 && currentInLawnPos != null && MouseHandler.mouseClicked) {
                    Point plantPos = LawnManager.getPlacedPlantPosition((int) currentInLawnPos.getX(), (int) currentInLawnPos.getY());
                    switch (currentPlantState) {
                        case 0:
                            addPlantInList(new PeaShooter((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 1:
                            addPlantInList(new SunFlower((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 2:
                            addPlantInList(new CherryBomb((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 3:
                            addPlantInList(new Wallnut((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 4:
                            addPlantInList(new PotatoMine((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 5:
                            addPlantInList(new SnowPea((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        case 6:
                            addPlantInList(new Repeater((int) plantPos.getX(), (int) plantPos.getY(), (int) currentInLawnPos.getX(), (int) currentInLawnPos.getY()));
                            break;
                        default:
                            break;
                    }
                    Random random = new Random();
                    int rand = random.nextInt(2);
                    if(rand == 0)
                        SoundManager.playAudio(Sounds.PLANT_PUT1);
                    else
                        SoundManager.playAudio(Sounds.PLANT_PUT2);

                    currentPlantState = -1;
                    MouseHandler.mouseClicked = false;
                }
            }
        }
    }

    public static void drawPlants(Graphics g) {
        if (placedPlants.isEmpty())
            return;
        for (int i = 0; i < placedPlants.size(); i++) {
            placedPlants.get(i).drawAnimation(g);
        }
    }

    public static void updatePlants() {
        if (placedPlants.isEmpty())
            return;
        synchronized(placedPlants) {
            for (Plant plant : placedPlants) {
                plant.behave();
            }
        }
    }

    public static void drawProjectiles(Graphics g) {
        if (projectileList.isEmpty()) {
            return;
        }
        Iterator<Projectile> iterator = projectileList.iterator();
        while (iterator.hasNext()) {
            Entity entry = iterator.next();
            if (entry != null) {
                entry.draw(g);
            }
        }
    }

    public static void updateProjectiles() {
        if (projectileList.isEmpty()) {
            return;
        }
        synchronized(projectileList) {
            Iterator<Projectile> it = projectileList.iterator();
            while (it.hasNext()) {
                Projectile proj = it.next();
                proj.setX(proj.getX() + 0.05f);
                int column = proj.getColumn();
                int row = proj.getRow();
                boolean hitZombie = false;
                for (int i = column; i < 9; i++) {
                    for (Map.Entry<Integer, Zombie> entry : ZombieManager.lawnZombies[row][i].entrySet()) {
                        Zombie zombie = entry.getValue();
                        if (proj.getX() >= zombie.getRealX() - 30) {
                            if(proj.getProjType() == ProjectileType.SNOW_PEA)
                                zombie.setSlowed(true);
                            else {
                                Random rand = new Random();
                                int random = rand.nextInt(3);
                                if(random == 0)
                                    SoundManager.playAudio(Sounds.SPLAT);
                                else if(random == 1)
                                    SoundManager.playAudio(Sounds.SPLAT2);
                                else
                                    SoundManager.playAudio(Sounds.SPLAT3);
                            }

                            projectileList.remove(proj);
                            zombie.takeDamage(20);
                            Plant parentPlant = proj.getParentPlant();
                            hitZombie = true;
                            break;
                        }
                    }
                    if (hitZombie)
                        break;
                }
            }
        }
    }

    public static void removePlant(int row,int column) {
        plantGrid[row][column] = null;
        LawnManager.lawnLayout[row][column] = false;
        if(placedPlants.isEmpty())
            return;
        synchronized(placedPlants) {
            Iterator<Plant> it = placedPlants.iterator();
            while (it.hasNext()) {
                Plant plant = it.next();
                if (plant != null && plant.getRow() == row && plant.getColumn() == column) {
                    placedPlants.remove(plant);
                }
            }
        }
    }

    public static void drawExplosionList(Graphics g) {
        if(explosionList.isEmpty())
            return;
        Iterator<Explosion> it = explosionList.iterator();
        while(it.hasNext()) {
            Explosion exp = it.next();
            exp.drawExplosionAnimation(g);
        }
    }

    public static void cleanExplosions() {
        if(explosionList.isEmpty())
            return;
        Iterator<Explosion> it = explosionList.iterator();
        while(it.hasNext()) {
            Explosion exp = it.next();
            if(exp.getFrameIndex() == cherryExplosion.getNumOfFrames()-1)
                explosionList.remove(exp);
        }
    }

    public static void debugPlantList() {

    }
}
