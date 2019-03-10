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
    private Animation animation;

    public Shot(int x, int y, boolean visible, Game game) {
        super(x, y);
        speed = 5;
        width = 20;
        height = 40;
        this.visible = visible;
        this.game = game;
        animation = new Animation(Assets.playerBlast, 100);
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
        animation = new Animation(Assets.playerBlast, 100);

    }

    @Override
    public void tick() {
        if(isVisible()){
            animation.tick();
            setY(getY() - speed);
            if (getY() == 0) {
                game.deleteLaser();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (isVisible()) {
            if (animation != null) {

                try {
                    g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
                } catch (NullPointerException e) {
                    System.out.println("Couldn't render animated shot");
                }
            }
        }
    }
    
    
    
}
