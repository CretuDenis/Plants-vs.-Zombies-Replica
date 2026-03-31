import java.awt.*;
import java.util.ArrayList;

public class LawnManager {
    private static final int ROWS = 5;
    private static final int COLUMNS = 9;
    private int initialX, initialY;
    private static int cellWidth,cellHeight;
    public static Interactable[][] grassGrid = new Interactable[ROWS][COLUMNS];
    public static boolean[][] lawnLayout = new boolean[ROWS][COLUMNS];

    LawnManager(int initialX,int initialY,int cellWidth,int cellHeight) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public void placeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                grassGrid[i][j] = new Interactable(initialX + j*cellWidth + 10,initialY + i*cellHeight - 5,"../resources/assets/test.png",1150/9,600/6);
            }
        }
    }

    public static Point getCursorPosInGrid() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                if(grassGrid[i][j].mouseInBounds())
                    return new Point(i,j);
            }
        }

        return null;
    }

    static ArrayList<Entity> debugPlants = new ArrayList<>();
    public static void debugPlantPos() {
        Animation a = new Animation("peashooter",77,50);
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                Point p = getPlacedPlantPosition(i,j);
                debugPlants.add(new Entity((float)p.getX(),(float)p.getY(),100,100,a));
            }
        }
    }

    public static void drawDebugPlants(Graphics g) {
        for(Entity plant : debugPlants) {
            plant.drawAnimation(g);
        }
    }


    public static Point getPlacedPlantPosition(int row,int column) {
        int x = (int)grassGrid[row][column].getX();
        int y = (int)grassGrid[row][column].getY();

        return new Point(x + (cellWidth - PlantManager.plantSpriteWidth)/2,y + PlantManager.plantSpriteWidth/4 - 25);
    }

   public void drawGrid(Graphics g) { // only for debug purposes they are transparent
       Graphics2D g2d = (Graphics2D) g;
       for(int i = 0; i < ROWS; i++) {
           for(int j = 0; j < COLUMNS; j++) {
               grassGrid[i][j].draw(g);
           }
       }
   }
}

