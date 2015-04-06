package my;

import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.Timer;

import java.awt.Frame;
import java.awt.event.*;

/**
 * Text rendering example
 */
public class TMain {

	public static void main(String[] args) {
		Frame frame = new Frame("Main2");
		final GLCanvas canvas = new GLCanvas();
		Runnable updater = new Runnable() {
			@Override
			public void run() {
				canvas.display();
			}
		};
		TMainRenderer r = new TMainRenderer(updater);
		r.addListeners(canvas);
		canvas.addGLEventListener(r);
		frame.add(canvas);
		frame.setSize(640, 480);
		//frame.setUndecorated(true);
		//int size = frame.getExtendedState();
		//size |= Frame.MAXIMIZED_BOTH;
		//frame.setExtendedState(size);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		canvas.requestFocus();

		// TODO need a game loop
		// update the renderer every 16ms

		// display every 16ms
		
         final Timer t = new Timer(16, null);
         t.addActionListener(new ActionListener() {
             @Override
			public void actionPerformed(ActionEvent e) {
                 canvas.display();
             }
         });
         t.start();
		 
	}


}
