package zeromax.interfaces;

import zeromax.domain.Bullet;
import zeromax.equipment.Barrel;
import zeromax.equipment.Wheel;

public interface Tank {
    void move(Facing facing);
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
