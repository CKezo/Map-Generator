import java.awt.*;

public class GridCell {
    private int x;
    private int y;
    private Color color;
    private Color nextColor;
    private Color trueColor;
    private Color backUpColor; //for debugging
    private Color backUpColor1; //for debugging
    private final GridCell[] neighbors;
    private int neighborCount;
    private double maxTemp;
    private double minTemp;
    private double avgTemp;
    private double nextMaxTemp;
    private double nextMinTemp;
    private double nextAvgTemp;
    private boolean isCoast;
    private double water;
    private double nextWater;
    private boolean isLake;
    private boolean isRiver;
    private String biome;
    private Color biomeColor;
    private static final Color oceanBlue = new Color(0, 35, 150);

    public GridCell() {
        this.x = 0;
        this.y = 0;
        this.color = oceanBlue;
        this.nextColor = Color.black;
        this.trueColor = Color.black;
        this.backUpColor = Color.black;
        this.backUpColor1 = Color.black;
        this.biomeColor = Color.black;
        this.biome = "Ocean";
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getNextColor() {
        return nextColor;
    }

    public void setNextColor(Color nextColor) {
        this.nextColor = nextColor;
    }

    public Color getTrueColor() {
        return trueColor;
    }

    public void setTrueColor(Color trueColor) {
        this.trueColor = trueColor;
    }

    public Color getBackUpColor() {
        return backUpColor;
    }

    public void setBackUpColor(Color backUpColor) {
        this.backUpColor = backUpColor;
    }

    public Color getBackUpColor1() {
        return backUpColor1;
    }

    public void setBackUpColor1(Color backUpColor1) {
        this.backUpColor1 = backUpColor1;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getNextMaxTemp() {
        return nextMaxTemp;
    }

    public void setNextMaxTemp(double nextMaxTemp) {
        this.nextMaxTemp = nextMaxTemp;
    }

    public double getNextMinTemp() {
        return nextMinTemp;
    }

    public void setNextMinTemp(double nextMinTemp) {
        this.nextMinTemp = nextMinTemp;
    }

    public double getNextAvgTemp() {
        return nextAvgTemp;
    }

    public void setNextAvgTemp(double nextAvgTemp) {
        this.nextAvgTemp = nextAvgTemp;
    }

    public boolean isCoast() {
        return isCoast;
    }

    public void setCoast(boolean coast) {
        isCoast = coast;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getNextWater() {
        return nextWater;
    }

    public void setNextWater(double nextWater) {
        this.nextWater = nextWater;
    }

    public boolean isLake() {
        return isLake;
    }

    public void setLake(boolean lake) {
        isLake = lake;
    }

    public boolean isRiver() {
        return isRiver;
    }

    public void setRiver(boolean river) {
        isRiver = river;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public Color getBiomeColor() {
        return biomeColor;
    }

    public void setBiomeColor(Color biomeColor) {
        this.biomeColor = biomeColor;
    }
}
