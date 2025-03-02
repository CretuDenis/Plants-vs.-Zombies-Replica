import java.awt.*;
import java.text.AttributedString;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class SunManager {
    private static CopyOnWriteArrayList<Sun> sunList = new CopyOnWriteArrayList<>();
    public static Animation sunAnimation = new Animation("sun",22,4);
    private static Sun[] sunArray = new Sun[1000];
    private static int sunArraySize = 0;
    private static int sunArrayIndex = 0;
    private static Random random = new Random();
    public static final int collectionX = 45;
    public static final int collectionY = 0;
    public static int sunCount = 50;
    public static int skySunCounter = 0;
    private static AttributedString string = new AttributedString(Integer.toString(sunCount));

    static public void spawnSunSunflower(int sunflowerX,int sunflowerY) {
        synchronized(sunList) {
            int randomAngle = random.nextInt(21) + 80;
            sunList.add(new Sun(sunflowerX, sunflowerY - 50, sunflowerY, (float) randomAngle, 1.0f));
        }
    }
    static public void spawnSunFromSky() {
        synchronized(sunList) {
            skySunCounter++;
            if (skySunCounter > 250000) {
                skySunCounter = 0;
                int randomFinalHeight = random.nextInt(400) + 200;
                int randomX = random.nextInt(900) + 100;
                Sun newSun = new Sun(randomX, -100, randomFinalHeight, 270, 0.2f);
                sunList.add(newSun);
            }
        }
    }
    static public void updateSunList() {
        if (sunList.isEmpty())
            return;
        synchronized(sunList) {
            Iterator<Sun> it = sunList.iterator();
            while (it.hasNext()) {
                Sun sun = it.next();
                if (sun.getAliveCounter() > 1000 || ((int) sun.getX() == 45 && (int) sun.getY() == 0)) {
                    sunList.remove(sun);
                    continue;
                }
                if (!sun.getCollected())
                    sun.trajectoryMove((int) sun.getAngle());
                else
                    sun.lineMoveToCollection((int) sun.getCollectedX(), (int) sun.getCollectedY(), 45, 0);
            }
        }
    }

    static public void interactWithSun() {
        if(sunList.isEmpty())
            return;
        synchronized(sunList) {
            for (int i = 0; i < sunList.size(); i++) {
                if (sunList.get(i).mouseClicked() && !sunList.get(i).getCollected()) {
                    SoundManager.playAudio(Sounds.SUN_PICK);
                    sunCount += 25;
                    sunList.get(i).setCollectedPos(sunList.get(i).getX(), sunList.get(i).getY());
                    sunList.get(i).setCollected(true);
                    MouseHandler.mouseClicked = false;
                }
            }
        }
    }
    static public void drawSunList(Graphics g) {
        if(!sunList.isEmpty()) {
            for(int i = 0; i < sunList.size(); i++) {
                sunList.get(i).drawAnimation(g);
            }
        }
    }

    static public void drawSunAmount(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,0,0));
        g2d.drawString(string.getIterator(),100,100);
    }
    //static public void drawSunList(Graphics g) {
    //    for (int i = 0; i < sunCount; i++) {
    //        Sun sun = sunArray[i];
    //        if (sun != null) {
    //            sun.draw(g, Sun.getSunSprite());
    //        }
    //    }
    //}
}
