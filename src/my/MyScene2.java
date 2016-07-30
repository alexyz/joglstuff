package my;

import java.util.*;

import my.obj.MyFactory;
import my.obj.MyObject;
import my.obj.MyObjectList;

class MyScene2 {

    static MyObjectList create(MyFactory f) {
        
        MyObject t = f.terrain();
        
        List<MyObject> terr = new ArrayList<MyObject>();
        
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                terr.add(f.list(f.trans(x - 1, 0, z - 1), t));
            }
        }
        
        MyObjectList cube = f.list(f.trans(-0.1f,0,-0.1f), f.scale(0.1f,1f,0.1f), f.cube());
        
        MyObjectList r = f.list(f.timeRotator(2, 'y'), f.set(cube, f.list(f.scale(2), f.set(terr))));
                        
        return r;

    }

}
