package com.fifino.flappydroid;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class FlappyDroidGame extends AndroidGame {
    public enum DebugMode{ OFF, FILL, DRAW}
    public static DebugMode debugMode = FlappyDroidGame.DebugMode.OFF;
    @Override
    public Screen getInitScreen() {
        return new MainMenuScreen(this);
    }

}