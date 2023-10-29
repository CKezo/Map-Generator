import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private static final int WIDTH = 1060;
    private static final int HEIGHT = 650;

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
        gameGrid.islandPlacer();
        gameGrid.lakePlacer();
        gameGrid.addNoise();
        gameGrid.defineCoasts();
        gameGrid.mountainPlacer();
        gameGrid.riverPlacer();
        gameGrid.storeTrueColor();
        gameGrid.defineWaterAvail("west");
        frame.add(gameGrid);
        frame.setVisible(true);
        Insets insets = frame.getInsets();
        Grid.leftInset = insets.left;
        Grid.topInset = insets.top;

        System.out.println(frame.getInsets());      //LET'S YOU KNOW HOW MUCH THE FRAME OFFSETS THE PANEL
    }
    public static void main(String[] args) {
        newMap();
    }
}

//TO DO

/*
-temperature and island size needs to scale to size of map
-Grid class, privatize the variables again and make getters for them
-lakeplacer I'm fairly sure needs a sufficiently large continent to place the lake farther from oceans so make sure that distance requirement
is taken into account with variable map sizes
*/
