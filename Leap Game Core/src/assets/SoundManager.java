package assets;

import GameStuff.GameSettings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	public static Music ui_down;
	public static Music game_coin, game_ollie, game_wood, game_fail, game_done,
			game_star;
	public static Music background;
	public static Music menu_pop;

	private static Music CreateSound(String name) {
		return Gdx.audio.newMusic(Gdx.files.internal("Sounds/" + name));
	}

	public static void Load() {
		ui_down = CreateSound("sound_down.wav");
		ui_down.setVolume(0.5f);
		game_coin = CreateSound("sound_coin.wav");
		game_ollie = CreateSound("sound_ollie.wav");
		game_wood = CreateSound("sound_grindwood.mp3");
		game_wood.setVolume(0.3f);
		game_wood.setLooping(true);

		background = CreateSound("sound_bg.mp3");
		background.setVolume(0.5f);
		background.setLooping(true);
		if (GameSettings.SoundON)
			background.play();

		game_fail = CreateSound("sound_fail.mp3");
		game_done = CreateSound("sound_done.mp3");
		game_star = CreateSound("sound_star.mp3");
		menu_pop = CreateSound("sound_pop.mp3");

	}
}
