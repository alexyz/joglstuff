package my.obj.prim;

import java.awt.image.*;
import java.nio.*;
import java.util.Arrays;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.swing.*;

import com.jogamp.common.nio.Buffers;

/**
 * A terrain map generated from an image.
 */
public class Terrain extends MyPrimitive {

	@Override
	public void displayOnce(GL2 gl) {
		BufferedImage im = cross(blur(random()));

		FloatBuffer[] buf = getVertices(im, 0.25f);
		FloatBuffer v = buf[0], c = buf[1];
		int w = im.getWidth(), h = im.getHeight();

		gl.glTranslatef(-0.5f, 0, -0.5f);
		gl.glVertexPointer(3, GL.GL_FLOAT, 0, v);
		gl.glColorPointer(3, GL.GL_FLOAT, 0, c);
		gl.glBegin(GL2.GL_QUADS);
		for (int x = 0; x < w - 1; x++) {
			for (int y = 0; y < h - 1; y++) {
				gl.glArrayElement((x * w) + y);
				gl.glArrayElement((x * w) + y + 1);
				gl.glArrayElement(((x + 1) * w) + y + 1);
				gl.glArrayElement(((x + 1) * w) + y);
			}
		}
		gl.glEnd();
	}

	//

	private static FloatBuffer[] getVertices(BufferedImage im, float ymax) {
		int w = im.getWidth(), h = im.getHeight();
		float wf = w - 1, hf = h - 1, yUnit = ymax / 256f;
		FloatBuffer vbuf = Buffers.newDirectFloatBuffer(w * h * 3);
		FloatBuffer cbuf = Buffers.newDirectFloatBuffer(w * h * 3);

		for (int x = 0; x < w; x++) {
			for (int z = 0; z < h; z++) {
				int p = im.getRGB(x, z) & 0xff;
				vbuf.put(x / wf).put(yUnit * p).put(z / hf);
				float c = p / 256f;
				cbuf.put(c).put(c).put(c);
			}
		}

		vbuf.rewind();
		cbuf.rewind();
		return new FloatBuffer[] { vbuf, cbuf };
	}

	private static BufferedImage random() {
		int[] t = new int[36 * 36];
		for (int n = 0; n < t.length; n++)
			t[n] = rand.nextInt();

		// Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pix, 0, w))

		BufferedImage im = new BufferedImage(36, 36, BufferedImage.TYPE_BYTE_GRAY);
		for (int x = 0; x < 36; x++) {
			for (int y = 0; y < 36; y++) {
				im.setRGB(x, y, randgrey());
			}
		}
		return im;
	}

	private static int randgrey() {
		int p = rand.nextInt() & 0xff;
		int q = p | (p << 8) | (p << 16);
		return q;
	}

	private static BufferedImage blur(BufferedImage im) {
		float[] matrix = new float[9];
		Arrays.fill(matrix, 1f/9f);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix), ConvolveOp.EDGE_ZERO_FILL, null);
		BufferedImage bim = op.filter(im, null);
		return bim;
	}

	/**
	 * Cross adjust the given image.
	 */
	private static BufferedImage cross(BufferedImage im) {
		int w = im.getWidth(), h = im.getHeight();

		for (int x = 0; x < w; x++) {
			int p1 = im.getRGB(x, 1);
			int p2 = im.getRGB(x, h - 2);
			int q = av(p1, p2);
			im.setRGB(x, 0, q);
			im.setRGB(x, h - 1, q);
		}

		for (int y = 0; y < h; y++) {
			int p1 = im.getRGB(1, y);
			int p2 = im.getRGB(w - 2, y);
			int q = av(p1, p2);
			im.setRGB(0, y, q);
			im.setRGB(w - 1, y, q);
		}

		return im;
	}

	/**
	 * Average the two rgb values.
	 */
	private static int av(int p, int q) {
		int r = (((p >> 16) & 0xff) + ((q >> 16) & 0xff)) >> 1;
		int g = (((p >> 8) & 0xff) + ((q >> 8) & 0xff)) >> 1;
		int b = ((p & 0xff) + (q & 0xff)) >> 1;
		return b | (g << 8) | (r << 16);
	}

	public static void main(String[] args) {
		// Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pix, 0, w))
		BufferedImage im = random();
		BufferedImage bim = cross(blur(im));

		JPanel p = new JPanel();
		p.add(new JLabel("original", new ImageIcon(im.getScaledInstance(360, 360, 0)), 0));
		p.add(new JLabel("blurred", new ImageIcon(bim.getScaledInstance(360, 360, 0)), 0));
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);


	}

}
