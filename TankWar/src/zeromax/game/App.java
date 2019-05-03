package zeromax.game;

import zeromax.interfaces.Config;

public class App {
    public static void main(String[] args) {
        GameWindowController gwc = new GameWindowController(Config.TITLE,Config.WIDTH,Config.HEIGHT,Config.FPS);
        gwc.start();
    }
}
