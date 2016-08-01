package my.obj.sc;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

public abstract class SpObject extends MyObject {
	
	static C redc = new C(red);
	static C darkredc = new C(darkred);
	static C greenc = new C(green);
	static C darkgreenc = new C(darkgreen);
	static C bluec = new C(blue);
	static C darkbluec = new C(darkblue);
	
	static void displayQuads(GL2 gl, Q[] qa) {
		gl.glBegin(GL2.GL_QUADS);
		for (int n = 0; n < qa.length; n++) {
			qa[n].display(gl);
		}
		gl.glEnd();
	}
	
	static void displayTriangles(GL2 gl, T[] ta) {
		gl.glBegin(GL2.GL_TRIANGLES);
		for (T t : ta) {
			t.display(gl);
		}
		gl.glEnd();
	}
}
