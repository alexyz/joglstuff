package my.obj.op;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="timerotation")
public class MyTimeRotation extends MyRotation {
    
	@XmlAttribute
	public float d = 360;
	
    @Override
	public void update (float t) {
        aoffset = (t * d) % 360f;
    }
    
}
