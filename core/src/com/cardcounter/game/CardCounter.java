package com.cardcounter.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CardCounter extends Game {
	
	public static final String LOG = CardCounter.class.getSimpleName();
	private FPSLogger fpsLogger;
	private AssetManager assets = new AssetManager();
	private Skin skin;
	private TextureAtlas skinAtlas;
	
	@Override
	public void create () {
		Gdx.app.log(CardCounter.LOG, "Creating game on " + Gdx.app.getType());
		fpsLogger = new FPSLogger();
		
		// Load all assets for use throughout the game
		assets.load("splash.png", Texture.class);
		assets.load("menu_background.png", Texture.class);
		assets.load("play_background.png", Texture.class);
		assets.load("help_1.png", Texture.class);
		assets.load("help_2.png", Texture.class);
		assets.load("help_3.png", Texture.class);
		assets.load("help_4.png", Texture.class);
		assets.load("help_5.png", Texture.class);
		assets.load("cardFlip.mp3", Sound.class);
		assets.load("click.mp3", Sound.class);
		assets.load("select.mp3", Sound.class);
		assets.finishLoading();
		
		// Create a skin for use throughout the game
		this.skin = new Skin(Gdx.files.internal("uiskin.json"));
		this.skinAtlas = new TextureAtlas(Gdx.files.internal("cardcounter.pack"));
		skin.addRegions(this.skinAtlas);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width,  height);
		Gdx.app.log(CardCounter.LOG, "Resizing game to: " + width + " x " + height);
		
		if (getScreen() == null) {
			setScreen(getMenuScreen(false));
		}
	}

	@Override
	public void render () {
		super.render();
		fpsLogger.log();
	}
	
	@Override
	public void pause() {
		super.pause();
		Gdx.app.log(CardCounter.LOG, "Pausing game");
	}
	
	@Override
	public void resume() {
		super.resume();
		Gdx.app.log(CardCounter.LOG, "Resuming game");
	}
	
	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		Gdx.app.log(CardCounter.LOG, "Setting screen: " + screen.getClass().getSimpleName());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(CardCounter.LOG, "Disposing game");
		assets.dispose();
		skin.dispose();
	}
	
	public SplashScreen getSplashScreen() {
		return new SplashScreen(this);
	}
	
	public MenuScreen getMenuScreen(boolean isMuted) {
		return new MenuScreen(this, isMuted);
	}
	
	public EasyPlayScreen getEasyPlayScreen(boolean isMuted) {
		return new EasyPlayScreen(this, isMuted);
	}
		
	public NormalPlayScreen getNormalPlayScreen(boolean isMuted) {
		return new NormalPlayScreen(this, isMuted);
	}
	
	public HardPlayScreen getHardPlayScreen(boolean isMuted) {
		return new HardPlayScreen(this, isMuted);
	}
	
	public HelpScreen getHelpScreen(boolean isMuted) {
		return new HelpScreen(this, isMuted);
	}
	
	public Skin getSkin() {
		return this.skin;
	}
	
	public AssetManager getAssets() {
		return assets;
	}
}
