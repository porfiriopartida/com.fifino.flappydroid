package com.kilobolt.samplegame.entities;

import java.util.ArrayList;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public class Floor extends AndroidEntity{
	private Image image;
	private int offsetX = 0;
	private int offsetY = 1240;
	int tileWidth = 40;
	int tileHeight = 40;
	public Floor(Image image) {
		this.image = image;
		ArrayList<Image> list = new ArrayList<Image>();
		list.add(image);
		this.setImages(list);
		Bound b = new Bound();
		Rectangle rectangleA = new Rectangle();
		rectangleA.setX(0).setY(0).setHeight(tileHeight).setWidth(5 * tileWidth).setParentX(offsetX).setParentY(offsetY);
		Rectangle rectangleB = new Rectangle();
		rectangleB.setX(280).setY(0).setHeight(tileHeight).setWidth(10 * tileWidth).setParentX(offsetX).setParentY(offsetY);
		
		ArrayList<Rectangle> rectangles  = new ArrayList<Rectangle>();
		rectangles.add(rectangleA);
		rectangles.add(rectangleB);
		
		b.setRectangles(rectangles).setX(offsetX).setY(offsetY);
		
		this.setBound(b);
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		int tileOffsetX = offsetX;
		int tileOffsetY = offsetY;
		for(int i=0; i<5; i++){
			g.drawImage(this.image, tileOffsetX, tileOffsetY);
			tileOffsetX += tileWidth;
		}
		tileOffsetX += 80;
		for(int i=0; i<10; i++){
			g.drawImage(this.image, tileOffsetX, tileOffsetY);
			tileOffsetX += tileWidth;
		}
		
		if(AndroidEntity.DEBUG_MODE){
			getBound().draw(g); 
		}
	}

	public int getHeight() {
		return tileHeight;
	}
}
