package zeromax.model;

import zeromax.domain.*;
import zeromax.interfaces.Collideable;
import zeromax.interfaces.Config;
import zeromax.interfaces.Drawable;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private Drawable[][] mapItem;
    private Drawable BORDER;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.init();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Drawable[][] getMapItem() {
        return mapItem;
    }

    public Drawable getMapItem(int i, int j) {
        if (i < width && i >= 0 && j < height && j >= 0) return mapItem[i][j];
        else return BORDER=new Border(i*Config.TILEX,j*Config.TILEY);
    }

    public void setMapItem(int i,int j,Drawable d) {
        mapItem[i][j] = d;
    }

    private void init() {
        Random rand = new Random();
        mapItem = new Drawable[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 5 == 0 || j % 5 == 0) {
                    if (rand.nextInt(5) == 4) {
                        mapItem[i][j] = new Grass(Config.TILEX * i, Config.TILEY * j);
                    }
                } else {
                    switch (rand.nextInt(5)) {
                        case 0:
                            break;
                        case 1:
                            mapItem[i][j] = new Wall(Config.TILEX * i, Config.TILEY * j);
                            break;
                        case 2:
                            mapItem[i][j] = new Steel(Config.TILEX * i, Config.TILEY * j);
                            break;
                        case 3:
                            mapItem[i][j] = new Grass(Config.TILEX * i, Config.TILEY * j);
                            break;
                        case 4:
                            mapItem[i][j] = new Water(Config.TILEX * i, Config.TILEY * j);
                            break;
                    }

                }
            }
        }
    }
}
