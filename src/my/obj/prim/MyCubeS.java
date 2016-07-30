package my.obj.prim;

import javax.xml.bind.annotation.XmlRootElement;

import com.jogamp.opengl.GL2;

/**
 * A cube created with two quad strips
 */
@XmlRootElement(name="mycubeqs")
public class MyCubeS extends MyCube {
	
    private static final float[][] QS1 = { F, E, B, A, D, C, H, G, darkred };
    private static final float[][] QS2 = { C, A, G, E, H, F, D, B, darkblue };
    private static final float[][][] QSA = { QS1, QS2 };
    
    @Override
	protected
	void displayOnce(GL2 gl) {
        displayQuadStrip(gl, QSA);
    }
    
}
