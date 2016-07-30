package my.obj;

import java.util.*;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * A list of objects to display.
 */
@XmlRootElement(name="list")
public class MyObjectList extends MyObject {
	
	@XmlMixed
	public final List<MyObject> list = new ArrayList<>();

	@Override
	public void init(GLAutoDrawable glad) {
		for (MyObject o : list) {
			o.init(glad);
		}
	}

	/**
	 * Display all items in the list, leaving the top matrix unchanged.
	 */
	@Override
	public void display(GL2 gl) {
		gl.glPushMatrix();
		for (MyObject o : list)
			o.display(gl);
		gl.glPopMatrix();
	}

	@Override
	public void addListeners(GLCanvas glc) {
		for (MyObject o : list) {
			o.addListeners(glc);
		}
	}

	@Override
	public void update (float t) {
		for (MyObject a : list) {
			a.update(t);
		}
	}

}
