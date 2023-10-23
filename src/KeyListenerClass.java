import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerClass extends Grid implements KeyListener{
    private String windComesFrom = "west";
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'b') {
            Main.gameGrid.drawBiomes();
        }
        if(e.getKeyChar() == 'e') {
            Main.gameGrid.drawEarthMap();
        }
        if(e.getKeyChar() == 'w') {
            Main.gameGrid.drawPrecipMap();
        }
        if(e.getKeyChar() == 'l') {
            Main.gameGrid.drawMinTemp();
        }
        if(e.getKeyChar() == 'h') {
            Main.gameGrid.drawMaxTempMap();
        }
        if(e.getKeyChar() == 'r') {
            Main.gameGrid.drawMaxRangeMap();
        }
        if(e.getKeyChar() == 'a') {
            Main.gameGrid.drawAvgTempMap();
        }
        if(e.getKeyChar() == '9') {
            Main.newMap();
        }
        if(e.getKeyChar() == 'd') {
            if (windComesFrom.equals("west")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("northwest");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "northwest";
            }
            else if (windComesFrom.equals("northwest")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("north");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "north";
            }
            else if (windComesFrom.equals("north")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("northeast");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "northeast";
            }
            else if (windComesFrom.equals("northeast")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("east");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "east";
            }
            else if (windComesFrom.equals("east")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("southeast");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "southeast";
            }
            else if (windComesFrom.equals("southeast")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("south");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "south";
            }
            else if (windComesFrom.equals("south")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("southwest");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "southwest";
            }
            else if (windComesFrom.equals("southwest")) {
                Main.gameGrid.drawEarthMap();
                Main.gameGrid.defineWaterAvail("west");
                Main.gameGrid.drawPrecipMap();
                windComesFrom = "west";
            }
        }
        if(e.getKeyChar() == 'q') {
            //Main.gameGrid.evolve();
        }
    }
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
