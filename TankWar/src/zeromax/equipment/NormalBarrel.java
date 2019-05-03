package zeromax.equipment;

public class NormalBarrel extends Barrel {
    private int intervalCount;

    public NormalBarrel() {
        damage = 10;
        shotRange = 64 * 10;
        speed = 10;
        durability = 100;
        interval = 50;
        intervalCount = 0;
    }

    @Override
    public void addIntervalCount() {
        intervalCount++;
    }

    @Override
    public void setZeroIntervalCount() {
        intervalCount = 0;
    }

    @Override
    public boolean isShootable() {
        return (interval <= intervalCount);
    }
}
