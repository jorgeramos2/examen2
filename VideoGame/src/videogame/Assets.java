/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {
    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store the player image
    public static BufferedImage alien;      // to store the alien image
    public static BufferedImage bomb;       // to store the bomb image
    public static BufferedImage laser;      // to store the laser image
    public static BufferedImage playerDie;

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/space.png");
        player = ImageLoader.loadImage("/images/spaceship.png");
        alien=ImageLoader.loadImage("/images/alien.png");
        bomb=ImageLoader.loadImage("/images/bomb.png");
        laser=ImageLoader.loadImage("/images/laser.png");
        playerDie = ImageLoader.loadImage("/images/playerDie.png");
    }
    
}
