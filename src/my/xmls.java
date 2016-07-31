package my;

import java.io.*;

import javax.xml.bind.*;

import my.obj.*;

public class xmls {
	
	static JAXBContext jc;
	
	static JAXBContext getjc() throws Exception {
		if (jc == null) {
			jc = JAXBContext.newInstance(MyObject.CLASSES.toArray(new Class[MyObject.CLASSES.size()]), null);
		}
		return jc;
	}
	
	static MyObject load (InputStream is) throws Exception {
		Unmarshaller m = getjc().createUnmarshaller();
		MyObject o = (MyObject) m.unmarshal(is);
		//print("", o);
		return o;
	}
	
	static void print(String i, Object o) {
		System.out.println(i + o + " [" + o.getClass() + "]");
		if (o instanceof MyObjectList) {
			for (Object o2 : ((MyObjectList)o).list)
				print(i + "  ", o2);
		}
	}
	
	public static void main (String[] args) throws Exception {
		Marshaller m = getjc().createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (StringWriter w = new StringWriter()) {
        	MyObjectList o1 = MyScene1.scene1(new MyFactory());
        	print("", o1);
			m.marshal(o1, w);
        	Unmarshaller u = getjc().createUnmarshaller();
        	String s = w.toString();
        	System.out.println(s);
			Object o2 = u.unmarshal(new StringReader(s));
        	print("", o2);
        }
	}
	
}
