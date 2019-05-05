package zeromax.domain;

import zeromax.equipment.*;
import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.utils.CollisionUtils;
import zeromax.utils.DrawUtils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static zeromax.interfaces.Facing.*;

public class EnemyTank implements Tank, Drawable, Collideable, Hitable, Clearable, Moveable {
    private int killCount;
    private Barrel equipmentBarrel;
    private int equipmentArmor;
    private Wheel equipmentWheel;
    private int equipmentLight;

    private int botAI;
    private int healthPoint = 100;
    private boolean toBeCleared = false;

    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing nowFacing;
    private static final int displayPriority = 0;

    private String imgPath = "TankWar/res/img/enemy_1_d.gif";

    public EnemyTank(int posX, int posY) {
        this(posX, posY, 0);
    }

    public EnemyTank(int posX, int posY, int botAI) {
        this.posX = posX;
        this.posY = posY;
        killCount = 0;
        equipmentBarrel = new NormalBarrel();
        equipmentArmor = 0;
        equipmentWheel = new EnemyNormalWheel();
        equipmentLight = 0;
        nowFacing = SOUTH;
        imgPath = "TankWar/res/img/enemy_1_d.gif";
        this.botAI = botAI;
        try {
            int[] arr = DrawUtils.getSize(imgPath);
            x = arr[0];
            y = arr[1];
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
        try {
            DrawUtils.draw(imgPath, posX, posY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove) {
        if (collideCheck(map, facing, listMove)
                || ((facing == EAST || facing == WEST) && posX % Config.TILEX == 0)
                || ((facing == NORTH || facing == SOUTH) && posY % Config.TILEY == 0)) {
            Random rand = new Random();
            switch (facing) {
                case SOUTH:
                case NORTH:
                    if (rand.nextInt(2) == 1) {
                        nowFacing = Facing.EAST;
                        imgPath = "TankWar/res/img/enemy_1_r.gif";
                    } else {
                        nowFacing = Facing.WEST;
                        imgPath = "TankWar/res/img/enemy_1_l.gif";
                    }
                    break;
                case EAST:
                case WEST:
                    if (rand.nextInt(2) == 1) {
                        nowFacing = Facing.NORTH;
                        imgPath = "TankWar/res/img/enemy_1_u.gif";
                    } else {
                        nowFacing = Facing.SOUTH;
                        imgPath = "TankWar/res/img/enemy_1_d.gif";
                    }
                    break;
            }
        }
    }

    //TODO:还是这里屎一样的代码，必须重构了，不然根本没办法实现Moveable、Hitable之间的碰撞。加入注释掉的代码就会bug
    private boolean collideCheck(Map map, Facing facing, CopyOnWriteArrayList<Moveable> listMove) {
        boolean turn = false;
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
                        turn = true;
                        break;
                    }
                    d = map.getMapItem(nowi2, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() + ((Collideable) d).getY();
                        turn = true;
                        break;
                    }
                    if (j != nowj2 && j == endj1) posY -= equipmentWheel.speed;
                }
//                for (Moveable move : listMove) {
//                    if (move == this) continue;
//                    if (move instanceof Bullet) {
//                        if (((Bullet) move).getShotFrom() == this) continue;
//                        else {
//                            if (CollisionUtils.isCollisionWithRect(((Bullet) move).getPosX(), ((Bullet) move).getPosY(),
//                                    ((Bullet) move).getX(), ((Bullet) move).getY(),
//                                    endi1, endj1, x, nowj1 - posY)) {
//                                ((Bullet) move).setHit(this);
//                                ((Bullet) move).setToBeCleared(true);
//                                posY = ((Bullet) move).getPosY() + ((Bullet) move).getY();
//
//                            }
//                        }
//                    } else if (move instanceof Collideable) {
//                        if (CollisionUtils.isCollisionWithRect(((Collideable) move).getPosX(), ((Collideable) move).getPosY(),
//                                ((Collideable) move).getX(), ((Collideable) move).getY(),
//                                endi1, endj1, x, nowj1 - posY)) {
//                            posY = ((Collideable) move).getPosY() + ((Collideable) move).getY();
//                            turn = true;
//                        }
//                    }
//                }
            }
            break;
            case SOUTH: {
                endj1 = (posY + equipmentWheel.speed) / Config.TILEY;
                endj2 = (posY + equipmentWheel.speed + y) / Config.TILEY;
                for (int j = nowj1; j <= endj2; j++) {
                    d = map.getMapItem(nowi1, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() - y;
                        turn = true;
                        break;
                    }
                    d = map.getMapItem(nowi2, j);
                    if (d instanceof Collideable) {
                        posY = ((Collideable) d).getPosY() - y;
                        turn = true;
                        break;
                    }
                    if (j != nowj1 && j == endj2) posY += equipmentWheel.speed;
                }
//                for (Moveable move : listMove) {
//                    if (move == this) continue;
//                    if (move instanceof Bullet) {
//                        if (((Bullet) move).getShotFrom() == this) continue;
//                        else {
//                            if (CollisionUtils.isCollisionWithRect(((Bullet) move).getPosX(), ((Bullet) move).getPosY(),
//                                    ((Bullet) move).getX(), ((Bullet) move).getY(),
//                                    nowi1, nowj2, x, posY - nowj1)) {
//                                ((Bullet) move).setHit(this);
//                                ((Bullet) move).setToBeCleared(true);
//                                posY = ((Collideable) move).getPosY() - y;
//
//                            }
//                        }
//                    } else if (move instanceof Collideable) {
//                        if (CollisionUtils.isCollisionWithRect(((Collideable) move).getPosX(), ((Collideable) move).getPosY(),
//                                ((Collideable) move).getX(), ((Collideable) move).getY(),
//                                nowi1, nowj2, x, posY - nowj1)) {
//                            posY = ((Collideable) move).getPosY() - y;
//                            turn = true;
//                        }
//                    }
//                }
            }
            break;
            case WEST: {
                endi1 = (posX - equipmentWheel.speed) / Config.TILEX;
                endi2 = (posX - equipmentWheel.speed + x) / Config.TILEX;
                for (int i = nowi2; i >= endi1; i--) {
                    d = map.getMapItem(i, nowj1);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() + ((Collideable) d).getX();
                        turn = true;
                        break;
                    }
                    d = map.getMapItem(i, nowj2);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() + ((Collideable) d).getX();
                        turn = true;
                        break;
                    }
                    if (i != nowi2 && i == endi1) posX -= equipmentWheel.speed;
                }
//                for (Moveable move : listMove) {
//                    if (move == this) continue;
//                    if (move instanceof Bullet) {
//                        if (((Bullet) move).getShotFrom() == this) continue;
//                        else {
//                            if (CollisionUtils.isCollisionWithRect(((Bullet) move).getPosX(), ((Bullet) move).getPosY(),
//                                    ((Bullet) move).getX(), ((Bullet) move).getY(),
//                                    endi1, endj1, nowi1 - posX, y)) {
//                                ((Bullet) move).setHit(this);
//                                ((Bullet) move).setToBeCleared(true);
//                                posX = ((Collideable) move).getPosX() + ((Collideable) move).getX();
//
//                            }
//                        }
//                    } else if (move instanceof Collideable) {
//                        if (CollisionUtils.isCollisionWithRect(((Collideable) move).getPosX(), ((Collideable) move).getPosY(),
//                                ((Collideable) move).getX(), ((Collideable) move).getY(),
//                                endi1, endj1, nowi1 - posX, y)) {
//                            posX = ((Collideable) move).getPosX() + ((Collideable) move).getX();
//                            turn = true;
//                        }
//                    }
//                }
            }
            break;
            case EAST: {
                endi1 = (posX + equipmentWheel.speed) / Config.TILEX;
                endi2 = (posX + equipmentWheel.speed + x) / Config.TILEX;
                for (int i = nowi1; i <= endi2; i++) {
                    d = map.getMapItem(i, nowj1);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() - x;
                        turn = true;
                        break;
                    }
                    d = map.getMapItem(i, nowj2);
                    if (d instanceof Collideable) {
                        posX = ((Collideable) d).getPosX() - x;
                        turn = true;
                        break;
                    }
                    if (i != nowi1 && i == endi2) posX += equipmentWheel.speed;
                }
//                for (Moveable move : listMove) {
//                    if (move == this) continue;
//                    if (move instanceof Bullet) {
//                        if (((Bullet) move).getShotFrom() == this) continue;
//                        else {
//                            if (CollisionUtils.isCollisionWithRect(((Bullet) move).getPosX(), ((Bullet) move).getPosY(),
//                                    ((Bullet) move).getX(), ((Bullet) move).getY(),
//                                    nowi2, nowj1, posX - nowi1, y)) {
//                                ((Bullet) move).setHit(this);
//                                ((Bullet) move).setToBeCleared(true);
//                                posX = ((Collideable) move).getPosX() - x;
//
//                            }
//                        }
//                    } else if (move instanceof Collideable) {
//                        if (CollisionUtils.isCollisionWithRect(((Collideable) move).getPosX(), ((Collideable) move).getPosY(),
//                                ((Collideable) move).getX(), ((Collideable) move).getY(),
//                                nowi2, nowj1, posX - nowi1, y)) {
//
//                            posX = ((Collideable) move).getPosX() - x;
//                            turn = true;
//                        }
//                    }
//                }
            }
            break;
        }
        return turn;
    }

    @Override
    public Bullet shoot() {
        if (equipmentBarrel.isShootable()) {
            equipmentBarrel.setZeroIntervalCount();
            return new Bullet(this);
        } else return null;
    }

    @Override
    public Blast showBlast() {
        return new Blast(posX + x / 2, posY + y / 2);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        healthPoint -= bullet.getDamage();
        if (healthPoint > 0) return false;
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
