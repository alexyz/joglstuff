package my.obj.op;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.Dimension;
import java.awt.event.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="mousedragrotation")
public class MyMouseDragRotation extends MyRotation implements MouseListener, MouseMotionListener {
	
	@XmlAttribute
	public String axis;
	
	@XmlAttribute
	public float f = 1;
	
    private float prevMouseX, prevMouseY;

	private GLCanvas canvas;
    
    @Override
	public void addListeners(GLCanvas canvas) {
        this.canvas = canvas;
		canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }
    
	@Override
	public void mouseDragged (MouseEvent e) {
		float xp = e.getX();
        float yp = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = f * 360.0f * ((xp - prevMouseX) / size.width);
        float thetaX = f * 360.0f * ((prevMouseY - yp) / size.height);
        prevMouseX = xp;
        prevMouseY = yp;
        switch (String.valueOf(axis)) {
        	case "x": aoffset += thetaX; break;
        	case "y": aoffset += thetaY; break;
        }
        canvas.firePropertyChange("redisplay", 0, 1);
	}

	@Override
	public void mouseMoved (MouseEvent e) {
	}

	@Override
	public void mouseClicked (MouseEvent e) {
	}

	@Override
	public void mousePressed (MouseEvent e) {
		prevMouseX = e.getX();
        prevMouseY = e.getY();
	}

	@Override
	public void mouseReleased (MouseEvent e) {
	}

	@Override
	public void mouseEntered (MouseEvent e) {
	}

	@Override
	public void mouseExited (MouseEvent e) {
	}
    
}
