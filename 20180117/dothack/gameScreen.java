package com.dothack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import static com.dothack.Constants.ARROW_HEIGHT;
import static com.dothack.Constants.ARROW_LEFT_COLOR;
import static com.dothack.Constants.ARROW_RIGHT_COLOR;
import static com.dothack.Constants.ARROW_WIDTH;
import static com.dothack.Constants.BACKGROUND_COLOR;
import static com.dothack.Constants.CONTROLS_MARGIN_X;
import static com.dothack.Constants.CONTROLS_MARGIN_Y;
import static com.dothack.Constants.JUMP_COLOR;
import static com.dothack.Constants.WORLD_HEIGHT;
import static com.dothack.Constants.WORLD_WIDTH;

/**
 * Created by Daniel on 11/30/2017.
 */

public class gameScreen extends InputAdapter implements Screen {


    SpongeBoiGame game;

    ExtendViewport gameViewport;
    SpongeBoi sponge;

    ScreenViewport controlsViewport;
    ShapeRenderer renderer;

    float width;
    float height;

    public gameScreen(SpongeBoiGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameViewport =new ExtendViewport (WORLD_HEIGHT, WORLD_WIDTH);

        sponge = new SpongeBoi(gameViewport);
        Gdx.input.setInputProcessor(this);

        controlsViewport = new ScreenViewport();


        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);


    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        controlsViewport.update(width, height, true);
        sponge.init();
    }

    @Override
    public void render(float delta) {
        // Update actor positions
        sponge.update(delta);

        //draw actors
        gameViewport.apply(true);
        Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sponge.render();

        //draw controls
        controlsViewport.apply(true);
        renderer.setProjectionMatrix(controlsViewport.getCamera().combined);
        renderer.begin();
        renderer.set(ShapeRenderer.ShapeType.Filled);

        width = controlsViewport.getWorldWidth()/20f;
        height = controlsViewport.getWorldHeight()/24f;


        renderer.setColor(ARROW_LEFT_COLOR);
        renderer.rect(width, height, width*1.5f, height*1.3f);
        renderer.setColor(ARROW_RIGHT_COLOR);
        renderer.rect(width*2.5f, height, width*1.5f, height*1.3f);

        renderer.setColor(JUMP_COLOR);



        renderer.end();


    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldClick = controlsViewport.unproject(new Vector2(screenX,screenY));
        if (worldClick.x >= width && worldClick.x <= (width + width*1.5f) && worldClick.y >= height && height<= (height+height*1.3f))
        {
            sponge.moveLeft(); //set boolean to false to have spongeboi move left
        }
        if (worldClick.x >= (width*2.5f) && worldClick.x <= (width*2.5f + width*1.5f) && worldClick.y >= height && height<= (height+height*1.3f))
        {
            sponge.moveRight(); //set boolean to true to have spongeboi move right
        }

        return true;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        //stop sponge from moving
        sponge.stopMoving();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //detect where click goes, if off arrow then stop spongeboi from moving, otherwise change direction/maintain movement
        Vector2 worldClick = controlsViewport.unproject(new Vector2(screenX,screenY));
        if (worldClick.x >= width && worldClick.x <= (width + width*1.5f) && worldClick.y >= height && height<= (height+height*1.3f))
        {
            sponge.moveLeft();
        }
        else if (worldClick.x >= (width*2.5f) && worldClick.x <= (width*2.5f + width*1.5f) && worldClick.y >= height && height<= (height+height*1.3f))
        {
            sponge.moveRight();
        }
        else sponge.stopMoving();

        return true;
    }
        @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        renderer.dispose();
    }

    @Override
    public void dispose() {


    }
}
