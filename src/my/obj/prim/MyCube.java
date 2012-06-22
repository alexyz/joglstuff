package my.obj.prim;

import javax.media.opengl.GL2;


public class MyCube extends MyPrimitive {
    
    // vertices
    
    /** left top near */
    protected static final float[] A = { -h, 1, h }; 
    /** right top near */
    protected static final float[] B = { h, 1, h }; 
    /** left bottom near */
    protected static final float[] C = { -h, 0, h }; 
    /** right bottom near */
    protected static final float[] D = { h, 0, h }; 
    /** left top far */
    protected static final float[] E = { -h, 1, -h }; 
    /** right top far */
    protected static final float[] F = { h, 1, -h }; 
    /** left bottom far */
    protected static final float[] G = { -h, 0, -h }; 
    /** right bottom far */
    protected static final float[] H = { h, 0, -h };
    
    // squares + colours
    
    /** front */
    private static final float[][] Q1 = { A, C, D, B, darkblue };
    /** back */
    private static final float[][] Q2 = { E, F, H, G, darkblue };
    /** left */
    private static final float[][] Q3 = { A, E, G, C, darkgreen };
    /** right */
    private static final float[][] Q4 = { B, D, H, F, darkgreen };
    /** top */
    private static final float[][] Q5 = { E, A, B, F, darkred };
    /** bottom */
    private static final float[][] Q6 = { C, G, H, D, darkred };
    
    private static final float[][][] QA = { Q1, Q2, Q3, Q4, Q5, Q6 };
    
    @Override
	protected void displayOnce(GL2 gl) {
        displayQuads(gl, QA);
    }

}