package assets;

import GameStuff.GameSettings;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GraphicsManager {

	public static Resolution[] resolutions = new Resolution[4];
	public static ResolutionFileResolver resolver;

	public static void Load() {
		resolutions[0] = new Resolution(300, 500, "500x300");
		resolutions[1] = new Resolution(480, 800, "800x480");
		resolutions[2] = new Resolution(768, 1280, "1280x768");
		resolutions[3] = new Resolution(1152, 1920, "1920x1152");

		resolver = new ResolutionFileResolver(new InternalFileHandleResolver(),
				resolutions[GameSettings.GQ]);
		System.out.println(GameSettings.GQ);

	}

	public static class Settings {

		public static AssetManager manager;

		public static TextureRegion backs, graphics, sound, ingameads, ld, md,
				hd, xhd, unchecked, checked, sound_on, sound_off;

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);

			manager.load("Graphics/backs.png", Texture.class);
			manager.load("Graphics/graphics.png", Texture.class);
			manager.load("Graphics/graphics.png", Texture.class);
			manager.load("Graphics/sound.png", Texture.class);
			manager.load("Graphics/ingameads.png", Texture.class);
			manager.load("Graphics/ld.png", Texture.class);
			manager.load("Graphics/md.png", Texture.class);
			manager.load("Graphics/hd.png", Texture.class);
			manager.load("Graphics/xhd.png", Texture.class);
			manager.load("Graphics/unchecked.png", Texture.class);
			manager.load("Graphics/checked.png", Texture.class);
			manager.load("Graphics/sound_on.png", Texture.class);
			manager.load("Graphics/sound_off.png", Texture.class);
			manager.finishLoading();

			backs = CreateTextureRegion("backs.png");
			graphics = CreateTextureRegion("graphics.png");
			sound = CreateTextureRegion("sound.png");
			ingameads = CreateTextureRegion("ingameads.png");
			ld = CreateTextureRegion("ld.png");
			md = CreateTextureRegion("md.png");
			hd = CreateTextureRegion("hd.png");
			xhd = CreateTextureRegion("xhd.png");
			unchecked = CreateTextureRegion("unchecked.png");
			checked = CreateTextureRegion("checked.png");
			sound_on = CreateTextureRegion("sound_on.png");
			sound_off = CreateTextureRegion("sound_off.png");

		}
	}

	public static class Shop {

		public static AssetManager manager;

		public static TextureRegion maro, remove, add, wheels, bearings, bar,
				trucks, deck;

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);

			manager.load("Graphics/maro.png", Texture.class);
			manager.load("Graphics/remove.png", Texture.class);
			manager.load("Graphics/add.png", Texture.class);
			manager.load("Graphics/wheels.png", Texture.class);
			manager.load("Graphics/bearings.png", Texture.class);
			manager.load("Graphics/bar.png", Texture.class);
			manager.load("Graphics/deck.png", Texture.class);
			manager.load("Graphics/trucks.png", Texture.class);
			manager.finishLoading();

			maro = CreateTextureRegion("maro.png");
			remove = CreateTextureRegion("remove.png");
			add = CreateTextureRegion("add.png");
			wheels = CreateTextureRegion("wheels.png");
			bearings = CreateTextureRegion("bearings.png");
			bar = CreateTextureRegion("bar.png");
			trucks = CreateTextureRegion("trucks.png");
			deck = CreateTextureRegion("deck.png");

		}
	}

	public static class Game {

		//

		public static final int ollieFrames = 20, idleFrames = 15,
				talpaFrames = 15, grindFrames = 10;

		// dialog stuff

		public static TextureRegion dialog_dialog, dialog_cofee,
				dialog_congrats, dialog_paused, dialog_fail, dialog_menu,
				dialog_next, dialog_play, dialog_skull, dialog_star0,
				dialog_star1, dialog_retry;

		public static TextureRegion[] tiles = new TextureRegion[15];
		public static TextureRegion[] gamestars = new TextureRegion[4];
		public static TextureRegion[] anim_ollie = new TextureRegion[ollieFrames];
		public static TextureRegion[] anim_idle = new TextureRegion[idleFrames];
		public static TextureRegion[] anim_talpa = new TextureRegion[talpaFrames];
		public static TextureRegion[] anim_grind = new TextureRegion[grindFrames];
		public static TextureRegion coin;
		public static TextureRegion star;
		public static TextureRegion bg, bg2, bg_4;
		public static TextureRegion flag;
		public static TextureRegion pause;
		public static TextureRegion fail, mario, hat;

		public static TextureAtlas tileAtlas;

		public static AssetManager manager;

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static TextureRegion CreateTileTextureRegion(String name) {
			Texture aux = manager.get("Tiles/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Dispose() {
			manager.dispose();
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);

			for (int i = 1; i <= 11; i++)
				manager.load("Tiles/" + i + ".png", Texture.class);

			// dialog stuff
			manager.load("Graphics/dialog_dialog.png", Texture.class);
			manager.load("Graphics/bg_4.png", Texture.class);
			manager.load("Graphics/hat.png", Texture.class);
			manager.load("Graphics/dialog_cofee.png", Texture.class);
			manager.load("Graphics/dialog_congrats.png", Texture.class);
			manager.load("Graphics/dialog_paused.png", Texture.class);
			manager.load("Graphics/dialog_fail.png", Texture.class);
			manager.load("Graphics/dialog_next.png", Texture.class);
			manager.load("Graphics/dialog_play.png", Texture.class);
			manager.load("Graphics/dialog_menu.png", Texture.class);
			manager.load("Graphics/dialog_star1.png", Texture.class);
			manager.load("Graphics/dialog_star0.png", Texture.class);
			manager.load("Graphics/dialog_skull.png", Texture.class);
			manager.load("Graphics/dialog_retry.png", Texture.class);

			manager.load("Tiles/mario.png", Texture.class);

			manager.load("Graphics/bg.png", Texture.class);

			manager.load("Graphics/background_sunny.png", Texture.class);
			manager.load("Graphics/flag.png", Texture.class);
			manager.load("Graphics/pause.png", Texture.class);

			for (int i = 0; i < ollieFrames; i++)
				manager.load("Graphics/ollie__" + String.format("%03d", i)
						+ ".png", Texture.class);
			for (int i = 0; i < idleFrames; i++)
				manager.load("Graphics/idle__" + String.format("%03d", i)
						+ ".png", Texture.class);
			for (int i = 0; i < talpaFrames; i++)
				manager.load("Graphics/talpa__" + String.format("%03d", i)
						+ ".png", Texture.class);
			for (int i = 0; i < grindFrames; i++)
				manager.load("Graphics/grind__" + String.format("%03d", i)
						+ ".png", Texture.class);

			manager.finishLoading();
			hat = CreateTextureRegion("hat.png");
			bg2 = CreateTextureRegion("background_sunny.png");
			bg_4 = CreateTextureRegion("bg_4.png");
			// dialog
			dialog_dialog = CreateTextureRegion("dialog_dialog.png");

			dialog_retry = CreateTextureRegion("dialog_retry.png");
			dialog_cofee = CreateTextureRegion("dialog_cofee.png");
			dialog_congrats = CreateTextureRegion("dialog_congrats.png");
			dialog_paused = CreateTextureRegion("dialog_paused.png");
			dialog_fail = CreateTextureRegion("dialog_fail.png");
			dialog_menu = CreateTextureRegion("dialog_menu.png");
			dialog_next = CreateTextureRegion("dialog_next.png");
			dialog_play = CreateTextureRegion("dialog_play.png");
			dialog_skull = CreateTextureRegion("dialog_skull.png");
			dialog_star0 = CreateTextureRegion("dialog_star0.png");
			dialog_star1 = CreateTextureRegion("dialog_star1.png");

			for (int i = 0; i < 11; i++)
				tiles[i] = CreateTileTextureRegion((i + 1) + ".png");
			mario = CreateTileTextureRegion("mario.png");

			pause = CreateTextureRegion("pause.png");

			bg = CreateTextureRegion("bg.png");
			flag = CreateTextureRegion("flag.png");

			for (int i = 0; i < ollieFrames; i++)
				anim_ollie[i] = CreateTextureRegion("ollie__"
						+ String.format("%03d", i) + ".png");
			for (int i = 0; i < idleFrames; i++)
				anim_idle[i] = CreateTextureRegion("idle__"
						+ String.format("%03d", i) + ".png");
			for (int i = 0; i < talpaFrames; i++)
				anim_talpa[i] = CreateTextureRegion("talpa__"
						+ String.format("%03d", i) + ".png");
			for (int i = 0; i < grindFrames; i++)
				anim_grind[i] = CreateTextureRegion("grind__"
						+ String.format("%03d", i) + ".png");
		}

		public void render() {
			// TODO Auto-generated method stub

		}
	}

	public static class MainMenu {

		public static AssetManager manager;

		public static TextureRegion logo, play, fb, help, settings, quit,
				mario;

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);

			manager.load("Graphics/head.png", Texture.class);
			manager.load("Graphics/button_fb.png", Texture.class);
			manager.load("Graphics/button_help.png", Texture.class);
			manager.load("Graphics/button_play.png", Texture.class);
			manager.load("Graphics/button_quit.png", Texture.class);
			manager.load("Graphics/button_settings.png", Texture.class);
			manager.load("Graphics/mainlogo.png", Texture.class);
			manager.finishLoading();

			fb = CreateTextureRegion("button_fb.png");
			mario = CreateTextureRegion("head.png");
			help = CreateTextureRegion("button_help.png");
			play = CreateTextureRegion("button_play.png");
			quit = CreateTextureRegion("button_quit.png");
			settings = CreateTextureRegion("button_settings.png");
			logo = CreateTextureRegion("mainlogo.png");

		}

	}

	public static class Help {

		public static AssetManager manager;

		public static TextureRegion[] text = new TextureRegion[5];

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);

			manager.load("Graphics/text1.png", Texture.class);
			manager.load("Graphics/text2.png", Texture.class);
			manager.load("Graphics/text3.png", Texture.class);
			manager.load("Graphics/text4.png", Texture.class);
			manager.load("Graphics/text5.png", Texture.class);

			manager.finishLoading();

			text[0] = CreateTextureRegion("text1.png");
			text[1] = CreateTextureRegion("text2.png");
			text[2] = CreateTextureRegion("text5.png");
			text[3] = CreateTextureRegion("text3.png");
			text[4] = CreateTextureRegion("text4.png");

		}
	}

	public static class LevelMenu2 {

		public static AssetManager manager;

		public static TextureRegion[] box = new TextureRegion[5];
		public static TextureRegion[] digits = new TextureRegion[10];
		public static TextureRegion left, right;
		public static TextureRegion shop, back;
		public static int[] dw = new int[] { 22, 15, 20, 20, 23, 19, 20, 19,
				21, 21 };
		public static int dh = 31;

		public static int NumberWidth(int nr) {
			char[] chars = ("" + nr).toCharArray();
			int x = 0;
			for (int i = 0; i < chars.length; i++) {
				x += dw[chars[i] - 48] + 3;
			}
			return x;
		}

		public static void DrawNumber(int nr, SpriteBatch batch, float x,
				float y) {
			char[] chars = ("" + nr).toCharArray();

			for (int i = 0; i < chars.length; i++) {
				int id = chars[i] - 48;
				batch.draw(digits[id], x, y, dw[id], dh);
				x += dw[id] + 3;
			}
		}

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			manager.setLoader(Texture.class, new TextureLoader(resolver));
			Texture.setAssetManager(manager);
			manager.load("Graphics/shop.png", Texture.class);
			manager.load("Graphics/back.png", Texture.class);
			manager.load("Graphics/box0.png", Texture.class);
			manager.load("Graphics/box1.png", Texture.class);
			manager.load("Graphics/box2.png", Texture.class);
			manager.load("Graphics/box3.png", Texture.class);
			manager.load("Graphics/box4.png", Texture.class);
			manager.load("Graphics/leftarrow.png", Texture.class);
			for (int i = 0; i <= 9; i++)
				manager.load("Graphics/" + i + ".png", Texture.class);

			manager.finishLoading();

			shop = CreateTextureRegion("shop.png");
			back = CreateTextureRegion("back.png");
			box[0] = CreateTextureRegion("box0.png");
			box[1] = CreateTextureRegion("box1.png");
			box[2] = CreateTextureRegion("box2.png");
			box[3] = CreateTextureRegion("box3.png");
			box[4] = CreateTextureRegion("box4.png");

			for (int i = 0; i <= 9; i++)
				digits[i] = CreateTextureRegion(i + ".png");
			left = CreateTextureRegion("leftarrow.png");
			right = CreateTextureRegion("leftarrow.png");
			right.flip(true, false);

		}
	}

	public static class Splash {

		public static TextureRegion logo;

		public static AssetManager manager;

		public static void Dispose() {
			manager.dispose();
		}

		public static TextureRegion CreateTextureRegion(String name) {
			Texture aux = manager.get("Graphics/" + name, Texture.class);
			aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return new TextureRegion(aux);
		}

		public static void Load() {

			manager = new AssetManager();
			/*
			 * manager.setLoader(Texture.class, new TextureLoader(resolver));
			 * Texture.setAssetManager(manager);
			 * manager.load("Graphics/logo.png", Texture.class);
			 * 
			 * manager.finishLoading();
			 * 
			 * Texture aux = manager.get("Graphics/logo.png", Texture.class);
			 * aux.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			 */
			Texture texture = new Texture("logo.png");
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			logo = new TextureRegion(texture);

		}
	}
}
