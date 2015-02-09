package com.cardcounter.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen {
	
	private boolean isMuted;
	
	public MenuScreen(CardCounter game, boolean isMuted) {
		super(game);
		this.isMuted = isMuted;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		Image background = new Image(game.getAssets().get("menu_background.png", Texture.class));
		background.setFillParent(true);
		background.setPosition(0, 0);
		
		Button easy = new Button(game.getSkin().getDrawable("button_easy"));
		easy.setPosition(width/5 - easy.getWidth()/2, height/3 - easy.getHeight()/2);
		easy.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("select.mp3", Sound.class).play();
				game.setScreen(game.getEasyPlayScreen(isMuted));
			}
		});
		
		Button normal = new Button(game.getSkin().getDrawable("button_normal"));
		normal.setPosition(width/2 - normal.getWidth()/2, height/3 - normal.getHeight()/2);
		normal.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("select.mp3", Sound.class).play();
				game.setScreen(game.getNormalPlayScreen(isMuted));
			}
		});
		
		Button hard = new Button(game.getSkin().getDrawable("button_hard"));
		hard.setPosition(width*4/5 - hard.getWidth()/2,  height/3 - normal.getHeight()/2);
		hard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("select.mp3", Sound.class).play();
				game.setScreen(game.getHardPlayScreen(isMuted));
			}
		});
		
		Button muteButton = new Button(getSkin().getDrawable("mute"), getSkin().getDrawable("mute"), getSkin().getDrawable("unmute"));
		muteButton.setPosition(width/3 - muteButton.getWidth()/2, height/8 - muteButton.getHeight()/2);
		muteButton.setChecked(isMuted);
		muteButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted) {
					isMuted = true;
				}
				else {
					game.getAssets().get("select.mp3", Sound.class).play();
					isMuted = false;
				}
					
			}
		});
		
		Button helpButton = new Button(getSkin().getDrawable("help"));
		helpButton.setPosition(width*2/3 - muteButton.getWidth()/2, height/8 - muteButton.getHeight()/2);
		helpButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.getHelpScreen(isMuted));
			}
		});
		
		stage.addActor(background);
		stage.addActor(easy);
		stage.addActor(normal);
		stage.addActor(hard);
		stage.addActor(muteButton);
		stage.addActor(helpButton);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.draw();
		stage.act();
	}
}
