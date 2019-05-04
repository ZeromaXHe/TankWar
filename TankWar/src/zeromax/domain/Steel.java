package zeromax.domain;

import zeromax.interfaces.*;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class Steel implements Drawable, Collideable, Hitable, Clearable {
    private int healthPoint = 100;
    private int posX;
    private int posY;
    private int x = Config.TILEX;
    private int y = Config.TILEY;
    private String imgPath = "TankWar\\res\\img/steel.gif";
    private static final int displayPriority = 0;
    private boolean toBeCleared = false;

    public Steel(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public int getDisplayPriority() {
        return displayPriority;
    }

    @Override
    public boolean isToBeCleared() {
        return toBeCleared;
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
}
