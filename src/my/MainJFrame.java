package my;

import com.jogamp.opengl.awt.GLCanvas;

import my.obj.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.InputStream;
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
	
	public static void main(String[] args) throws Exception {
		MainJFrame f = new MainJFrame();
		f.setVisible(true);
		Thread.sleep(300000);
		System.exit(0);
	}
	
	private final List<MyObject> selected = new ArrayList<>();
	private final GLCanvas canvas = new GLCanvas();
	private final AttrJPanel attr = new AttrJPanel();
	private final JTree tree;
	
	public MainJFrame () throws Exception {
		super("Main [https://github.com/alexyz/joglstuff]");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas.setPreferredSize(new Dimension(640,480));
		
		MyFactory f = new MyFactory();
		//MyObject all = MyScene1.scene1(f);
		MyObject all;
		try (InputStream is = getClass().getResourceAsStream("/scene1.xml")) {
			all = XmlUtil.load(is);
		}
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
	
	private static JMenuItem item (String text, ActionListener l) {
		JMenuItem item = new JMenuItem(text);
		item.addActionListener(l);
		return item;
	}
	
	private JPopupMenu createTreePopupMenu () {
		//create, move up, down, remove, wrap, replace, merge?
		// save, load
		JPopupMenu menu = new JPopupMenu();
		menu.add(item("Create", e -> create()));
		menu.add(item("Remove", e -> remove()));
		menu.add(item("Move up", e -> move(-1)));
		menu.add(item("Move down", e -> move(1)));
		return menu;
	}
	
	private void move (int off) {
		TreePath[] a = tree.getSelectionPaths();
		if (a.length != 1) {
			JOptionPane.showMessageDialog(this, "Can't move multiple objects");
			return;
		}
		TreePath p = a[0];
		Object[] nodes = p.getPath();
		if (nodes.length <= 1) {
			JOptionPane.showMessageDialog(this, "Can't move root object");
		}
		MyObject o1 = (MyObject) nodes[nodes.length-1];
		MyObjectList o2 = (MyObjectList) nodes[nodes.length-2];
		int i = o2.list.indexOf(o1);
		Collections.swap(o2.list, i, i + off);
		tree.getModel().valueForPathChanged(p.getParentPath(), null);
	}

	private void remove () {
		TreePath[] a = tree.getSelectionPaths();
		if (a.length == 0) {
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "Really remove " + a.length + " objects?") == JOptionPane.YES_OPTION) {
			for (TreePath p : a) {
				Object[] nodes = p.getPath();
				if (nodes.length > 1) {
					MyObject o1 = (MyObject) nodes[nodes.length-1];
					MyObjectList o2 = (MyObjectList) nodes[nodes.length-2];
					o2.list.remove(o1);
					tree.getModel().valueForPathChanged(p.getParentPath(), null);
				} else {
					JOptionPane.showMessageDialog(this, "Can't remove root object");
				}
			}
		}
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
		TreePath[] a = tree.getSelectionPaths();
		if (a.length != 1) {
			return;
		}
		TreePath p = a[0];
		Object[] nodes = p.getPath();
		MyObject o1 = (MyObject) nodes[nodes.length-1];
		MyObject newo = rethrow(() -> c.newInstance());
		if (o1 instanceof MyObjectList) {
			((MyObjectList) o1).list.add(newo);
			tree.getModel().valueForPathChanged(p, null);
		} else {
			MyObjectList o2 = (MyObjectList) nodes[nodes.length-2];
			o2.list.add(o2.indexOf(o1) + 1, newo);
			tree.getModel().valueForPathChanged(p.getParentPath(), null);
		}
	}

	private void select (TreeSelectionEvent e) {
		selected.stream().forEach(o -> o.selected = false);
		selected.clear();
		Object o = e.getPath().getLastPathComponent();
		if (o instanceof MyObject) {
			MyObject mo = (MyObject)o;
			mo.selected = true;
			selected.add(mo);
			attr.setObject(mo);
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
