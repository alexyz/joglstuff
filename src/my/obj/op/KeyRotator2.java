package my.obj.op;

import java.awt.event.*;
import java.util.regex.*;
import com.jogamp.opengl.awt.GLCanvas;


public class KeyRotator2 extends Rotator {
    private char xup, xdown, yup, ydown, zup, zdown, reset = ' ';
    private final float delta;
    private final KeyListener kl = new KeyAdapter() {
        @Override
		public void keyTyped(KeyEvent e) {
            char c = Character.toLowerCase(e.getKeyChar());
            if (c == xup)
                xa += delta;
            else if (c == xdown)
                xa -= delta;
            else if (c == yup)
                ya += delta;
            else if (c == ydown)
                ya -= delta;
            else if (c == zup)
                za += delta;
            else if (c == zdown)
                za -= delta;
            else if (c == reset) {
                xa = 0f;
                ya = 0f;
                za = 0f;
            }
            System.out.printf("KeyRotator: xd=%.1f, yd=%.1f, zd=%.1f\n", xa, ya, za);
        }
    };
    public KeyRotator2(float delta, String keys) {
        this.delta = delta;
        Pattern p = Pattern.compile("(\\w)=(\\w\\w)");
        Matcher m = p.matcher(keys);
        while (m.find()) {
            char axis = m.group(1).charAt(0);
            char up = m.group(2).charAt(0);
            char down = m.group(2).charAt(1);
            if (axis == 'x') {
                xup = up;
                xdown = down;
            } else if (axis == 'y') {
                yup = up;
                ydown = down;
            } else if (axis == 'z') {
                zup = up;
                zdown = down;
            }
        }
    }
    
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addKeyListener(kl);
    }
    
}
