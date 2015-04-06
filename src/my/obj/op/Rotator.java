package my.obj.op;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

/**
 * A dynamic rotation.
 * FIXME very similar to Rotate.
 */
public abstract class Rotator extends MyObject {
    protected float xa = 0, ya = 0, za = 0;
    private float xv = 1, yv = 1, zv = 1;
    public final Rotator rotate(float xa, float ya, float za) {
        this.xa = xa;
        this.ya = ya;
        this.za = za;
        return this;
    }
    @Override
	public final void display(GL2 gl) {
        if (xv != 0f)
            gl.glRotatef(xa, xv, 0f, 0f);
        if (yv != 0f)
            gl.glRotatef(ya, 0f, yv, 0f);
        if (zv != 0f)
            gl.glRotatef(za, 0f, 0f, zv);
    }
}
