package com.kilobolt.samplegame;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Screen;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void paint(float deltaTime) {

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

    }

    @Override
    protected void initializeAssets() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void setupEntities() {
        // TODO Auto-generated method stub
    }
}