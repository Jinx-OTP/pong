package com.jinx.otp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Pong extends Game {

    final float WORLD_WIDTH = 10;
    final float WORLD_HEIGHT = 6;

    Viewport viewport;
    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create() {
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        configureFont();
        setScreen(new MainMenuScreen(this));
    }

    private void configureFont() {
        font = new BitmapFont();        
        font.setColor(Color.VIOLET);
        font.setUseIntegerPositions(false);
        float windowAdjustedScale = WORLD_HEIGHT / Gdx.graphics.getHeight();
        font.getData().setScale(windowAdjustedScale);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}