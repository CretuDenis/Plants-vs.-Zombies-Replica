import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean[] keys = new boolean[256];
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_W:
                keys['W'] = true;
                break;
            case KeyEvent.VK_A:
                keys['A'] = true;
                break;
            case KeyEvent.VK_S:
                keys['S'] = true;
                break;
            case KeyEvent.VK_D:
                keys['D'] = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
                keys['W'] = false;
                break;
            case KeyEvent.VK_A:
                keys['A'] = false;
                break;
            case KeyEvent.VK_S:
                keys['S'] = false;
                break;
            case KeyEvent.VK_D:
                keys['D'] = false;
                break;
            default:
                break;
        }
    }
}
