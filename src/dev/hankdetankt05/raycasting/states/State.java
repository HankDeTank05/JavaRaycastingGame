package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }

    // CLASS

    protected Handler handler;

    public State(Handler handler){
        this.handler = handler;
    }

    public abstract void update();

//    public abstract void processInput();

    public abstract void draw(Graphics g);
}
