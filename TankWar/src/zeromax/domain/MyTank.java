package zeromax.domain;

import zeromax.equipment.Barrel;
import zeromax.equipment.NormalBarrel;
import zeromax.equipment.NormalWheel;
import zeromax.equipment.Wheel;
import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class MyTank implements Tank, Drawable {
    private int killCount;
    private Barrel equipmentBarrel;
    private int equipmentArmor;
    private Wheel equipmentWheel;
    private int equipmentLight;
    private String imgPath = "TankWar\\res\\img/tank_u.gif";

    private int tPosX;
    private int tPosY;
    private int x;
    private int y;
    private Facing nowFacing;

    @Override
    public int getTPosX() {
        return tPosX;
    }

    @Override
    public int getTPosY() {
        return tPosY;
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
        tPosX = posX;
        tPosY = posY;
        try {
            int[] arr=DrawUtils.getSize(imgPath);
            x = arr[0];
            y = arr[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void draw() {
        try {
            DrawUtils.draw(imgPath, tPosX, tPosY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bullet shoot() {

        return new Bullet(this);
    }

    @Override
    public void move(Facing facing) {
        switch (facing) {
            case EAST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_r.gif";
                }
                if (tPosX + equipmentWheel.speed < Config.WIDTH - x) tPosX += equipmentWheel.speed;
                else tPosX = Config.WIDTH - x;
                break;
            case WEST:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_l.gif";
                }
                if (tPosX - equipmentWheel.speed > 0) tPosX -= equipmentWheel.speed;
                else tPosX = 0;
                break;
            case NORTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_u.gif";
                }
                if (tPosY - equipmentWheel.speed > 0) tPosY -= equipmentWheel.speed;
                else tPosY = 0;
                break;
            case SOUTH:
                if (nowFacing != facing) {
                    nowFacing = facing;
                    imgPath = "TankWar\\res\\img/tank_d.gif";
                }
                if (tPosY + equipmentWheel.speed < Config.HEIGHT - y) tPosY += equipmentWheel.speed;
                else tPosY = Config.HEIGHT - y;
                break;
        }
    }

    @Override
    public void pickUpItem() {

    }

}
