package com.dothack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.dothack.Constants.BACKGROUND_COLOR;

public class SpongeBoi {
	SpriteBatch batch;
	Texture img;
	TextureRegion[] animationFrames;
	float elapsedTime;
	Vector2 position;
	boolean goRight;
	boolean moving;
	Viewport viewport;
	int index;


	public SpongeBoi(Viewport viewport) {
		this.viewport = viewport;
		init();
	}

	public void init()
	{

		position = new Vector2 (0f, 0f);
		index = 0;
		elapsedTime = 0;
		img = new Texture("SpongeBoiii.png");
		TextureRegion[][] tmpFrames = TextureRegion.split(img,64,64);
		animationFrames = new TextureRegion[4];
		goRight = true;
		moving = false;
		int index = 0;
		for (int i=0; i<2; i++)
			for (int j=0; j<2; j++)
				animationFrames [index++] = tmpFrames[i][j];
	}

	public void update (float delta) {
		//Update the index of the array for which graphic of SpongeBoi
		elapsedTime += delta;
		float time = elapsedTime - (int)elapsedTime;
		if (time < .25)
			index = 0;
		else if (time < .5)
			index = 1;
		else if (time < .75)
			index = 2;
		else index =3;

		//if move then move
		if (moving)
		{
			if(goRight)
				position.x+=2;
			else
				position.x-=2;
			checkLocation();
		}
		// if goright, move right if !goright move left,


	}

	public void checkLocation(){
		if ((position.x > viewport.getWorldWidth()-64) || (position.x <0)) {
			changeDirection();
		}
	}

	public void moveRight(){
		if(!goRight) {
			changeDirection();
			goRight=true;
		}
		moving = true;

	}

	public void moveLeft(){
		if(goRight) {
			changeDirection();
			goRight=false;
		}
		moving = true;
	}

	public void stopMoving(){
		moving = false;
	}

	public void changeDirection()
	{
		for (int i = 0; i < 4; i++)
			animationFrames[i].flip(true, false);
		goRight = !goRight;
	}

	public void render () {

		batch = new SpriteBatch();

		Gdx.gl.glClearColor(BACKGROUND_COLOR.r, BACKGROUND_COLOR.g, BACKGROUND_COLOR.b, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.draw(animationFrames[index], position.x, 0);
		batch.end();


	}

}
