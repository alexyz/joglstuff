package my;

import java.io.*;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.*;

import org.w3c.dom.Document;

import my.obj.*;

public class XmlUtil {
	
	private static JAXBContext _jc;
	private static TransformerFactory _tf;
	private static SchemaFactory _sf;
	private static Document _xsd;
	private static Schema _sch;
	
	private static SchemaFactory getSf() throws Exception {
		if (_sf == null) {
			_sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		}
		return _sf;
	}
	
	private static JAXBContext getContext () throws Exception {
		if (_jc == null) {
			_jc = JAXBContext.newInstance(MyObject.CLASSES.toArray(new Class[MyObject.CLASSES.size()]), null);
		}
		return _jc;
	}
	
	private static TransformerFactory getTf () throws Exception {
		if (_tf == null) {
			_tf =TransformerFactory.newInstance();
		}
		return _tf;
	}
	
	private static Schema getSchema() throws Exception {
		if (_sch == null) {
			_sch = getSf().newSchema(new DOMSource(getXsd()));
		}
		return _sch;
	}

	private static Document getXsd () throws Exception {
		if (_xsd == null) {
			DOMResult sr = new DOMResult();
			sr.setSystemId("myobjects");
			getContext().generateSchema(new SchemaOutputResolver() {
				@Override
				public Result createOutput (String namespaceUri, String suggestedFileName) throws IOException {
					System.out.println("create output " + namespaceUri + ", " + suggestedFileName);
					return sr;
				}
			});
			_xsd = (Document) sr.getNode();
		}
		return _xsd;
	}
	
	public static MyObject load (InputStream is) throws Exception {
		Unmarshaller u = getContext().createUnmarshaller();
		System.out.println(toXmlString(getXsd()));
		System.out.flush();
		u.setSchema(getSchema());
		MyObject o = (MyObject) u.unmarshal(is);
		print("", o);
		return o;
	}
	
	static void print (String i, Object o) {
		System.out.println(i + o + " [" + o.getClass() + "]");
		if (o instanceof MyObjectList) {
			for (Object o2 : ((MyObjectList) o).list)
				print(i + "  ", o2);
		}
	}
	
	public static void main (String[] args) throws Exception {
		Marshaller m = getContext().createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		String xmlStr;
		try (StringWriter sw = new StringWriter()) {
			MyObjectList o1 = MyScene1.scene1(new MyFactory());
			// print("", o1);
			m.marshal(o1, sw);
			xmlStr = sw.toString();
		}
		
		System.out.println(xmlStr);
		
		Document xsd = getXsd();
		
		String xsdStr = toXmlString(xsd);
		System.out.println(xsdStr);
		
		Unmarshaller u = getContext().createUnmarshaller();
		u.setSchema(getSchema());
		
		Object o2 = u.unmarshal(new StringReader(xmlStr));
		print("", o2);
	}

	private static String toXmlString (Document xsd) throws Exception {
		try (StringWriter sw = new StringWriter()) {
			Transformer t = getTf().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(xsd), new StreamResult(sw));
			return sw.toString();
		}
	}
	
}
