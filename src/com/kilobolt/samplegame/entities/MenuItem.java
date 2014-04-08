package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public class MenuItem extends AndroidEntity {
    private Image image;
    private int offsetX = 0;
    private int offsetY = 0;

    public MenuItem(Image image) {
        this.image = image;
        offsetX = 800/2 - image.getWidth()/2;
        offsetY = 1200/2 - image.getHeight()/2;
        int width = image.getWidth();
        int height = image.getHeight();

        ArrayList<Image> list = new ArrayList<Image>();
        list.add(image);
        this.setImages(list);
        Bound b = new Bound();
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width)
                .setParentX(offsetX).setParentY(offsetY);

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);

        b.setRectangles(rectangles).setX(offsetX).setY(offsetY);

        this.setBound(b);

    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, offsetX, offsetY);
    }

    @Override
    public void update() {
    }

}
