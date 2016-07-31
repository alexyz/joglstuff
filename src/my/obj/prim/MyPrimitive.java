package my.obj.prim;

import java.util.Random;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import my.obj.MyObject;

/**
 * A visible, compiled object
 */
public abstract class MyPrimitive extends MyObject {
    
    protected static final Random rand = new Random();
    
    /** half unity co-ordinate */
    protected static final float h = 0.5f;
    
    private int list;
    
    /**
     * Display this object. This will only be called once.
     */
    protected abstract void displayOnce(GL2 gl);
    
    @Override
	public final void init(GLAutoDrawable drawable) {
        // may already be compiled
        if (list != 0)
            return;
        
        GL2 gl = drawable.getGL().getGL2();
        list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        displayOnce(gl);
        gl.glEndList();
        System.out.println("Compiled " + getClass() + " to " + list);
    }
    
    @Override
	public final void display(GL2 gl) {
    	gl.glCallList(list);
    	if (selected) {
    		gl.glPushMatrix();
    		gl.glBegin(GL.GL_LINES);
    		gl.glLineWidth(2.0f);
    		
    		gl.glColor3fv(white, 0);
			gl.glVertex3f(0, 0, -1);
			gl.glColor3fv(white, 0);
			gl.glVertex3f(0, 0, 1);
			
			gl.glColor3fv(white, 0);
			gl.glVertex3f(0, -1, 0);
			gl.glColor3fv(white, 0);
			gl.glVertex3f(0, 1, 0);
			
			gl.glColor3fv(white, 0);
			gl.glVertex3f(-1, 0, 0);
			gl.glColor3fv(white, 0);
			gl.glVertex3f(1, 0, 0);
			
			gl.glEnd();
			gl.glPopMatrix();
    	}
    }
    
    protected static final void displayTriangles(GL2 gl, float[][][] TL) {
        display(gl, GL.GL_TRIANGLES, TL);
    }
    
    protected static final void displayQuads(GL2 gl, float[][][] QL) {
        display(gl, GL2.GL_QUADS, QL);
    }
    
    protected static final void displayQuadStrip(GL2 gl, float[][][] QL) {
        display(gl, GL2.GL_QUAD_STRIP, QL);
    }
    
    /**
     * FIXME need to show quad strips seperatly, also supply colours seperatly
     */
    private static void display(GL2 gl, int type, float[][][] polys) {
        int colourIndex = polys[0].length - 1;
        gl.glBegin(type);
        for (int n = 0; n < polys.length; n++) {
            float[][] p = polys[n];
            if (p == null)
                throw new RuntimeException("invalid quad at Q[" + n + "]");
            gl.glColor3fv(p[colourIndex], 0);
            for (int m = 0; m < colourIndex; m++) {
                gl.glVertex3fv(p[m], 0);
            }
        }
        gl.glEnd();
    }
}