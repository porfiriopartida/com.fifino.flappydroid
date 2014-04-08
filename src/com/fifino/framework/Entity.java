package com.fifino.framework;

import java.util.List;

import com.fifino.framework.entities.Bound;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public interface Entity {
    public void setBound(Bound bound);

    public Bound getBound();

    public void setImages(List<Image> images);

    public List<Image> getImages();

    public void addImage(Image image);

    public boolean collides(Entity e);

    public boolean isCollidable();

    public void draw(Graphics g);
}