package untaek.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key_Listener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed : " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

        System.out.println("keyReleased : " + e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {

        System.out.println("keyTyped : " + e.getKeyCode());
    }
}
