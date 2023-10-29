import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerClass extends Grid implements MouseListener {
    public void mouseClicked(MouseEvent e) {
        double mouseX = Math.floor((e.getX() - (double)leftInset - topBlackspace)/cellWidth);     //3 and 26 are how many pixels the panel is offset from the sides of the window
        double mouseY = Math.floor((e.getY() - (double)topInset - topBlackspace)/cellHeight);
        if(mouseX >= 0 && mouseX < cellColumnCount && mouseY >= 0 && mouseY < cellRowCount){
            System.out.println((int)mouseX + " " + (int)mouseY);
        }
        System.out.println("____________");
    }
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
