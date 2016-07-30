package my.obj.op;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;

import my.obj.*;

@XmlRootElement(name="translation")
public class MyTranslation extends MyVector {
	
	protected float xo, yo, zo;
    
    @Override
	public void display(GL2 gl) {
        gl.glTranslatef(x + xo, y + yo, z + zo);
    }
}
