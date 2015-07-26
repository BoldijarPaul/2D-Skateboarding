package GameStuff;

import Stuff.GamePrefs;

public class GameSettings {

	public static boolean FULL = true;

	public static int GQ = 0;
	// 0 = ld
	// 1 = md
	// 2 = hd
	// 3 = xhd

	public static float Stats1 = 0;
	public static float Stats2 = 0;
	public static float Stats3 = 0;
	public static float Stats4 = 0;

	public void LoadStats() {
		GamePrefs.LoadStats();
	}

	public static boolean SoundON = true;
	public static boolean InGameAds = true;

	public static void Load() {
		GamePrefs.LoadSettings();
		GamePrefs.LoadStats();
		System.out.println("DONE LODING GQ E " + GQ);
	}

	public static void Save() {
		GamePrefs.SaveSettings();
	}

	public static void ChangeQuality(int q) {
		GQ = q;
	}
}
