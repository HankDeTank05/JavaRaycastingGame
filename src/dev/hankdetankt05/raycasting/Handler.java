package dev.hankdetankt05.raycasting;

import dev.hankdetankt05.raycasting.input.KeyManager;
import dev.hankdetankt05.raycasting.worlds.World;

public class Handler {

    private Game game;
    private World world;

    public Handler(Game game){
        this.game = game;
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

//    public KeyManager getKeyManager(){
//        return game.getKeyManager();
//    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public World getWorld(){
        return world;
    }

    public void setWorld(World world){
        this.world = world;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

//    public GameCamera getGameCamera() {
//        return game.getGameCamera();
//    }
}
