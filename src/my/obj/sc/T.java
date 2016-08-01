package my.obj.sc;

import com.jogamp.opengl.GL2;

class T {
	public final P[] v = new P[3];
	public final C c;
	public T (P p0, P p1, P p2, C c) {
		v[0] = p0;
		v[1] = p1;
		v[2] = p2;
		this.c = c;
	}
	public void display(GL2 gl) {
		gl.glColor3fv(c.v, 0);
		for (P p : v) {
			gl.glVertex3fv(p.v, 0);
		}
	}
}
