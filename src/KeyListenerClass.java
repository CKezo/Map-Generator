import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerClass extends Grid implements KeyListener{
    private String windComesFrom = "west";
    public void keyTyped(KeyEvent e) {
        Grid grid = Main.getGameGrid();
        if(e.getKeyChar() == 'b') {
            grid.drawBiomes();
        }
        if(e.getKeyChar() == 'e') {
            grid.drawEarthMap();
        }
        if(e.getKeyChar() == 'w') {
            grid.drawPrecipMap();
        }
        if(e.getKeyChar() == 'l') {
            grid.drawMinTemp();
        }
        if(e.getKeyChar() == 'h') {
            grid.drawMaxTempMap();
        }
        if(e.getKeyChar() == 'r') {
            grid.drawMaxRangeMap();
        }
        if(e.getKeyChar() == 'a') {
            grid.drawAvgTempMap();
        }
        if(e.getKeyChar() == '9') {
            Main.newMap();
            windComesFrom = "west";
        }
        if(e.getKeyChar() == '1') {
            grid.removeSharpInlets();
        }
        if(e.getKeyChar() == '2') {
            grid.addNoise();
        }
        if(e.getKeyChar() == '3') {
            grid.noSquareInlets();
        }
        if(e.getKeyChar() == '4') {
            grid.storeBackup();
        }
        if(e.getKeyChar() == '5') {
            grid.restoreBackup();
        }
        if(e.getKeyChar() == '6') {
            grid.switchBetweenTransformations();
        }
        if(e.getKeyChar() == '7') {
            grid.fixLongStraightCoasts();
        }
        if(e.getKeyChar() == 'd') {
            if (windComesFrom.equals("west")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("northwest");
                grid.drawPrecipMap();
                windComesFrom = "northwest";
            }
            else if (windComesFrom.equals("northwest")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("north");
                grid.drawPrecipMap();
                windComesFrom = "north";
            }
            else if (windComesFrom.equals("north")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("northeast");
                grid.drawPrecipMap();
                windComesFrom = "northeast";
            }
            else if (windComesFrom.equals("northeast")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("east");
                grid.drawPrecipMap();
                windComesFrom = "east";
            }
            else if (windComesFrom.equals("east")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("southeast");
                grid.drawPrecipMap();
                windComesFrom = "southeast";
            }
            else if (windComesFrom.equals("southeast")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("south");
                grid.drawPrecipMap();
                windComesFrom = "south";
            }
            else if (windComesFrom.equals("south")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("southwest");
                grid.drawPrecipMap();
                windComesFrom = "southwest";
            }
            else if (windComesFrom.equals("southwest")) {
                grid.drawEarthMap();
                grid.defineWaterAvail("west");
                grid.drawPrecipMap();
                windComesFrom = "west";
            }
        }
    }
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
