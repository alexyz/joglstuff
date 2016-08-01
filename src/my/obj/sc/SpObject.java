package my.obj.sc;

import java.util.function.*;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

public abstract class SpObject extends MyObject {
	
	static C redc = new C(red);
	static C darkredc = new C(darkred);
	static C greenc = new C(green);
	static C darkgreenc = new C(darkgreen);
	static C bluec = new C(blue);
	static C darkbluec = new C(darkblue);
	
	static void displayQuads(GL2 gl, Q[] qa, C[] ca) {
		gl.glBegin(GL2.GL_QUADS);
		for (int n = 0; n < qa.length; n++) {
			ca[n].display(gl);
			qa[n].display(gl);
		}
		gl.glEnd();
	}
	
	static void displayTriangles(GL2 gl, T[] ta, IntFunction<C> f) {
		gl.glBegin(GL2.GL_TRIANGLES);
		for (int n = 0; n < ta.length; n++) {
			f.apply(n).display(gl);
			ta[n].display(gl);
		}
		gl.glEnd();
	}
}
