package com.jinx.otp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class MainMenuScreen implements Screen {

    final Pong game;
    boolean isTouched;

    public MainMenuScreen(Pong game) {
        this.game = game;
        this.isTouched = false;
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            isTouched = true;
        }
    }

    private void logic() {
        if (isTouched) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.PINK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        drawTitle();
        game.batch.end();
    }

    private void drawTitle() {
        final float titleY = game.WORLD_HEIGHT / 2;
        final float titleX = game.WORLD_WIDTH / 3;
        game.font.draw(game.batch, "Welcome to Pong <3", titleX, titleY);
        final float subTitleX = titleX;
        final float subTitleY = titleY - 1;
        game.font.draw(game.batch, "Click anywhere to start. ^^", subTitleX, subTitleY);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        game.viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}