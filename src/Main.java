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
        gameGrid.riverPlacer();
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
