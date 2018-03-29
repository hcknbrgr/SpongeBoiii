package com.dothack;

import com.badlogic.gdx.Game;

/**
 * Created by Daniel on 11/30/2017.
 */

public class SpongeBoiGame extends Game {
    @Override
    public void create() {showGameScreen();}
    public void showGameScreen()
    {
        setScreen(new gameScreen(this));
    }

}
