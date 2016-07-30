package my.obj.op;

import java.awt.event.*;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.awt.GLCanvas;

import my.Main;

@XmlRootElement(name="keyrotation")
public class MyKeyRotation extends MyRotation implements KeyListener {
	
	@XmlAttribute
	public String keys;
	@XmlAttribute
    public float d;
	
    @Override
	public void addListeners(GLCanvas glc) {
        glc.addKeyListener(this);
    }

	@Override
	public void keyTyped (KeyEvent e) {
		char c = e.getKeyChar();
        if (c == key(keys,0)) {
        	aoffset -= d;
        } else if (c == key(keys,1)) {
        	aoffset += d;
        } else if (c == ' ') {
            aoffset = 0;
        }
        Main.display();
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
