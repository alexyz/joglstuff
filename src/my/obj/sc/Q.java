package my.obj.sc;

import com.jogamp.opengl.GL2;

class Q {
	public final P[] v = new P[4];
	public final C c;
	public Q (P p0, P p1, P p2, P p3, C c) {
		v[0] = p0;
		v[1] = p1;
		v[2] = p2;
		v[3] = p3;
		this.c = c;
	}
	public void display(GL2 gl) {
		gl.glColor3fv(c.v, 0);
		for (int n = 0; n < 4; n++) {
			gl.glVertex3fv(v[n].v, 0);
		}
	}
}