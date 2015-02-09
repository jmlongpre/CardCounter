package com.cardcounter.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {
	
	public SplashScreen(CardCounter game) {
		super(game);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		Image splashScreen = new Image(game.getAssets().get("splash.png", Texture.class));
		splashScreen.setFillParent(true);
		splashScreen.setPosition(0, 0);
		splashScreen.getColor().a = 0f;
		splashScreen.addAction(sequence(fadeIn(1f), delay(2f), fadeOut(1f), new Action() {
			@Override
			public boolean act(float delta) {
				game.setScreen(game.getMenuScreen(false));
				return true;
			}
		}));
		
		stage.addActor(splashScreen);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.draw();
		stage.act();
	}
	
}
