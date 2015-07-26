package com.fainosag.marioskate;

import screens.GameScreen;
import screens.HelpScreen;
import screens.LevelMenuScreen2;
import screens.MainMenuScreen;
import screens.SettingsScreen;
import screens.ShopScreen;
import screens.SplashScreen;
import swipe.SwipeListener;
import swipe.SwipeType;
import GameStuff.GameSettings;
import Stuff.AndroidHandler;
import Stuff.GamePrefs;
import Stuff.Languages;
import assets.GraphicsManager;
import assets.SoundManager;

import com.badlogic.gdx.Game;

public class MainClass extends Game implements SwipeListener {

	public AndroidHandler androidHandler;

	public static enum Screens {
		Splash, Game, MainMenu, LevelMenu, Settings, Help, Shop;
	}

	public static boolean Done_Loading = false;
	private GameScreen gameScreen;

	public MainClass(AndroidHandler androidHandler) {
		// TODO Auto-generated constructor stub
		this.androidHandler = androidHandler;
	}

	public void SetScreen(Screens screen) {
		if (screen == Screens.Splash) {
			setScreen(new SplashScreen(this));
		}
		if (screen == Screens.MainMenu) {
			setScreen(new MainMenuScreen(this));
		}
		if (screen == Screens.Game) {
			gameScreen = new GameScreen(this);
			setScreen(gameScreen);
		}
		if (screen == Screens.LevelMenu) {
			setScreen(new LevelMenuScreen2(this));
		}
		if (screen == Screens.Settings) {
			setScreen(new SettingsScreen(this));
		}
		if (screen == Screens.Help) {
			setScreen(new HelpScreen(this));
		}
		if (screen == Screens.Shop) {
			setScreen(new ShopScreen(this));
		}
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

		// Gdx.app.getPreferences("fainosag").clear();

		Languages.Load();
		GamePrefs.Load();
		GameSettings.Load();
		GraphicsManager.Load();

		GraphicsManager.Splash.Load();

		Done_Loading = false;
		GraphicsManager.Game.Load();
		GraphicsManager.MainMenu.Load();
		GraphicsManager.LevelMenu2.Load();
		GraphicsManager.Settings.Load();
		GraphicsManager.Help.Load();
		GraphicsManager.Shop.Load();
		Done_Loading = true;
		SoundManager.Load();
		SetScreen(Screens.Splash);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
	}

	@Override
	public void onSwipe(SwipeType type, float amount) {
		// TODO Auto-generated method stub
		if (gameScreen != null) {
			gameScreen.onSwipe(type, amount);
		}

	}

}
