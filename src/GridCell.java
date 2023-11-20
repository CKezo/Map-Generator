import java.awt.*;

public class GridCell {
    private int x;
    private int y;
    public Color color;
    public Color nextColor;
    public Color trueColor;
    public Color backUpColor;
    private final GridCell[] neighbors;
    private int neighborCount;
    public double maxTemp;
    public double minTemp;
    public double avgTemp;
    public double nextMaxTemp;
    public double nextMinTemp;
    public double nextAvgTemp;
    public boolean isCoast;
    public double water;
    public double nextWater;
    public boolean isLake;
    public boolean isRiver;

    public GridCell() {
        this.x = 0;
        this.y = 0;
        this.color = Grid.oceanBlue;
        this.nextColor = Color.black;
        this.trueColor = Color.black;
        this.backUpColor = Color.black;
        this.neighborCount = 0;
        this.isCoast = false;
        this.isLake = false;
        this.isRiver = false;
        this.water = 0;
        this.nextWater = 0;
        this.maxTemp = 0;
        this.minTemp = 0;
        this.avgTemp = 0;
        this.neighbors = new GridCell[4];
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int valueX) {
        this.x = valueX;
    }

    public void setY(int valueY) {
        this.y = valueY;
    }

    public void addNeighbor(GridCell neighbor) {
        neighbors[neighborCount] = neighbor;
        neighborCount++;
    }

    public GridCell[] getNeighbors() {
        return neighbors;
    }

    public int getNeighborCount(){
        return neighborCount;
    }

    public void changeCellColor(Color newColor) {
        this.color = newColor;
    }

}
