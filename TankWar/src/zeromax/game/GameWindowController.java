package zeromax.game;

import zeromax.domain.MyTank;
import zeromax.model.Map;
import zeromax.ui_n_camera.Camera;
import zeromax.ui_n_camera.UI;
import zeromax.utils.Window;

public class GameWindowController extends Window {
    Camera cam = new Camera();
    UI ui = new UI();
    Map map;
    MyTank mt = new MyTank();

    public GameWindowController(String title, int width, int height, int fps) {
        super(title, width, height, fps);
        map = new Map(width,height);
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onMouseEvent(int key, int x, int y) {

    }

    @Override
    protected void onKeyEvent(int key) {

    }

    @Override
    protected void onDisplayUpdate() {
        cam.displayUpdate();
        ui.displayUpdate();
        mt.draw();
    }
}
