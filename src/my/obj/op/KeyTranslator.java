package my.obj.op;

import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jogamp.opengl.awt.GLCanvas;


// TODO keytrans+rotate, e.g. keyRotator("x=ad") keyTranslator("z=ws y=qe", 0.25f)
public class KeyTranslator extends MyTranslation {
    // these could just be local to the constructor
    private char xup, xdown, yup, ydown, zup, zdown, reset = ' ';
    private final float delta;
    private final KeyListener kl = new KeyAdapter() {
        @Override
		public void keyTyped(KeyEvent e) {
            char c = Character.toLowerCase(e.getKeyChar());
            if (c == xup)
                x += delta;
            else if (c == xdown)
                x -= delta;
            else if (c == yup)
                y += delta;
            else if (c == ydown)
                y -= delta;
            else if (c == zup)
                z += delta;
            else if (c == zdown)
                z -= delta;
            else if (c == reset) {
                x = 0f;
                y = 0f;
                z = 0f;
            }
            System.out.printf("KeyTranslator: xd=%.1f, yd=%.1f, zd=%.1f\n", x, y, z);
        }
    };
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addKeyListener(kl);
    }
    
    public KeyTranslator(float delta, String keys) {
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
    
}
