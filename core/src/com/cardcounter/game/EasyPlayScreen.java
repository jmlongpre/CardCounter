package com.cardcounter.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class EasyPlayScreen extends AbstractScreen {
	
	// Sets the spacing for playing cards
	private static final float MULTI = 30f;
	
	// Sets the number of cards to be dealt
	private static final int HANDSIZE = 20;
	
	// Images, buttons, tables, and labels which require multiple scopes
	private Button correctImage;
	private Button wrongImage;
	private Button plusButton;
	private Button minusButton;
	private Button playButton;
	private Button menuButton;
	private Button muteButton;
	private Button restartButton;
	private Label countLabel;
	private Label answerLabel;
	private Label actualLabel;
	private Table answerTable;
	private ArrayList<Label> runningCountLabels;
	private int[] runningCountList;
	
	// ints and booleans that require multiple scopes and can be pre-set
	private int playerCount = 0;
	private int actualCount = 0;
	private int answerCount = 0;
	private int currentCard = 0;
	private boolean isMuted = false;
	
	// cardDeck is an ArrayList of cards (typeof Image)
	private ArrayList<Image> cardDeck = new ArrayList<Image>();
	
	// Timer must be declared here, as it is not static
	private Timer timer = new Timer();


	
	public EasyPlayScreen(CardCounter game, boolean isMuted) {
		super(game);
		this.isMuted = isMuted;
		runningCountList = new int[HANDSIZE+1];
		runningCountList[0] = 0;
		runningCountLabels = new ArrayList<Label>();
		
		// Generate the cards in the deck and determine the actualCount beforehand
		Random randomGenerator = new Random();
		for (int i = 0; i < HANDSIZE; i++) {
			int value = randomGenerator.nextInt(52);
			cardDeck.add(new Image(getSkin().getDrawable(Integer.toString(value))));
			if (value < 20) {
				actualCount++;
				runningCountList[i+1] = runningCountList[i] + 1;
			}
			else if (value > 31) {
				actualCount--;
				runningCountList[i+1] = runningCountList[i] - 1;
			}
			else {
				runningCountList[i+1] = runningCountList[i];
			}
			
		}
		
		for (int i = 0; i < HANDSIZE; i++) {
			runningCountLabels.add(new Label("0", getSkin()));
			runningCountLabels.get(i).setText(Integer.toString(runningCountList[i+1]));
		}
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Create the background Image and add it to stage
		Image background = new Image(game.getAssets().get("play_background.png", Texture.class));
		background.setFillParent(true);
		background.setPosition(0, 0);
		stage.addActor(background);
		
		// Create a label which tracks the playerCount throughout the game (may not be correct)
		countLabel = new Label("Your Count: " + playerCount, getSkin());
		countLabel.setPosition((float)(width/2 - countLabel.getWidth()/2), (float)(countLabel.getHeight()));
		
		// Create a label which shows the actualCount for the game (initially invisible) 
		actualLabel = new Label("The Actual Count: " + actualCount, getSkin());
		actualLabel.setPosition(width/2 - actualLabel.getWidth()/2, height/3 - actualLabel.getHeight()/2);
		
		// Create a label which shows the player's final answer (initially invisible) 
		answerLabel = new Label("Enter your Count: " + answerCount, getSkin());
		answerLabel.setPosition(width/2 - answerLabel.getWidth()/2, height*2/3 - actualLabel.getHeight()/2);
		
		// Create the image which notifies the player that they're correct (initially invisible)
		correctImage = new Button(getSkin().getDrawable("correct"));
		correctImage.setPosition(width/2 - correctImage.getWidth()/2, height/2 - correctImage.getHeight()/2);
		correctImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				for (int i = 0; i < HANDSIZE; i++) {
					cardDeck.get(i).setVisible(true);
					runningCountLabels.get(i).setVisible(true);
					correctImage.setVisible(false);
					answerLabel.setVisible(false);
					actualLabel.setVisible(false);
				}
			}
		});
		
		// Create the image which notifies the player that they're wrong (initially invisible) 
		wrongImage = new Button(getSkin().getDrawable("wrong"));
		wrongImage.setPosition(width/2 - correctImage.getWidth()/2, height/2 - correctImage.getHeight()/2);
		wrongImage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				for (int i = 0; i < HANDSIZE; i++) {
					cardDeck.get(i).setVisible(true);
					runningCountLabels.get(i).setVisible(true);
					correctImage.setVisible(false);
					answerLabel.setVisible(false);
					actualLabel.setVisible(false);
				}
			}
		});
		
		// Create the plus button, which increments playerCount when clicked
		plusButton = new Button(getSkin().getDrawable("button_plus"), getSkin().getDrawable("button_plus_pressed"));
		plusButton.setPosition(plusButton.getWidth()/4, (float)(height/2 - plusButton.getHeight()/2));
		plusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("click.mp3", Sound.class).play();
				playerCount++;
			}
		});
		
		// Create the minus button, which decrements playerCount when clicked
		minusButton = new Button(getSkin().getDrawable("button_minus"), getSkin().getDrawable("button_minus_pressed"));
		minusButton.setPosition((float)(width - minusButton.getWidth()*1.25), (float)(height/2 - minusButton.getHeight()/2));
		minusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("click.mp3", Sound.class).play();
				playerCount--;
			}
		});
		
		// Create the play button, which executes runGame() when clicked
		playButton = new Button(getSkin().getDrawable("play"));
		playButton.setPosition(width/2 - playButton.getWidth()/2, height/2 - playButton.getHeight()/2);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("click.mp3", Sound.class).play();
				playButton.remove();
				runGame();
			}
		});
		
		// Create the restart button, which calls a new EasyPlayScreen when clicked
		restartButton = new Button(getSkin().getDrawable("restart"));
		restartButton.setPosition(width/2 - restartButton.getWidth()/2, (float) (height - restartButton.getHeight()*1.5));
		restartButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("select.mp3", Sound.class).play();
				game.setScreen(game.getEasyPlayScreen(isMuted));
			}
		});
		
		// Create the menu button, which calls a new MenuScreen when clicked
		menuButton = new Button(getSkin().getDrawable("menu"));
		menuButton.setPosition(width/4 - menuButton.getWidth()/2, (float)(height - menuButton.getHeight()*1.5));
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isMuted)
					game.getAssets().get("select.mp3", Sound.class).play();
				game.setScreen(game.getMenuScreen(isMuted));
			}
		});
		
		// Create the mute button, which toggles isMuted when clicked
		muteButton = new Button(getSkin().getDrawable("mute"), getSkin().getDrawable("mute"), getSkin().getDrawable("unmute"));
		muteButton.setPosition(3*width/4 - muteButton.getWidth()/2, (float)(height - menuButton.getHeight()*1.5));
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
		
		// Create the cards; for every card in cardDeck, sets scale, position, visibility, and adds to stage
		for (int i = 0; i < cardDeck.size(); i++) {
			cardDeck.get(i).setPosition(width/2 - cardDeck.get(i).getWidth()*1.65f + i*MULTI, height/2 - cardDeck.get(i).getHeight()/2);
			cardDeck.get(i).setVisible(false);
			runningCountLabels.get(i).setPosition(width/2 - cardDeck.get(i).getWidth()*1.6f + i*MULTI, height/2 + cardDeck.get(i).getHeight()/2 + runningCountLabels.get(i).getHeight());
			runningCountLabels.get(i).setVisible(false);
			stage.addActor(cardDeck.get(i));
			stage.addActor(runningCountLabels.get(i));
		}
		
		// Create the answerPlusButton, which increments the player's final guess (answerCount)
		Button answerPlusButton = new Button(getSkin().getDrawable("plus"));
		answerPlusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				answerCount++;
			}
		});
		
		// Create the answerMinusButton, which decrements the player's final guess (answerCount)
		Button answerMinusButton = new Button(getSkin().getDrawable("minus"));
		answerMinusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				answerCount--;
			}
		});
		
		// Create the enterButton, which submits the player's final guess (answerCount) and sets correctImage accordingly
		Button enterButton = new Button(getSkin().getDrawable("enter"));
		enterButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addActor(actualLabel);
				answerTable.remove();
				if (answerCount == actualCount) {
					stage.addActor(correctImage);
				}
				else {
					stage.addActor(wrongImage);
				}
			}
		});
		
		// Create answerTable, which holds answerMinusButton, answerPlusButton, and enterButton (initially invisible)
		answerTable = new Table(getSkin());
		answerTable.setPosition(width/2 - answerTable.getWidth()/2, height/2 - answerTable.getHeight()/2);
		answerTable.add(answerMinusButton).pad(10f);
		answerTable.add(answerPlusButton).pad(10f);
		answerTable.add(enterButton).pad(10f);
		
		// Finally, add all actors to the stage
		stage.addActor(playButton);
		stage.addActor(plusButton);
		stage.addActor(minusButton);
		stage.addActor(countLabel);
		stage.addActor(restartButton);
		stage.addActor(menuButton);
		stage.addActor(muteButton);
	}
	
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Refresh all labels which require updating (countLabel and answerLabel only)
		countLabel.setText("Your count: " + playerCount);
		answerLabel.setText("Enter your Count: " + answerCount);
		
		// Draw sprites from batch (only backgroundSprite)
		batch.begin();
		batch.end();
		
		// Draw the stage and call act() on all actors
		stage.draw();
		stage.act();
	}
	
	
	// runGame() initiates all game play; on the last iteration of task, all cards are hidden and answerTable is revealed
	public void runGame() {
		Task task = new Task() {
			@Override
			public void run() {
				if (currentCard < cardDeck.size()) {
					cardDeck.get(currentCard).setVisible(true);
					if (!isMuted)
						game.getAssets().get("cardFlip.mp3", Sound.class).play();
					currentCard++;
				}
				else {
					for (Image card : cardDeck) {
						card.setVisible(false);
					}
					stage.addActor(answerTable);
					stage.addActor(answerLabel);
				}
			}
		};
		
		timer.scheduleTask(task, 1f, 1.5f, cardDeck.size());
	}
	
}
