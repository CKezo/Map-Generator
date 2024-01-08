import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerClass extends Grid implements MouseListener {
    private Main mainWindow;

    public MouseListenerClass(Main mainWindow) {
        this.mainWindow = mainWindow;
    }
    public void mouseClicked(MouseEvent e) {
        double mouseX = Math.floor((e.getX() - (double)getLeftInset() - getTopBlackspace())/getCellWidth());     //3 and 26 are how many pixels the panel is offset from the sides of the window
        double mouseY = Math.floor((e.getY() - (double)getTopInset() - getTopBlackspace())/getCellHeight());
        if(mouseX >= 0 && mouseX < getCellColumnCount() && mouseY >= 0 && mouseY < getCellRowCount()){
            StringBuilder output = new StringBuilder();
            output.append((int)mouseX + " " + (int)mouseY);

            GridCell cell = mainWindow.getGameGrid().getCellAtXY((int)mouseX, (int)mouseY);
            if(cell.isLake()){
                output.append("\nLake");
            } else if (cell.isRiver()) {
                output.append("\nRiver");
            } else {
                output.append("\n").append(cell.getBiome());
            }
            output.append("\n-------------");
            mainWindow.updateTextArea(output.toString());
        }
    }
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
