import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerClass extends Grid implements MouseListener {
    public void mouseClicked(MouseEvent e) {
        double mouseX = Math.floor((e.getX() - (double)getLeftInset() - getTopBlackspace())/getCellWidth());     //3 and 26 are how many pixels the panel is offset from the sides of the window
        double mouseY = Math.floor((e.getY() - (double)getTopInset() - getTopBlackspace())/getCellHeight());
        if(mouseX >= 0 && mouseX < getCellColumnCount() && mouseY >= 0 && mouseY < getCellRowCount()){
            System.out.println((int)mouseX + " " + (int)mouseY);
            GridCell cell = Main.getGameGrid().getCellAtXY((int)mouseX, (int)mouseY);
            if(cell.isLake()){
                System.out.println("Lake");
            } else if (cell.isRiver()) {
                System.out.println("River");
            } else {
                System.out.println(cell.getBiome());
            }
        }
        System.out.println("____________");
    }
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
