package zeromax.game;

import org.lwjgl.input.Keyboard;
import zeromax.domain.*;
import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.interfaces.Tank;
import zeromax.model.Map;
import zeromax.ui_n_camera.Camera;
import zeromax.ui_n_camera.UI;
import zeromax.utils.Window;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWindowController extends Window {
    Camera cam = new Camera();
    UI ui = new UI();
    Map map;
    CopyOnWriteArrayList<Drawable> list = new CopyOnWriteArrayList<>();
    MyTank mt;

    public GameWindowController(String title, int width, int height, int fps) {
        super(title, width, height, fps);
        map = new Map(width / 64, height / 64);
    }

    @Override
    protected void onCreate() {

        int[][] mapItem = map.getMapItem();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                switch (mapItem[i][j]) {
                    case 0:
                        break;
                    case 1:
                        list.add(new Wall(Config.TILEX * i, Config.TILEY * j));
                        break;
                    case 2:
                        list.add(new Steel(Config.TILEX * i, Config.TILEY * j));
                        break;
                    case 3:
                        list.add(new Grass(Config.TILEX * i, Config.TILEY * j));
                        break;
                    case 4:
                        list.add(new Water(Config.TILEX * i, Config.TILEY * j));
                        break;
                }
            }
        }
        mt = new MyTank(Config.WIDTH / 2, Config.HEIGHT / 2);
        list.add(mt);
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        switch (key) {
            case Keyboard.KEY_W:
                System.out.println("坦克在向北移动");
                mt.move(Facing.NORTH);
                break;
            case Keyboard.KEY_A:
                System.out.println("坦克在向西移动");
                mt.move(Facing.WEST);
                break;
            case Keyboard.KEY_S:
                System.out.println("坦克在向南移动");
                mt.move(Facing.SOUTH);
                break;
            case Keyboard.KEY_D:
                System.out.println("坦克在向东移动");
                mt.move(Facing.EAST);
                break;
            case Keyboard.KEY_J:
                Bullet bullet = mt.shoot();
                if (bullet != null) {
                    System.out.println("坦克正在开火");
                    list.add(bullet);
                    break;
                } else {
                    System.out.println("正在重新装填，无法开火");
                }

        }
    }

    @Override
    protected void onDisplayUpdate() {
        cam.displayUpdate();
        ui.displayUpdate();
        Collections.sort(list, Comparator.comparing(Drawable::getDisplayPriority));//方法引用简化了lambda表达式：(o1,o2)->{return o1.getDisplayPriority() - o2.getDisplayPriority();});
        for (Drawable drawable : list) {
            drawable.draw();
            if (drawable instanceof Bullet) {
                if (((Bullet) drawable).isOutOfMap()) list.remove(drawable);
                ((Bullet) drawable).move();
            }
            if (drawable instanceof Tank) {
                ((Tank) drawable).getEquipmentBarrel().addIntervalCount();
            }
        }

    }
}
