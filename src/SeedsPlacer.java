import java.awt.*;

public class SeedsPlacer {
    public static SeedPanel[] seeds = new SeedPanel[7];
    private final int initialX = 175;
    private final int initialY = 15;
    private final int seedsGap = 90;
    SeedsPlacer() {
        seeds = new SeedPanel[7];
        seeds[0] = new SeedPanel("../resources/assets/seed_peashooter.png",initialX,initialY);
        seeds[1] = new SeedPanel("../resources/assets/seed_sunflower.png",initialX+seedsGap,initialY);
        seeds[2] = new SeedPanel("../resources/assets/seed_cherrybomb.png",initialX+2*seedsGap,initialY);
        seeds[3] = new SeedPanel("../resources/assets/seed_wallnut.png",initialX+3*seedsGap,initialY);
        seeds[4] = new SeedPanel("../resources/assets/seed_potatomine.png",initialX+4*seedsGap,initialY);
        seeds[5] = new SeedPanel("../resources/assets/seed_snowpea.png",initialX+5*seedsGap,initialY);
        seeds[6] = new SeedPanel("../resources/assets/seed_repeater.png",initialX+6*seedsGap,initialY);
    }

    public static int getCurrentSelectedPanel() {
        for(int i = 0; i < seeds.length; i++)
            if(seeds[i].mouseInBounds())
                return i;
        return -1;
    }

    public static boolean mouseInPanels() {
        for(int i = 0; i < seeds.length; i++)
        {
            if(seeds[i].mouseInBounds())
                return true;
        }

        return false;
    }

    public void drawSeeds(Graphics g) {
        for(int i = 0; i < seeds.length; i++)
            seeds[i].draw(g);
    }

}
