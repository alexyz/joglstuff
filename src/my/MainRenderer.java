package my;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.fixedfunc.GLPointerFunc;
import com.jogamp.opengl.glu.GLU;

import my.obj.MyFactory;
import my.obj.MyObjectList;

/**
 * TODO
 * plane-plane intersection
 * plane-line intersection
 */
public class MainRenderer implements GLEventListener {

    private static final GLU glu = new GLU();
    private final MyObjectList root;
    
    public MainRenderer() {
        MyFactory f = new MyFactory();
        
        //MyObjectList all = MyScene2.create(f);
        MyObjectList all = MyScene1.scene1(f);
        
        // could use f.mouseTranslator("z=W")
        
        // add to root with camera rotate/translate
        root = f.list(
                f.trans(0,-1,-5),
                f.keyTranslator(0.25f, "y=eq z=ws"), // need to call gluLookAt really
                f.keyRotator(2, "y=da"),
                f.mouseRotator(),
                //f.timeRotator(1, "y"),
                all);
        
    }
    
    public void addListeners(GLCanvas glc) {
    	root.addListeners(glc);
    }
    
    @Override
	public void display(GLAutoDrawable glad) {
    	//System.out.println("renderer display");
        root.update();
        
        final GL2 gl = glad.getGL().getGL2();
        // clear buffers to preset values
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        // replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        root.display(gl);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
        System.out.printf("displayChanged(gld,%s,%s)\n", modeChanged, deviceChanged);
    }

    @Override
	public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        
        //gl.glPolygonMode(GL.GL_FRONT, GL.GL_LINE);
        //gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE);
        //gl.glCullFace(GL.GL_BACK);
        gl.glEnable(GL.GL_CULL_FACE);
        
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
        
        // experimental
        gl.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY);
        
        root.init(glad);
    }

    @Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        System.out.printf("reshape(gld,%d,%d,%d,%d)\n", x, y, width, height);
        final GL2 gl = gLDrawable.getGL().getGL2();
        
        
        // specify which matrix is the current matrix
        // Applies subsequent matrix operations to the projection matrix stack
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        
        gl.glLoadIdentity();
        
        // set up a perspective projection matrix 
        // (field of view angle, aspect ratio, distance to the near clipping plane, ... far clipping plane)
        final float ar = height <= 0 ? 1 : ((float)width) / ((float)height);
        glu.gluPerspective(50f, ar, 1.0, 1000.0);
        
        // can rotate the camera here...
        // gl.glRotatef(10, 1, 1, 1);
        
        // TODO need to know x,y-x',y' for intersection with frustum at z=0
        // which you can do with a plane intersection with the y-lines...
        //frustum.init(gl);
        //System.out.println(frustum);
        
        // Applies subsequent matrix operations to the modelview matrix stack.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        
        gl.glLoadIdentity();
    }

	@Override
	public void dispose(GLAutoDrawable glad) {
		System.out.println("dispose");
	}

}
