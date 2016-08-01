package my.obj.sc;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL2;

/** it's a tetrahedron */
@XmlRootElement(name="sptet")
public class SpTet extends SpObject {
	
	private static final float sqrt2 = (float) Math.sqrt(2.0);
	
	private static final P p1 = new P(1,0,-1/sqrt2); // right
	private static final P p2 = new P(0,1,1/sqrt2); // far
	private static final P p3 = new P(-1,0,-1/sqrt2); // left
	private static final P p4 = new P(0,-1,1/sqrt2); // near
	
	private static final T t1 = new T(p3,p2,p1);
	private static final T t2 = new T(p2,p3,p4);
	private static final T t3 = new T(p3,p1,p4);
	private static final T t4 = new T(p4,p1,p2);
	
	private static final T[] ta = { t1, t2, t3, t4 };
	private static final C[] ca = { redc, greenc, bluec, darkbluec };
	
	@Override
	public void display (GL2 gl) {
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 0.5f);
		displayTriangles(gl, ta, n -> ca[n]);
		gl.glPopMatrix();
	}
	
}
