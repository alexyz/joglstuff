package my.obj;

import java.util.List;

import javax.media.opengl.GL2;


/**
 * A list of objects to display independently.
 */
public class MyObjectSet extends MyObjectList {
    public MyObjectSet(MyObject... list) {
        super(list);
    }
    public MyObjectSet(List<MyObject> list) {
        super(list);
    }
    @Override
	public void display(GL2 gl) {
        for (MyObject o : list) {
            gl.glPushMatrix();
            o.display(gl);
            gl.glPopMatrix();
        }
    }
}