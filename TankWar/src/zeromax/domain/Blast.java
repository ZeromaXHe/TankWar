package zeromax.domain;

import zeromax.interfaces.Clearable;
import zeromax.interfaces.Drawable;
import zeromax.utils.DrawUtils;
import zeromax.utils.SoundUtils;

import java.io.IOException;

public class Blast implements Drawable, Clearable {
    private int posX;
    private int posY;
    private int x;
    private int y;
    private static final int displayPriority = 2;
    private boolean toBeCleared = false;
    private String[] imgPath = {"TankWar/res/img/blast_1.gif",
            "TankWar/res/img/blast_2.gif",
            "TankWar/res/img/blast_3.gif",
            "TankWar/res/img/blast_4.gif",
            "TankWar/res/img/blast_5.gif",
            "TankWar/res/img/blast_6.gif",
            "TankWar/res/img/blast_7.gif",
            "TankWar/res/img/blast_8.gif"};
    private int index;

    public Blast(int posX,int posY){
        index = 0;
        try {
            int[] arr = DrawUtils.getSize(imgPath[index]);
            x = arr[0];
            y = arr[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.posX = posX-x/2;
        this.posY = posY-y/2;
        try {
            SoundUtils.play("TankWar/res/snd/blast.wav");
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
            DrawUtils.draw(imgPath[index], posX, posY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(index<7) index++;
        else {
            toBeCleared = true;
            index = 0;
        }
    }

    @Override
    public boolean isToBeCleared() {
        return toBeCleared;
    }
}
