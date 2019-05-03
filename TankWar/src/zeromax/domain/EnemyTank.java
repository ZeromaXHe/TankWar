package zeromax.domain;

import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;

public class EnemyTank implements Tank, Drawable {
    private int killCount;
    private int equipmentBarrel;
    private int equipmentArmor;
    private int equipmentWheel;
    private int equipmentLight;
    private int botAI;
    private int tPosX;
    private int tPosY;
    private int x;
    private int y;
    private Facing nowFacing;

    @Override
    public int getTPosX() {
        return tPosX;
    }

    @Override
    public int getTPosY() {
        return tPosY;
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
        equipmentBarrel = 0;
        equipmentArmor = 0;
        equipmentWheel = 0;
        equipmentLight = 0;
        botAI = 0;
    }

    EnemyTank(int botAI) {
        killCount = 0;
        equipmentBarrel = 0;
        equipmentArmor = 0;
        equipmentWheel = 0;
        equipmentLight = 0;
        switch (botAI) {
            case 0:
                botAI = 0;
            case 1:
                botAI = 1;
        }
    }

    @Override
    public void draw() {

    }

    @Override
    public void move(Facing facing) {

    }

    @Override
    public Bullet shoot() {
        return new Bullet();
    }

    @Override
    public void pickUpItem() {

    }
}
