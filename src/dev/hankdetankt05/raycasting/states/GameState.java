package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;

import java.awt.*;

public class GameState extends State {

    private World world;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler, "res/worlds/world1.txt");
        handler.setWorld(world);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
