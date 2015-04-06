package my;

import java.awt.Dimension;
import java.awt.event.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.awt.GLCanvas;

import my.obj.MyObject;

class MouseRotator extends MyObject {
    float viewRotateX = 0, viewRotateY = 0, viewRotateZ = 0;
    private final Runnable run;
    private int prevMouseX, prevMouseY;
    
    private final MouseMotionListener mml = new MouseMotionAdapter() {
        // Methods required for the implementation of MouseMotionListener
        @Override
		public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            Dimension size = e.getComponent().getSize();
            float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
            float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);
            prevMouseX = x;
            prevMouseY = y;
            viewRotateX += thetaX;
            viewRotateY += thetaY;
            update();
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
            System.out.printf("MouseRotator: x=%.1f y=%.1f\n", viewRotateX, viewRotateY);
        }
    };
    
    private final MouseWheelListener mwl = new MouseWheelListener() {
        @Override
		public void mouseWheelMoved(MouseWheelEvent e) {
            int t = e.getScrollType();
            if (t == MouseWheelEvent.WHEEL_UNIT_SCROLL)
                viewRotateZ += (360f + (e.getUnitsToScroll() * 2f)) % 360f;
            update();
        }
    };
    
    public MouseRotator(Runnable run) {
        // TODO add some kind of offset and multiplier
        this.run = run;
    }
    
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addMouseListener(ml);
        glc.addMouseMotionListener(mml);
        glc.addMouseWheelListener(mwl);
    }
    
    @Override
	public void display(GL2 gl) {
        gl.glRotatef(viewRotateX, 1f, 0f, 0f);
        gl.glRotatef(viewRotateY, 0f, 1f, 0f);
        gl.glRotatef(viewRotateZ, 0f, 0f, 1f);
    }
    
    private void update() {
        if (run != null)
            run.run();
    }
        
}