import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerClass extends Grid implements KeyListener{
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
        if(e.getKeyChar() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        if(e.getKeyChar() == 'n') {
            Main.newMap();
            grid = Main.getGameGrid();
            grid.setWindComesFrom("west");
            Main.updateWindDirectionButton();
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
        if(e.getKeyChar() == '8') {
            grid.addRiver();
            grid.repaint();
        }
        if(e.getKeyChar() == 'd') {
            grid.defineWaterAvail();
            Main.updateWindDirectionButton();
        }
    }
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
