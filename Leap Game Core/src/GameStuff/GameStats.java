package GameStuff;

import Stuff.B2DContactListener;
import Stuff.GamePrefs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameStats {

	public static int Level = 11;
	public static int Stars = 0;
	public static int Coins = 0;
	public static int a;
	public static Vector2 GameSpawnPosition;
	public static boolean NeedToDeleteBody = false;
	public static Color GameBackgroundColor = Color.WHITE;
	public static Array<Integer> CoinsGot = new Array<Integer>();
	public static Array<Integer> CoinsTaken = new Array<Integer>();

	public static int GameState = 3;
	public static float WorldWidth;
	public static float WorldHeight;

	// 0 = playing
	// 1 = pause
	// 2 = dead
	// 3 = finish

	public static boolean OnGround() {
		return B2DContactListener.FootAndTileCount > 0;
	}

	public static boolean OnRail() {
		return B2DContactListener.FootAndRailCount > 0;
	}

	public static boolean IsCoin(int id) {
		for (int i = 0; i < CoinsTaken.size; i++) {
			if (CoinsTaken.get(i).equals(id)) {

				return true;
			}
		}
		return false;
	}

	public static boolean AddTheCoin(int id) {

		return !IsCoin(id);
	}

	public static void Reset() {
		CoinsGot.clear();
		CoinsTaken = GamePrefs.TakenCoins();

		B2DContactListener.FootAndTileCount = 0;
		B2DContactListener.FootAndRailCount = 0;
		Stars = 0;
		GameState = 0;
		Coins = 0;

	}

	public static void GotCoin(int id) {
		CoinsGot.add(id);

	}

	// probleme
	// tilesurile nu sunt pt orice rezolutie
	// backgroundul nu e terminat
	// bug la colturi
}
