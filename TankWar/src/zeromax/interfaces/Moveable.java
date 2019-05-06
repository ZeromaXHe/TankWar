package zeromax.interfaces;

import zeromax.model.Map;

import java.util.concurrent.CopyOnWriteArrayList;

public interface Moveable extends Sizeable{
    void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove);

    void setPosX(int posX);

    void setPosY(int posY);

}
