package my.obj;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * A list of objects to display.
 */
public class MyObjectList extends MyObject {
	protected final MyObject[] list;
	private final MyObject[] animated;

	public MyObjectList(MyObject... list) {
		this.list = list;
		ArrayList<MyObject> a = new ArrayList<MyObject>();
		getAnimated(a);
		this.animated = a.toArray(new MyObject[a.size()]);
	}

	public MyObjectList(List<MyObject> list) {
		this(list.toArray(new MyObjectList[list.size()]));
	}
	
	@Override
	public void init(GLAutoDrawable glad) {
		for (MyObject o : list)
			o.init(glad);
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

	private void getAnimated(List<MyObject> a) {
		for (MyObject o : list) {
			if (o.isUpdatable())
				a.add(o);
			if (o instanceof MyObjectList)
				((MyObjectList) o).getAnimated(a);
		}
	}

	@Override
	public void addListeners(GLCanvas glc) {
		for (MyObject o : list)
			o.addListeners(glc);
	}

	/**
	 * Update animated objects. Note that the list itself is not updatable.
	 */
	public void update() {
		if (animated.length > 0) {
			long t = System.nanoTime();
			for (MyObject a : animated) {
				//System.out.println("update " + a + " at " + t);
				a.update(t);
			}
		}
	}
}