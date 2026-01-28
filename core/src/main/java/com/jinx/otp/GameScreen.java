package com.jinx.otp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final float RACKET_WIDTH = 0.5f;
    final float RACKET_HEIGHT = 1f;
    final float BALL_WIDTH = 0.25f;
    final float BALL_HEIGHT = 0.25f;

    final float RACKET_SPEEED = 2f;
    final float BALL_SPEED = 0.5f;

    final Pong game;

    PlayerDirection playerOneDirection;
    PlayerDirection playerTwoDirection;

    float ballVelocityX;
    float ballVelocityY;

    Texture racketTexture;
    Texture ballTexture;

    Sprite racketPlayerOneSprite;
    Sprite racketPlayerTwoSprite;
    Sprite ballSprite;

    int pointsPlayerOne = 0;
    int pointsPlayerTwo = 0;

    public GameScreen(Pong game) {
        this.game = game;
        final String racketTextureFileName = "pong-racket.png";
        racketTexture = new Texture(racketTextureFileName);

        ballVelocityX = 1f;
        ballVelocityY = 0.5f;
    
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
        resetBallPosition();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        input();
        logic(delta);
        draw();
    }

    private void input() {
        playerOneInput();
        playerTwoInput();
    }

    private void playerOneInput() {
        playerOneDirection = PlayerDirection.NONE;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerOneDirection = PlayerDirection.UP;
            return;
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerOneDirection = PlayerDirection.DOWN;
        }
    }

    private void playerTwoInput() {
        playerTwoDirection = PlayerDirection.NONE;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerTwoDirection = PlayerDirection.UP;
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerTwoDirection = PlayerDirection.DOWN;
        }
    }

    private void logic(float delta) {
        moveRackets(delta);
        moveBall(delta);
    }

    private void moveRackets(float delta) {
        final float racketSpeed = RACKET_SPEEED * delta;

        final float maxRacketY = game.WORLD_HEIGHT - RACKET_HEIGHT;
        final float minRacketY = 0f;

        switch (playerOneDirection) {
            case UP:
                racketPlayerOneSprite.translateY(racketSpeed);
                break;
            case DOWN:
                racketPlayerOneSprite.translateY(-racketSpeed);
                break;
            default:
                break;
        }
        racketPlayerOneSprite.setY(
                MathUtils.clamp(racketPlayerOneSprite.getY(), minRacketY, maxRacketY));

        switch (playerTwoDirection) {
            case UP:
                racketPlayerTwoSprite.translateY(racketSpeed);
                break;
            case DOWN:
                racketPlayerTwoSprite.translateY(-racketSpeed);
                break;
            default:
                break;
        }
        racketPlayerTwoSprite.setY(
                MathUtils.clamp(racketPlayerTwoSprite.getY(), minRacketY, maxRacketY));
    }

    private void moveBall(float delta) {
        final float ballSpeedX = ballVelocityX * delta;
        final float ballSpeedY = ballVelocityY * delta;

        final float ballMaxY = game.WORLD_HEIGHT - BALL_HEIGHT;
        final float ballMinY = 0f;

        ballSprite.translate(ballSpeedX, ballSpeedY);

        ballSprite.setY(MathUtils.clamp(ballSprite.getY(), ballMinY, ballMaxY));

        if (ballMaxY == ballSprite.getY() || ballMinY == ballSprite.getY()) {
            ballVelocityY *= -1;            
        }

        if (isBallOutOfLeftBound()) {
            ++pointsPlayerTwo;
            resetBallPosition();
        } else if (isBallOutOfRightBound()) {
            ++pointsPlayerOne;
            resetBallPosition();
        }
        

        if (isBallGoingToPlayerOne()) {
            checkRacketHit(racketPlayerOneSprite);
        } else {
            checkRacketHit(racketPlayerTwoSprite);            
        }
    }

    private boolean isBallOutOfLeftBound() {
        return -BALL_WIDTH >= ballSprite.getX();
    }

    private boolean isBallOutOfRightBound() {
        return game.WORLD_WIDTH <= ballSprite.getX();
    }

    private void resetBallPosition() {
        final float startX = game.viewport.getWorldWidth() / 2 - 0.5f * BALL_WIDTH;
        final float startY = game.viewport.getWorldHeight() / 2 - 0.5f * BALL_HEIGHT;
        ballSprite.setX(startX);
        ballSprite.setY(startY);
    }

    private void checkRacketHit(Sprite racket) {
        float ballX = ballSprite.getX();
        float ballY = ballSprite.getY();
        Rectangle ball = new Rectangle(ballX, ballY, BALL_WIDTH, BALL_HEIGHT);

        float racketX = racket.getX();
        float racketY = racket.getY();
        Rectangle racketOne = new Rectangle(racketX, racketY, RACKET_WIDTH, RACKET_HEIGHT);

        if (ball.overlaps(racketOne)) {
            ballVelocityX *= -1;
        }
    }

    /**
     * The ball is floating towards player one, if it flies 
     * to the left (neg direction)
     */
    private boolean isBallGoingToPlayerOne() {
        return ballVelocityX < 0f;
    }

    private void draw() {
        ScreenUtils.clear(Color.PINK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        racketPlayerOneSprite.draw(game.batch);
        racketPlayerTwoSprite.draw(game.batch);
        ballSprite.draw(game.batch);

        game.font.draw(game.batch, 
                       pointsPlayerOne + ":" + pointsPlayerTwo, 
                       0f,
                       game.WORLD_HEIGHT - 2f);

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


    enum PlayerDirection {
        UP,
        DOWN,
        NONE
    }

}
