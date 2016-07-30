package my.obj.op;

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.event.*;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="mousewheelrotation")
public class MyMouseWheelRotation extends MyRotation implements MouseWheelListener {
	
    private GLCanvas canvas;

	@Override
	public void addListeners(GLCanvas canvas) {
        this.canvas = canvas;
		canvas.addMouseWheelListener(this);
    }

	@Override
	public void mouseWheelMoved (MouseWheelEvent e) {
		int t = e.getScrollType();
        if (t == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            aoffset += (360f + (e.getUnitsToScroll() * 2f)) % 360f;
        }
        canvas.firePropertyChange("redisplay", 0, 1);
	}
	
}
