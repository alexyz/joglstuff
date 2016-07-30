package my;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import my.obj.MyObject;

public class AttrJPanel extends JPanel implements ChangeListener, ActionListener{
	
	private MyObject o;

	public AttrJPanel () {
		super(new GridBagLayout());
		setPreferredSize(new Dimension(320,240));
	}
	
	public void setObject(MyObject o) {
		this.o = o;
		// public floats, ints, strings
		removeAll();
		setBorder(null);
		if (o != null) {
			setBorder(new TitledBorder(o.getClass().getSimpleName()));
			Map<String,JComponent> map = new TreeMap<>();
			try {
				for (Field field : o.getClass().getFields()) {
					int mod = field.getModifiers();
					if (Modifier.isPublic(mod) && !Modifier.isStatic(mod)) {
						if (field.getType().equals(float.class)) {
							JSpinner spinner = new JSpinner(new SpinnerNumberModel(((Number)field.get(o)).doubleValue(), -1000, 1000, 0.25));
							spinner.setName(field.getName());
							spinner.addChangeListener(this);
							map.put(field.getName(), spinner);
						} else if (field.getType().equals(int.class)) {
							JSpinner c = new JSpinner(new SpinnerNumberModel(((Number)field.get(o)).doubleValue(), -1000, 1000, 1));
							c.setName(field.getName());
							c.addChangeListener(this);
							map.put(field.getName(), c);
						} else if (field.getType().equals(String.class)) {
							JTextField tf = new JTextField(8);
							String v = (String) field.get(o);
							tf.setText(v != null ? v : "");
							tf.addActionListener(this);
							map.put(field.getName(), tf);
						} else {
							map.put(field.getName(), new JLabel(field.getType().toString()));
						}
					}
				}
				GridBagConstraints c = new GridBagConstraints();
				c.gridy = 0;
				for (String name : map.keySet()) {
					c.gridx = 0;
					c.anchor = GridBagConstraints.EAST;
					add(new JLabel(name), c);
					c.gridx = 1;
					c.anchor = GridBagConstraints.WEST;
					add(map.get(name), c);
					c.gridy++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		revalidate();
		repaint();
	}
	
	@Override
	public void stateChanged (ChangeEvent ce) {
		try {
			JSpinner c = (JSpinner) ce.getSource();
			SpinnerNumberModel m = (SpinnerNumberModel) c.getModel();
			Field f = o.getClass().getField(c.getName());
			if (f.getType().equals(float.class)) {
				f.setFloat(o, ((Number)m.getValue()).floatValue());
			} else if (f.getType().equals(int.class)) {
				f.setInt(o, ((Number)m.getValue()).intValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed (ActionEvent ae) {
		try {
			JTextField c = (JTextField) ae.getSource();
			Field f = o.getClass().getField(c.getName());
			String v = c.getText();
			f.set(o, v.length() > 0 ? v : null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
