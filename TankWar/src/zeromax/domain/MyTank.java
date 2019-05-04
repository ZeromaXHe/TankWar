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

//    @Deprecated
//    private void southCollideCheck(Map map) {
//        int estimatedPosY1 = posY;
//        int estimatedPosY2 = posY;
//        for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
//            int moveDistance = Math.min(Config.TILEY, equipmentWheel.speed - checkDistance);
//            Drawable d1 = map.getMapItem((posX + 1) / Config.TILEX, (posY + y + checkDistance) / Config.TILEY);
//            Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY + y + checkDistance) / Config.TILEY);
//            if (d1 instanceof Collideable) {
//                if (estimatedPosY1 + moveDistance < ((Collideable) d1).getPosY() - y)
//                    estimatedPosY1 += moveDistance;
//                else estimatedPosY1 = ((Collideable) d1).getPosY() - y;
//            } else estimatedPosY1 += moveDistance;
//            if (d2 instanceof Collideable) {
//                if (estimatedPosY2 + moveDistance < ((Collideable) d2).getPosY() - y)
//                    estimatedPosY2 += moveDistance;
//                else estimatedPosY2 = ((Collideable) d2).getPosY() - y;
//            } else estimatedPosY2 += moveDistance;
//            if (posY == Math.min(estimatedPosY1, estimatedPosY2)) break;
//            else posY = Math.min(estimatedPosY1, estimatedPosY2);
//        }
//    }
//
//    @Deprecated
//    private void northCollideCheck(Map map) {
//        int estimatedPosY1 = posY;
//        int estimatedPosY2 = posY;
//        for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEY) {
//            int moveDistance = Math.min(Config.TILEY, equipmentWheel.speed - checkDistance);
//            Drawable d1 = map.getMapItem((posX + 1) / Config.TILEX, (posY - checkDistance) / Config.TILEY - 1);
//            Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY - checkDistance) / Config.TILEY - 1);
//            if (d1 instanceof Collideable) {
//                if (estimatedPosY1 - moveDistance > ((Collideable) d1).getPosY() + y)
//                    estimatedPosY1 -= moveDistance;
//                else estimatedPosY1 = ((Collideable) d1).getPosY() + y;
//            } else estimatedPosY1 -= moveDistance;
//            if (d2 instanceof Collideable) {
//                if (estimatedPosY2 - moveDistance > ((Collideable) d2).getPosY() + y)
//                    estimatedPosY2 -= moveDistance;
//                else estimatedPosY2 = ((Collideable) d2).getPosY() + y;
//            } else estimatedPosY2 -= moveDistance;
//            if (posY == Math.max(estimatedPosY1, estimatedPosY2)) break;
//            else posY = Math.max(estimatedPosY1, estimatedPosY2);
//        }
//    }
//
//    @Deprecated
//    private void westCollideCheck(Map map) {
//        int estimatedPosX1 = posX;
//        int estimatedPosX2 = posX;
//        for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
//            int moveDistance = Math.min(Config.TILEX, equipmentWheel.speed - checkDistance);
//            Drawable d1 = map.getMapItem((posX - checkDistance) / Config.TILEX - 1, (posY + 1) / Config.TILEY);
//            Drawable d2 = map.getMapItem((posX - checkDistance) / Config.TILEX - 1, (posY + y - 1) / Config.TILEY);
//            if (d1 instanceof Collideable) {
//                if (estimatedPosX1 - moveDistance > ((Collideable) d1).getPosX() + x)
//                    estimatedPosX1 -= moveDistance;
//                else estimatedPosX1 = ((Collideable) d1).getPosX() + x;
//            } else estimatedPosX1 -= moveDistance;
//            if (d2 instanceof Collideable) {
//                if (estimatedPosX2 - moveDistance > ((Collideable) d2).getPosX() + x)
//                    estimatedPosX2 -= moveDistance;
//                else estimatedPosX2 = ((Collideable) d2).getPosX() + x;
//            } else estimatedPosX2 -= Config.TILEX;
//            if (posX == Math.max(estimatedPosX1, estimatedPosX2)) break;
//            else posX = Math.max(estimatedPosX1, estimatedPosX2);
//        }
//    }
//
//    @Deprecated
//    private void eastCollideCheck(Map map) {
//        int estimatedPosX1 = posX;
//        int estimatedPosX2 = posX;
//        for (int checkDistance = 0; checkDistance < equipmentWheel.speed; checkDistance += Config.TILEX) {
//            Drawable d1 = map.getMapItem((posX + x + checkDistance) / Config.TILEX, (posY + 1) / Config.TILEY);
//            Drawable d2 = map.getMapItem((posX + x + checkDistance) / Config.TILEX, (posY + y - 1) / Config.TILEY);
//            int moveDistance = Math.min(Config.TILEX, equipmentWheel.speed - checkDistance);
//            if (d1 instanceof Collideable) {
//                if (estimatedPosX1 + moveDistance < ((Collideable) d1).getPosX() - x)
//                    estimatedPosX1 += moveDistance;
//                else estimatedPosX1 = ((Collideable) d1).getPosX() - x;
//            } else estimatedPosX1 += moveDistance;
//            if (d2 instanceof Collideable) {
//                if (estimatedPosX2 + moveDistance < ((Collideable) d2).getPosX() - x)
//                    estimatedPosX2 += moveDistance;
//                else estimatedPosX2 = ((Collideable) d2).getPosX() - x;
//            } else estimatedPosX2 += moveDistance;
//            if (posX == Math.min(estimatedPosX1, estimatedPosX2)) break;
//            else posX = Math.min(estimatedPosX1, estimatedPosX2);
//        }
//    }

    @Override
    public void pickUpItem() {

    }

}
