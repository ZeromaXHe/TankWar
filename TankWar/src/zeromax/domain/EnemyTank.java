package zeromax.domain;

import zeromax.equipment.Barrel;
import zeromax.equipment.NormalBarrel;
import zeromax.equipment.NormalWheel;
import zeromax.equipment.Wheel;
import zeromax.interfaces.Collideable;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.model.Map;

public class EnemyTank implements Tank, Drawable, Collideable {
    private int killCount;
    private Barrel equipmentBarrel;
    private int equipmentArmor;
    private Wheel equipmentWheel;
    private int equipmentLight;
    private int botAI;
    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing nowFacing;
    private static final int displayPriority = 0;

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Facing getNowFacing() {
        return nowFacing;
    }

    EnemyTank() {
        killCount = 0;
        equipmentBarrel = new NormalBarrel();
        equipmentArmor = 0;
        equipmentWheel = new NormalWheel();
        equipmentLight = 0;
        botAI = 0;
    }

    EnemyTank(int botAI) {
        killCount = 0;
        equipmentBarrel = new NormalBarrel();
        equipmentArmor = 0;
        equipmentWheel = new NormalWheel();
        equipmentLight = 0;
        switch (botAI) {
            case 0:
                botAI = 0;
            case 1:
                botAI = 1;
        }
    }

    @Override
    public int getDisplayPriority() {
        return displayPriority;
    }

    @Override
    public void draw() {

    }

    @Override
    public void move(Facing facing, Map map) {

    }

    @Override
    public Bullet shoot() {
        return new Bullet();
    }

    @Override
    public void pickUpItem() {

    }

    @Override
    public Barrel getEquipmentBarrel() {
        return equipmentBarrel;
    }

    @Override
    public Wheel getEquipmentWheel() {
        return equipmentWheel;
    }
}
