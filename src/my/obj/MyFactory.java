package my.obj;

import java.util.Arrays;
import java.util.List;

import my.obj.op.*;
import my.obj.prim.*;

public class MyFactory {
    public MyObject pyramid() {
        return new MyPyramid();
    }
    public MyObject cube() {
        return new MyCube();
    }
    public MyObject trans(float x, float y, float z) {
    	MyTranslation t = new MyTranslation();
    	t.x = x;
    	t.y = y;
    	t.z = z;
    	return t;
    }
    public MyObjectList list(MyObject... list) {
        MyObjectList l = new MyObjectList();
        l.list.addAll(Arrays.asList(list));
		return l;
    }
    public MyObjectList set(MyObject... list) {
    	MyObjectList l = new MyObjectSet();
        l.list.addAll(Arrays.asList(list));
		return l;
    }
    public MyObjectList set(List<MyObject> list) {
    	MyObjectList l = new MyObjectSet();
        l.list.addAll(list);
		return l;
    }
    public MyObject scale(float x, float y, float z) {
        MyScale s = new MyScale();
        s.x = x;
        s.y = y;
        s.z = z;
		return s;
    }
    public MyObject scale(float s) {
        MyScale ms = new MyScale();
        ms.x = s;
        ms.y = s;
        ms.z = s;
		return ms;
    }
//    public MyObject scale(float s, String axi) {
//        return new MyScale(s, axi);
//    }
//    public MyObject timeRotator(float aps, float x, float y, float z) {
//        return new MyTimeRotation(aps, x, y, z);
//    }
    public MyObject timeRotator(float aps, char axis) {
    	MyTimeRotation r = new MyTimeRotation();
    	r.d = aps;
    	if (axis == 'x') r.x = 1;
    	if (axis == 'y') r.y = 1;
    	if (axis == 'z') r.z = 1;
        return r;
    }
    public MyObject checker(int x, int z) {
        MyCheckerBoard c = new MyCheckerBoard();
        c.sx = x;
        c.sz = z;
		return c;
    }
    public MyObject mouseRotator() {
        MyMouseDragRotation rx = new MyMouseDragRotation();
        rx.x = 1;
        rx.axis = "x";
        MyMouseDragRotation ry = new MyMouseDragRotation();
        ry.y = 1;
        ry.axis = "y";
        MyMouseWheelRotation rz = new MyMouseWheelRotation();
        rz.z = 1;
        MyObjectComp c = new MyObjectComp();
        c.list.add(rx);
        c.list.add(ry);
        c.list.add(rz);
        return c;
    }
    public MyObject keyRotator(float delta, String xk, String yk, String zk) {
    	MyObjectComp c = new MyObjectComp();
    	if (xk != null) {
    		MyKeyRotation r = new MyKeyRotation();
    		r.x = 1;
    		r.d = delta;
    		r.keys = xk;
    		c.list.add(r);
    	}
    	if (yk != null) {
    		MyKeyRotation r = new MyKeyRotation();
    		r.y = 1;
    		r.d = delta;
    		r.keys = yk;
    		c.list.add(r);
    	}
    	if (zk != null) {
    		MyKeyRotation r = new MyKeyRotation();
    		r.z = 1;
    		r.d = delta;
    		r.keys = zk;
    		c.list.add(r);
    	}
        return c;
    }
    public MyTranslation keyTranslator(float delta, String xk, String yk, String zk) {
    	MyKeyTranslation t = new MyKeyTranslation();
    	t.xkeys = xk;
    	t.ykeys = yk;
    	t.zkeys = zk;
    	t.d = delta;
        return t;
    }
    public MyObject squarev() {
        return new MySquareV();
    }
    public MyObject terrain() {
        return new Terrain();
    }
}