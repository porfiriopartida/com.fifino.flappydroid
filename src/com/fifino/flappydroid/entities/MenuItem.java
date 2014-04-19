package com.fifino.flappydroid.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.implementation.AndroidImage;

public class MenuItem extends AndroidEntity {
    private int x = 0;
    private int y = 0;
    private int width, height;
    AndroidImage image;
    public MenuItem(AndroidImage image, int x, int y) {
    	this.x = x;
    	this.y = y;
        width = image.getWidth();
        height = image.getHeight();
        this.image = image;

//        ArrayList<AndroidImage> list = new ArrayList<AndroidImage>();
//        list.add(image);
//        this.setImages(list);
        Bound b = new Bound();
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width)
                .setParentX(x).setParentY(y);

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);

        b.setRectangles(rectangles).setX(x).setY(y);

        this.setBound(b);

    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, x, y);
    }

    @Override
    public void update(float deltaTime) {
    }

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getAngle() {
		return 0;
	}

	public void setImage(Image debugButton) {
	}
}
