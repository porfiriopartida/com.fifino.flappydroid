package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.flappydroid.Assets;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.implementation.AndroidImage;

public class Coin extends AndroidEntity {
	int width = 100, height = 110;
	int x, y;
	public Coin(){
		AndroidImage image = (AndroidImage) Assets.coin;
        ArrayList<AndroidImage> list = new ArrayList<AndroidImage>();
        list.add(image);
        this.setImages(list);
		Bound b = new Bound();
		Rectangle r = new Rectangle();
		r.setX(0).setY(0).setWidth(image.getWidth()).setHeight(image.getHeight());
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(r);
		b.setRectangles(rectangles);
		setBound(b);
	}
	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void update(float delta) {
		
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
    public Coin setX(int x) {
        this.x = x;
        this.getBound().setX(x);
        return this;
    }
    public Coin setY(int y) {
        this.y = y;
        this.getBound().setY(y);
        return this;
    }
}
