package my;

import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class MainJFrame extends JFrame {
	
	public static void main(String[] args) {
		MainJFrame f = new MainJFrame();
		f.setVisible(true);
	}
	
	private final GLCanvas canvas = new GLCanvas();
	
	public MainJFrame () {
		super("Main [https://github.com/alexyz/joglstuff]");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(canvas);
		setSize(640, 480);
		
		MainRenderer r = new MainRenderer();
		r.addListeners(canvas);
		canvas.addGLEventListener(r);
		
		// display every 25ms
		final Timer t = new Timer(25, null);
		t.addActionListener(e -> display());
		t.start();
	}

	/**
	 * Repaint the canvas (call from AWT thread only).
	 */
	private void display() {
		if (isVisible()) {
			canvas.display();
		}
	}

}
