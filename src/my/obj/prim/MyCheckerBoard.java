package my.obj.prim;

import com.jogamp.opengl.GL2;


public class MyCheckerBoard extends MyPrimitive {
    private final float[][][] qa;
    public MyCheckerBoard(int sx, int sz) {
        // vertices (shared)
        float[][][] v = new float[sx + 1][sz + 1][3];
        for (int n = 0; n <= sx; n++) {
            for (int m = 0; m <= sz; m++) {
                float vx = ((1f * n) / sx) - h;
                float vy = 0;
                float vz = ((1f * m) / sz) - h;
                v[n][m] = new float[] {vx, vy, vz};
                //System.out.printf("v[%d][%d] = %s\n", n, m, Arrays.toString(v[n][m]));
            }
        }
        // quad graph
        float[][][] q = new float[sx * sz][5][];
        int i = 0;
        for (int n = 0; n < sx; n++) {
            for (int m = 0; m < sz; m++, i++) {
                q[i][0] = v[n][m];
                q[i][1] = v[n][m + 1];
                q[i][2] = v[n + 1][m + 1];
                q[i][3] = v[n + 1][m];
                q[i][4] = ((n & 1) ^ (m & 1)) == 1 ? lightgrey : darkgrey;
            }
        }
        
        qa = q;
    }
    
    @Override
	protected void displayOnce(GL2 gl) {
        displayQuads(gl, qa);
    }
}