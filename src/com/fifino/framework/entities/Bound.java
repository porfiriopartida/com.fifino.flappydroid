package com.fifino.framework.entities;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;

import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.implementation.AndroidEntity.DebugMode;
import com.kilobolt.framework.Graphics;

public class Bound {
    private int x;
    private int y;
    private List<Rectangle> rectangles;
    private int debugColor = 0;

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

    /**
     * Gets the 2 rectangles that collided from local and remote entities.
     * @param b
     * @return
     */
    public Rectangle[] getCollisionRectangles(Bound b) {
        List<Rectangle> rectanglesLocal = this.getRectangles();
        List<Rectangle> rectanglesRemote = b.getRectangles();

        for (Rectangle rectangleLocal : rectanglesLocal) {
            for (Rectangle rectangleRemote : rectanglesRemote) {
                if (rectangleLocal.intersects(rectangleRemote)) {
                    return new Rectangle[]{rectangleLocal, rectangleRemote};
                }
            }
        }
        return null;
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
    public int getColor(){
        if(this.debugColor == 0){
            Random rnd = new Random();
            this.debugColor = Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        }
        return this.debugColor;
    }
    public void draw(Graphics g) {
        for (Rectangle r : rectangles) {
			 if (AndroidEntity.debugMode == DebugMode.FILL) {
		            g.fillRect(r.getAbsoluteX(), r.getAbsoluteY(), r.getWidth(),
		                    r.getHeight(), getColor());
			 }else if(AndroidEntity.debugMode == DebugMode.DRAW){
		            g.drawRect(r.getAbsoluteX(), r.getAbsoluteY(), r.getWidth(),
		                    r.getHeight(), getColor());
			 }
        }
    }
}
