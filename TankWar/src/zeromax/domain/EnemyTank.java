package zeromax.domain;

import zeromax.interfaces.Tank;

public class EnemyTank implements Tank {
    private int killCount;
    private int equipmentBarrel;
    private int equipmentArmor;
    private int equipmentWheel;
    private int equipmentLight;
    private int botAI;
    EnemyTank(){
        killCount = 0;
        equipmentBarrel = 0;
        equipmentArmor = 0;
        equipmentWheel = 0;
        equipmentLight = 0;
        botAI = 0;
    }
    EnemyTank(int botAI){
        killCount = 0;
        equipmentBarrel = 0;
        equipmentArmor = 0;
        equipmentWheel = 0;
        equipmentLight = 0;
        switch(botAI){
            case 0: botAI = 0;
            case 1: botAI = 1;
        }
    }
    @Override
    public void draw(){

    }
    @Override
    public void move(){

    }
    @Override
    public void shoot(){

    }
    @Override
    public void pickUpItem(){

    }
}
