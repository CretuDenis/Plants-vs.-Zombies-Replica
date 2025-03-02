import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    public static boolean mouseClicked = false;
    public static boolean mouseReleased = true;
    public static int mouseX = 0,mouseY = 0;

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked = true;
        mouseReleased = false;
        PlantManager.placePlant();
        PlantManager.getCurrentCursorPlant();
        //PlantManager.debugPlantList();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
        mouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)e.getPoint().getX();
        mouseY = (int)e.getPoint().getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)e.getPoint().getX();
        mouseY = (int)e.getPoint().getY();

    }
}
