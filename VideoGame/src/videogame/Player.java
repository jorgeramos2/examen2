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
    
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.speed = 10;
        dead = false;
    }

    public int getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void die(){
        dead = true;
    }
    
    public boolean isDead(){
        return dead;
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

    @Override
    public void render(Graphics g) {
        if(dead){
            g.drawImage(Assets.playerDie, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
}
