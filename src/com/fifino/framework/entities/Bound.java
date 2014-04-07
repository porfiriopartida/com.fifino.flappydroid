package com.fifino.framework.entities;

import java.util.List;

import android.graphics.Color;
import android.graphics.Point;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;

public class Bound {
	private int x;
	private int y;
	private List<Rectangle> rectangles;

	public int getX() {
		return x;
	}

	public Bound setX(int x) {
		this.x = x;
		for (Rectangle rectangle : rectangles) {
			rectangle.setParentX(x);
		}
		return this;
	}

	public int getY() {
		return y;
	}

	public Bound setY(int y) {
		this.y = y;
		for (Rectangle rectangle : rectangles) {
			rectangle.setParentY(y);
		}
		return this;
	}

	public List<Rectangle> getRectangles() {
		return rectangles;
	}

	public Bound setRectangles(List<Rectangle> rectangles) {
		this.rectangles = rectangles;
		return this;
	}

	public Bound addRectangle(Rectangle rectangle) {
		this.rectangles.add(rectangle);
		return this;
	}

	public boolean collides(Bound b) {
		List<Rectangle> rectanglesLocal = this.getRectangles();
		List<Rectangle> rectanglesRemote = b.getRectangles();

		for (Rectangle rectangleLocal : rectanglesLocal) {
			for (Rectangle rectangleRemote : rectanglesRemote) {
				if (rectangleLocal.intersects(rectangleRemote)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean collides(Point p) {
		List<Rectangle> rectanglesLocal = this.getRectangles();
		for (Rectangle rectangleLocal : rectanglesLocal) {
			if (rectangleLocal.intersects(p)) {
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		for(Rectangle r : rectangles){
			g.drawRect(r.getAbsoluteX(), r.getAbsoluteY(), r.getWidth(), r.getHeight(), Color.RED);
		}
	}
}
