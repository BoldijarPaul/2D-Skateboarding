package Stuff;

import GameStuff.GameSettings;
import GameStuff.GameStats;

import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

public class GamePrefs {

	// nivele, etc

	public static Preferences prefs;
	public static boolean first_time = false;

	// first_loaded - boolean
	// level<id>_stars - int (0-3)
	// level<id>_locked - boolean
	// level<id>_coins<coinid> - boolean true = luata false =nu e luata
	// game_quality - int -
	// 0 = ld
	// 1 = md
	// 2 = hd
	// 3 = xhd
	// sound_on
	// ingameads
	// coins - int - numar de monezi

	// mstats1 - float nr maxim
	// stats1 int nivelul 0 - 20
	// stats2 stats3 stats4

	public static float[] level = new float[] { 0.0f, 0.05f, 0.1f, 0.15f, 0.2f,
			0.25f, 0.3f, 0.35f, 0.4f, 0.45f, 0.5f, 0.55f, 0.6f, 0.65f, 0.7f,
			0.75f, 0.8f, 0.85f, 0.9f, 0.95f, 1f };

	public static void LoadStats() {

		GameSettings.Stats1 = level[prefs.getInteger("stats1", 0)];
		GameSettings.Stats2 = level[prefs.getInteger("stats2", 0)];
		GameSettings.Stats3 = level[prefs.getInteger("stats3", 0)];
		GameSettings.Stats4 = level[prefs.getInteger("stats4", 0)];
	}

	public static int GetUpgradePrice(int nr) {
		if (prefs.getInteger("mstats" + nr, 0) == 20)
			return -1;
		return prefs.getInteger("mstats" + nr, 0) + 2;
	}

	public static int GetMaxStats(int nr) {
		return prefs.getInteger("mstats" + nr, 0);
	}

	public static int GetStats(int nr) {
		return prefs.getInteger("stats" + nr, 0);
	}

	public static void DebugStats() {
		System.out.println("mstats1 ( bearings ) este " + GetMaxStats(1));
		System.out.println("mstats2 (   deck   ) este " + GetMaxStats(2));
		System.out.println("mstats3 (  wheels  ) este " + GetMaxStats(3));
		System.out.println("mstats4 (  trucks  ) este " + GetMaxStats(4));

		System.out.println("stats1  ( bearings ) este " + GetStats(1));
		System.out.println("stats2  (   deck   ) este " + GetStats(2));
		System.out.println("stats3  (  wheels  ) este " + GetStats(3));
		System.out.println("stats4  (  trucks  ) este " + GetStats(4));
	}

	public static void BuyStats(int nr) {
		if (GetMaxStats(nr) == 20) {
			System.out.println("Este deja la maxim.");
			return;
		}

		System.out.println("Am cumparat upgrade la stats" + nr + " pentru "
				+ GetUpgradePrice(nr) + " banuti");
		RemoveCoins(GetUpgradePrice(nr));
		int oldStats = GetMaxStats(nr);
		prefs.putInteger("mstats" + nr, oldStats + 1);
		prefs.flush();
		LoadStats();
	}

	public static void AddStats(int nr) {

		int stats = GetStats(nr);
		int maxstats = GetMaxStats(nr);
		if (stats < maxstats) {
			prefs.putInteger("stats" + nr, stats + 1);
			prefs.flush();
			LoadStats();
			System.out.println("Am marit stats" + nr + " ca se poate .stats="
					+ stats + " maxstats=" + maxstats);
		}
	}

	public static void RemoveStats(int nr) {

		int stats = GetStats(nr);

		if (stats > 0) {
			prefs.putInteger("stats" + nr, stats - 1);
			prefs.flush();
			LoadStats();
			System.out.println("Am scazut stats" + nr + " ca se poate .stats="
					+ stats + " > 0 ");
		}
	}

	public static int GetCoins() {
		return prefs.getInteger("coins", 0);
	}

	public static void AddCoins(int nr) {
		int actualCoins = prefs.getInteger("coins", 0);
		prefs.putInteger("coins", actualCoins + nr);
		prefs.flush();
	}

	public static void RemoveCoins(int nr) {
		int actualCoins = prefs.getInteger("coins", 0);
		prefs.putInteger("coins", actualCoins - nr);
		prefs.flush();
	}

	public static LevelBox2 CreateLevelBox(int level) {
		return new LevelBox2(0, 0,
				prefs.getBoolean("level" + level + "_locked"),
				prefs.getInteger("level" + level + "_stars"), level);
	}

	public static void SaveSettings() {
		prefs.putInteger("game_quality", GameSettings.GQ);
		prefs.putBoolean("sound_on", GameSettings.SoundON);
		prefs.putBoolean("ingameads", GameSettings.InGameAds);
		prefs.flush();

		if (GameSettings.SoundON) {
			if (SoundManager.background.isPlaying() == false)
				SoundManager.background.play();
		} else {
			if (SoundManager.background.isPlaying())
				SoundManager.background.stop();
		}
	}

	public static Array<Integer> TakenCoins() {
		Array<Integer> arr = new Array<Integer>();

		for (int i = 0; i <= 150; i++) {
			if (prefs
					.getBoolean("level" + GameStats.Level + "coins" + i, false) == true) {

				arr.add(i);

			}
		}

		return arr;
	}

	public static void UnlockNextLevel(int level) {

		prefs.putBoolean("level" + (level + 1) + "_locked", false);
		if (prefs.getInteger("level" + level + "_stars") < GameStats.Stars)
			prefs.putInteger("level" + level + "_stars", GameStats.Stars);

		for (int i = 0; i < GameStats.CoinsGot.size; i++) {
			prefs.putBoolean(
					"level" + level + "coins" + GameStats.CoinsGot.get(i), true);

		}
		prefs.flush();

	}

	public static void FirstLoad() {
		// apelata doar prima oara
		first_time = true;
		prefs.putBoolean("first_time", false);
		for (int i = 1;; i++) {
			if (Gdx.files.internal("Maps/level" + i + ".tmx").exists()) {

				prefs.putInteger("level" + i + "_stars", 0);
				if (i != 1)
					prefs.putBoolean("level" + i + "_locked", false); // de
																		// editat
																		// in
																		// true
				else
					prefs.putBoolean("level" + i + "_locked", false);
			} else {

				break;

			}
		}
		prefs.flush();

	}

	public static void Load() {
		// apelata in mainclass la inceput
		prefs = Gdx.app.getPreferences("fainosag");
		first_time = false;
		if (prefs.getBoolean("first_time", true))
			FirstLoad();

	}

	public static void LoadSettings() {
		GameSettings.GQ = prefs.getInteger("game_quality", 1);
		GameSettings.InGameAds = prefs.getBoolean("ingameads", true);
		GameSettings.SoundON = prefs.getBoolean("sound_on", true);

	}

}
