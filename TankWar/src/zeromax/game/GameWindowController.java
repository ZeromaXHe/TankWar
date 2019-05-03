package zeromax.game;

import org.lwjgl.input.Keyboard;
import zeromax.domain.Bullet;
import zeromax.domain.MyTank;
import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;
import zeromax.interfaces.Facing;
import zeromax.model.Map;
import zeromax.ui_n_camera.Camera;
import zeromax.ui_n_camera.UI;
import zeromax.utils.Window;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameWindowController extends Window {
    Camera cam = new Camera();
    UI ui = new UI();
    Map map;
    CopyOnWriteArrayList<Drawable> list = new CopyOnWriteArrayList<>();
    MyTank mt;

    public GameWindowController(String title, int width, int height, int fps) {
        super(title, width, height, fps);
        map = new Map(width,height);
    }

    @Override
    protected void onCreate() {
        mt = new MyTank(Config.WIDTH/2,Config.HEIGHT/2);
        list.add(mt);
    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {
        switch (key){
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
                System.out.println("坦克正在开火");
                Bullet bullet = mt.shoot();
                list.add(bullet);
                break;
        }
    }

    @Override
    protected void onDisplayUpdate() {
        cam.displayUpdate();
        ui.displayUpdate();
        for(Drawable drawable:list){
            drawable.draw();
            if(drawable instanceof Bullet){
                if(((Bullet) drawable).isOutOfMap()) list.remove(drawable);
                ((Bullet) drawable).move();
            }
        }

    }
}
