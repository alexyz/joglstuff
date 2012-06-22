package my;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.util.ArrayList;

public class TMainRenderer implements GLEventListener {

    private static final GLU glu = new GLU();

    private final MouseRotator mr;
    private final KeyRotator kr;
    private final ArrayList<Integer> lists = new ArrayList<Integer>();
    private final TextRenderer renderer = new TextRenderer(new Font("Serif", Font.PLAIN, 72));
    
    private float xRotate, yRotate, zRotate;
    
    public TMainRenderer(Runnable updator) {
        mr = new MouseRotator(updator);
        kr = new KeyRotator();
    }
    
	@Override
	public void dispose(GLAutoDrawable drawable) {
		System.out.println("dispose");
	}
    
    @Override
	public void display(GLAutoDrawable glad) {
        final GL2 gl = glad.getGL().getGL2();
        
        // clear buffers to preset values
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        
        // replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        
        // multiply the current matrix by a translation matrix
        // i.e. move away from camera
        gl.glTranslatef(0f, 0f, -5f);
        
        // XXX
        gl.glRotatef(mr.viewRotateX, 1f, 0f, 0f);
        gl.glRotatef(mr.viewRotateY, 0f, 1f, 0f);

        // multiply the current matrix by a rotation matrix
        // once for each dimension
        gl.glRotatef(xRotate, 1f, 0f, 0f);
        gl.glRotatef(yRotate, 0f, 1f, 0f);
        gl.glRotatef(zRotate, 0f, 0f, 1f);
        
        gl.glPushMatrix();
        gl.glTranslatef(-1,1,-1);
        gl.glColor3fv(new float[] { 0.5f, 0.5f, 0.5f }, 0);
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluSphere(quad, 0.5f, 16, 16);
        gl.glPopMatrix();

        for (int n = 0; n < lists.size(); n++) {
            int list = lists.get(n);
            gl.glCallList(list);
        }
        
        gl.glTranslatef(1, 1, -1);
        renderer.begin3DRendering();
        renderer.draw3D(".hello", 0, 0, 0, 1f / 72f);
        renderer.end3DRendering();

        gl.glRotatef(90, 0, 1, 0);
        renderer.begin3DRendering();
        renderer.draw3D(".bye", 0, 0, 0, 1f / 72f);
        renderer.end3DRendering();

        xRotate += kr.xDelta;
        yRotate += kr.yDelta;
        zRotate += kr.zDelta;
    }
    
    private static void makeSquare(GL2 gl) {
        float[] darkblue = { 0, 0, 0.5f };
        
        // points - not an exact pyramid
        float[] A = { 0, 0, 0 };
        float[] B = { 0, 0, -1 };
        float[] C = { 1, 0, -1 };
        float[] D = { 1, 0, 0 };
        // squares + colour
        float[][] Q1 = { A, B, C, D, darkblue };
        // object
        float[][][] QA = { Q1 };
        
        drawSquareObject(gl, QA);
    }
    
    private static void drawSquareObject(GL2 gl, float[][][] QL) {
        gl.glBegin(GL2.GL_QUADS);
        for (int n = 0; n < QL.length; n++) {
            float[][] q = QL[n];
            gl.glColor3fv(q[4], 0);
            for (int m = 0; m < 4; m++) {
                gl.glVertex3fv(q[m], 0);
            }
        }
        gl.glEnd();
    }
    
    private static void drawTriangleObject(GL2 gl, float[][][] TL) {
        gl.glBegin(GL.GL_TRIANGLES);
        // set the current color
        // and specify a vertex
        for (int ti = 0; ti < TL.length; ti++) {
            float[][] t = TL[ti];
            gl.glColor3fv(t[3], 0);
            for (int vi = 0; vi < 3; vi++) {
                gl.glVertex3fv(t[vi], 0);
            }
        }
        gl.glEnd();
    }
    
    private static void makePyramid(GL2 gl) {
        // TODO abstract out TriangleObject and Pyramid
        // colour constants
        float[] red = { 1, 0, 0 };
        float[] green = { 0, 1, 0 };
        float[] blue = { 0, 0, 1 };
        float[] yellow = { 1, 1, 0 };
        // points - not an exact pyramid
        float[] A = { 0, 1, 0 };
        float[] B = { -1, -1, 1 };
        float[] C = { 1, -1, 1 };
        float[] D = { 0, -1, -1 };
        // triangles + colour
        float[][] T1 = { A, B, C, red };
        float[][] T2 = { A, C, D, green };
        float[][] T3 = { A, D, B, blue };
        float[][] T4 = { B, C, D, yellow };
        // object
        float[][][] TL = { T1, T2, T3, T4 };
        drawTriangleObject(gl, TL);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
        //
    }

    @Override
	public void init(GLAutoDrawable glad) {
        final GL2 gl = glad.getGL().getGL2();
        
        // select flat or smooth shading
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        // specify clear values for the color buffers
        gl.glClearColor(0f, 0f, 0f, 0f);
        // specify the clear value for the depth buffer
        gl.glClearDepth(1f);
        // do depth comparisons and update the depth buffer
        gl.glEnable(GL.GL_DEPTH_TEST);
        // specify the value used for depth buffer comparisons
        gl.glDepthFunc(GL.GL_LEQUAL);
        // the quality of color, texture coordinate, and fog coordinate interpolation
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        int p = gl.glGenLists(1);
        gl.glNewList(p, GL2.GL_COMPILE);
        makePyramid(gl);
        gl.glEndList();
        lists.add(p);
        
        int q = gl.glGenLists(1);
        gl.glNewList(q, GL2.GL_COMPILE);
        makeSquare(gl);
        gl.glEndList();
        lists.add(q);

    }
    
    public void addListeners(GLCanvas glc) {
        glc.addKeyListener(kr.kl);
        mr.addListeners(glc);    	
    }

    @Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50f, h, 1.0, 1000.0);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}
