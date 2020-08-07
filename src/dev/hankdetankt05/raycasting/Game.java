package dev.hankdetankt05.raycasting;

import dev.hankdetankt05.raycasting.display.Display;
import dev.hankdetankt05.raycasting.input.KeyManager;
import dev.hankdetankt05.raycasting.states.GameState;
import dev.hankdetankt05.raycasting.states.MenuState;
import dev.hankdetankt05.raycasting.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{

    private Display display;
    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    public final int fps = 120;
    public final long NS_PER_UPDATE = 1000000000 / fps;

    /* States */
    private State gameState;
    private State menuState;

    /* Input */
    private KeyManager keyManager;

    /* Handler */
    private Handler handler;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init(){
        // initialize graphics and setup the game
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        // TODO: implement the Assets class
//        Assets.init();

        handler = new Handler(this);

        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(gameState);
    }

    private void update(){
//        keyManager.update();

        if(State.getState() != null){
            State.getState().update();
        }

    }

    private void draw(Graphics g){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        // clear screen
        g.clearRect(0, 0, width, height);
        /* vvv DRAW HERE! vvv */

        if(State.getState() != null){
            State.getState().draw(g);
        }

        /* ^^^ DRAW HERE! ^^^ */
        bs.show();
        g.dispose();
    }

    private void processInput(){
        keyManager.update();
    }

    @Override
    public void run() {

        init();

        long lastTime = System.nanoTime();
        long lag = 0;

        // main game loop
        while(running){
            long currentTime = System.nanoTime();
            long elapsed = currentTime - lastTime;
            lastTime = currentTime;
            lag += elapsed;
            processInput();

            while(lag >= NS_PER_UPDATE){
                update();
                lag -= NS_PER_UPDATE;
            }
            draw(g);

        }

        stop();

    }

    public synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
}
