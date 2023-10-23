import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerClass extends Grid implements MouseListener {
    public void mouseClicked(MouseEvent e) {
        double mouseX = Math.floor((e.getX()-3)/cellWidth);     //3 and 26 are how many pixels the panel is offset from the sides of the window
        double mouseY = Math.floor((e.getY()-26)/cellHeight);
        //double mouseX = e.getX();
        //double mouseY = e.getY();
        //System.out.println(Main.gameGrid.grid[(int) mouseX][(int) mouseY].speciesPresent);
        System.out.println("Species Present:");
        for (int sp = 0; sp < Main.gameGrid.grid[(int) mouseX][(int) mouseY].speciesPresent.size(); sp++) {
            //System.out.println(((Species)Main.gameGrid.grid[(int) mouseX][(int) mouseY].speciesPresent.get(sp)).name + " population: " + ((Colony)Main.gameGrid.grid[(int) mouseX][(int) mouseY].colonies.get(sp)).totalNumber);
            //System.out.println(((Species)Main.gameGrid.grid[(int) mouseX][(int) mouseY].speciesPresent.get(sp)).idealTempLow);
        }
        System.out.println((int)mouseX + " " + (int)mouseY);
        System.out.println("____________");
    }
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
