package zeromax.domain;

import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.utils.DrawUtils;

import java.io.IOException;

public class Bullet implements Drawable {
    private int damage;
    private int speed;
    private int interval;//使用fps计数
    private int bulletPosX;
    private int bulletPosY;
    private int x;
    private int y;
    private Facing facing;

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
                bulletPosX = tank.getTPosX()+tank.getX()/2-x/2;
                bulletPosY = tank.getTPosY()-y;
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
                bulletPosX = tank.getTPosX()+tank.getX()/2-x/2;
                bulletPosY = tank.getTPosY()+tank.getY();
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
                bulletPosX = tank.getTPosX()-x;
                bulletPosY = tank.getTPosY()+tank.getY()/2-y/2;
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
                bulletPosX = tank.getTPosX()+tank.getX();
                bulletPosY = tank.getTPosY()+tank.getY()/2-y/2;
                break;
        }

    }
    @Override
    public void draw(){
        try {
            DrawUtils.draw(imgPath, bulletPosX, bulletPosY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(){
        switch(facing){
            case WEST: bulletPosX -= speed;break;
            case EAST: bulletPosX += speed;break;
            case SOUTH: bulletPosY += speed;break;
            case NORTH: bulletPosY -= speed;break;
        }
    }
    public boolean isOutOfMap(){
        return (bulletPosY<0||bulletPosY> Config.HEIGHT||bulletPosX<0||bulletPosX>Config.WIDTH);
    }

}
