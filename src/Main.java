import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private final int WIDTH = 1060;
    private final int HEIGHT = 700;

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        //Return to below when you integrate the system output in the terminal into the main program
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        if(gd.isFullScreenSupported()){
//            setUndecorated(true);
//            gd.setFullScreenWindow(this);
//        } else {
//            System.err.println("Full screen not supported");
//            setPreferredSize(new Dimension(WIDTH, HEIGHT));
//            setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        }

        KeyListenerClass theKeyListener = new KeyListenerClass();
        theKeyListener.addKeyListener(theKeyListener);
        theKeyListener.setFocusable(true);
        add(theKeyListener);

        MouseListenerClass theMouseListener = new MouseListenerClass();
        this.addMouseListener(theMouseListener);
        theMouseListener.setFocusable(true);
        add(theMouseListener);
    }

    private static Grid gameGrid;
    private static Main frame = new Main();

    public static Grid getGameGrid(){
        return gameGrid;
    }
    public static void newMap() {
        gameGrid = new Grid();
        gameGrid.islandPlacer();
        gameGrid.removeSharpInlets();
        gameGrid.addNoise();
        gameGrid.addNoise();
        gameGrid.lakePlacer();
        gameGrid.addNoise();
        gameGrid.removeSharpInlets();
        gameGrid.noSquareInlets();
        gameGrid.fixLongStraightCoasts();
        gameGrid.defineCoasts();
        gameGrid.mountainPlacer();
        //gameGrid.riverPlacer();
        gameGrid.storeTrueColor();
        gameGrid.defineWaterAvail("west");
        frame.add(gameGrid);
        frame.setVisible(true);
        Insets insets = frame.getInsets();
        Grid.setLeftInset(insets.left);
        Grid.setTopInset(insets.top);

        //System.out.println(frame.getInsets());      //Let's you know how much the frame offsets the panel
    }
    public static void main(String[] args) {
        newMap();
    }
}

//TO DO

/*
-precipitation fix for how rivers/lakes affect systems - since the lakes on our map only represent giant lakes, they can still generate
    some nearby precipitation. River adjacency will no longer raise water to .8-.9 in adjacent spaces - for this, we simply want to change
    the biome from desert to grassland, grassland to deciduous forest, savana to rainforest. However, come back and give this approach
    some more thought about how this would fit in with a potential evolution simulator in terms of actual water availability in the square. Just
    set the water number in the adjacent squares to a set number based on what would qualify it for the corresponding biomes based on heat/current water.
-Optimization - No reason to calculate the exact rainfall for all cells especially when we are averaging the values across neighbors multiple times anyways.
    Can probably calculate every other square, and set the non-calculated squares to be a simple average of their neighbor cells instead of computing the distance
    in all 8 directions for every single cell. Once that's done, the further averaging out of water levels will smooth things over.
-islandPlacer - currently have some commented out code that I'd like to get back in once we figure out an optimal blend of what size islands make
    for the most realistic looking land masses
-Investigate why mountains receive more precipitation even when surrounded by desert on both sides
-temperature and island size needs to scale to size of map
-lakeplacer I'm fairly sure needs a sufficiently large continent to place the lake farther from oceans so make sure that distance requirement
is taken into account with variable map sizes
-looks like past me created temporary neighbor variables just to get the count from that new temporary variable when I could just getNeighborCount directly, clean up
-down the road bug to fix: There's something that does stop the program from running but causes a rare edge case where, I'm suspecting, an island
    is placed near the left/right edges of the map in a way that causes it to stretch across the whole world.
*/
