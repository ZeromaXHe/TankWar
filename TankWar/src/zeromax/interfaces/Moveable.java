package zeromax.interfaces;

import zeromax.model.Map;

import java.util.concurrent.CopyOnWriteArrayList;

public interface Moveable {
    void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove);
}
