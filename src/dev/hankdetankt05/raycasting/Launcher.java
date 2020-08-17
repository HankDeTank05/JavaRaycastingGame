package dev.hankdetankt05.raycasting;

public class Launcher {

    private static double scale = 1.75;

    public static void main(String[] args){
        Game game = new Game("Raycasting", (int)(640*scale), (int)(480*scale));
        game.start();
    }

}
