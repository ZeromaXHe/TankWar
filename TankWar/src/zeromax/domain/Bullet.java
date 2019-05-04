package zeromax.domain;

import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.utils.DrawUtils;
import zeromax.utils.SoundUtils;

import java.io.IOException;

public class Bullet implements Drawable, Collideable, Hitable, Clearable {
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
    private boolean initialMove = false;
    private Hitable hit;

    private String imgPath = "TankWar\\res\\img/bullet_u.gif";

    public Bullet() {
        damage = 10;
        speed = 5;
        interval = 10;
    }

    public Bullet(Tank tank) {
        damage = 10;
        speed = 5;
        interval = 10;

        facing = tank.getNowFacing();
        switch (facing) {
            case NORTH:
                imgPath = "TankWar\\res\\img/bullet_u.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX() / 2 - x / 2;
                posY = tank.getPosY() - y;
                break;
            case SOUTH:
                imgPath = "TankWar\\res\\img/bullet_d.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX() / 2 - x / 2;
                posY = tank.getPosY() + tank.getY();
                break;
            case WEST:
                imgPath = "TankWar\\res\\img/bullet_l.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() - x;
                posY = tank.getPosY() + tank.getY() / 2 - y / 2;
                break;
            case EAST:
                imgPath = "TankWar\\res\\img/bullet_r.gif";
                try {
                    int[] arr = DrawUtils.getSize(imgPath);
                    x = arr[0];
                    y = arr[1];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                posX = tank.getPosX() + tank.getX();
                posY = tank.getPosY() + tank.getY() / 2 - y / 2;
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
    public void draw() {
        try {
            DrawUtils.draw(imgPath, posX, posY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Hitable getHit() {
        return hit;
    }

    public int getDamage() {
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
        return new Blast(posX,posY);
    }

    @Override
    public boolean decreaseHP(Bullet bullet) {
        return true;
    }

    @Override
    public boolean isToBeCleared() {
        return toBeCleared;
    }

    public void move(Map map) {
        hitCheck(map, facing);
    }

    private void hitCheck(Map map, Facing facing) {
        int nowi1 = (posX + 1) / Config.TILEX;
        int nowj1 = (posY + 1) / Config.TILEY;
        int nowi2 = (posX + x - 1) / Config.TILEY;
        int nowj2 = (posY + y - 1) / Config.TILEY;
        int midx = (posX + x / 2) / Config.TILEX;
        int midy = (posY + y / 2) / Config.TILEY;

        int endi1 = nowi1;
        int endj1 = nowj1;
        int endi2 = nowi2;
        int endj2 = nowj2;
        Drawable d;
        if (!initialMove) {
            initialMove = true;
            if (facing == Facing.NORTH || facing == Facing.SOUTH) {
                Drawable d1 = map.getMapItem(midx, nowj1);
                Drawable d2 = map.getMapItem(midx, nowj2);
                if (d1 instanceof Hitable) {
                    toBeCleared = true;
                    hit = (Hitable) d1;
                    hitSound();
                    return;
                } else if (d2 instanceof Hitable) {
                    toBeCleared = true;
                    hit = (Hitable) d2;
                    hitSound();
                    return;
                }
            } else if (facing == Facing.WEST || facing == Facing.EAST) {
                Drawable d1 = map.getMapItem(nowi1, midy);
                Drawable d2 = map.getMapItem(nowi2, midy);
                if (d1 instanceof Hitable) {
                    toBeCleared = true;
                    hit = (Hitable) d1;
                    hitSound();
                    return;
                } else if (d2 instanceof Hitable) {
                    toBeCleared = true;
                    hit = (Hitable) d2;
                    hitSound();
                    return;
                }
            }
        }
        switch (facing) {
            case NORTH: {
                endj1 = (posY - speed) / Config.TILEY;
                endj2 = (posY - speed + y) / Config.TILEY;
                if (nowj2 == endj1) {
                    posY -= speed;
                    break;
                }
                for (int j = nowj2; j >= endj1; j--) {

                    d = map.getMapItem(midx, j);
                    if (d instanceof Hitable) {
                        posY = ((Hitable) d).getPosY() + ((Hitable) d).getY();
                        toBeCleared = true;
                        hit = (Hitable) d;
                        hitSound();
                        return;
                    }

                    if (j != nowj2 && j == endj1) posY -= speed;
                }
            }
            break;
            case SOUTH: {
                endj1 = (posY + speed) / Config.TILEY;
                endj2 = (posY + speed + y) / Config.TILEY;
                if (nowj1 == endj2) {
                    posY += speed;
                    break;
                }
                for (int j = nowj1; j <= endj2; j++) {
                    d = map.getMapItem(midx, j);
                    if (d instanceof Hitable) {
                        posY = ((Hitable) d).getPosY() - y;
                        toBeCleared = true;
                        hit = (Hitable) d;
                        hitSound();
                        return;
                    }

                    if (j != nowj1 && j == endj2) posY += speed;
                }
            }
            break;
            case WEST: {
                endi1 = (posX - speed) / Config.TILEX;
                endi2 = (posX - speed + x) / Config.TILEX;
                if (nowi2 == endi1) {
                    posX -= speed;
                    break;
                }
                for (int i = nowi2; i >= endi1; i--) {
                    d = map.getMapItem(i, midy);
                    if (d instanceof Hitable) {
                        posX = ((Hitable) d).getPosX() + ((Hitable) d).getX();
                        toBeCleared = true;
                        hit = (Hitable) d;
                        hitSound();
                        return;
                    }

                    if (i != nowi2 && i == endi1) posX -= speed;
                }
            }
            break;
            case EAST: {
                endi1 = (posX + speed) / Config.TILEX;
                endi2 = (posX + speed + x) / Config.TILEX;
                if (nowi1 == endi2) {
                    posX += speed;
                    break;
                }
                for (int i = nowi1; i <= endi2; i++) {
                    d = map.getMapItem(i, midy);
                    if (d instanceof Hitable) {
                        posX = ((Hitable) d).getPosX() - x;
                        toBeCleared = true;
                        hit = (Hitable) d;
                        hitSound();
                        return;
                    }

                    if (i != nowi1 && i == endi2) posX += speed;
                }
            }
            break;
        }

    }

    private void hitSound(){
        try {
            SoundUtils.play("TankWar/res/snd/hit.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
