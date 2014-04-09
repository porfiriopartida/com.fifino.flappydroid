package com.fifino.framework.implementation;

import java.util.List;
import java.util.Observable;

import android.graphics.Point;

import com.fifino.framework.Entity;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public abstract class AndroidEntity extends Observable implements Entity {
    public enum DebugMode{ OFF, FILL, DRAW}
    public static DebugMode debugMode = DebugMode.OFF; 
    
    private boolean isVisible;
    
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    private Bound bound;
    private List<Image> images;

    @Override
    public Bound getBound() {
        return bound;
    }

    @Override
    public void setBound(Bound bound) {
        this.bound = bound;
    }

    @Override
    public List<Image> getImages() {
        return images;
    }

    @Override
    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public void addImage(Image image) {
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
		for (Image image : images) {
			DrawMode drawMode = getDrawMode();
			if (drawMode == DrawMode.REGULAR) {
				g.drawImage(image, getX(), getY());
			} else if (drawMode == DrawMode.SCALE) {
				g.drawScaledImage(image, getX(), getY(), getWidth(), getHeight());
			} else if (drawMode == DrawMode.ROTATE) {
				g.drawRotatedImage(image, getX(), getY(), getAngle());
			}else if(drawMode == DrawMode.SCALE_ROTATE)
			{
				g.drawScaledRotatedImage(image, getX(), getY(), getWidth(), getHeight(), getAngle());
			}
		}
		if (AndroidEntity.debugMode != DebugMode.OFF) {
			drawBounds(g);
		}
	}

	public void drawBounds(Graphics g) {
		 this.getBound().draw(g);
	}
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getAngle();
	public abstract DrawMode getDrawMode();
	public enum DrawMode {REGULAR, ROTATE, SCALE, SCALE_ROTATE};
}
