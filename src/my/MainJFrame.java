package my;

import com.jogamp.opengl.awt.GLCanvas;

import my.obj.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

public class MainJFrame extends JFrame {

	@FunctionalInterface
	public static interface CheckedFunction<T> {
		public T apply () throws Exception;
	}
	
	public static <T> T rethrow (CheckedFunction<T> cf) {
		try {
			return cf.apply();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		MainJFrame f = new MainJFrame();
		f.setVisible(true);
	}
	
	private final GLCanvas canvas = new GLCanvas();
	private final AttrJPanel attr = new AttrJPanel();
	private final JTree tree;
	
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
		
		tree = new JTree(new MyObjectTreeModel(root));
		tree.addTreeSelectionListener(e -> select(e));
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed (MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup(e);
				}
			}
			@Override
			public void mouseReleased (MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup(e);
				}
			}
		});
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
	
	private void popup (MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getPoint().x, e.getPoint().y);
		if (path != null) {
			tree.setSelectionPath(path);
			JPopupMenu menu = createTreePopupMenu();
			menu.show(tree, e.getPoint().x, e.getPoint().y);
		}
	}
	
	private JPopupMenu createTreePopupMenu () {
		JMenuItem create = new JMenuItem("Create");
		create.addActionListener(e -> create());
		//create, move up, down, remove, wrap, merge?
		// save, load
		JPopupMenu menu = new JPopupMenu();
		menu.add(create);
		return menu;
	}
	
	private void create() {
		JDialog d = new JDialog(this, "Create", true);
		JComboBox<Class<? extends MyObject>> c = new JComboBox<>(new Vector<>(MyObject.CLASSES));
		JButton ok = new JButton("OK");
		ok.addActionListener(e -> {
			create2((Class<? extends MyObject>) c.getSelectedItem());
			d.setVisible(false);
		});
		JPanel p = new JPanel();
		p.add(c);
		p.add(ok);
		d.setContentPane(p);
		d.pack();
		d.setLocationRelativeTo(this);
		d.setVisible(true);
	}
	
	private void create2 (Class<? extends MyObject> c) {
		TreePath p = tree.getSelectionPath();
		Object[] nodes = p.getPath();
		MyObject o1 = (MyObject) nodes[nodes.length-1];
		MyObject newo = rethrow(() -> c.newInstance());
		if (o1 instanceof MyObjectList) {
			((MyObjectList) o1).list.add(newo);
			tree.getModel().valueForPathChanged(p, null);
		} else {
			MyObjectList o2 = (MyObjectList) nodes[nodes.length-2];
			o2.list.add(o2.indexOf(o1), newo);
			tree.getModel().valueForPathChanged(p.getParentPath(), null);
		}
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
