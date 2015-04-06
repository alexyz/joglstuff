package my.obj.prim;

import com.jogamp.opengl.GL2;


/**
 * Tetrahedron (inaccurate)
 */
public class MyPyramid extends MyPrimitive {
    /** top */
    private static final float[] A = { 0, 1, 0 };
    /** left bottom near */
    private static final float[] B = { -h, 0, h };
    /** centre bottom far */
    private static final float[] C = { 0, 0, -h };
    /** right bottom near */
    private static final float[] D = { h, 0, h };
    
    // triangles + colour
    /** back left */
    private static final float[][] T1 = { A, C, B, red };
    /** back right */
    private static final float[][] T2 = { A, D, C, green };
    /** front */
    private static final float[][] T3 = { A, B, D, blue };
    /** bottom */
    private static final float[][] T4 = { B, C, D, yellow };
    private static final float[][][] TA = { T1, T2, T3, T4 };
    
    @Override
	public void displayOnce(GL2 gl) {
        displayTriangles(gl, TA);
    }
}