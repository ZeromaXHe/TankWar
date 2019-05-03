package zeromax.domain;

public class Bullet {
    private int damage;
    private int speed;
    private int interval;//使用fps计数

    Bullet(){
        damage = 10;
        speed = 5;
        interval = 10;
    }
    Bullet(int equipmentBarrel){
        switch (equipmentBarrel){
            case 0: damage = 10; speed = 5; interval = 10; break;
            case 1: damage = 20; speed = 5; interval = 15; break;
        }
    }

    public void move(){

    }

}
