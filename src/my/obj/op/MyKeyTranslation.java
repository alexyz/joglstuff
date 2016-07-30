package my.obj.op;

import java.awt.event.*;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.awt.GLCanvas;

@XmlRootElement(name="keytranslation")
public class MyKeyTranslation extends MyTranslation implements KeyListener {
	
	@XmlAttribute
	public String xkeys;
	@XmlAttribute
	public String ykeys;
	@XmlAttribute
	public String zkeys;
	@XmlAttribute
    public float d;
	
	private GLCanvas canvas;
	
    @Override
	public void addListeners(GLCanvas canvas) {
        this.canvas = canvas;
		canvas.addKeyListener(this);
    }
    
	@Override
	public void keyTyped (KeyEvent e) {
		char c = e.getKeyChar();
        if (c == key(xkeys, 0)) {
        	xo -= 1;
        } else if (c == key(xkeys, 1)) {
        	xo += 1;
        }
        if (c == key(ykeys, 0)) {
        	yo -= 1;
        } else if (c == key(ykeys, 1)) {
        	yo += 1;
        }
        if (c == key(zkeys, 0)) {
        	zo -= 1;
        } else if (c == key(zkeys, 1)) {
        	zo += 1;
        }
        if (c == ' ') {
        	xo = 0;
        	yo = 0;
        	zo = 0;
        }
        canvas.firePropertyChange("redisplay", 0, 1);
	}

	@Override
	public void keyPressed (KeyEvent e) {
		//
	}

	@Override
	public void keyReleased (KeyEvent e) {
		//
	}

}
