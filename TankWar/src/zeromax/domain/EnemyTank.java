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

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
        if (CollisionUtils.collideCheck(this, map, facing, listMove, equipmentWheel.speed)
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
