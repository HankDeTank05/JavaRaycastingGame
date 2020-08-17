package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.input.KeyManager;

import java.awt.*;

public class GameState extends State {

    private final int mapWidth = 24, mapHeight = 24;
    private final int[][] worldMap = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private double posX = 22, posY = 12; // position vector
    private double dirX = -1, dirY = 0; // direction vector
    private double planeX = 0, planeY = 0.66; // camera plane vector

    private long time = System.nanoTime(); // time of the current frame
    private long oldTime = System.nanoTime(); // time of the previous frame

    private double moveSpeed = 0.002;
    private double rotationSpeed = 0.0009;

    private int[] lineHeight;
    private Color[] lineColor;

    private int width, height;

    public GameState(Handler handler) {
        super(handler);
        width = handler.getWidth();
        height = handler.getHeight();
        lineHeight = new int[width];
        lineColor = new Color[width];
    }

    @Override
    public void update() {
        for(int x = 0; x < width; x++) {
            // calculate ray position and direction
            double cameraX = 2 * x / (double) width - 1; // x-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            // which box of the map we're in
            int mapX = (int) posX;
            int mapY = (int) posY;

            // length of ray from current position to next x- or y-side
            double sideDistX;
            double sideDistY;

            // length of ray from one x- or y- side to the next x- or y-side
            double deltaDistX = Math.abs(1 / rayDirX);
            double deltaDistY = Math.abs(1 / rayDirY);
            double perpWallDist;

            // which direction to step in x- or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0; // was a wall hit?
            // TODO: if there are rendering problems, the following line of code might be the cause
            int side = 0; // was a NS wall or an EW wall hit?

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }

            // perform DDA
            while (hit == 0) {
                // jump to next map square, OR in x-direction, OR in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                // check if ray has hit a wall
                if (worldMap[mapX][mapY] > 0) {
                    hit = 1;
                }
            }

            // calculate distance projected on camera direction (euclidean distance will give fisheye effect!)
            if (side == 0) {
                perpWallDist = (mapX - posX + (1 - stepX) / 2) / rayDirX;
            } else {
                perpWallDist = (mapY - posY + (1 - stepY) / 2) / rayDirY;
            }

            // calculate height of line to draw on screen
            lineHeight[x] = (int) (height / perpWallDist);

            // choose wall color
            Color color;
            switch (worldMap[mapX][mapY]) {
                case 1:
                    color = Color.red;
                    break;
                case 2:
                    color = Color.green;
                    break;
                case 3:
                    color = Color.blue;
                    break;
                case 4:
                    color = Color.white;
                    break;
                default:
                    color = Color.yellow;
                    break;
            }

            // give x and y sides different brightness
            if (side == 1) {
                color = color.darker();
            }

            lineColor[x] = color;
        }
        // timing for input and FPS counter
        oldTime = time;
        time = System.nanoTime();
        long frameTime = (time - oldTime) / 1000000000;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        for(int x = 0; x < width; x++){
            int drawStart = -lineHeight[x] / 2 + height / 2;
            if(drawStart < 0){
                drawStart = 0;
            }
            int drawEnd = lineHeight[x] / 2 + height / 2;
            if(drawEnd >= height){
                drawEnd = height - 1;
            }
            g.setColor(lineColor[x]);
            g.drawLine(x, drawStart, x, drawEnd);
        }
    }

    public void processInput(KeyManager km){

        // move forward if the W key is pressed
        if(km.forward){
            if(worldMap[(int)(posX + dirX * moveSpeed)][(int)posY] == 0){
                posX += dirX * moveSpeed;
            }
            if(worldMap[(int)posX][(int)(posY + dirY * moveSpeed)] == 0){
                posY += dirY * moveSpeed;
            }
        }

        // move backward if the S key is pressed
        if(km.backward){
            if(worldMap[(int)(posX - dirX * moveSpeed)][(int)posY] == 0){
                posX -= dirX * moveSpeed;
            }
            if(worldMap[(int)posX][(int)(posY - dirY * moveSpeed)] == 0){
                posY -= dirY * moveSpeed;
            }
        }

        // strafe left if the A key is pressed
        // TODO: implement strafe left

        // strafe right if the D key is pressed
        // TODO: implement strafe right

        // rotate left if the left arrow key is pressed
        if(km.lTurn){
            double oldDirX = dirX;
            dirX = dirX * Math.cos(rotationSpeed) - dirY * Math.sin(rotationSpeed);
            dirY = oldDirX * Math.sin(rotationSpeed) + dirY * Math.cos(rotationSpeed);
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(rotationSpeed) - planeY * Math.sin(rotationSpeed);
            planeY = oldPlaneX * Math.sin(rotationSpeed) + planeY * Math.cos(rotationSpeed);
        }

        // rotate right if the right arrow key is pressed
        if(km.rTurn){
            double oldDirX = dirX;
            dirX = dirX * Math.cos(-rotationSpeed) - dirY * Math.sin(-rotationSpeed);
            dirY = oldDirX * Math.sin(-rotationSpeed) + dirY * Math.cos(-rotationSpeed);
            double oldPlaneX = planeX;
            planeX = planeX * Math.cos(-rotationSpeed) - planeY * Math.sin(-rotationSpeed);
            planeY = oldPlaneX * Math.sin(-rotationSpeed) + planeY * Math.cos(-rotationSpeed);
        }
    }
}
