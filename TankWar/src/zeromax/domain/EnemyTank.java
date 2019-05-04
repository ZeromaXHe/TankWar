package zeromax.domain;

import zeromax.equipment.Barrel;
import zeromax.equipment.NormalBarrel;
import zeromax.equipment.NormalWheel;
import zeromax.equipment.Wheel;
import zeromax.interfaces.*;
import zeromax.model.Map;

public class EnemyTank implements Tank, Drawable, Collideable, Hitable, Clearable {
    private int killCount;
    private Barrel equipmentBarrel;
    private int equipmentArmor;
    private Wheel equipmentWheel;
    private int equipmentLight;

    private int botAI;
    private int healthPoint=100;
    private boolean toBeCleared =false;

    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing nowFacing;
    private static final int displayPriority = 0;

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

    @Override
    public boolean isToBeCleared() {
        return toBeCleared;
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
    public Blast showBlast() {
        return new Blast(posX+x/2,posY+y/2);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        healthPoint-=bullet.getDamage();
        if(healthPoint>0)return false;
        else {
            toBeCleared = true;
            return true;
        }
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
