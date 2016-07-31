package my.obj.sc;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import my.obj.MyObject;

@XmlRootElement(name="splinebox")
public class SpLineBox extends MyObject {
	
	@Override
	public void display (GL2 gl) {
		gl.glPushMatrix();
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glLineWidth(2.0f);
		gl.glBegin(GL.GL_LINES);
		for (float a = -1; a <= 1; a+=2) {
			for (float b = -1; b <= 1; b+=2) {
				gl.glColor3fv(red, 0);
				gl.glVertex3f(-1, a, b);
				gl.glColor3fv(darkred, 0);
				gl.glVertex3f(1, a, b);
				
				gl.glColor3fv(green, 0);
				gl.glVertex3f(a, -1, b);
				gl.glColor3fv(darkgreen, 0);
				gl.glVertex3f(a, 1, b);
				
				gl.glColor3fv(blue, 0);
				gl.glVertex3f(a, b, -1);
				gl.glColor3fv(darkblue, 0);
				gl.glVertex3f(a, b, 1);
			}
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
	
}
