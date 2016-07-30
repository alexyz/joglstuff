package my.obj;

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
	
}
