package com.jinx.otp;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final float RACKET_WIDTH = 0.5f;
    final float RACKET_HEIGHT = 1f;
    final float BALL_WIDTH = 0.25f;
    final float BALL_HEIGHT = 0.25f;

    final Pong game;

    Texture racketTexture;
    Texture ballTexture;

    Sprite racketPlayerOneSprite;
    Sprite racketPlayerTwoSprite;
    Sprite ballSprite;

    public GameScreen(Pong game) {
        this.game = game;
        final String racketTextureFileName = "pong-racket.png";
        racketTexture = new Texture(racketTextureFileName);

        setupPlayerOneRacket();
        setupPlayerTwoRacket();
        setupBall();
    }
    
    /**
     * Create a racket at the bottom left corner of the screen
     */
    private void setupPlayerOneRacket() {
        racketPlayerOneSprite = new Sprite(racketTexture);
        racketPlayerOneSprite.setSize(RACKET_WIDTH, RACKET_HEIGHT);
        final float startX = 0f;
        final float startY = 0f;
        racketPlayerOneSprite.setX(startX);
        racketPlayerOneSprite.setY(startY);
    }

    /**
     * Create a rackat at the bottom right corner
     */
    private void setupPlayerTwoRacket() {
        racketPlayerTwoSprite = new Sprite(racketTexture);
        racketPlayerTwoSprite.setSize(RACKET_WIDTH, RACKET_HEIGHT);
        final float startX = game.viewport.getWorldWidth() - RACKET_WIDTH;
        final float startY = 0f;
        racketPlayerTwoSprite.setX(startX);
        racketPlayerTwoSprite.setY(startY);
    }

    /**
     * Place a ball in the center of the screen
     */
    private void setupBall() {
        final String ballTextureFileName = "pong-ball.png";
        ballTexture = new Texture(ballTextureFileName);
        ballSprite = new Sprite(ballTexture);
        ballSprite.setSize(BALL_WIDTH, BALL_HEIGHT);
        final float startX = game.viewport.getWorldWidth() / 2 - 0.5f * BALL_WIDTH;
        final float startY = game.viewport.getWorldHeight() / 2 - 0.5f * BALL_HEIGHT;
        ballSprite.setX(startX);
        ballSprite.setY(startY);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {}

    private void logic() {}

    private void draw() {
        ScreenUtils.clear(Color.PINK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        racketPlayerOneSprite.draw(game.batch);
        racketPlayerTwoSprite.draw(game.batch);
        ballSprite.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        racketTexture.dispose();
        ballTexture.dispose();
    }

}
