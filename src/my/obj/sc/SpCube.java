package my.obj.sc;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;

@XmlRootElement(name="spcube")
public class SpCube extends SpObject {
	
	private static final P p0 = new P(-1, -1, -1); // left bottom near
	private static final P p1 = new P(-1, -1, 1); // left bottom far
	private static final P p2 = new P(-1, 1, -1); // left top near
	private static final P p3 = new P(-1, 1, 1); // left top far
	private static final P p4 = new P(1, -1, -1); // right bottom near
	private static final P p5 = new P(1, -1, 1); // right bottom far
	private static final P p6 = new P(1, 1, -1); // right top near
	private static final P p7 = new P(1, 1, 1); // right top far
	
	// can only change one direction at a time
	// order implies facing direction...
	private static final Q q0 = new Q(p0, p1, p3, p2); // left
	private static final Q q1 = new Q(p6, p7, p5, p4); // right
	private static final Q q2 = new Q(p4, p5, p1, p0); // bottom
	private static final Q q3 = new Q(p2, p3, p7, p6); // top
	private static final Q q4 = new Q(p0, p2, p6, p4); // near
	private static final Q q5 = new Q(p5, p7, p3, p1); // far
	
	private static final Q[] qa = { q0, q1, q2, q3, q4, q5 };
	private static final C[] ca = { redc, darkredc, greenc, darkgreenc, bluec, darkbluec };
	
	@Override
	public void display (GL2 gl) {
		//glDisable(GL_CULL_FACE);
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 0.5f);
		displayQuads(gl, qa, ca);
		gl.glPopMatrix();
		//glEnable(GL_CULL_FACE);
	}
}
