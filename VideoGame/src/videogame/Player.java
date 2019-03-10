/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item{

    private int direction;
    private int width;
    private int height;
    private Game game;
    private int speed;
    private boolean dead;
    /**
     * 
     * @param x position 
     * @param y postion
     * @param direction
     * @param width of player
     * @param height of player
     * @param game 
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.speed = 10;
        dead = false;
    }
/**
 * Get direction of player
 * @return  an <code>int</code> value with the direction
 */
    public int getDirection() {
        return direction;
    }
/**
 * Get width of player
 * @return  an <code>int</code> value with the width
 */
    public int getWidth() {
        return width;
    }
/**
 * Get the height of a player
 * @return  an <code>int</code> value with the height
 */
    public int getHeight() {
        return height;
    }
/**
 * Set the direction of player
 * @param direction 
 */
    public void setDirection(int direction) {
        this.direction = direction;
    }
/**
 * Set the width of player
 * @param width 
 */
    public void setWidth(int width) {
        this.width = width;
    }
/**
 * Set the height of player
 * @param height 
 */
    public void setHeight(int height) {
        this.height = height;
    }
 /**
  * Get the perimeter 
  * @return  an <code>Rectangle</code> value with the perimeter
  */   
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void die(){
        dead = true;
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public void revive(){
        dead = false;
    }
   /**
    * Conver int values to string
    * @return 
    */ 
    public String toString(){
        return "" + getX();
    }
    /**
     * Method used to change player's x position. Used when laoding a past game state
     */
    public void load(int newXPos){
        setX(newXPos);
    }

    @Override
    public void tick() {
        // moving player depending on flags

        if (!dead) {
            if (game.getKeyManager().left) {
                setX(getX() - speed);
            }
            if (game.getKeyManager().right) {
                setX(getX() + speed);
            }

            // reset x position and y position if colision
            if (getX() >= game.getWidth() - width) {
                setX(game.getWidth() - width);
            }
            if (getX() <= 0) {
                setX(0);
            }
        }

    }
/**
 * Render player
 * @param g 
 */
    @Override
    public void render(Graphics g) {
        if(dead){
            g.drawImage(Assets.playerDie, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
