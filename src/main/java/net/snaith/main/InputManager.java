package net.snaith.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
    public static boolean upPress, downPress, leftPress, rightPress, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch(code) {
            case KeyEvent.VK_W:
                upPress = true;
                break;
            case KeyEvent.VK_A:
                leftPress = true;
                break;
            case KeyEvent.VK_S:
                downPress = true;
                break;
            case KeyEvent.VK_D:
                rightPress = true;
                break;
            case KeyEvent.VK_ESCAPE:
                pausePressed = !pausePressed;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
