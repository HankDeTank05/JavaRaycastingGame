package dev.hankdetankt05.raycasting.worlds;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.utils.Utils;

import java.awt.*;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;

    public World(Handler handler, String path){
        this.handler = handler;
        loadWorld(path);
    }

    public void update(){

    }

    public void draw(Graphics g){
        /* vvv RAYCASTING GOES HERE vvv */

//        handler.getGameCamera().draw(g);

        /* ^^^ RAYCASTING GOES HERE ^^^ */
    }

    private void loadWorld(String path){
        // TODO: implement the Utils class
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");

        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }
}
