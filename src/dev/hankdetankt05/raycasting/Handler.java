package dev.hankdetankt05.raycasting;

import dev.hankdetankt05.raycasting.input.KeyManager;

public class Handler {

    private Game game;

    public Handler(Game game){
        this.game = game;
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

//    public GameCamera getGameCamera() {
//        return game.getGameCamera();
//    }
}
