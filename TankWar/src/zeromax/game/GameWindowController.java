package zeromax.game;

import org.lwjgl.input.Keyboard;
import zeromax.domain.*;
import zeromax.interfaces.*;
import zeromax.model.Map;
import zeromax.ui_n_camera.Camera;
import zeromax.ui_n_camera.UI;
import zeromax.utils.SoundUtils;
import zeromax.utils.Window;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWindowController extends Window {
    Camera cam = new Camera();
    UI ui = new UI();
    Map map;
    CopyOnWriteArrayList<Drawable> list = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Moveable> listMove = new CopyOnWriteArrayList<>();
    MyTank mt;
    EnemyTank[] et = new EnemyTank[3];

    public GameWindowController(String title, int width, int height, int fps) {
        super(title, width, height, fps);
        map = new Map(width / 64, height / 64);
    }

    @Override
    protected void onCreate() {

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if(map.getMapItem(i,j)!=null)list.add(map.getMapItem(i,j));
            }
        }
        mt = new MyTank(5*Config.TILEX, Config.HEIGHT-Config.TILEY);
        list.add(mt);
        listMove.add(mt);
        et[0] = new EnemyTank(0,0);
        et[1] = new EnemyTank(Config.WIDTH-Config.TILEX,0);
        et[2] = new EnemyTank(Config.TILEX,0);
        for(EnemyTank enemy : et){
            list.add(enemy);
            listMove.add(enemy);
        }
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        switch (key) {
            case Keyboard.KEY_W:
                System.out.println("坦克在向北移动");
                mt.move(Facing.NORTH, map, listMove);
                break;
            case Keyboard.KEY_A:
                System.out.println("坦克在向西移动");
                mt.move(Facing.WEST, map, listMove);
                break;
            case Keyboard.KEY_S:
                System.out.println("坦克在向南移动");
                mt.move(Facing.SOUTH, map, listMove);
                break;
            case Keyboard.KEY_D:
                System.out.println("坦克在向东移动");
                mt.move(Facing.EAST, map, listMove);
                break;
            case Keyboard.KEY_J:
                Bullet bullet = mt.shoot();
                if (bullet != null) {
                    System.out.println("坦克正在开火");
                    list.add(bullet);
                } else {
                    System.out.println("正在重新装填，无法开火");
                }
                break;

        }
    }

    @Override
    protected void onDisplayUpdate() {
        cam.displayUpdate();
        ui.displayUpdate();
        Collections.sort(list, Comparator.comparing(Drawable::getDisplayPriority));//方法引用简化了lambda表达式：(o1,o2)->{return o1.getDisplayPriority() - o2.getDisplayPriority();});
        for (Drawable drawable : list) {
            if (drawable instanceof Bullet) {
                ((Bullet) drawable).move(null,map, listMove);
                if (((Bullet) drawable).isToBeCleared()) {
                    Hitable hit = ((Bullet) drawable).getHit();
                    Blast blast = hit.showBlast();
                    if(blast!=null)list.add(blast);
                    if(hit.decreaseHP((Bullet)drawable)) {
                        int i=hit.getPosX()/Config.TILEX;
                        int j=hit.getPosY()/Config.TILEY;
                        if(map.getMapItem(i,j) instanceof Hitable && (Hitable)(map.getMapItem(i,j))==hit)map.setMapItem(i,j,null);
                    }
                }
            }
            if (drawable instanceof Tank) {
                ((Tank) drawable).getEquipmentBarrel().addIntervalCount();
                if(drawable instanceof EnemyTank){
                    ((EnemyTank) drawable).move(((EnemyTank) drawable).getNowFacing(),map, listMove);
                    if(((EnemyTank) drawable).getEquipmentBarrel().isShootable()){
                        Bullet bullet = ((EnemyTank) drawable).shoot();
                        System.out.println("敌方正在开火");
                        list.add(bullet);
                    }
                }
            }

            if(drawable instanceof Clearable){
                if(((Clearable) drawable).isToBeCleared()){
                    if(drawable instanceof Moveable) listMove.remove(drawable);
                    list.remove(drawable);
                    continue;
                }
            }
            drawable.draw();
        }

    }
}
