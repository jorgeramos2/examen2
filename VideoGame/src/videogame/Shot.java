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
 * @author hectordiazaceves
 */
public class Shot extends Item{
    
    private int speed;
    private int width;
    private int height;
    private boolean visible;
    private Game game;

    public Shot(int x, int y, boolean visible, Game game) {
        super(x, y);
        speed = 5;
        width = 10;
        height = 20;
        this.visible = visible;
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
    
    public Rectangle getPerimeter(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
    
    public boolean intersectAlien(Object alien){
        return alien instanceof Alien && getPerimeter().intersects(((Alien) alien).getPerimeter());
    }
    
    public String toString(){
        return ""+getX()+","+getY()+"," + isVisible();
    }
    
    /**
     * Method to update the shot's x,y and visibility. Used when loading an old game state
     * @param newX
     * @param newY
     * @param newVisibility 
     */
    public void load(int newX, int newY, boolean newVisibility){
        
        setX(newX);
        setY(newY);
        setVisible(newVisibility);
    }

    @Override
    public void tick() {
        if(isVisible()){
            setY(getY() - speed);
            if (getY() == 0) {
                game.deleteLaser();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if(isVisible()){
            g.drawImage(Assets.laser, getX(), getY(),getWidth() , getHeight(), null);
        }
    }
    
    
    
}
