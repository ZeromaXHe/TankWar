package zeromax.model;

import java.util.Random;

public class Map {
    private int width;
    private int height;
    private int[][] mapItem;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.init();
    }

    public int[][] getMapItem() {
        return mapItem;
    }

    private void init() {
        Random rand = new Random();
        mapItem = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 5 == 0 || j % 5 == 0) {
                    mapItem[i][j] = 0;
                } else {
                    mapItem[i][j] = rand.nextInt(5);
                }
            }
        }
    }
}
