package my.obj.op;

import com.jogamp.opengl.awt.GLCanvas;

import my.Main;

import java.awt.Dimension;
import java.awt.event.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="mousedragrotation")
public class MyMouseDragRotation extends MyRotation implements MouseListener, MouseMotionListener {
	
	@XmlAttribute
	public String axis;
	
    private int prevMouseX, prevMouseY;
    
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addMouseListener(this);
        glc.addMouseMotionListener(this);
    }
    
	@Override
	public void mouseDragged (MouseEvent e) {
		int xp = e.getX();
        int yp = e.getY();
        Dimension size = e.getComponent().getSize();
        float thetaY = 360.0f * ((float) (xp - prevMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (prevMouseY - yp) / (float) size.height);
        prevMouseX = xp;
        prevMouseY = yp;
        switch (String.valueOf(axis)) {
        	case "x": aoffset += thetaX; break;
        	case "y": aoffset += thetaY; break;
        }
        Main.display();
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
