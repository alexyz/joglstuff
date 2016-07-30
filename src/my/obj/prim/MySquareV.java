package my.obj.prim;

import java.nio.FloatBuffer;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import my.obj.MyObject;

import com.jogamp.common.nio.Buffers;

@XmlRootElement(name="mysquare")
public class MySquareV extends MyObject {
    
    private int list;
    
    public MySquareV () {
    	//
	}
    
    @Override
	public void display(GL2 gl) {
        if (list == 0) {
            System.out.println("creating list...");
            float h = 0.5f;
            float vertices[] = new float[] { -h, 1, h, -h, 0, h, h, 0, h, h, 1, h };
            float colors[] = new float[] { 1, 0, 0, 0.75f, 0, 0, 0.5f, 0, 0, 0.25f, 0, 0 };

            FloatBuffer verticesBuf = Buffers.newDirectFloatBuffer(vertices.length);
            for (int i = 0; i < vertices.length; i++)
                verticesBuf.put(vertices[i]);
            verticesBuf.rewind();
            
            FloatBuffer coloursBuf = Buffers.newDirectFloatBuffer(colors.length);
            for (int i = 0; i < colors.length; i++)
                coloursBuf.put(colors[i]);
            coloursBuf.rewind();
            
            // must be direct...
            //FloatBuffer verticesBuf = FloatBuffer.wrap(vertices);
            //FloatBuffer coloursBuf = FloatBuffer.wrap(vertices);

            list = gl.glGenLists(1);
            gl.glNewList(list, GL2.GL_COMPILE);
            
            gl.glVertexPointer(3, GL.GL_FLOAT, 0, verticesBuf);
            gl.glColorPointer(3, GL.GL_FLOAT, 0, coloursBuf);
            gl.glBegin(GL2.GL_QUADS);
            for (int n = 0; n < 4; n++)
                gl.glArrayElement (n);
            gl.glEnd();
            
            gl.glEndList();
            
            for (int i = 0; i < colors.length; i++) {
                coloursBuf.put(0);
                verticesBuf.put(0);
            }
        }
        
        gl.glCallList(list);

    }
    
}
