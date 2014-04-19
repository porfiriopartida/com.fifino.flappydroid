package com.fifino.framework.implementation;

import java.util.List;
import java.util.Observable;

import android.graphics.Point;

import com.fifino.flappydroid.FlappyDroidGame;
import com.fifino.framework.Entity;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.implementation.AndroidImage;

public abstract class AndroidEntity extends Observable implements Entity {
    private boolean isVisible = true;
    
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    private Bound bound;
    private List<AndroidImage> images;

    @Override
    public Bound getBound() {
        return bound;
    }

    @Override
    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public List<AndroidImage> getImages() {
        return images;
    }

    public void setImages(List<AndroidImage> images) {
        this.images = images;
    }

    public void addImage(AndroidImage image) {
        this.images.add(image);
    }

    @Override
    public boolean collides(Entity remoteEntity) {
        if (!isCollidable() || !remoteEntity.isCollidable()) {
            return false;
        }
        Bound localBound = this.getBound();
        Bound remoteBound = remoteEntity.getBound();
        return localBound.collides(remoteBound);
    }

    @Override
    public Rectangle[] getCollisionRectangles(Entity remoteEntity) {
        if (!isCollidable() || !remoteEntity.isCollidable()) {
            return null;
        }
        Bound localBound = this.getBound();
        Bound remoteBound = remoteEntity.getBound();
        return localBound.getCollisionRectangles(remoteBound);
    }

    public boolean collides(Point p) {
        return this.getBound().collides(p);
    }
    public void draw(Graphics g){
    	if(isVisible()){
			for (AndroidImage image : images) {
				g.drawImage(image, getX(), getY());
			}
			drawBounds(g);
    	}
	}

	public void drawBounds(Graphics g) {
		if (FlappyDroidGame.debugMode != FlappyDroidGame.DebugMode.OFF) {
			 this.getBound().draw(g);
		}
	}
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getAngle();
}
