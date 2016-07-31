package my.obj;

import java.awt.Color;
import java.util.*;

import javax.xml.bind.annotation.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.awt.GLCanvas;

import my.obj.op.*;
import my.obj.prim.*;
import my.obj.sc.*;

/**
 * A displayable object
 */
public abstract class MyObject {
	
	/** all concrete myobject classes */
	public static final List<Class<? extends MyObject>> CLASSES = Arrays.asList(
			MyObjectComp.class,
			MyObjectList.class,
			MyObjectSet.class,
			
			MyKeyRotation.class,
			MyKeyTranslation.class,
			MyMouseDragRotation.class,
			MyMouseWheelRotation.class,
			MyRotation.class,
			MyScale.class,
			MyTimeRotation.class,
			MyTranslation.class,
			
			MyCheckerBoard.class,
			MyCube.class,
			MyCubeS.class,
			MyPyramid.class,
			MySquareV.class,
			Terrain.class,
			
			SpLineBox.class,
			SpCube.class
		);
	
	public static final float[] white = color(Color.white);
	public static final float[] red = { 1, 0, 0 };
    public static final float[] green = { 0, 1, 0 };
    public static final float[] blue = { 0, 0, 1 };
    public static final float[] yellow = { 1, 1, 0 };
    public static final float[] darkblue = { 0, 0, 0.5f };
    public static final float[] darkgreen = { 0, 0.5f, 0 };
    public static final float[] darkred = { 0.5f, 0, 0 };
    public static final float[] lightgrey = { 2f/3, 2f/3, 2f/3 };
    public static final float[] darkgrey = { 1f/3, 1f/3, 1f/3 };
    
	public static char key(String k, int n) {
    	return k != null && k.length() > n ? k.charAt(n) : 0;
    }
	
	public static float[] color(Color c) {
		return new float[] { c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f };
	}
	
	@XmlTransient
	public boolean selected;
	
	public MyObject () {
		//
	}

	public abstract void display(GL2 gl);
	
	public void init(GLAutoDrawable drawable) {
		//
	}
	
	public void addListeners(GLCanvas canvas) {
		//
	}
	
	public void update(float t) {
		//
	}
	
	public int getChildCount() {
		return 0;
	}
	
	public MyObject getChild(int i) {
		throw new RuntimeException();
	}
	
	public int indexOf(MyObject child) {
		throw new RuntimeException();
	}
	
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public String toString () {
		XmlRootElement e = getClass().getAnnotation(XmlRootElement.class);
		return e != null && e.name() != null ? e.name() : getClass().getSimpleName();
	}
}
