package zeromax.equipment;

import zeromax.interfaces.Pickable;

public abstract class Barrel implements Pickable {
    int damage;
    int shotRange;
    int durability;
    int speed;
    int interval;

    public abstract void addIntervalCount();

    public abstract void setZeroIntervalCount();

    public abstract boolean isShootable();
}
