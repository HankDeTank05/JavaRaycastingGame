package dev.hankdetankt05.raycasting;

public class Handler {

    private Game game;
    // TODO: implement a World class
//    private World world;

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

//    public World getWorld(){
//        return world;
//    }

//    public void setWorld(World world){
//        this.world = world;
//    }
}
