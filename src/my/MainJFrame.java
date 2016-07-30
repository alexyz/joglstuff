package my;

import com.jogamp.opengl.awt.GLCanvas;

import my.obj.*;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;

public class MainJFrame extends JFrame {
	
	public static void main(String[] args) {
		MainJFrame f = new MainJFrame();
		f.setVisible(true);
	}
	
	private final GLCanvas canvas = new GLCanvas();
	private final AttrJPanel attr = new AttrJPanel();
	
	public MainJFrame () {
		super("Main [https://github.com/alexyz/joglstuff]");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas.setPreferredSize(new Dimension(640,480));
		MyFactory f = new MyFactory();
		MyObject all = MyScene1.scene1(f);
        // add to root with camera rotate/translate
        MyObject root = f.list(
                f.trans(0,-1,-5),
                f.keyTranslator(0.25f, null, "eq", "ws"), // need to call gluLookAt really
                f.keyRotator(2, null, "da", null),
                f.mouseRotator(),
                //f.timeRotator(1, "y"),
                all);
		
		JTree tree = new JTree(new MyObjectTreeModel(root));
		tree.addTreeSelectionListener(e -> select(e));
		JScrollPane treeSp = new JScrollPane(tree);
		JSplitPane treeAttrSp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, treeSp, attr);
		JSplitPane treeAttrCanvasSp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treeAttrSp, canvas);
		setContentPane(treeAttrCanvasSp);
		pack();
		
		MainRenderer r = new MainRenderer(root);
		r.addListeners(canvas);
		canvas.addGLEventListener(r);
		
		// display every 25ms
		final Timer t = new Timer(50, null);
		t.addActionListener(e -> display());
		t.start();
	}

	private void select (TreeSelectionEvent e) {
		Object o = e.getPath().getLastPathComponent();
		if (o instanceof MyObject) {
			attr.setObject((MyObject)o);
		} else {
			attr.setObject(null);
		}
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
