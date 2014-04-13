package com.kilobolt.samplegame;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame {
    public enum DebugMode{ OFF, FILL, DRAW}
    public static DebugMode debugMode = SampleGame.DebugMode.OFF;
    @Override
    public Screen getInitScreen() {
        return new MainMenuScreen(this);
    }

}