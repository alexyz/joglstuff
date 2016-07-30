package my.obj.op;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;

import my.obj.*;

@XmlRootElement(name="scale")
public class MyScale extends MyVector {

	@Override
	public void display(GL2 gl) {
        gl.glScalef(x, y, z);
    }
	
}
