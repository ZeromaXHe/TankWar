package zeromax.interfaces;

import zeromax.domain.Bullet;

public interface Tank {
    void move(Facing facing);
    Bullet shoot();
    void pickUpItem();

    int getTPosX();

    int getTPosY();

    int getX();

    int getY();

    Facing getNowFacing();

}
