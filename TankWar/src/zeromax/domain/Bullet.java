package zeromax.domain;

import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.utils.DrawUtils;
import zeromax.utils.SoundUtils;

import java.io.IOException;

public class Bullet implements Drawable {
    private int damage;
    private int speed;
    private int interval;//使用fps计数
    private int posX;
    private int posY;
    private int x;
    private int y;
    private Facing facing;
    private static final int displayPriority = 0;

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

    public void move(){
        switch(facing){
            case WEST: posX -= speed;break;
            case EAST: posX += speed;break;
            case SOUTH: posY += speed;break;
            case NORTH: posY -= speed;break;
        }
    }
    public boolean isOutOfMap(){
        return (posY <0|| posY > Config.HEIGHT|| posX <0|| posX >Config.WIDTH);
    }

}
