package zeromax.domain;

import zeromax.interfaces.Collideable;
import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class Wall implements Drawable, Collideable {
    private int healthyPoint;
    private int posX;
    private int posY;
    private int x = Config.TILEX;
    private int y = Config.TILEY;
    private String imgPath = "TankWar\\res\\img/wall.gif";
    private static final int displayPriority = 0;

    public Wall(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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
}