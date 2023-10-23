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
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        KeyListenerClass theKeyListener = new KeyListenerClass();
        theKeyListener.addKeyListener(theKeyListener);
        theKeyListener.setFocusable(true);
        add(theKeyListener);
        MouseListenerClass theMouseListener = new MouseListenerClass();
        this.addMouseListener(theMouseListener);
        theMouseListener.setFocusable(true);
        add(theMouseListener);
    }
    public static Grid gameGrid;
    public static Main frame = new Main();
    public static void newMap() {
        gameGrid = new Grid();
        //gameGrid.setLayout(new BorderLayout());
        gameGrid.islandPlacer();
        gameGrid.lakePlacer();
        //you removed the water part of the add noise, uncomment when you calibrate addnoise for land properly
        //also, the islandMaker needs to not allow straight diagonal lines. Always need a lil bit of curve.
        //the inset of the game window needs to adjust, not be a solid number.
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.defineCoasts();
        gameGrid.mountainPlacer();
        gameGrid.riverPlacer();
        gameGrid.storeTrueColor();
        gameGrid.defineWaterAvail("west");
        frame.add(gameGrid);
        frame.setVisible(true);

        System.out.println(frame.getInsets());      //LET'S YOU KNOW HOW MUCH THE FRAME OFFSETS THE PANEL
    }
    public static void main(String[] args) {
        newMap();
    }
}