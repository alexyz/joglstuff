package my;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

import my.obj.MyObject;

public class MainRenderer implements GLEventListener, PropertyChangeListener {

	public static final float ms = 1000, us = 1000_000, ns = 1000_000_000;
    private static final GLU glu = new GLU();
    private final MyObject root;
    private long startTimeNano;
    private boolean displayed;
	private GLAutoDrawable drawable;
    
    public MainRenderer(MyObject root) {
		this.root = root;
    }
    
    public void addListeners(GLCanvas canvas) {
    	root.addListeners(canvas);
    	canvas.addPropertyChangeListener(this);
    }
    
    @Override
	public void display(GLAutoDrawable drawable) {
    	//System.out.println("renderer display");
    	long nt = System.nanoTime();
    	float t;
    	if (startTimeNano == 0) {
    		startTimeNano = nt;
    		t = 0;
    	} else {
    		t = (nt - startTimeNano) / ns;
    	}
        root.update(t);
        
        final GL2 gl = drawable.getGL().getGL2();
        // clear buffers to preset values
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        // replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        root.display(gl);
        
        displayed = false;
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        System.out.printf("displayChanged(gld,%s,%s)\n", modeChanged, deviceChanged);
    }

    @Override
	public void init(GLAutoDrawable drawable) {
        this.drawable = drawable;
        
		GL2 gl = drawable.getGL().getGL2();
        
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
        
        root.init(drawable);
    }

    @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.printf("reshape(gld,%d,%d,%d,%d)\n", x, y, width, height);
        final GL2 gl = drawable.getGL().getGL2();
        
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

	@Override
	public void propertyChange (PropertyChangeEvent e) {
		if (e.getPropertyName().equals("redisplay")) {
			if (!displayed) { 
				drawable.display();
				displayed = true;
			}
		} else {
			System.out.println("pce " + e.getPropertyName());
		}
	}

}
