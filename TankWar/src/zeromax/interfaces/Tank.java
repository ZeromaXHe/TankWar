package zeromax.interfaces;

import zeromax.domain.Bullet;
import zeromax.equipment.Barrel;
import zeromax.equipment.Wheel;
import zeromax.model.Map;

import java.util.concurrent.CopyOnWriteArrayList;

public interface Tank {
    void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove);

    Bullet shoot();

    void pickUpItem();

    Barrel getEquipmentBarrel();

    Wheel getEquipmentWheel();

    int getPosX();

    int getPosY();

    int getX();

    int getY();

    Facing getNowFacing();
}
