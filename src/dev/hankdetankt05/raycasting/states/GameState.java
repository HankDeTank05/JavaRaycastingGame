package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.gfx.Assets;
import dev.hankdetankt05.raycasting.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GameState extends State {

    private final int mapWidth = 24, mapHeight = 24;
    private final int[][] worldMap = {
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

    private double posX = 22, posY = 11.5; // position vector
    private double dirX = -1, dirY = 0; // direction vector
    private double planeX = 0, planeY = 0.66; // camera plane vector

    private long time = System.nanoTime(); // time of the current frame
    private long oldTime = System.nanoTime(); // time of the previous frame

    private double moveSpeed = 0.05;
    private double rotationSpeed = 0.03;

    private int[] lineHeight;
    private int[] drawStart;
    private int[] drawEnd;
    private BufferedImage[] drawColumn;

    private int texWidth = 64;
    private int texHeight = 64;

    private BufferedImage[][] textures = new BufferedImage[8][texWidth];


    private boolean moveForwardX = false;
    private boolean moveForwardY = false;
    private boolean moveBackwardX = false;
    private boolean moveBackwardY = false;
    private boolean moveLeftX = false;
    private boolean moveLeftY = false;
    private boolean moveRightX = false;
    private boolean moveRightY = false;
    private boolean rotateLeft = false;
    private boolean rotateRight = false;

    private int width, height;

    public GameState(Handler handler) {
        super(handler);
        width = handler.getWidth();
        height = handler.getHeight();
        lineHeight = new int[width];
        drawStart = new int[width];
        drawEnd = new int[width];
        drawColumn = new BufferedImage[width];

        generateTextures();
    }

    private void generateTextures(){
        for(int x = 0; x < texWidth; x++){
            for(int y = 0; y < texHeight; y++) {
                int mappedIndex = texWidth * y + x;

                int xorcolor = (x * 256 / texWidth) ^ (y * 256 / texHeight);
//                int xcolor = x * 256 / texWidth;
                int ycolor = y * 256 / texHeight;
                int xycolor = y * 128 / texHeight + x * 128 / texWidth;

                int tex0scalar = 0;
                if (x != y && x != texWidth - y) {
                    tex0scalar = x;
                }

                int tex5scalar = 0;
                if (x % 16 > 0 && y % 16 > 0) {
                    tex5scalar = 1;
                }
            }

            textures[0][x] = Assets.eagle.getSubimage(x, 0, 1, texHeight); //flat red texture with black cross
            textures[1][x] = Assets.redbrick.getSubimage(x, 0, 1, texHeight); //sloped greyscale
            textures[2][x] = Assets.purplestone.getSubimage(x, 0, 1, texHeight); //sloped yellow gradient
            textures[3][x] = Assets.greystone.getSubimage(x, 0, 1, texHeight); //xor greyscale
            textures[4][x] = Assets.bluestone.getSubimage(x, 0, 1, texHeight); //xor green
            textures[5][x] = Assets.mossy.getSubimage(x, 0, 1, texHeight); //red bricks
            textures[6][x] = Assets.wood.getSubimage(x, 0, 1, texHeight); //red gradient
            textures[7][x] = Assets.colorstone.getSubimage(x, 0, 1, texHeight); //flat grey texture

        }
    }

    @Override
    public void update() {
        for(int x = 0; x < width; x++) {

            if(moveForwardX){

            }
            if(moveForwardY){

            }
            if(moveBackwardX){

            }
            if(moveBackwardY){

            }
            if(moveLeftX){

            }
            if(moveLeftY){

            }
            if(moveRightX){

            }
            if(moveRightY){

            }
            if(rotateLeft){

            }
            if(rotateRight){

            }

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

            drawStart[x] = -lineHeight[x] / 2 + height / 2;
//            if(drawStart[x] < 0){
//                drawStart[x] = 0;
//            }
            drawEnd[x] = lineHeight[x] / 2 + height / 2;
            if(drawEnd[x] >= height){
                drawEnd[x] = height - 1;
            }

//            // choose wall color
//            Color color;
//            switch (worldMap[mapX][mapY]) {
//                case 1:
//                    color = Color.red;
//                    break;
//                case 2:
//                    color = Color.green;
//                    break;
//                case 3:
//                    color = Color.blue;
//                    break;
//                case 4:
//                    color = Color.white;
//                    break;
//                default:
//                    color = Color.yellow;
//                    break;
//            }
//
//            // give x and y sides different brightness
//            if (side == 1) {
//                color = color.darker();
//            }
//
//            lineColor[x] = color;

            // texturing calculations
            int texNum = worldMap[mapX][mapY] - 1; // 1 is subtracted from it so that texture 0 can be used!

            // calculate the value of wallX
            double wallX; // where exactly the wall was hit
            if(side == 0){
                wallX = posY + perpWallDist * rayDirY;
            }
            else{
                wallX = posX + perpWallDist * rayDirX;
            }
            wallX -= Math.floor((wallX));

            // x-coordinate on the texture
            int texX = (int)(wallX * (double)texWidth);
            if(side == 0 && rayDirX > 0){
                texX = texWidth - texX - 1;
            }
            if(side == 1 && rayDirY < 0){
                texX = texWidth - texX - 1;
            }

            // how much to increase the texture coordinate per screen pixel
            double step = 1.0 * texHeight / lineHeight[x];
            // starting texture coordinate
            double texPos = (drawStart[x] - height / 2 + lineHeight[x] / 2) * step;

            BufferedImage image = textures[texNum][texX];
            // make color darker for y-sides
//                if(side == 1){
//                    color = color.darker();
//                }
            drawColumn[x] = image;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        for(int x = 0; x < width; x++){
            g.drawImage(drawColumn[x], x, drawStart[x], 1, lineHeight[x], null);
        }
    }

    public void processInput(KeyManager km){
        moveForwardX = false;
        moveForwardY = false;
        moveBackwardX = false;
        moveBackwardY = false;
        moveLeftX = false;
        moveLeftY = false;
        moveRightX = false;
        moveRightY = false;
        rotateLeft = false;
        rotateRight = false;

        // move forward if the W key is pressed
        if(km.forward){
            if(worldMap[(int)(posX + dirX * moveSpeed)][(int)posY] == 0){
                posX += dirX * moveSpeed;
                moveForwardX = true;
            }
            if(worldMap[(int)posX][(int)(posY + dirY * moveSpeed)] == 0){
                posY += dirY * moveSpeed;
                moveForwardY = true;
            }
        }

        // move backward if the S key is pressed
        if(km.backward){
            if(worldMap[(int)(posX - dirX * moveSpeed)][(int)posY] == 0){
                posX -= dirX * moveSpeed;
                moveBackwardX = true;
            }
            if(worldMap[(int)posX][(int)(posY - dirY * moveSpeed)] == 0){
                posY -= dirY * moveSpeed;
                moveBackwardY = true;
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
