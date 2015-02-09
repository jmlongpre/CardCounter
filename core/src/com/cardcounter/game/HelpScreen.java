package com.cardcounter.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class HelpScreen extends AbstractScreen {
	
	private boolean isMuted;
	private ArrayList<String> pageStrings = new ArrayList<String>();
	private int currentPage;
	private Image background;
	
	public HelpScreen(CardCounter game, boolean isMuted) {
		super(game);
		this.isMuted = isMuted;
		
		// Add the background paths to the pageStrings array
		pageStrings.add("help_1.png");
		pageStrings.add("help_2.png");
		pageStrings.add("help_3.png");
		pageStrings.add("help_4.png");
		pageStrings.add("help_5.png");
		
		currentPage = 0;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Gets the current texture based on the value of currentPage, and sets the background to the correct texture.
		Texture backgroundTexture = game.getAssets().get(pageStrings.get(currentPage), Texture.class);
		background = new Image(new SpriteDrawable(new Sprite(backgroundTexture)));
		background.setFillParent(true);
		background.setPosition(0, 0);
		
		// Sets the texture, position, and ClickListener for the forward arrow button.
		Button forwardButton = new Button(game.getSkin().getDrawable("forward"));
		forwardButton.setPosition(width*9/10 - forwardButton.getWidth()/2, height*10/11 - forwardButton.getHeight()/2);
		forwardButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Advances to the next help page, provided we are not at the last help page.
				if (currentPage < 4) {
					currentPage++;
					Texture newBackground = game.getAssets().get(pageStrings.get(currentPage), Texture.class);
					background.setDrawable(new SpriteDrawable(new Sprite(newBackground)));
				}
				// If we are at the last help page, routes us back to the menu.
				else {
					game.setScreen(game.getMenuScreen(isMuted));
				}
			}
		});
		
		// Sets the texture, position, and ClickListener for the back arrow button.
		Button backButton = new Button(game.getSkin().getDrawable("back"));
		backButton.setPosition(width/10 - backButton.getWidth()/2, height*10/11 - backButton.getHeight()/2);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Retreats to the previous help page, provided we are not on the first help page.
				if (currentPage > 0) {
					currentPage--;
					Texture newBackground = game.getAssets().get(pageStrings.get(currentPage), Texture.class);
					background.setDrawable(new SpriteDrawable(new Sprite(newBackground)));
				}
				// If we are on the first help page, routes us back to the menu.
				else {
					game.setScreen(game.getMenuScreen(isMuted));
				}
			}
		});
		
		// Finally, add all actors to the stage
		stage.addActor(background);
		stage.addActor(forwardButton);
		stage.addActor(backButton);
	}
	
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Draw sprites from batch (only backgroundSprite)
		batch.begin();
		batch.end();
		
		// Draw the stage and call act() on all actors
		stage.draw();
		stage.act();
	}
	
}
