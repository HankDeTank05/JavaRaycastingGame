package dev.hankdetankt05.raycasting.utils;

import java.awt.*;

public class DrawCommand {

    private Color color;
    private int x, y;

    public DrawCommand(Color color, int x, int y){
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void execute(Graphics g, int size){
        g.setColor(color);
        g.fillRect(x, y, size, size);
    }

}
