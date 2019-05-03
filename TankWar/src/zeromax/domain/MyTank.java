package zeromax.domain;

import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class MyTank implements Tank {
    private int killCount;
    private int equipmentBarrel;
    private int equipmentArmor;
    private int equipmentWheel;
    private int equipmentLight;
    private String imgPath;
    private int mtPosX;
    private int mtPosY;
    private Facing facing;

    public MyTank(){
        killCount = 0;
        equipmentBarrel = 0;
        equipmentArmor = 0;
        equipmentWheel = 0;
        imgPath = "TankWar/res/img/tank_u.gif";
        facing = Facing.NORTH;
        mtPosX = 0;
        mtPosY = 0;
    }
    @Override
    public void draw(){
        try {
            DrawUtils.draw(imgPath, mtPosX, mtPosY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void shoot(){

    }
    @Override
    public void move(){

    }
    @Override
    public void pickUpItem(){

    }

}
