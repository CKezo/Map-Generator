import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main extends JFrame{
    private final int WIDTH = 1200;
    private final int HEIGHT = 775;
    private static JTextArea textArea;
    private static Grid gameGrid = new Grid();
    private static Main frame = new Main();

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        //Return to below when you integrate the system output in the terminal into the main program
        /*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if(gd.isFullScreenSupported()){
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setMinimumSize(new Dimension(WIDTH, HEIGHT));
        }*/

        KeyListenerClass theKeyListener = new KeyListenerClass();
        theKeyListener.addKeyListener(theKeyListener);
        theKeyListener.setFocusable(true);
        add(theKeyListener);

        MouseListenerClass theMouseListener = new MouseListenerClass(this);
        this.addMouseListener(theMouseListener);
        theMouseListener.setFocusable(true);
        add(theMouseListener);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        add(scrollPane, BorderLayout.SOUTH);

        JButton earthViewButton = createButton("(E)arth View", e -> gameGrid.drawEarthMap());
        JButton biomesButton = createButton("(B)iomes", e -> gameGrid.drawBiomes());
        //JButton biomesButton = createButton("(D)irection of Wind: E", e -> gameGrid.draw);
        JButton waterButton = createButton("(W)ater Availability", e -> gameGrid.drawPrecipMap());
        JButton highTempButton = createButton("(H)ighest Temperature", e -> gameGrid.drawMaxTempMap());
        JButton lowTempButton = createButton("(L)owest Temperature", e -> gameGrid.drawMinTemp());
        JButton avgTempButton = createButton("(A)verage Temperature", e -> gameGrid.drawAvgTempMap());
        JButton rangeTempButton = createButton("(R)ange of Temperature", e -> gameGrid.drawMaxRangeMap());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(Box.createVerticalStrut(20));

        buttonPanel.add(earthViewButton);
        buttonPanel.add(biomesButton);
        buttonPanel.add(waterButton);
        buttonPanel.add(highTempButton);
        buttonPanel.add(lowTempButton);
        buttonPanel.add(avgTempButton);
        buttonPanel.add(rangeTempButton);

        buttonPanel.add(Box.createVerticalGlue());
        add(buttonPanel, BorderLayout.EAST);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusable(false);
        button.addActionListener(actionListener);
        Dimension maxSize = new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height);
        button.setMaximumSize(maxSize);
        return button;
    }
    public static void updateTextArea(String text) {
        textArea.append(text + "\n"); // Append new text with a newline
    }

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
-PRIORITY - Remove redundant text at the bottom once all buttons are implemented. This is now in progress - fix before moving on.
-Need CustomButton class to set max width of buttons so we can eventually have it scale dynamically with how much space the map itself takes up.
-Add inset offset for where buttons start at top right. Implement wind direction change and new map buttons.
-For the wind direction logic in the keylistener class, why are we drawing the earth map before hand? Tried commenting it out
    and setting the defineWaterAvail getColors() to getTrueColors() but that didn't fix it. Investigate further.
-Precipitation fix for how rivers/lakes affect systems - since the lakes on our map only represent giant lakes, they can still generate
    some nearby precipitation. River adjacency will no longer raise water to .8-.9 in adjacent spaces - for this, we simply want to change
    the biome from desert to grassland, grassland to deciduous forest, savana to rainforest. However, come back and give this approach
    some more thought about how this would fit in with a potential evolution simulator in terms of actual water availability in the square. Just
    set the water number in the adjacent squares to a set number based on what would qualify it for the corresponding biomes based on heat/current water.
-Optimization - No reason to calculate the exact rainfall for all cells especially when we are averaging the values across neighbors multiple times anyways.
    Can probably calculate every other square, and set the non-calculated squares to be a simple average of their neighbor cells instead of computing the distance
    in all 8 directions for every single cell. Once that's done, the further averaging out of water levels will smooth things over.
-IslandPlacer - currently have some commented out code that I'd like to get back in once we figure out an optimal blend of what size islands make
    for the most realistic looking land masses
-Investigate why mountains receive more precipitation even when surrounded by desert on both sides
-Temperature and island size needs to scale to size of map
-Lakeplacer I'm fairly sure needs a sufficiently large continent to place the lake farther from oceans so make sure that distance requirement
is taken into account with variable map sizes
-Looks like past me created temporary neighbor variables just to get the count from that new temporary variable when I could just getNeighborCount directly, clean up
-There's something that doesn't stop the program from running but causes a rare edge case where, I'm suspecting, an island
    is placed near the left/right edges of the map in a way that causes it to stretch across the whole world.
*/
