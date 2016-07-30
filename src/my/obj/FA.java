package my.obj;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class FA extends XmlAdapter<String, Float> {
	@Override
	public String marshal (Float v) throws Exception {
		if (v != null) {
			float f = v.floatValue();
			if (f != 0) {
				int i = (int) f;
				return f != i ? String.valueOf(f) : String.valueOf(i);
			}
		}
		return null;
	}
	
	@Override
	public Float unmarshal (String v) throws Exception {
		return v != null && v.length() > 0 ? Float.parseFloat(v) : 0;
	}
}
