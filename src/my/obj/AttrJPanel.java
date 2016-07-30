package my.obj;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import javax.swing.*;

public class AttrJPanel extends JPanel {
	
	public AttrJPanel () {
		setPreferredSize(new Dimension(320,240));
	}
	
	private static void set(Field f, Object o, float v) {
		try {
			f.setFloat(o, v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setObject(MyObject o) {
		System.out.println("set obj " + o);
		// public floats
		// public strings
		removeAll();
		if (o != null) {
			Map<String,JComponent> map = new TreeMap<>();
			try {
				for (Field field : o.getClass().getFields()) {
					int mod = field.getModifiers();
					if (Modifier.isPublic(mod) && !Modifier.isStatic(mod)) {
						if (field.getType() == float.class) {
							SpinnerNumberModel model = new SpinnerNumberModel(field.getFloat(o), -1000, 1000, 0.125);
							JSpinner c = new JSpinner(model);
							c.addChangeListener(e -> set(field, o, ((Number)model.getValue()).floatValue()));
							map.put(field.getName(), c);
						}
					}
				}
				setLayout(new GridLayout(Math.max(1,map.size()), 2));
				for (String name : map.keySet()) {
					add(new JLabel(name));
					add(map.get(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		revalidate();
		repaint();
	}
}
