import java.awt.*;

public abstract class Plant extends Entity{
    private int health;
    private int range; // in tiles
    private boolean placed;
    private int row,column;
    private int type;
    private int sunCost;

    private int id;
    Plant(int x,int y,int health, int range,int row, int column,Animation animation,int type) {
        this.placed = false;
        setPosition(x,y);
        this.health = health;
        this.range = range;
        this.row = row;
        this.column = column;
        this.type = type;
        setAnimation(animation);
    }

    Plant() {

    }

    public abstract void behave();

    public boolean getPlaced() { return placed; }
    public int getHealth() { return health; }
    public int getRange() { return range; }
    public int getRow() { return row; }
    public int getColumn() { return column; }
    public int getID() { return id; }
    public int getType() { return type;}
    public int getSunCost() { return sunCost; }

    public void setSunCost(int sunCost) { this.sunCost = sunCost; }
    public void setHealth(int health) { this.health = health; }
    public void setRange(int range) { this.range = range; }
    public void setRow(int row) { this.row = row; }
    public void setColumn(int column) { this.column = column; }
    public void setType(int type) { this.type = type; }
    public void takeDamage() { this.health = this.health - 5; }

    public void drawFrame(int plantType,int frameIndex, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(PlantManager.animationBuffer[plantType].getFrame(frameIndex),(int)getX(),(int)getY(),getWidth(),getHeight(),null);
    }

    public abstract void drawAnimation(Graphics g);
}
