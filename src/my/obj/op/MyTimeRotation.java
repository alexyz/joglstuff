package my.obj.op;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;


public class MyTimeRotation extends MyRotation {
    // TODO should probable implement Rotatable
    private float aa = 0;
    private long t;
    public MyTimeRotation(float aps, String axi) {
        super(aps, axi);
    }
    public MyTimeRotation(float aps, float x, float y, float z) {
        super(aps,x,y,z);
    }
    @Override
	public boolean isUpdatable() {
        return true;
    }
    @Override
	public void update(long t) {
    	//System.out.println("update time rotation t/ns=" + ((t - this.t) / ns));
        aa = (a * ((t - this.t) / ns)) % 360f;
    }
    @Override
	public void display(GL2 gl) {
    	//System.out.println("update " + this + " rotation " + aa); 
        gl.glRotatef(aa, x, y, z);
    }
    @Override
    public void init(GLAutoDrawable glad) {
    	t = System.nanoTime();
    }
}