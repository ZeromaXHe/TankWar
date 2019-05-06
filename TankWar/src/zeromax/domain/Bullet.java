package zeromax.domain;

import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.utils.CollisionUtils;
import zeromax.utils.DrawUtils;
import zeromax.utils.SoundUtils;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bullet implements Drawable, Hitable, Clearable, Moveable, Collideable {
    private int damage;
    private int speed;

    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing facing;
    private static final int displayPriority = 0;
    private boolean toBeCleared = false;

    private Hitable hit;
    private Tank shotFrom;

    private String imgPath = "TankWar\\res\\img/shot_top.gif";

    public Bullet(Tank tank) {
        damage = 10;
        speed = 5;

        shotFrom = tank;

        facing = tank.getFacing();
        switch (facing) {
            case NORTH:
                imgPath = "TankWar\\res\\img/shot_top.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX() / 2 - x / 2;
                posY = tank.getPosY() - y - 1;
                break;
            case SOUTH:
                imgPath = "TankWar\\res\\img/shot_bottom.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX() / 2 - x / 2;
                posY = tank.getPosY() + tank.getY() + 1;
                break;
            case WEST:
                imgPath = "TankWar\\res\\img/shot_left.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() - x - 1;
                posY = tank.getPosY() + tank.getY() / 2 - y / 2;
                break;
            case EAST:
                imgPath = "TankWar\\res\\img/shot_right.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX() + 1;
                posY = tank.getPosY() + tank.getY() / 2 - y / 2;
                break;
        }
        try {
            SoundUtils.play("TankWar/res/snd/fire.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Facing getFacing() {
        return facing;
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

    public Tank getShotFrom() {
        return shotFrom;
    }

    public Hitable getHit() {
        return hit;
    }

    public void setHit(Hitable hit) {
        this.hit = hit;
    }

    int getDamage() {
        return damage;
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
        return new Blast(posX, posY);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        toBeCleared = true;
        return true;
    }

    @Override
    public boolean isToBeCleared() {
        return toBeCleared;
    }

    public void setToBeCleared(boolean toBeCleared) {
        this.toBeCleared = toBeCleared;
    }

    @Override
    public void move(Facing facing, Map map, CopyOnWriteArrayList<Moveable> listMove) {

        CollisionUtils.collideCheck(this, map, facing, listMove, speed);
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

}
