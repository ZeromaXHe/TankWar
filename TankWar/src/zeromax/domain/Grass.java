package zeromax.domain;

import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class Grass implements Drawable {
    //private int healthyPoint;
    private int posX;
    private int posY;
    private static final int x = Config.TILEX;
    private static final int y = Config.TILEY;
    private static final String imgPath = "TankWar\\res\\img/grass.gif";

    private static final int displayPriority= 1;

    public Grass(int posX, int posY){
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
}
