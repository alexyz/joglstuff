package my.obj.op;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

/**
 * A rotation.
 */
public class MyRotation extends MyObject {
    protected final float a, x, y, z;
    /**
     * Create a fixed rotation for the given axis.
     */
    public MyRotation(float a, String axi) {
        this.a = a;
        this.x = axi.indexOf("x") >= 0 ? 1 : 0;
        this.y = axi.indexOf("y") >= 0 ? 1 : 0;
        this.z = axi.indexOf("z") >= 0 ? 1 : 0;
    }
    /**
     * Create a fixed rotation.
     */
    public MyRotation(float a, float x, float y, float z) {
        this.a = a;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
	public void display(GL2 gl) {
        gl.glRotatef(a, x, y, z);
    }
}