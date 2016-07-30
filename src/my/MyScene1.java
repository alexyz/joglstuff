package my;

import my.obj.MyFactory;
import my.obj.MyObjectList;

class MyScene1 extends MyObjectList {
    
    static MyObjectList scene1(MyFactory f) {
            return f.set(
                    
            f.list(
                    f.pyramid(),
                    f.trans(1,0,0),
                    f.mouseRotator(),
                    f.scale(0.5f),
                    f.pyramid()),

            f.list(
                    f.trans(-1,0,0),
                    f.cube()),
            
            f.list(
                    f.trans(0,0.25f,1f),
                    f.scale(0.5f),
                    f.squarev()),

            f.list(
                    f.trans(0,1,0),
                    f.timeRotator(15, 'y'),
                    f.list(
                            f.scale(0.5f),
                            f.cube()),
                            f.list(
                                    f.trans(0,0.15f,-1),
                                    f.timeRotator(30, 'z'),
                                    f.scale(0.2f),
                                    f.cube())),
            f.list(
                    f.trans(0,0,1),
                    f.keyRotator(2f, "zx", "cv", "bn"),
                    f.scale(2),
                    f.checker(6,6)));
                    //f.terrain()));

    }

}

