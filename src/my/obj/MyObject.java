package my.obj;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;

/**
 * A displayable object
 */
public abstract class MyObject {

	public static final float ms = 1000, us = 1000000, ns = 1000000000;

	public abstract void display(GL2 gl);
	
	/**
	 * Compile the object (if required)
	 */
	public void init(GLAutoDrawable glad) {
		//
	}
	
	/**
	 * Set up any objects that have mouse or keyboard listeners.
	 */
	public void addListeners(GLCanvas glc) {
		//
	}
	/**
	 * Set the time.
	 */
	public void update(long t) {
		throw new RuntimeException();
	}
	/**
	 * Returns true if this object requires the time.
	 */
	public boolean isUpdatable() {
		return false;
	}
}







