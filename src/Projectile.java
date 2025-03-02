enum ProjectileType { NORMAL_PEA, SNOW_PEA}
public class Projectile extends Entity{
    private Plant parentPlant = null;
    private int row,column;
    private int type; // 0 for normal 1 for
    private ProjectileType projType;

    Projectile(float x, float y, int width,int height,String sprite,ProjectileType projType,int row,int column)
    {
        super(x,y,width,height,sprite);
        this.row = row;
        this.column = column;
        this.projType = projType;
    }

    int getRow() { return row; }
    int getColumn() { return column; }
    int getType() { return type; }
    Plant getParentPlant() { return parentPlant; }
    ProjectileType getProjType() { return projType; }
    void setParentPlant(Plant parent) { parentPlant = parent; }

}
