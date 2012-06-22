package my.obj;

import java.util.List;

import my.obj.op.KeyRotator2;
import my.obj.op.KeyTranslator;
import my.obj.op.MouseRotator2;
import my.obj.op.MyScale;
import my.obj.op.MyTimeRotation;
import my.obj.op.MyTranslation;
import my.obj.op.Rotator;
import my.obj.prim.MyCheckerBoard;
import my.obj.prim.MyCube;
import my.obj.prim.MyPyramid;
import my.obj.prim.MySquareV;
import my.obj.prim.Terrain;

public class MyFactory {
    public MyObject pyramid() {
        return new MyPyramid();
    }
    public MyObject cube() {
        return new MyCube();
    }
    public MyObject trans(float x, float y, float z) {
        return new MyTranslation().translate(x,y,z);
    }
    public MyObjectList list(MyObject... list) {
        return new MyObjectList(list);
    }
    public MyObjectList set(MyObject... list) {
        return new MyObjectSet(list);
    }
    public MyObjectList set(List<MyObject> list) {
        return new MyObjectSet(list);
    }
    public MyObject scale(float x, float y, float z) {
        return new MyScale(x, y, z);
    }
    public MyObject scale(float s) {
        return new MyScale(s, s, s);
    }
    public MyObject scale(float s, String axi) {
        return new MyScale(s, axi);
    }
    public MyObject timeRotator(float aps, float x, float y, float z) {
        return new MyTimeRotation(aps, x, y, z);
    }
    public MyObject timeRotator(float aps, String axi) {
        return new MyTimeRotation(aps, axi);
    }
    public MyObject checker(int x, int z) {
        return new MyCheckerBoard(x, z);
    }
    public Rotator mouseRotator() {
        return new MouseRotator2();
    }
    public Rotator keyRotator(float delta, String keys) {
        return new KeyRotator2(delta, keys);
    }
    public MyTranslation keyTranslator(float delta, String keys) {
        return new KeyTranslator(delta, keys);
    }
    public MyObject squarev() {
        return new MySquareV();
    }
    public MyObject terrain() {
        return new Terrain();
    }
}