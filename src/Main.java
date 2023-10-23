import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        KeyListenerClass theKeyListener = new KeyListenerClass();
        theKeyListener.addKeyListener(theKeyListener);
        theKeyListener.setFocusable(true);
        add(theKeyListener);
        MouseListenerClass theMouseListener = new MouseListenerClass();
        this.addMouseListener(theMouseListener);
        theMouseListener.setFocusable(true);
        add(theMouseListener);
        //setVisible(true);
    }
    public static Grid gameGrid;
    public static Main frame = new Main();
    public static void newMap() {
        gameGrid = new Grid();
        gameGrid.islandPlacer();
        gameGrid.lakePlacer();
        gameGrid.addNoise();
        gameGrid.defineCoasts();
        gameGrid.mountainPlacer();
        gameGrid.riverPlacer();
        gameGrid.storeTrueColor();
        gameGrid.defineWaterAvail("west");
        gameGrid.defineMoveToNeighbors();
        //gameGrid.placeInitialSpecies();
        frame.add(gameGrid);
        frame.setVisible(true);
        /*for (int i = 0; i < 286; i++) {
            gameGrid.evolve();
        }*/
        //System.out.println(frame.getInsets());      LET'S YOU KNOW HOW MUCH THE FRAME OFFSETS THE PANEL
    }
    public static void main(String[] args) {
        newMap();
    }
}