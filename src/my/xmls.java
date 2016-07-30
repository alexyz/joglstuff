package my;

import javax.xml.bind.*;

import my.obj.*;
import my.obj.op.*;
import my.obj.prim.*;

public class xmls {
	
	private static Class<?>[] classes = new Class<?>[] {
		MyObjectComp.class,
		MyObjectList.class,
		MyObjectSet.class,
		
		MyKeyRotation.class,
		MyKeyTranslation.class,
		MyMouseDragRotation.class,
		MyMouseWheelRotation.class,
		MyRotation.class,
		MyScale.class,
		MyTimeRotation.class,
		MyTranslation.class,
		
		MyCheckerBoard.class,
		MyCube.class,
		MyCubeS.class,
		MyPyramid.class,
		MySquareV.class,
		Terrain.class
	};
	
	public static void main (String[] args) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(classes, null);
		Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(MyScene1.scene1(new MyFactory()), System.out);
	}
	
}
