package my.obj;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL2;

/**
 * A list of objects to display independently.
 */
@XmlRootElement(name="set")
public class MyObjectSet extends MyObjectList {
	
    @Override
	public void display(GL2 gl) {
        for (MyObject o : list) {
            gl.glPushMatrix();
            o.display(gl);
            gl.glPopMatrix();
        }
    }
    
}
