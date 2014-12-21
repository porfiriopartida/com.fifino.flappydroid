package com.fifino.flappydroid;

//import com.kilobolt.framework.Game;
import com.kilobolt.framework.Screen;

public abstract class FlappyDroidScreen extends Screen{

    protected FlappyDroidGame game;

    public FlappyDroidScreen(FlappyDroidGame game) {
        super(game);
        this.game = game;
    }
}
