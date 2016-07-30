package my.obj.op;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;

import my.obj.*;

@XmlRootElement(name="rotation")
public class MyRotation extends MyVector {
	
	@XmlAttribute
    public float a;
	
	protected float aoffset;
	
    @Override
	public void display(GL2 gl) {
        gl.glRotatef(a + aoffset, x, y, z);
    }
    
}
