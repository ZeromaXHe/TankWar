package zeromax.domain;

import zeromax.equipment.Barrel;
import zeromax.equipment.NormalBarrel;
import zeromax.equipment.NormalWheel;
import zeromax.equipment.Wheel;
import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.utils.CollisionUtils;
import zeromax.utils.DrawUtils;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyTank implements Tank, Drawable, Collideable, Hitable, Moveable {
    private int killCount;
    private Barrel equipmentBarrel;
    private int equipmentArmor;
    private Wheel equipmentWheel;
    private int equipmentLight;
    private String imgPath = "TankWar\\res\\img/tank_u.gif";
    private static final int displayPriority = 0;
    private int healthPoint = 100;

    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing nowFacing;

    public MyTank(int posX, int posY) {

        killCount = 0;
        equipmentBarrel = new NormalBarrel();
        equipmentArmor = 0;
        equipmentWheel = new NormalWheel();

        nowFacing = Facing.NORTH;
        this.posX = posX;
        this.posY = posY;
        try {
            int[] arr = DrawUtils.getSize(imgPath);
            x = arr[0];
            y = arr[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getDisplayPriority() {
        return displayPriority;
    }

    public Barrel getEquipmentBarrel() {
        return equipmentBarrel;
    }

    public Wheel getEquipmentWheel() {
        return equipmentWheel;
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
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public Blast showBlast() {
        return new Blast(posX + x / 2, posY + y / 2);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        healthPoint -= bullet.getDamage();
        if (healthPoint > 0) return false;
        else return true;
    }

    @Override
    public Facing getNowFacing() {
        return nowFacing;
    }


    @Override
    public void draw() {
        try {
            DrawUtils.draw(imgPath, posX, posY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bullet shoot() {
        if (equipmentBarrel.isShootable()) {
            equipmentBarrel.setZeroIntervalCount();
            return new Bullet(this);
        } else return null;
    }

    @Override
    public void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove) {
        switch (facing) {
            case EAST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_r.gif";
                } else {
                    CollisionUtils.collideCheck(this, map, facing, listMove, equipmentWheel.speed);
                }
                break;
            case WEST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_l.gif";
                } else {
                    CollisionUtils.collideCheck(this, map, facing, listMove, equipmentWheel.speed);
                }
                break;
            case NORTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_u.gif";
                } else {
                    CollisionUtils.collideCheck(this, map, facing, listMove, equipmentWheel.speed);
                }
                break;
            case SOUTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_d.gif";
                } else {
                    CollisionUtils.collideCheck(this, map, facing, listMove, equipmentWheel.speed);
                }
                break;
        }

    }

    @Override
    public void pickUpItem() {

    }

}
