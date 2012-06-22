package my.obj.prim;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import my.obj.MyObject;

/**
 * A visible, compiled object
 */
public abstract class MyPrimitive extends MyObject {
    
    protected static final Random rand = new Random();
    
    // colour constants
    protected static final float[] red = { 1, 0, 0 };
    protected static final float[] green = { 0, 1, 0 };
    protected static final float[] blue = { 0, 0, 1 };
    protected static final float[] yellow = { 1, 1, 0 };
    protected static final float[] darkblue = { 0, 0, 0.5f };
    protected static final float[] darkgreen = { 0, 0.5f, 0 };
    protected static final float[] darkred = { 0.5f, 0, 0 };
    protected static final float[] lightgrey = { 2f/3, 2f/3, 2f/3 };
    protected static final float[] darkgrey = { 1f/3, 1f/3, 1f/3 };
    /** half unity co-ordinate */
    protected static final float h = 0.5f;
    
    private int list;
    
    /**
     * Display this object. This will only be called once.
     */
    protected abstract void displayOnce(GL2 gl);
    
    @Override
	public final void init(GLAutoDrawable glad) {
        // may already be compiled
        if (list != 0)
            return;
        
        GL2 gl = glad.getGL().getGL2();
        list = gl.glGenLists(1);
        gl.glNewList(list, GL2.GL_COMPILE);
        displayOnce(gl);
        gl.glEndList();
        System.out.println("Compiled " + getClass() + " to " + list);
    }
    
    @Override
	public final void display(GL2 gl) {
        // displayImpl(gl);
        gl.glCallList(list);
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