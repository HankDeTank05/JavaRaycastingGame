package dev.hankdetankt05.raycasting.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 64, height = 64, scale = 1;

    public static BufferedImage eagle, redbrick, purplestone, greystone, bluestone, mossy, wood, colorstone;

    public static void init(){
        eagle = ImageLoader.loadImage("/textures/eagle.png");
        redbrick = ImageLoader.loadImage("/textures/redbrick.png");
        purplestone = ImageLoader.loadImage("/textures/purplestone.png");
        greystone = ImageLoader.loadImage("/textures/greystone.png");
        bluestone = ImageLoader.loadImage("/textures/bluestone.png");
        mossy = ImageLoader.loadImage("/textures/mossy.png");
        wood = ImageLoader.loadImage("/textures/wood.png");
        colorstone = ImageLoader.loadImage("/textures/colorstone.png");
    }
}
