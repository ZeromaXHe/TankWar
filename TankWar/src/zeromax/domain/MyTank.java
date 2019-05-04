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

public class MyTank implements Tank, Drawable, Collideable, Hitable {
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
    public Blast showBlast() {
        return new Blast(posX+x/2,posY+y/2);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        healthPoint-=bullet.getDamage();
        if(healthPoint>0)return false;
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
    public void move(Facing facing, Map map) {
        switch (facing) {
            case EAST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_r.gif";
                } else {
                    collideCheck(map, facing);
                }
                break;
            case WEST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_l.gif";
                } else {
                    collideCheck(map, facing);
                }
                break;
            case NORTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_u.gif";
                } else {
                    collideCheck(map, facing);
                }
                break;
            case SOUTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_d.gif";
                } else {
                    collideCheck(map, facing);
                }
                break;
        }

    }

    private void collideCheck(Map map, Facing facing) {
        int nowi1 = (posX + 1) / Config.TILEX;
        int nowj1 = (posY + 1) / Config.TILEY;
        int nowi2 = (posX + x - 1) / Config.TILEY;
        int nowj2 = (posY + y - 1) / Config.TILEY;
        int endi1 = nowi1;
        int endj1 = nowj1;
        int endi2 = nowi2;
        int endj2 = nowj2;
        Drawable d;
        switch (facing) {
            case NORTH: {
                endj1 = (posY - equipmentWheel.speed) / Config.TILEY;
                endj2 = (posY - equipmentWheel.speed + y) / Config.TILEY;
                for (int j = nowj2; j >= endj1; j--) {
                    d = map.getMapItem(nowi1, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() + ((Collideable) d).getY();
                        break;
                    }
                    d = map.getMapItem(nowi2, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() + ((Collideable) d).getY();
                        break;
                    }
                    if (j != nowj2 && j == endj1) posY -= equipmentWheel.speed;
                }
            }
            break;
            case SOUTH: {
                endj1 = (posY + equipmentWheel.speed) / Config.TILEY;
                endj2 = (posY + equipmentWheel.speed + y) / Config.TILEY;
                for (int j = nowj1; j <= endj2; j++) {
                    d = map.getMapItem(nowi1, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() - y;
                        break;
                    }
                    d = map.getMapItem(nowi2, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() - y;
                        break;
                    }
                    if (j != nowj1 && j == endj2) posY += equipmentWheel.speed;
                }
            }
            break;
            case WEST: {
                endi1 = (posX - equipmentWheel.speed) / Config.TILEX;
                endi2 = (posX - equipmentWheel.speed + x) / Config.TILEX;
                for (int i = nowi2; i >= endi1; i--) {
                    d = map.getMapItem(i, nowj1);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() + ((Collideable) d).getX();
                        break;
                    }
                    d = map.getMapItem(i, nowj2);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() + ((Collideable) d).getX();
                        break;
                    }
                    if (i != nowi2 && i == endi1) posX -= equipmentWheel.speed;
                }
            }
            break;
            case EAST: {
                endi1 = (posX + equipmentWheel.speed) / Config.TILEX;
                endi2 = (posX + equipmentWheel.speed + x) / Config.TILEX;
                for (int i = nowi1; i <= endi2; i++) {
                    d = map.getMapItem(i, nowj1);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() - x;
                        break;
                    }
                    d = map.getMapItem(i, nowj2);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() - x;
                        break;
                    }
                    if (i != nowi1 && i == endi2) posX += equipmentWheel.speed;
                }
            }
            break;
        }
    }


    @Override
    public void pickUpItem() {

    }

}
