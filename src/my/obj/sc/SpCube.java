package my.obj.sc;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;

import my.obj.MyObject;

@XmlRootElement(name="spcube")
public class SpCube extends MyObject {
	
	static C redc = new C(red);
	static C darkredc = new C(darkred);
	static C greenc = new C(green);
	static C darkgreenc = new C(darkgreen);
	static C bluec = new C(blue);
	static C darkbluec = new C(darkblue);
	
	static void displayQuads(GL2 gl, Q[] qa, int len) {
		gl.glBegin(GL2.GL_QUADS);
		for (int n = 0; n < len; n++) {
			qa[n].display(gl);
		}
		gl.glEnd();
	}
	
	@Override
	public void display (GL2 gl) {
			P p0 = new P(-1, -1, -1); // left bottom near
			P p1 = new P(-1, -1, 1); // left bottom far
			P p2 = new P(-1, 1, -1); // left top near
			P p3 = new P(-1, 1, 1); // left top far
			P p4 = new P(1, -1, -1); // right bottom near
			P p5 = new P(1, -1, 1); // right bottom far
			P p6 = new P(1, 1, -1); // right top near
			P p7 = new P(1, 1, 1); // right top far

			// can only change one direction at a time
			// order implies facing direction...
			Q q0 = new Q(p0, p1, p3, p2, redc); // left
			Q q1 = new Q(p6, p7, p5, p4, darkredc); // right
			Q q2 = new Q(p4, p5, p1, p0, greenc); // bottom
			Q q3 = new Q(p2, p3, p7, p6, darkgreenc); // top
			Q q4 = new Q(p0, p2, p6, p4, bluec); // near
			Q q5 = new Q(p5, p7, p3, p1, darkbluec); // far

			Q[] qa = new Q[] { q0, q1, q2, q3, q4, q5 };

			//glDisable(GL_CULL_FACE);
			gl.glPushMatrix();
			gl.glScalef(0.5f, 0.5f, 0.5f);
			displayQuads(gl, qa, 6);
			gl.glPopMatrix();
			//glEnable(GL_CULL_FACE);
		}
}
