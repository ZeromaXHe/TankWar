package zeromax.domain;

import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.utils.DrawUtils;
import zeromax.utils.SoundUtils;

import java.io.IOException;

public class Bullet implements Drawable, Collideable, Hitable {
    private int damage;
    private int speed;
    private int interval;//使用fps计数
    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing facing;
    private static final int displayPriority = 0;
    private boolean toBeCleared = false;

    private String imgPath = "TankWar\\res\\img/bullet_u.gif";

    public Bullet(){
        damage = 10;
        speed = 5;
        interval = 10;
    }
    public Bullet(Tank tank){
        damage = 10;
        speed = 5;
        interval = 10;

        facing = tank.getNowFacing();
        switch (facing){
            case NORTH:
                imgPath = "TankWar\\res\\img/bullet_u.gif";
                try {
                    int[] arr=DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX()+tank.getX()/2-x/2;
                posY = tank.getPosY()-y;
                break;
            case SOUTH:
                imgPath = "TankWar\\res\\img/bullet_d.gif";
                try {
                    int[] arr=DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX()+tank.getX()/2-x/2;
                posY = tank.getPosY()+tank.getY();
                break;
            case WEST:
                imgPath = "TankWar\\res\\img/bullet_l.gif";
                try {
                    int[] arr=DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX()-x;
                posY = tank.getPosY()+tank.getY()/2-y/2;
                break;
            case EAST:
                imgPath = "TankWar\\res\\img/bullet_r.gif";
                try {
                    int[] arr=DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX()+tank.getX();
                posY = tank.getPosY()+tank.getY()/2-y/2;
                break;
        }
        try {
            SoundUtils.play("TankWar/res/snd/fire.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getDisplayPriority() {
        return displayPriority;
    }

    @Override
    public void draw(){
        try {
            DrawUtils.draw(imgPath, posX, posY);
        } catch (IOException e) {
            e.printStackTrace();
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

    public boolean isToBeCleared() {
        return toBeCleared;
    }

    public void move(Map map){//TODO：下面这坨又臭又长的代码需要重构
        switch(facing){
            case WEST: {
                int estimatedPosX1 = posX;
                int estimatedPosX2 = posX;
                for (int checkDistance = 0; checkDistance < speed; checkDistance += Config.TILEX) {
                    int moveDistance = Math.min(Config.TILEX,speed-checkDistance);
                    Drawable d1 = map.getMapItem((posX - checkDistance - 1) / Config.TILEX , (posY + 1) / Config.TILEY);
                    Drawable d2 = map.getMapItem((posX - checkDistance - 1) / Config.TILEX , (posY + y - 1) / Config.TILEY);
                    if (d1 != null && d1 instanceof Hitable) {
                        if (estimatedPosX1 - moveDistance > ((Hitable) d1).getPosX() + x)
                            estimatedPosX1 -= moveDistance;
                        else {
                            estimatedPosX1 = ((Hitable) d1).getPosX() + x;
                            toBeCleared = true;
                        }
                    } else estimatedPosX1 -= moveDistance;
                    if (d2 != null && d2 instanceof Hitable) {
                        if (estimatedPosX2 - moveDistance > ((Hitable) d2).getPosX() + x)
                            estimatedPosX2 -= moveDistance;
                        else {
                            estimatedPosX2 = ((Hitable) d2).getPosX() + x;
                            toBeCleared = true;
                        }
                    } else estimatedPosX2 -= moveDistance;
                    if (posX == Math.max(estimatedPosX1, estimatedPosX2)) {
                        toBeCleared = true;
                        break;
                    } else posX = Math.max(estimatedPosX1, estimatedPosX2);
                }
                break;
            }
            case EAST: {
                int estimatedPosX1 = posX;
                int estimatedPosX2 = posX;
                for (int checkDistance = 0; checkDistance < speed; checkDistance += Config.TILEX) {
                    int moveDistance = Math.min(Config.TILEX,speed-checkDistance);
                    Drawable d1 = map.getMapItem((posX + x + checkDistance + 1) / Config.TILEX, (posY + 1) / Config.TILEY);
                    Drawable d2 = map.getMapItem((posX + x + checkDistance + 1) / Config.TILEX, (posY + y - 1) / Config.TILEY);
                    if (d1 != null && d1 instanceof Hitable) {
                        if (estimatedPosX1 + moveDistance < ((Hitable) d1).getPosX() - x)
                            estimatedPosX1 += moveDistance;
                        else {
                            toBeCleared =true;
                            estimatedPosX1 = ((Hitable) d1).getPosX() - x;
                        }
                    } else estimatedPosX1 += moveDistance;
                    if (d2 != null && d2 instanceof Hitable) {
                        if (estimatedPosX2 + moveDistance < ((Hitable) d2).getPosX() - x)
                            estimatedPosX2 += moveDistance;
                        else {
                            toBeCleared =true;
                            estimatedPosX2 = ((Hitable) d2).getPosX() - x;
                        }
                    } else estimatedPosX2 += moveDistance;
                    if (posX == Math.min(estimatedPosX1, estimatedPosX2)) {
                        toBeCleared =true;
                        break;
                    }
                    else posX = Math.min(estimatedPosX1, estimatedPosX2);
                }
                break;
            }
            case SOUTH: {
                int estimatedPosY1 = posY;
                int estimatedPosY2 = posY;
                for (int checkDistance = 0; checkDistance < speed; checkDistance += Config.TILEX) {
                    int moveDistance = Math.min(Config.TILEY,speed-checkDistance);
                    Drawable d1 = map.getMapItem( (posX + 1) / Config.TILEX,(posY + y + checkDistance + 1) / Config.TILEY);
                    Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY + y + checkDistance + 1) / Config.TILEY);
                    if (d1 != null && d1 instanceof Hitable) {
                        if (estimatedPosY1 + moveDistance < ((Hitable) d1).getPosY() - y)
                            estimatedPosY1 += moveDistance;
                        else {
                            toBeCleared =true;
                            estimatedPosY1 = ((Hitable) d1).getPosY() - y;
                        }
                    } else estimatedPosY1 += moveDistance;
                    if (d2 != null && d2 instanceof Hitable) {
                        if (estimatedPosY2 + moveDistance < ((Hitable) d2).getPosY() - y)
                            estimatedPosY2 += moveDistance;
                        else {
                            estimatedPosY2 = ((Hitable) d2).getPosY() - y;
                            toBeCleared =true;
                        }
                    } else estimatedPosY2 += moveDistance;
                    if (posY == Math.min(estimatedPosY1, estimatedPosY2)) {
                        toBeCleared =true;
                        break;
                    }
                    else posY = Math.min(estimatedPosY1, estimatedPosY2);
                }
                break;
            }
            case NORTH: {
                int estimatedPosY1 = posY;
                int estimatedPosY2 = posY;
                for (int checkDistance = 0; checkDistance < speed; checkDistance += Config.TILEY) {
                    int moveDistance = Math.min(Config.TILEY,speed-checkDistance);
                    Drawable d1 = map.getMapItem((posX + 1) / Config.TILEX, (posY - checkDistance - 1) / Config.TILEY);
                    Drawable d2 = map.getMapItem((posX + x - 1) / Config.TILEX, (posY - checkDistance - 1) / Config.TILEY);
                    if (d1 != null && d1 instanceof Hitable) {
                        if (estimatedPosY1 - moveDistance > ((Hitable) d1).getPosY() + y)
                            estimatedPosY1 -= moveDistance;
                        else {
                            estimatedPosY1 = ((Hitable) d1).getPosY() + y;
                            toBeCleared = true;
                        }
                    } else estimatedPosY1 -= moveDistance;
                    if (d2 != null && d2 instanceof Hitable) {
                        if (estimatedPosY2 - moveDistance > ((Hitable) d2).getPosY() + y)
                            estimatedPosY2 -= moveDistance;
                        else {
                            estimatedPosY2 = ((Hitable) d2).getPosY() + y;
                            toBeCleared = true;
                        }
                    } else estimatedPosY2 -= moveDistance;
                    if (posY == Math.max(estimatedPosY1, estimatedPosY2)) {
                        toBeCleared = true;
                        break;
                    } else posY = Math.max(estimatedPosY1, estimatedPosY2);
                }
                break;
            }
        }
    }
//    public void isOutOfMap(){
//        toBeCleared = (posY <0|| posY > Config.HEIGHT|| posX <0|| posX >Config.WIDTH);
//    }

}
