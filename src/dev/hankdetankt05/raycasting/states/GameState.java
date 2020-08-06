package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.input.KeyManager;
import dev.hankdetankt05.raycasting.worlds.World;

import java.awt.*;

public class GameState extends State {

    private World world;

    private static final int DEFAULT_SCREEN_WIDTH = 640,
                             DEFAULT_SCREEN_HEIGHT = 480;

    private int width, height;
    private double posX, posY, dirX, dirY, planeX, planeY;

    private long time, oldTime;

    private int[][] worldMap = {
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

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler,  "res/worlds/world1.txt");
        handler.setWorld(world);
        this.width = DEFAULT_SCREEN_WIDTH;
        this.height = DEFAULT_SCREEN_HEIGHT;
        this.posX = 22;
        this.posY = 12;
        this.dirX = -1;
        this.dirY = 0;
        this.planeX = 0;
        this.planeY = 0.66;
        this.time = 0;
        this.oldTime = 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        for(int x = 0; x < width; x++){
            // calculate ray position and direction
            double cameraX = 2 * x / (double) width - 1; // x-coordinate in camera space
            double rayDirX = dirX + planeX * cameraX;
            double rayDirY = dirY + planeY * cameraX;

            // which box of the map we're in
            int mapX = (int)posX;
            int mapY = (int)posY;

            // length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            // length of ray from one x or y-side to next x or y-side
            double deltaDistX = Math.abs(1 / rayDirX);
            double deltaDistY = Math.abs(1 / rayDirY);
            double perpWallDist;

            // what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0;  // was there a wall hit?
            int side = -1;  // was a NS or a EW wall hit?

            // calculate step and initial sideDist
            if(rayDirX < 0){
                stepX = -1;
                sideDistX = (posX - mapX) * deltaDistX;
            }
            else{
                stepX = 1;
                sideDistX = (mapX + 1.0 - posX) * deltaDistX;
            }
            if(rayDirY < 0){
                stepY = -1;
                sideDistY = (posY - mapY) * deltaDistY;
            }
            else{
                stepY = 1;
                sideDistY = (mapY + 1.0 - posY) * deltaDistY;
            }

            // perform DDA
            while(hit == 0){
                // jump to next map square or in x-direction or in y-direction
                if(sideDistX < sideDistY){
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }
                else{
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                // check if ray has hit a wall
                if(worldMap[mapX][mapY] > 0){
                    hit = 1;
                }
            }

            if(side == 0){
                perpWallDist = (mapX - posX + (1 - stepX) / 2) / rayDirX;
            }
            else{
                perpWallDist = (mapY - posY + (1 - stepY) / 2) / rayDirY;
            }

            // calculate height of line to draw on screen
            int lineHeight = (int)(height / perpWallDist);

            // calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + height / 2;
            if(drawStart < 0){
                drawStart = 0;
            }
            int drawEnd = lineHeight / 2 + height / 2;
            if(drawEnd >= height){
                drawEnd = height - 1;
            }

            // choose wall color
            Color  color;
            switch(worldMap[mapX][mapY]){
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
            if (side == 1){
                for(int i = 0; i < 1; i ++) {
                    color = color.darker();
                }
            }

            // draw the pixels of the stripe as a vertical line
            g.setColor(color);
            g.drawLine(x, drawStart, x, drawEnd);

            // timing for input and FPS counter
            oldTime = time;
            time = System.nanoTime();
            double frameTime = (time - oldTime) / 1000000000.0;
//            System.out.println(1.0 / frameTime);

            double moveSpeed = frameTime * 5.0;  // the constant value is in squares/second
            double rotSpeed = frameTime * 3.0;  // the constant value is in radians/second

            KeyManager km = handler.getKeyManager();
            // move forward if no wall in front of you
            if(km.forward){
                if(worldMap[(int)(posX + dirX * moveSpeed)][(int)posY] == 0){
                    posX += dirX * moveSpeed;
                }
                if(worldMap[(int)posX][(int)(posY + dirY * moveSpeed)] == 0){
                    posY += dirY * moveSpeed;
                }
            }
            // move backwards if no wall behind you
            if(km.backward){
                if(worldMap[(int)(posX - dirX * moveSpeed)][(int)posY] == 0){
                    posX -= dirX * moveSpeed;
                }
                if(worldMap[(int)posX][(int)(posY - dirY * moveSpeed)] == 0){
                    posY -= dirY * moveSpeed;
                }
            }
            // strafe left if no wall to your left
            if(km.lStrafe){
                if(worldMap[(int)(posX - dirY * moveSpeed)][(int)(posY + dirX * moveSpeed)] == 0){
                    posX -= dirY * moveSpeed;
                    posY += dirX * moveSpeed;
                }
            }
            // strafe right if no wall to your right
            if(km.rStrafe){
                if(worldMap[(int)(posX + dirY * moveSpeed)][(int)(posY - dirX * moveSpeed)] == 0){
                    posX += dirY * moveSpeed;
                    posY -= dirX * moveSpeed;
                }
            }
            // rotate to the right
            if(km.rTurn){
                // both camera direction and camera plane must be rotated
                double oldDirX = dirX;
                dirX = dirX * Math.cos(-rotSpeed) - dirY * Math.sin(-rotSpeed);
                dirY = oldDirX * Math.sin(-rotSpeed) + dirY * Math.cos(-rotSpeed);
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(-rotSpeed) - planeY * Math.sin(-rotSpeed);
                planeY = oldPlaneX * Math.sin(-rotSpeed) + planeY * Math.cos(-rotSpeed);
            }
            // rotate to the left
            if(km.lTurn){
                // both camera direction and camera plane must be rotated
                double oldDirX = dirX;
                dirX = dirX * Math.cos(rotSpeed) - dirY * Math.sin(rotSpeed);
                dirY = oldDirX * Math.sin(rotSpeed) + dirY * Math.cos(rotSpeed);
                double oldPlaneX = planeX;
                planeX = planeX * Math.cos(rotSpeed) - planeY * Math.sin(rotSpeed);
                planeY = oldPlaneX * Math.sin(rotSpeed) + planeY * Math.cos(rotSpeed);
            }
        }
    }
}
