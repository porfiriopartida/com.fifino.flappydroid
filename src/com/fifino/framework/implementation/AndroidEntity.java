package com.fifino.framework.implementation;

import java.util.List;
import java.util.Observable;
import android.graphics.Point;
import com.fifino.framework.Entity;
import com.fifino.framework.entities.Bound;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public abstract class AndroidEntity extends Observable implements Entity {
    public final static boolean DEBUG_MODE = true;
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

    public boolean collides(Point p) {
        return this.getBound().collides(p);
    }
}
