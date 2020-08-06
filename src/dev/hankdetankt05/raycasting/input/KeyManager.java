package dev.hankdetankt05.raycasting.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private boolean[] keys;
    public boolean forward, backward, lStrafe, rStrafe, lTurn, rTurn;

    public KeyManager(){
        keys = new boolean[256];
    }

    public void update(){
        forward = keys[KeyEvent.VK_W];
        backward = keys[KeyEvent.VK_S];
        lStrafe = keys[KeyEvent.VK_A];
        rStrafe = keys[KeyEvent.VK_D];
        lTurn = keys[KeyEvent.VK_LEFT];
        rTurn = keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
