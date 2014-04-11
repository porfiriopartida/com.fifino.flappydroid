package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.implementation.AndroidImage;

public class MenuItem extends AndroidEntity {
    private int x = 0;
    private int y = 0;
    private int width, height;

    public MenuItem(AndroidImage image) {
        x = 800/2 - image.getWidth()/2;
        y = 1200/2 - image.getHeight()/2;
        width = image.getWidth();
        height = image.getHeight();

        ArrayList<AndroidImage> list = new ArrayList<AndroidImage>();
        list.add(image);
        this.setImages(list);
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

//    @Override
//    public void draw(Graphics g) {
//        g.drawImage(this.image, offsetX, offsetY);
//    }

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
}
