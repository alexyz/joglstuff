package my.obj;

import javax.xml.bind.annotation.*;

/**
 * point with x, y, z position
 */
@XmlRootElement(name="vector")
public abstract class MyVector extends MyObject {
	@XmlAttribute
	public float x;
	@XmlAttribute
	public float y;
	@XmlAttribute
	public float z;
}
