import java.awt.*;
import java.util.ArrayList;

public class GridCell {
    private int x;
    private int y;
    public Color color;
    public Color nextColor;
    public Color trueColor;
    private GridCell[] neighbors;
    private int neighborCount;
    public double maxTemp;
    public double minTemp;
    public double avgTemp;
    public double nextMaxTemp;
    public double nextMinTemp;
    public double nextAvgTemp;
    public Color oceanBlue = new Color(16, 0, 204);
    public boolean isCoast;
    public double water;
    public double nextWater;
    public boolean isLake;
    public boolean isRiver;
    public ArrayList moveToNeighbors;
    public ArrayList moveToOceanNeighbors;
    public ArrayList moveToRiverNeighbors;
    public ArrayList moveToLandNeighbors;
    public ArrayList speciesPresent;
    public ArrayList colonies;
    public ArrayList nextPhaseSpecies;
    public boolean newSpeciesPresent;

    public GridCell() {
        this.x = 0;
        this.y = 0;
        this.color = oceanBlue;
        this.nextColor = Color.black;
        this.trueColor = Color.black;
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
        this.moveToNeighbors = new ArrayList();
        this.moveToLandNeighbors = new ArrayList();
        this.moveToOceanNeighbors = new ArrayList();
        this.moveToRiverNeighbors = new ArrayList();
        this.speciesPresent = new ArrayList();
        this.colonies = new ArrayList();
        this.nextPhaseSpecies = new ArrayList();
        this.newSpeciesPresent = true;
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

    public boolean addNeighbor(GridCell neighbor) {
        if ((neighbor == null) || (neighborCount == 4)) {
            return false;
        } else {
            neighbors[neighborCount] = neighbor;
            neighborCount++;
        }
        return true;
    }

    public GridCell[] getNeighbors() {
        return neighbors;
    }

    public int getNeighborCount(){
        return neighborCount;
    }

    public void printNeighbors() {
        for (int n = 0; n < neighborCount; n++) {
            System.out.println("Coordinates for neighbor " + n + ": " + neighbors[n].x + ", " + neighbors[n].y);
        }
    }

    public void changeCellColor(Color newColor) {
        this.color = newColor;
    }

    public String getColor() {
        if (this.color == oceanBlue) {
            return "Blue";
        }
        return "";
    }
}
