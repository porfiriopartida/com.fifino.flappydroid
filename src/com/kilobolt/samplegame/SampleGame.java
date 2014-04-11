package com.kilobolt.samplegame;

import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame {
	static{
		AndroidEntity.debugMode = AndroidEntity.DebugMode.OFF;
	}
    @Override
    public Screen getInitScreen() {
        return new MainMenuScreen(this);
    }

}