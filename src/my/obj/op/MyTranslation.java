package my.obj.op;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

/**
 * A translation.
 */
public class MyTranslation extends MyObject {
    protected float x, y, z;
    /**
     * Create a translation.
     */
    public MyTranslation() {
        //
    }
    /**
     * Update the translation.
     */
    public MyTranslation translate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    @Override
	public void display(GL2 gl) {
        gl.glTranslatef(x, y, z);
    }
}