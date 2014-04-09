package com.kilobolt.samplegame;

import java.util.List;

import android.graphics.Color;
import android.graphics.Point;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.samplegame.entities.MenuItem;

public class MainMenuScreen extends Screen {
    private MenuItem startMenuItem;
    public MainMenuScreen(Game game) {
        super(game);
        initializeAssets();
        setupEntities();
    }

    @Override
    protected void setupEntities() {
        startMenuItem = new MenuItem(Assets.menu_start);
    }

    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (startMenuItem.collides(new Point(event.x, event.y))) {
                    // START GAME
                    game.setScreen(new GameScreen(game));
                }
            }
        }
    }
    
    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.WHITE);
        startMenuItem.draw(g);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        // Display "Exit Game?" Box
    }

    @Override
    protected void initializeAssets() {
        Graphics g = game.getGraphics();
        Assets.menu_start = g.newImage("start.png", ImageFormat.RGB565);
    }
}