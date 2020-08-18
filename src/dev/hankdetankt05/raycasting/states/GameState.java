package dev.hankdetankt05.raycasting.states;

import dev.hankdetankt05.raycasting.Handler;
import dev.hankdetankt05.raycasting.gfx.Assets;
import dev.hankdetankt05.raycasting.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GameState extends State {

    private final int mapWidth = 24, mapHeight = 24, numSprites = 19;
    private final int[][] worldMap = {
        {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 4, 6, 4, 4, 6, 4, 6, 4, 4, 4, 6, 4},
        {8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {8, 0, 3, 3, 0, 0, 0, 0, 0, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},
        {8, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6},
        {8, 0, 3, 3, 0, 0, 0, 0, 0, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 4, 0, 0, 0, 0, 0, 6, 6, 6, 0, 6, 4, 6},
        {8, 8, 8, 8, 0, 8, 8, 8, 8, 8, 8, 4, 4, 4, 4, 4, 4, 6, 0, 0, 0, 0, 0, 6},
        {7, 7, 7, 7, 0, 7, 7, 7, 7, 0, 8, 0, 8, 0, 8, 0, 8, 4, 0, 4, 0, 6, 0, 6},
        {7, 7, 0, 0, 0, 0, 0, 0, 7, 8, 0, 8, 0, 8, 0, 8, 8, 6, 0, 0, 0, 0, 0, 6},
        {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 6, 0, 0, 0, 0, 0, 4},
        {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 6, 0, 6, 0, 6, 0, 6},
        {7, 7, 0, 0, 0, 0, 0, 0, 7, 8, 0, 8, 0, 8, 0, 8, 8, 6, 4, 6, 0, 6, 6, 6},
        {7, 7, 7, 7, 0, 7, 7, 7, 7, 8, 8, 4, 0, 6, 8, 4, 8, 3, 3, 3, 0, 3, 3, 3},
        {2, 2, 2, 2, 0, 2, 2, 2, 2, 4, 6, 4, 0, 0, 6, 0, 6, 3, 0, 0, 0, 0, 0, 3},
        {2, 2, 0, 0, 0, 0, 0, 2, 2, 4, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 0, 0, 0, 3},
        {2, 0, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 4, 3, 0, 0, 0, 0, 0, 3},
        {1, 0, 0, 0, 0, 0, 0, 0, 1, 4, 4, 4, 4, 4, 6, 0, 6, 3, 3, 0, 0, 0, 3, 3},
        {2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 1, 2, 2, 2, 6, 6, 0, 0, 5, 0, 5, 0, 5},
        {2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 5, 0, 5, 0, 0, 0, 5, 5},
        {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 5, 0, 5, 0, 5, 0, 5, 0, 5},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 5, 0, 5, 0, 5, 0, 5, 0, 5},
        {2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 5, 0, 5, 0, 0, 0, 5, 5},
        {2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5}
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
    private Color[][] drawFloorCeiling;

    private int texWidth = 64;
    private int texHeight = 64;

    private static String[] fcms = {"none", "scanline", "vertical"};
    public static final int floorcastingMethod = 1;
    private static final int floorTexture = 3;
    private static final int ceilingTexture = 6;

    private static final int renderRes = 1;

    private BufferedImage[][] textures = new BufferedImage[11][texWidth];

    private static final double[][] sprites = {
            {20.5, 11.5, 10}, // green light in front of playerstart
            // green lights in every room
            {18.5, 4.5, 10},
            {10.0, 4.5, 10},
            {10.0, 12.5, 10},
            {3.5, 6.5, 10},
            {3.5, 20.5, 10},
            {3.5, 14.5, 10},
            {14.5, 20.5, 10},

            // row of pillars in front of wall: fisheye test
            {18.5, 10.5, 9},
            {18.5, 11.5, 9},
            {18.5, 12.5, 9},

            // some barrels around the map
            {21.5, 1.5, 8},
            {15.5, 1.5, 8},
            {16.0, 1.8, 8},
            {16.2, 1.2, 8},
            {3.5, 2.5, 8},
            {9.5, 15.5, 8},
            {10.0, 15.1, 8},
            {10.5, 15.8, 8}
    };

    private double[] ZBuffer;
    private int[] spriteOrder;
    private double[] spriteDistance;
    private BufferedImage[] drawSprites;
    private int[] drawSpritesStart;
    private int[] drawSpritesEnd;
    private int[] drawSpritesHeight;
    private int[] spriteStripeWidth;

    private static boolean spriteCasting = false;

    private int width, height;

    public GameState(Handler handler) {
        super(handler);
        width = handler.getWidth();
        height = handler.getHeight();
        lineHeight = new int[width];
        drawStart = new int[width];
        drawEnd = new int[width];
        drawColumn = new BufferedImage[width];
        drawFloorCeiling = new Color[width][height];
        ZBuffer = new double[width];
        spriteOrder = new int[numSprites];
        spriteDistance = new double[numSprites];
        drawSprites = new BufferedImage[width];
        drawSpritesStart = new int[width];
        drawSpritesEnd = new int[width];
        drawSpritesHeight = new int[width];
        spriteStripeWidth = new int[width];

        generateTextures();
    }

    private void generateTextures(){
        for(int x = 0; x < texWidth; x++){

            // surface textures
            textures[0][x] = Assets.eagle.getSubimage(x, 0, 1, texHeight);
            textures[1][x] = Assets.redbrick.getSubimage(x, 0, 1, texHeight);
            textures[2][x] = Assets.purplestone.getSubimage(x, 0, 1, texHeight);
            textures[3][x] = Assets.greystone.getSubimage(x, 0, 1, texHeight);
            textures[4][x] = Assets.bluestone.getSubimage(x, 0, 1, texHeight);
            textures[5][x] = Assets.mossy.getSubimage(x, 0, 1, texHeight);
            textures[6][x] = Assets.wood.getSubimage(x, 0, 1, texHeight);
            textures[7][x] = Assets.colorstone.getSubimage(x, 0, 1, texHeight);

            // sprite textures
            textures[8][x] = Assets.barrel.getSubimage(x, 0, 1, texHeight);
            textures[9][x] = Assets.pillar.getSubimage(x, 0, 1, texHeight);
            textures[10][x] = Assets.greenlight.getSubimage(x, 0, 1, texHeight);

            for(int y = 0; y < texHeight; y++){
                if(textures[8][x].getRGB(0, y) == Color.black.getRGB()){
                    textures[8][x].setRGB(0, y, new Color(0, 0, 0, 255).getRGB());
                }
                if(textures[9][x].getRGB(0, y) == Color.black.getRGB()){
                    textures[9][x].setRGB(0, y, new Color(0, 0, 0, 255).getRGB());
                }
                if(textures[10][x].getRGB(0, y) == Color.black.getRGB()){
                    textures[10][x].setRGB(0, y, new Color(0, 0, 0, 255).getRGB());
                }
            }
        }
    }

    private void sortSprites(int[] order, double[] dist, int amount){
        int endOfSortedSection = 0;
        while(endOfSortedSection < amount){
            int smallestIndex = findIndexOfSmallest(dist);
            swap(order, endOfSortedSection, smallestIndex);
            swap(dist, endOfSortedSection, smallestIndex);
            endOfSortedSection++;
        }
    }

    private int findIndexOfSmallest(double[] arr){
        int smallestIndex = 0;
        for(int i = 1; i < arr.length; i++){
            if(arr[i] < arr[smallestIndex]){
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }

    private void swap(double[] arr, int ia, int ib){
        double temp = arr[ia];
        arr[ia] = arr[ib];
        arr[ib] = temp;
    }

    private void swap(int[] arr, int ia, int ib){
        int temp = arr[ia];
        arr[ia] = arr[ib];
        arr[ib] = temp;
    }

    @Override
    public void update() {
        // FLOOR CASTING
        if(fcms[floorcastingMethod].equalsIgnoreCase("scanline")) {
            for (int y = 0; y < height; y+=renderRes) {
                // rayDir for leftmost ray (x = 0) and rightmost ray (x = width)
                float rayDirX0 = (float) (dirX - planeX);
                float rayDirY0 = (float) (dirY - planeY);
                float rayDirX1 = (float) (dirX + planeX);
                float rayDirY1 = (float) (dirY + planeY);

                // current y position compared to the center of the screen (the horizon)
                int p = y - height / 2;

                // vertical position of the camera
                float posZ = (float) (0.5 * height);

                // horizontal distance from the camera to the floor for the current row.
                // 0.5 is the z position exacttly in the middle between floor and ceiling
                float rowDistance = posZ / p;

                // calculate the real world step vector we have to add for each x (parallel to camera plane)
                // adding step by step avoids multiplications with a weight in the inner loop
                float floorStepX = rowDistance * (rayDirX1 - rayDirX0) / width;
                float floorStepY = rowDistance * (rayDirY1 - rayDirY0) / width;

                // real world coordinates of the leftmost column. this will be updated as we step to the right
                float floorX = (float) (posX + rowDistance * rayDirX0);
                float floorY = (float) (posY + rowDistance * rayDirY0);

                for (int x = 0; x < width; ++x) {
                    // the cell coord is simply got from the integer parts of floorX and floorY
                    int cellX = (int) floorX;
                    int cellY = (int) floorY;

                    // get the texture coordinate frmo the fractional part
                    int tx = (int) (texWidth * (floorX - cellX)) & (texWidth - 1);
                    int ty = (int) (texHeight * (floorY - cellY)) & (texHeight - 1);

                    floorX += floorStepX;
                    floorY += floorStepY;

                    // choose texture and draw the pixel
                    Color color;

                    // floor
                    color = new Color(textures[floorTexture][tx].getRGB(0, ty));
                    color = color.darker();
                    drawFloorCeiling[x][y] = color;

                    // ceiling (symmetrical, at height - y - 1 instead of y)
                    color = new Color(textures[ceilingTexture][tx].getRGB(0, ty));
                    color = color.darker();
                    drawFloorCeiling[x][height - y - 1] = color;
                }
            }
        }
        // WALL CASTING
        for(int x = 0; x < width; x+=renderRes) {

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

            if(fcms[floorcastingMethod].equalsIgnoreCase("vertical")){
                // FLOOR CASTING (vertical version, directly after drawing the vertical walls tripe for the current x)
                double floorXWall, floorYWall; // x, y position of the floor texel at the bottom of the wall

                // 4 different wall directions possible
                if(side == 0 && rayDirX > 0){
                    floorXWall = mapX;
                    floorYWall = mapY + wallX;
                }
                else if(side == 0 && rayDirX < 0){
                    floorXWall = mapX + 1.0;
                    floorYWall = mapY + wallX;
                }
                else if(side == 1 && rayDirY > 0){
                    floorXWall = mapX + wallX;
                    floorYWall = mapY;
                }
                else{
                    floorXWall = mapX + wallX;
                    floorYWall = mapY + 1.0;
                }

                double distWall, distPlayer, currentDist;

                distWall = perpWallDist;
                distPlayer = 0.0;

                // TODO: this might cause problems with drawing the image columns!
                if (drawEnd[x] < 0) {
                    drawEnd[x] = height;
                }

                // draw the floor from drawEnd to the bottom of the screen
                for(int y = drawEnd[x] + 1; y < height; y+=renderRes){
                    currentDist = height / (2.0 * y - height); // you could make a small lookup table for this instead

                    double weight = (currentDist - distPlayer) / (distWall - distPlayer);

                    double currentFloorX = weight * floorXWall + (1.0 - weight) * posX;
                    double currentFloorY = weight * floorYWall + (1.0 - weight) * posY;

                    int floorTexX, floorTexY;
                    floorTexX = (int)(currentFloorX * texWidth) % texWidth;
                    floorTexY = (int)(currentFloorY * texHeight) % texHeight;

                    // floor
                    drawFloorCeiling[x][y] = new Color(textures[floorTexture][floorTexX].getRGB(0, floorTexY)).darker();
                    // ceiling
                    drawFloorCeiling[x][height-y] = new Color(textures[ceilingTexture][floorTexX].getRGB(0, floorTexY)).darker();
                }
            }
            // SET THE ZBUFFER FOR TEH SPRITE CASTING
            ZBuffer[x] = perpWallDist;

            if(spriteCasting) {
                // SPRITE CASTING
                // sort sprites from far to close
                for (int i = 0; i < numSprites; i++) {
                    spriteOrder[i] = i;
                    spriteDistance[i] = ((posX - sprites[i][0]) * (posX - sprites[i][0]) + (posY - sprites[i][1]) * (posY - sprites[i][1]));
                }

                sortSprites(spriteOrder, spriteDistance, numSprites);

                // after sorting the sprites, do the projection and draw them
                for (int i = 0; i < numSprites; i++) {
                    // translate sprite position relative to camera
                    double spriteX = sprites[spriteOrder[i]][0] - posX;
                    double spriteY = sprites[spriteOrder[i]][1] - posY;

                    // transform sprite with the inverse camera matrix
                    // [ planeX     dirX ] - 1                                      [ dirY       -dirX ]
                    // [                 ]      = 1/(planeX*dirY - dirX*planeY) *   [                  ]
                    // [ planeY     dirY ]                                          [ -planeY   planeX ]

                    double invDet = 1.0 / (planeX * dirY - dirX * planeY); // required for correct matrix multiplication

                    double transformX = invDet * (dirY * spriteX - dirX * spriteY);
                    double transformY = invDet * (-planeY * spriteX + planeX * spriteY); // this is actually the depth inside the screen, that is what Z is in 3D

                    int spriteScreenX = (int) ((width / 2) * (1 + transformX / transformY));

                    // calculate height of the sprite on screen
                    drawSpritesHeight[x] = Math.abs((int) (height / (transformY))); // using transformY instead of real distance prevents fisheye
                    // calculate lowest and highest pixel to fill in current stripe
                    drawSpritesStart[x] = -drawSpritesHeight[x] / 2 + height / 2;
//                if(drawStartY < 0){
//                    drawStartY = 0;
//                }
                    drawSpritesEnd[x] = drawSpritesHeight[x] / 2 + height / 2;
//                if(drawEndY >= height){
//                    drawEndY = height - 1;
//                }

                    // calculate width of the sprite
                    int spriteWidth = Math.abs((int) (height / (transformY)));
                    int drawStartX = -spriteWidth / 2 + spriteScreenX;
                    if (drawStartX < 0) {
                        drawStartX = 0;
                    }
                    int drawEndX = spriteWidth / 2 + spriteScreenX;
                    if (drawEndX >= width) {
                        drawEndX = width - 1;
                    }

                    // loop through every vertical stripe of the sprite on screen
                    for (int stripe = drawStartX; stripe < drawEndX; stripe++) {
                        spriteStripeWidth[stripe] = spriteWidth / texWidth;
//                    if(spriteStripeWidth[stripe] <= 0){
//                        spriteStripeWidth[stripe] = 1;
//                    }
                        texX = (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texWidth / spriteWidth) / 256;
                        // the conditions in the if are:
                        // 1) it's in front of camera plane so you don't see things behind you
                        // 2) it's on the screen (left)
                        // 3) it's on the screen (right)
                        // 4) XBuffer, with perpendicular distance
                        if (transformY > 0 && stripe > 0 && stripe < width && transformY < ZBuffer[stripe]) {
                            BufferedImage spriteStripe = textures[(int) sprites[spriteOrder[i]][2]][texX];
                            drawSprites[stripe] = spriteStripe;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        for(int x = 0; x < width; x++){
            if(fcms[floorcastingMethod].equalsIgnoreCase("scanline") || fcms[floorcastingMethod].equalsIgnoreCase("vertical")) {
                for (int y = 0; y < drawEnd[x]; y+=renderRes) {
                    g.setColor(drawFloorCeiling[x][y]);
                    g.drawLine(x, y, x, y);
                }

                int adjustedDrawStart = drawStart[x];
                if (adjustedDrawStart < 0) {
                    adjustedDrawStart = 0;
                }

                for (int y = adjustedDrawStart; y < height; y+=renderRes) {
                    g.setColor(drawFloorCeiling[x][y]);
                    g.drawLine(x, y, x, y);
                }
            }
            g.drawImage(drawColumn[x], x, drawStart[x], 1, lineHeight[x], null);
            if(spriteCasting) {
                if (drawSprites[x] != null) {
                    g.drawImage(drawSprites[x], x, drawSpritesStart[x], spriteStripeWidth[x], drawSpritesHeight[x], null);
                    drawSprites[x] = null;
                }
            }
        }
    }

    @Override
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
