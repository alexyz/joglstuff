package my;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyRotator {
    float xDelta, yDelta, zDelta;
    public final KeyListener kl = new KeyAdapter() {
        @Override
		public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
        @Override
		public void keyTyped(KeyEvent e) {
            char c = Character.toLowerCase(e.getKeyChar());
            if (c == 'a')
                xDelta += 0.5f;
            else if (c == 'z')
                xDelta -= 0.5f;
            else if (c == 's')
                yDelta += 0.5f;
            else if (c == 'x')
                yDelta -= 0.5f;
            else if (c == 'd')
                zDelta += 0.5f;
            else if (c == 'c')
                zDelta -= 0.5f;
            else if (c == ' ') {
                xDelta = 0f;
                yDelta = 0f;
                zDelta = 0f;
            }
            System.out.printf("KeyRotator: xd=%.1f, yd=%.1f, zd=%.1f\n", xDelta, yDelta, zDelta);
        }
    };
}