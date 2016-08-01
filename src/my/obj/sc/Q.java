package my.obj.sc;

import com.jogamp.opengl.GL2;

class Q {
	public final P[] v = new P[4];
	public Q (P p0, P p1, P p2, P p3) {
		v[0] = p0;
		v[1] = p1;
		v[2] = p2;
		v[3] = p3;
	}
	public void display(GL2 gl) {
		for (P p : v) {
			gl.glVertex3fv(p.v, 0);
		}
	}
	
}
