import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private static final int WIDTH = 1060;
    private static final int HEIGHT = 675;

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
        gameGrid.removeSharpInlets();
        gameGrid.addNoise();
        gameGrid.addNoise();
        //then remove corners and long straights
        gameGrid.lakePlacer();
        gameGrid.addNoise();
        gameGrid.removeSharpInlets();
        gameGrid.noSquareInlets();
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

        //System.out.println(frame.getInsets());      //Let's you know how much the frame offsets the panel
    }
    public static void main(String[] args) {
        newMap();
    }
}

//TO DO

/*
-islandPlacer - currently have some commented out code that I'd like to get back in once we figure out an optimal blend of what size islands make
    for the most realistic looking land masses
-fix rivers cutting through mountains
-Investigate why mountains receive more precipitation even when surrounded by desert on both sides
-temperature and island size needs to scale to size of map
-Grid class, privatize the variables again and make getters for them
-lakeplacer I'm fairly sure needs a sufficiently large continent to place the lake farther from oceans so make sure that distance requirement
is taken into account with variable map sizes
-looks like past me created temporary neighbor variables just to get the count from that new temporary variable when I could just getNeighborCount directly, clean up
-down the road bug to fix: There's something that does stop the program from running but causes a rare edge case where, I'm suspecting, an island
    is placed near the left/right edges of the map in a way that causes it to stretch across the whole world.
*/
