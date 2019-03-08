/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author hectordiazaceves
 */
public class Shot extends Item{
    
    private int speed;
    private int width;
    private int height;
    private Game game;

    public Shot(int x, int y, Game game) {
        super(x, y);
        speed = 5;
        width = 10;
        height = 20;
        this.game = game;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void tick() {
        setY(getY() - speed);
        if(getY() == 0){
            game.deleteLaser();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.laser, getX(), getY(),getWidth() , getHeight(), null);
    }
    
    
    
}
