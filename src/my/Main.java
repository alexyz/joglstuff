package my;

import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.Timer;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

	private static final GLCanvas canvas = new GLCanvas();

	/**
	 * Repaint the canvas (call from AWT thread only).
	 */
	public static void display() {
		canvas.display();
	}

	public static void main(String[] args) {
		MainRenderer r = new MainRenderer();
		r.addListeners(canvas);
		canvas.addGLEventListener(r);
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

		Frame frame = new Frame("Main");
		frame.add(canvas);
		frame.setSize(640, 480);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.setVisible(true);
		canvas.requestFocus();

		// display every 25ms
		final Timer t = new Timer(25, null);
		t.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("display");
				canvas.display();
			}
		});
		t.start();
	}


}
