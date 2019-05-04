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

    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing nowFacing;

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
    public Facing getNowFacing() {
        return nowFacing;
    }

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
    public void move(Facing facing, Map map) {//TODO:下面这坨又臭又长的代码需要重构
        switch (facing) {
            case EAST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_r.gif";
                } else {
                    int estimatedPosX1 = posX;
                    int estimatedPosX2 = posX;
                    for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
                        Drawable d1 = map.getMapItem((posX + x + checkDistance) / Config.TILEX, (posY + 1) / Config.TILEY);
                        Drawable d2 = map.getMapItem((posX + x + checkDistance) / Config.TILEX, (posY + y - 1) / Config.TILEY);
                        int moveDistance = Math.min(Config.TILEX,equipmentWheel.speed-checkDistance);
                        if (d1 != null && d1 instanceof Collideable) {
                            if (estimatedPosX1 + moveDistance < ((Collideable) d1).getPosX() - x)
                                estimatedPosX1 += moveDistance;
                            else estimatedPosX1 = ((Collideable) d1).getPosX() - x;
                        } else estimatedPosX1 += moveDistance;
                        if (d2 != null && d2 instanceof Collideable) {
                            if (estimatedPosX2 + moveDistance < ((Collideable) d2).getPosX() - x)
                                estimatedPosX2 += moveDistance;
                            else estimatedPosX2 = ((Collideable) d2).getPosX() - x;
                        } else estimatedPosX2 += moveDistance;
                        if (posX == Math.min(estimatedPosX1, estimatedPosX2)) break;
                        else posX = Math.min(estimatedPosX1, estimatedPosX2);
                    }
                }
                break;
            case WEST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_l.gif";
                } else {
                    int estimatedPosX1 = posX;
                    int estimatedPosX2 = posX;
                    for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
                        int moveDistance = Math.min(Config.TILEX,equipmentWheel.speed-checkDistance);
                        Drawable d1 = map.getMapItem((posX - checkDistance) / Config.TILEX - 1, (posY + 1) / Config.TILEY);
                        Drawable d2 = map.getMapItem((posX - checkDistance) / Config.TILEX - 1, (posY + y - 1) / Config.TILEY);
                        if (d1 != null && d1 instanceof Collideable) {
                            if (estimatedPosX1 - moveDistance > ((Collideable) d1).getPosX() + x)
                                estimatedPosX1 -= moveDistance;
                            else estimatedPosX1 = ((Collideable) d1).getPosX() + x;
                        } else estimatedPosX1 -= moveDistance;
                        if (d2 != null && d2 instanceof Collideable) {
                            if (estimatedPosX2 - moveDistance > ((Collideable) d2).getPosX() + x)
                                estimatedPosX2 -= moveDistance;
                            else estimatedPosX2 = ((Collideable) d2).getPosX() + x;
                        } else estimatedPosX2 -= Config.TILEX;
                        if (posX == Math.max(estimatedPosX1, estimatedPosX2)) break;
                        else posX = Math.max(estimatedPosX1, estimatedPosX2);
                    }
                }
                break;
            case NORTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_u.gif";
                } else {
                    int estimatedPosY1 = posY;
                    int estimatedPosY2 = posY;
                    for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEY) {
                        int moveDistance = Math.min(Config.TILEY,equipmentWheel.speed-checkDistance);
                        Drawable d1 = map.getMapItem((posX + 1) / Config.TILEX, (posY - checkDistance) / Config.TILEY - 1);
                        Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY - checkDistance) / Config.TILEY - 1);
                        if (d1 != null && d1 instanceof Collideable) {
                            if (estimatedPosY1 - moveDistance > ((Collideable) d1).getPosY() + y)
                                estimatedPosY1 -= moveDistance;
                            else estimatedPosY1 = ((Collideable) d1).getPosY() + y;
                        } else estimatedPosY1 -= moveDistance;
                        if (d2 != null && d2 instanceof Collideable) {
                            if (estimatedPosY2 - moveDistance > ((Collideable) d2).getPosY() + y)
                                estimatedPosY2 -= moveDistance;
                            else estimatedPosY2 = ((Collideable) d2).getPosY() + y;
                        } else estimatedPosY2 -= moveDistance;
                        if (posY == Math.max(estimatedPosY1, estimatedPosY2)) break;
                        else posY = Math.max(estimatedPosY1, estimatedPosY2);
                    }
                }
                break;
            case SOUTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_d.gif";
                } else {
                    int estimatedPosY1 = posY;
                    int estimatedPosY2 = posY;
                    for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
                        int moveDistance = Math.min(Config.TILEY,equipmentWheel.speed-checkDistance);
                        Drawable d1 = map.getMapItem( (posX + 1) / Config.TILEX,(posY + y + checkDistance) / Config.TILEY);
                        Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY + y + checkDistance) / Config.TILEY);
                        if (d1 != null && d1 instanceof Collideable) {
                            if (estimatedPosY1 + moveDistance < ((Collideable) d1).getPosY() - y)
                                estimatedPosY1 += moveDistance;
                            else estimatedPosY1 = ((Collideable) d1).getPosY() - y;
                        } else estimatedPosY1 += moveDistance;
                        if (d2 != null && d2 instanceof Collideable) {
                            if (estimatedPosY2 + moveDistance < ((Collideable) d2).getPosY() - y)
                                estimatedPosY2 += moveDistance;
                            else estimatedPosY2 = ((Collideable) d2).getPosY() - y;
                        } else estimatedPosY2 += moveDistance;
                        if (posY == Math.min(estimatedPosY1, estimatedPosY2)) break;
                        else posY = Math.min(estimatedPosY1, estimatedPosY2);
                    }
                }
                break;
        }

    }

    @Override
    public void pickUpItem() {

    }

}
