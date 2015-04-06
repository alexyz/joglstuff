package my.obj.op;

import com.jogamp.opengl.awt.GLCanvas;

import my.Main;

import java.awt.Dimension;
import java.awt.event.*;

public class MouseRotator2 extends Rotator {
    private int prevMouseX, prevMouseY;
    
    private final MouseMotionListener mml = new MouseMotionAdapter() {
        // Methods required for the implementation of MouseMotionListener
        @Override
		public void mouseDragged(MouseEvent e) {
            int xp = e.getX();
            int yp = e.getY();
            Dimension size = e.getComponent().getSize();
            float thetaY = 360.0f * ((float) (xp - prevMouseX) / (float) size.width);
            float thetaX = 360.0f * ((float) (prevMouseY - yp) / (float) size.height);
            prevMouseX = xp;
            prevMouseY = yp;
            xa += thetaX;
            ya += thetaY;
            Main.display();
        }
    };

    private final MouseListener ml = new MouseAdapter() {
        // Methods required for the implementation of MouseListener
        @Override
		public void mousePressed(MouseEvent e) {
            prevMouseX = e.getX();
            prevMouseY = e.getY();
        }
        @Override
		public void mouseReleased(MouseEvent e) {
            System.out.printf("MouseRotator: x=%.1f y=%.1f\n", xa, ya);
        }
    };
    
    private final MouseWheelListener mwl = new MouseWheelListener() {
        @Override
		public void mouseWheelMoved(MouseWheelEvent e) {
            int t = e.getScrollType();
            if (t == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                za += (360f + (e.getUnitsToScroll() * 2f)) % 360f;
            Main.display();
        }
    };
    
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addMouseListener(ml);
        glc.addMouseMotionListener(mml);
        glc.addMouseWheelListener(mwl);
    }
    
}
