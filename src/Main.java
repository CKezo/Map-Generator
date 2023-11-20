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
        //printed 9, 9, 9
        //System.out.println(frame.getInsets());      //Let's you know how much the frame offsets the panel
    }
    public static void main(String[] args) {
        newMap();
    }
}

//TO DO

/*
-Idea for more naturally connected looking land masses: Lot of sharp unnatural looking inlets where 2 placed islands meet. Once all green land mass placed, before noise,
    full grid sweep and check cardinal directions of land squares. If a direction has ocean, check perhaps 3-5 squares in that direction. If find another land square,
    fill in the ocean spaces in between with land as well
-Investigate why mountains receive more precipitation even when surrounded by desert on both sides
-temperature and island size needs to scale to size of map
-Grid class, privatize the variables again and make getters for them
-lakeplacer I'm fairly sure needs a sufficiently large continent to place the lake farther from oceans so make sure that distance requirement
is taken into account with variable map sizes
*/
