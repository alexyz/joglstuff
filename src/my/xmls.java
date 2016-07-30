package my;

import javax.xml.bind.*;

import my.obj.*;

public class xmls {
	
	public static void main (String[] args) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(MyObject.CLASSES.toArray(new Class[MyObject.CLASSES.size()]), null);
		Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(MyScene1.scene1(new MyFactory()), System.out);
	}
	
}
