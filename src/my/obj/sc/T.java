package my.obj.sc;

import com.jogamp.opengl.GL2;

class T {
	public final P[] v = new P[3];
	public T (P p0, P p1, P p2) {
		v[0] = p0;
		v[1] = p1;
		v[2] = p2;
	}
	public void display(GL2 gl) {
		for (P p : v) {
			gl.glVertex3fv(p.v, 0);
		}
	}
}
