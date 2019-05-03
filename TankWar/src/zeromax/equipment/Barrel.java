package zeromax.equipment;

import zeromax.interfaces.Pickable;

public abstract class Barrel implements Pickable {
    public int damage;
    public int shotRange;
    public int durability;
    public int speed;
    public int interval;

    public abstract void addIntervalCount();

    public abstract void setZeroIntervalCount();

    public abstract boolean isShootable();
}
