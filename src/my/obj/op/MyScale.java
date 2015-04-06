package my.obj.op;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

/**
 * A scale.
 */
public class MyScale extends MyObject {
    protected final float x, y, z;
    /**
     * Create a constant scale for the given axis.
     */
    public MyScale(float s, String axi) {
        this.x = axi.indexOf("x") >= 0 ? s : 1;
        this.y = axi.indexOf("y") >= 0 ? s : 1;
        this.z = axi.indexOf("z") >= 0 ? s : 1;
    }
    /**
     * Create a constant scale.
     */
    public MyScale(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
	public void display(GL2 gl) {
        gl.glScalef(x, y, z);
    }
}