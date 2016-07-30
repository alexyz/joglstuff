package my;

import java.util.*;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import my.obj.MyObject;

public class MyObjectTreeModel implements TreeModel {

	private MyObject root;
	private List<TreeModelListener> listeners = new ArrayList<>();

	public MyObjectTreeModel (MyObject root) {
		this.root = root;
	}

	@Override
	public Object getRoot () {
		return root;
	}

	@Override
	public Object getChild (Object node, int index) {
		return ((MyObject)node).getChild(index);
	}

	@Override
	public int getChildCount (Object node) {
		return ((MyObject)node).getChildCount();
	}

	@Override
	public boolean isLeaf (Object node) {
		return ((MyObject)node).isLeaf();
	}

	@Override
	public void valueForPathChanged (TreePath path, Object newValue) {
		System.out.println("valueForPathChanged " + path + " => " + newValue);
		TreeModelEvent e = new TreeModelEvent(this, path);
		listeners.stream().forEach(c -> c.treeStructureChanged(e));
	}

	@Override
	public int getIndexOfChild (Object node, Object child) {
		return ((MyObject)node).indexOf((MyObject) child);
	}

	@Override
	public void addTreeModelListener (TreeModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeTreeModelListener (TreeModelListener l) {
		listeners.remove(l);
	}
	
}
