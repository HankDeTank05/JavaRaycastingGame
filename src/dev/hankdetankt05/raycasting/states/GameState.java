package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.input.KeyManager;
import dev.hankdetankt05.raycasting.worlds.World;

import java.awt.*;

public class GameState extends State {

    private World world;

    private static final int DEFAULT_SCREEN_WIDTH = 640,
                             DEFAULT_SCREEN_HEIGHT = 480;

    private static final int TEX_WIDTH = 64, TEX_HEIGHT = 64;

    private int width, height, texWidth, texHeight;
    private double posX, posY, dirX, dirY, planeX, planeY;

    private double moveSpeed, rotSpeed;

    private double[] perpWallDist, rayDirX, rayDirY;
    private int[] mapX, mapY, side;

    private long time, oldTime;

    private int[][] texture;

    private int[][] worldMap = {
        {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 7, 7, 7, 7, 7, 7, 7, 7},
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7},
        {4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7},
        {4, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7},
        {4, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 7},
        {4, 0, 4, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 7, 0, 7, 7, 7, 7, 7},
        {4, 0, 5, 0, 0, 0, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 7, 0, 0, 0, 7, 7, 7, 1},
        {4, 0, 6, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 7, 0, 0, 0, 0, 0, 0, 8},
        {4, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 1},
        {4, 0, 8, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 7, 0, 0, 0, 0, 0, 0, 8},
        {4, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 7, 0, 0, 0, 7, 7, 7, 1},
        {4, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 0, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7, 1},
        {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
        {8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
        {4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 6, 0, 6, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3},
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 6, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2},
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 2},
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 6, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2},
        {4, 0, 6, 0, 6, 0, 0, 0, 0, 4, 6, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 2},
        {4, 0, 0, 5, 0, 0, 0, 0, 0, 4, 6, 0, 6, 2, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2},
        {4, 0, 6, 0, 6, 0, 0, 0, 0, 4, 6, 0, 6, 2, 0, 0, 5, 0, 0, 2, 0, 0, 0, 2},
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 6, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2},
        {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3}
    };

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler,  "res/worlds/world1.txt");
        handler.setWorld(world);
        this.width = DEFAULT_SCREEN_WIDTH;
        this.height = DEFAULT_SCREEN_HEIGHT;
        this.texWidth = TEX_WIDTH;
        this.texHeight = TEX_HEIGHT;
        this.texture = new int[8][texWidth * texHeight];
        this.posX = 22.0;
        this.posY = 11.5;
        this.dirX = -1;
        this.dirY = 0;
        this.planeX = 0;
        this.planeY = 0.66;
        this.time = 0;
        this.oldTime = 0;

        this.perpWallDist = new double[width];
        this.rayDirX = new double[width];
        this.rayDirY = new double[width];
        this.mapX = new int[width];
        this.mapY = new int[width];
        this.side = new int[width];

        this.moveSpeed = handler.getGame().NS_PER_UPDATE * 5.0 / 1000000000;
        this.rotSpeed = handler.getGame().NS_PER_UPDATE * 3.0 / 1000000000;

        createTextures();
        textureSwap();
    }

    private void createTextures(){
        for(int x = 0; x < texWidth; x++){
            for(int y = 0; y < texHeight; y++){
                int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
                //int xcolor = x * 256 / texWidth;
                int ycolor = y * 256 / texHeight;
                int xycolor = y * 128 / texHeight + x * 128 / texWidth;
                int tex0multiplier = 0;
                int tex5multiplier = 0;
                if(x != y && x != texWidth - y){
                    tex0multiplier = 1;
                }
                if(x % 16 > 0 && y % 16 > 0){
                    tex5multiplier = 1;
                }
                texture[0][texWidth * y + x] = 65536 * 254 * tex0multiplier;  // flat red texture with black cross
                texture[1][texWidth * y + x] = xycolor + 256 * xycolor + 65536 * xycolor;  // sloped grayscale
                texture[2][texWidth * y + x] = 256 * xycolor + 65536 * xycolor;  // sloped yellow gradient
                texture[3][texWidth * y + x] = xorcolor + 256 * xorcolor + 65536 * xorcolor;  // xor grayscale
                texture[4][texWidth * y + x] = 256 * xorcolor; // xor green
                texture[5][texWidth * y + x] = 65536 * 192 * tex5multiplier;  // red bricks
                texture[6][texWidth * y + x] = 65536 * ycolor;  // red gradient
                texture[7][texWidth * y + x] = 128 + 256 * 128 + 65536 * 128;  // flat gray texture
            }
        }
    }

    private void textureSwap(){
        // swap texture x/y since they'll be used as vertical stripes
        for(int i = 0; i < texture.length; i++){
            for(int x = 0; x < texWidth; x++){
                for(int y = 0; y < x; y++){
                    int temp = texture[i][texWidth * y + x];
                    texture[i][texWidth * y + x] = texture[i][texWidth * x + y];
                    texture[i][texWidth * x + y] = temp;
                }
            }
        }
    }


    public void move(){
        // timing for input and FPS counter
//        oldTime = time;
//        time = System.nanoTime();
//        double frameTime = (time - oldTime) / 1000000000.0;
////            System.out.println(1.0 / frameTime);
//
//        double moveSpeed = frameTime * 5.0;  // the constant value is in squares/second
//        System.out.println("moveSpeed = " + moveSpeed);
//        double rotSpeed = frameTime * 3.0;  // the constant value is in radians/second
//        System.out.println("rotSpeed = " + rotSpeed);

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

    @Override
    public void update() {

        move();

        for(int x = 0; x < width; x++) {
            // calculate ray position and direction
            double cameraX = 2 * x / (double) width - 1; // x-coordinate in camera space
            rayDirX[x] = dirX + planeX * cameraX;
            rayDirY[x] = dirY + planeY * cameraX;

            // which box of the map we're in
            mapX[x] = (int) posX;
            mapY[x] = (int) posY;

            // length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            // length of ray from one x or y-side to next x or y-side
            double deltaDistX = Math.abs(1 / rayDirX[x]);
            double deltaDistY = Math.abs(1 / rayDirY[x]);
//            double perpWallDist;

            // what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0;  // was there a wall hit?
            side[x] = -1;  // was a NS or a EW wall hit?

            // calculate step and initial sideDist
            if (rayDirX[x] < 0) {
                stepX = -1;
                sideDistX = (posX - mapX[x]) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX[x] + 1.0 - posX) * deltaDistX;
            }
            if (rayDirY[x] < 0) {
                stepY = -1;
                sideDistY = (posY - mapY[x]) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY[x] + 1.0 - posY) * deltaDistY;
            }

            // perform DDA
            while (hit == 0) {
                // jump to next map square or in x-direction or in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX[x] += stepX;
                    side[x] = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY[x] += stepY;
                    side[x] = 1;
                }
                // check if ray has hit a wall
                if (worldMap[mapX[x]][mapY[x]] > 0) {
                    hit = 1;
                }
            }

            if (side[x] == 0) {
                perpWallDist[x] = (mapX[x] - posX + (1 - stepX) / 2) / rayDirX[x];
            } else {
                perpWallDist[x] = (mapY[x] - posY + (1 - stepY) / 2) / rayDirY[x];
            }
        }

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        for(int x = 0; x < width; x++){

            /* update code was here */

            //////////////////////////
            /* vvv DRAWING CODE vvv */

            // calculate height of line to draw on screen
            int lineHeight = (int)(height / perpWallDist[x]);

            // calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + height / 2;
            if(drawStart < 0){
                drawStart = 0;
            }
            int drawEnd = lineHeight / 2 + height / 2;
            if(drawEnd >= height){
                drawEnd = height - 1;
            }

            // texturing calculations
            int texNum = worldMap[mapX[x]][mapY[x]] - 1;  // 1 subtracted from it so that texture 0 can be used!

            // calculate value of wallX
            double wallX;  // where exactly the wall was hit
            if(side[x] == 0){
                wallX = posY + perpWallDist[x] * rayDirY[x];
            }
            else{
                wallX = posX + perpWallDist[x] * rayDirX[x];
            }

            wallX -= Math.floor(wallX);

            // x coordinate on the texture
            int texX = (int)(wallX * (double)texWidth);
            if(side[x] == 0 && rayDirX[x] > 0){
                texX = texWidth - texX - 1;
            }
            if(side[x] == 1 && rayDirY[x] < 0){
                texX = texWidth - texX - 1;
            }

            // How much to increase the texture coordinate per screen pixel
            double step = 1.0 * texHeight / lineHeight;
            // starting texture coordinate
            double texPos = (drawStart - height / 2 + lineHeight / 2) * step;
            for(int y = drawStart; y < drawEnd; y++){
                // cast the texture coordinate to integer and mask with (texHeight - 1) in case of overflow
                int texY = (int)texPos;
                texPos += step;
                int colorNum = texture[texNum][texHeight * texX + texY];
                // make color darker for y-sides: r, g and b byte each divided through with a "shift" and an "and"
                Color color = new Color(colorNum);
                if(side[x] == 1){
                    for(int i = 0; i < 1; i++) {
                        color = color.darker();
                    }
                }
                g.setColor(color);
                g.drawRect(x, y, 1, 1);
            }

            /*

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

            */

            /* ^^^ DRAWING CODE ^^^ */
            //////////////////////////
        }
    }
}
