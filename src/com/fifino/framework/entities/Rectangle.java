package com.fifino.framework.entities;

import com.kilobolt.framework.Input.TouchEvent;

import android.graphics.Point;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int parentX, parentY;

    public int getParentY() {
        return parentY;
    }

    public Rectangle setParentY(int parentY) {
        this.parentY = parentY;
        return this;
    }

    public int getParentX() {
        return parentX;
    }

    public Rectangle setParentX(int parentX) {
        this.parentX = parentX;
        return this;
    }

    public int getX() {
        return x;
    }

    public Rectangle setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Rectangle setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle setHeight(int height) {
        this.height = height;
        return this;
    }

    public boolean intersects(Rectangle rectangleRemote) {
        int localWidth = this.width;
        int localHeight = this.height;
        int remoteWidth = rectangleRemote.getWidth();
        int remoteHeight = rectangleRemote.getHeight();
        if (remoteWidth <= 0 || remoteHeight <= 0 || localWidth <= 0
                || localHeight <= 0) {
            return false;
        }
        int localX = getAbsoluteX();
        int localY = getAbsoluteY();
        int remoteX = rectangleRemote.getAbsoluteX();
        int remoteY = rectangleRemote.getAbsoluteY();
        remoteWidth += remoteX;
        remoteHeight += remoteY;
        localWidth += localX;
        localHeight += localY;
        return ((remoteWidth < remoteX || remoteWidth > localX)
                && (remoteHeight < remoteY || remoteHeight > localY)
                && (localWidth < localX || localWidth > remoteX) && (localHeight < localY || localHeight > remoteY));
    }

    public boolean intersects(Point p) {
        // Upper Left
        int ulx = getAbsoluteX();
        int uly = getAbsoluteY();
        // Bottom Left
        // int blx = getAbsoluteX();
        int bly = getAbsoluteY() + getHeight() - 1;
        // //Upper Right
        int urx = getAbsoluteX() + getWidth() - 1;
        // int ury = getAbsoluteY();
        // //Bottom Right
        // int brx = getAbsoluteX() + getWidth();
        // int bry = getAbsoluteY() + getHeight();

        if (p.x > ulx && p.x < urx && p.y > uly && p.y < bly) {
            return true;
        }
        return false;
    }

    public boolean collides(TouchEvent event, int x, int y, int width,
            int height) {
        if (event.x > x && event.x < x + width - 1 && event.y > y
                && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getAbsoluteY() {
        return getY() + getParentY();
    }

    public int getAbsoluteX() {
        return getX() + getParentX();
    }
}
