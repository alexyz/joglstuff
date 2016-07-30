package my.obj;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * A displayable object
 */
public abstract class MyObject {

	public static char key(String k, int n) {
    	return k != null && k.length() > n ? k.charAt(n) : 0;
    }

	public abstract void display(GL2 gl);
	
	public void init(GLAutoDrawable glad) {
		//
	}
	
	public void addListeners(GLCanvas glc) {
		//
	}
	
	public void update(float t) {
		//
	}
	
	public int getChildCount() {
		return 0;
	}
	
	public MyObject getChild(int i) {
		throw new RuntimeException();
	}
	
	public int indexOf(MyObject child) {
		throw new RuntimeException();
	}
	
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public String toString () {
		XmlRootElement e = getClass().getAnnotation(XmlRootElement.class);
		return e != null && e.name() != null ? e.name() : getClass().getSimpleName();
	}
}
