package my.obj;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL2;

/**
 * A list of objects to compose.
 */
@XmlRootElement(name="comp")
public class MyObjectComp extends MyObjectList {
	
    @Override
	public void display(GL2 gl) {
        for (MyObject o : list) {
            o.display(gl);
        }
    }
}