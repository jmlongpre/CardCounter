package com.cardcounter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AbstractScreen implements Screen{

	protected final CardCounter game;
	protected final Stage stage;
	protected BitmapFont font;
	protected SpriteBatch batch;
	protected Sprite backgroundSprite;
	protected int height;
	protected int width;
	
	public AbstractScreen(CardCounter game) {
		this.game = game;
		this.batch = getBatch();
		this.stage = new Stage();
	}
	
	@Override
	public void show() {
		Gdx.app.log(CardCounter.LOG, "Showing screen: " + getName());
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void resize(int width, int height) {
		Gdx.app.log(CardCounter.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height);
		this.width = width;
		this.height = height;
		stage.getViewport().update(width, height, true);
		stage.clear();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void hide() {
		Gdx.app.log(CardCounter.LOG, "Hiding screen: " + getName());
		dispose();
	}
	
	@Override
	public void pause() {
		Gdx.app.log(CardCounter.LOG, "Pausing screen: " + getName());
	}
	
	@Override
	public void resume() {
		Gdx.app.log(CardCounter.LOG, "Resuming screen: " + getName());
	}
	
	@Override
	public void dispose() {
		Gdx.app.log(CardCounter.LOG, "Disposing screen: " + getName());
		stage.dispose();
		if (font != null)
			font.dispose();
		if (batch != null)
			batch.dispose();
	}
	
	protected String getName() {
		return getClass().getSimpleName();
	}
	
	public BitmapFont getFont() {
		if (font == null)
			font = new BitmapFont();
		return font;
	}
	
	public SpriteBatch getBatch() {
		if (batch == null)
			batch = new SpriteBatch();
		return batch;
	}
	
	protected Skin getSkin() {
		return game.getSkin();
	}

}
