package my.obj.sc;

import com.jogamp.opengl.GL2;

class C {
	//public final String name;
	public final float[] v;
	public C (float[] a) {
		this.v = a;
	}
	public void display(GL2 gl) {
		gl.glColor3fv(v, 0);
	}
}