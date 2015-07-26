package screens;

import GameStuff.GameSettings;
import GameStuff.GameStats;
import Stuff.CoolButton;
import Stuff.GamePrefs;
import Stuff.Languages;

import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;
 

public class ShopScreen implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private boolean NoTouch = true;

	private CoolButton back;
	private CoolButton bearings, deck, wheels, trucks;
	private CoolButton bar1, bar2, bar3, bar4;
	private CoolButton add1, add2, add3, add4;
	private CoolButton remove1, remove2, remove3, remove4;

	public ShopScreen(MainClass c) {
		game = c;
	}

	private Vector3 tmp = new Vector3();

	@Override
	public void show() {

		GameSettings.Load();
		if (!GameSettings.FULL) {
			game.androidHandler.ShowAds(true);
		}
		GamePrefs.DebugStats();
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);

				// joint = new MouseJoint();
				back.TouchedUp(tmp.x, tmp.y);

				bearings.TouchedUp(tmp.x, tmp.y);
				deck.TouchedUp(tmp.x, tmp.y);
				wheels.TouchedUp(tmp.x, tmp.y);
				trucks.TouchedUp(tmp.x, tmp.y);

				bar1.TouchedUp(tmp.x, tmp.y);
				bar2.TouchedUp(tmp.x, tmp.y);
				bar3.TouchedUp(tmp.x, tmp.y);
				bar4.TouchedUp(tmp.x, tmp.y);

				add1.TouchedUp(tmp.x, tmp.y);
				add2.TouchedUp(tmp.x, tmp.y);
				add3.TouchedUp(tmp.x, tmp.y);
				add4.TouchedUp(tmp.x, tmp.y);

				remove1.TouchedUp(tmp.x, tmp.y);
				remove2.TouchedUp(tmp.x, tmp.y);
				remove3.TouchedUp(tmp.x, tmp.y);
				remove4.TouchedUp(tmp.x, tmp.y);

				if (NoTouch) {
					NoTouch = false;
					return false;

				}

				if (add1.TouchedUp(tmp.x, tmp.y)) {

					int coins = GamePrefs.GetCoins();
					int pret = GamePrefs.GetUpgradePrice(1);
					int stats = GamePrefs.GetStats(1);
					int mstats = GamePrefs.GetMaxStats(1);
					if (stats < mstats) {
						// trebuie doar sa marim nivelul.
						GamePrefs.AddStats(1);
						// nu afisam nimic
						return false;
					}
					if (pret == -1) {
						// afisam mesaj ca nu mai putem mari
						game.androidHandler.ShowInfoDialog(Languages
								.GetString("mesaj_eroare_full"));
						return false;
					}
					// verificam daca putem cumpara
					if (coins < pret) {
						// nu avem destule monede
						// afisam textul cu nr de meonezi
						String linie1 = Languages.GetString("mesaj_banuti")
								.replace("%1", coins + "");
						int dif = pret - coins;
						String linie2 = Languages.GetString(
								"mesaj_eroare_banuti").replace("%1", dif + "");
						game.androidHandler.ShowInfoDialog(linie1 + "\n"
								+ linie2);
						return false;
					}
					String line1 = Languages.GetString("mesaj_banuti_level")
							.replace("%1", coins + "")
							.replace("%2", mstats + "");

					String line2 = Languages.GetString(
							"mesaj_confirmare_upgrade")
							.replace("%1", pret + "");
					game.androidHandler.ShowBuyConfirmation(line1 + "\n"
							+ line2, 1);

				}
				if (remove1.TouchedUp(tmp.x, tmp.y)) {

					GamePrefs.RemoveStats(1);

				}

				if (add2.TouchedUp(tmp.x, tmp.y)) {

					int coins = GamePrefs.GetCoins();
					int pret = GamePrefs.GetUpgradePrice(2);
					int stats = GamePrefs.GetStats(2);
					int mstats = GamePrefs.GetMaxStats(2);
					if (stats < mstats) {
						// trebuie doar sa marim nivelul.
						GamePrefs.AddStats(2);
						// nu afisam nimic
						return false;
					}
					if (pret == -1) {
						// afisam mesaj ca nu mai putem mari
						game.androidHandler.ShowInfoDialog(Languages
								.GetString("mesaj_eroare_full"));
						return false;
					}
					// verificam daca putem cumpara
					if (coins < pret) {
						// nu avem destule monede
						// afisam textul cu nr de meonezi
						String linie1 = Languages.GetString("mesaj_banuti")
								.replace("%1", coins + "");
						int dif = pret - coins;
						String linie2 = Languages.GetString(
								"mesaj_eroare_banuti").replace("%1", dif + "");
						game.androidHandler.ShowInfoDialog(linie1 + "\n"
								+ linie2);
						return false;
					}
					String line1 = Languages.GetString("mesaj_banuti_level")
							.replace("%1", coins + "")
							.replace("%2", mstats + "");

					String line2 = Languages.GetString(
							"mesaj_confirmare_upgrade")
							.replace("%1", pret + "");
					game.androidHandler.ShowBuyConfirmation(line1 + "\n"
							+ line2, 2);

				}
				if (remove2.TouchedUp(tmp.x, tmp.y)) {

					GamePrefs.RemoveStats(2);

				}

				if (add3.TouchedUp(tmp.x, tmp.y)) {

					int coins = GamePrefs.GetCoins();
					int pret = GamePrefs.GetUpgradePrice(3);
					int stats = GamePrefs.GetStats(3);
					int mstats = GamePrefs.GetMaxStats(3);
					if (stats < mstats) {
						// trebuie doar sa marim nivelul.
						GamePrefs.AddStats(3);
						// nu afisam nimic
						return false;
					}
					if (pret == -1) {
						// afisam mesaj ca nu mai putem mari
						game.androidHandler.ShowInfoDialog(Languages
								.GetString("mesaj_eroare_full"));
						return false;
					}
					// verificam daca putem cumpara
					if (coins < pret) {
						// nu avem destule monede
						// afisam textul cu nr de meonezi
						String linie1 = Languages.GetString("mesaj_banuti")
								.replace("%1", coins + "");
						int dif = pret - coins;
						String linie2 = Languages.GetString(
								"mesaj_eroare_banuti").replace("%1", dif + "");
						game.androidHandler.ShowInfoDialog(linie1 + "\n"
								+ linie2);
						return false;
					}
					String line1 = Languages.GetString("mesaj_banuti_level")
							.replace("%1", coins + "")
							.replace("%2", mstats + "");

					String line2 = Languages.GetString(
							"mesaj_confirmare_upgrade")
							.replace("%1", pret + "");
					game.androidHandler.ShowBuyConfirmation(line1 + "\n"
							+ line2, 3);

				}
				if (remove3.TouchedUp(tmp.x, tmp.y)) {

					GamePrefs.RemoveStats(3);

				}

				if (add4.TouchedUp(tmp.x, tmp.y)) {

					int coins = GamePrefs.GetCoins();
					int pret = GamePrefs.GetUpgradePrice(4);
					int stats = GamePrefs.GetStats(4);
					int mstats = GamePrefs.GetMaxStats(4);
					if (stats < mstats) {
						// trebuie doar sa marim nivelul.
						GamePrefs.AddStats(4);
						// nu afisam nimic
						return false;
					}
					if (pret == -1) {
						// afisam mesaj ca nu mai putem mari
						game.androidHandler.ShowInfoDialog(Languages
								.GetString("mesaj_eroare_full"));
						return false;
					}
					// verificam daca putem cumpara
					if (coins < pret) {
						// nu avem destule monede
						// afisam textul cu nr de meonezi
						String linie1 = Languages.GetString("mesaj_banuti")
								.replace("%1", coins + "");
						int dif = pret - coins;
						String linie2 = Languages.GetString(
								"mesaj_eroare_banuti").replace("%1", dif + "");
						game.androidHandler.ShowInfoDialog(linie1 + "\n"
								+ linie2);
						return false;
					}
					String line1 = Languages.GetString("mesaj_banuti_level")
							.replace("%1", coins + "")
							.replace("%2", mstats + "");

					String line2 = Languages.GetString(
							"mesaj_confirmare_upgrade")
							.replace("%1", pret + "");
					game.androidHandler.ShowBuyConfirmation(line1 + "\n"
							+ line2, 4);

				}
				if (remove4.TouchedUp(tmp.x, tmp.y)) {

					GamePrefs.RemoveStats(4);

				}

				if (back.TouchedUp(tmp.x, tmp.y)) {
					game.SetScreen(Screens.MainMenu);
				}
				if (bearings.TouchedUp(tmp.x, tmp.y)) {
					game.androidHandler.ShowInfoDialog(Languages
							.GetString("info_bearings"));
				}
				if (trucks.TouchedUp(tmp.x, tmp.y)) {
					game.androidHandler.ShowInfoDialog(Languages
							.GetString("info_trucks"));
				}
				if (deck.TouchedUp(tmp.x, tmp.y)) {
					game.androidHandler.ShowInfoDialog(Languages
							.GetString("info_deck"));
				}
				if (wheels.TouchedUp(tmp.x, tmp.y)) {
					game.androidHandler.ShowInfoDialog(Languages
							.GetString("info_wheels"));
				}

				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);
				back.TouchedDown(tmp.x, tmp.y);

				bearings.TouchedDown(tmp.x, tmp.y);
				deck.TouchedDown(tmp.x, tmp.y);
				wheels.TouchedDown(tmp.x, tmp.y);
				trucks.TouchedDown(tmp.x, tmp.y);

				bar1.TouchedDown(tmp.x, tmp.y);
				bar2.TouchedDown(tmp.x, tmp.y);
				bar3.TouchedDown(tmp.x, tmp.y);
				bar4.TouchedDown(tmp.x, tmp.y);

				add1.TouchedDown(tmp.x, tmp.y);
				add2.TouchedDown(tmp.x, tmp.y);
				add3.TouchedDown(tmp.x, tmp.y);
				add4.TouchedDown(tmp.x, tmp.y);

				remove1.TouchedDown(tmp.x, tmp.y);
				remove2.TouchedDown(tmp.x, tmp.y);
				remove3.TouchedDown(tmp.x, tmp.y);
				remove4.TouchedDown(tmp.x, tmp.y);

				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				NoTouch = false;
				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);
				back.TouchedDown(tmp.x, tmp.y);

				bearings.TouchedDown(tmp.x, tmp.y);
				deck.TouchedDown(tmp.x, tmp.y);
				wheels.TouchedDown(tmp.x, tmp.y);
				trucks.TouchedDown(tmp.x, tmp.y);

				bar1.TouchedDown(tmp.x, tmp.y);
				bar2.TouchedDown(tmp.x, tmp.y);
				bar3.TouchedDown(tmp.x, tmp.y);
				bar4.TouchedDown(tmp.x, tmp.y);

				add1.TouchedDown(tmp.x, tmp.y);
				add2.TouchedDown(tmp.x, tmp.y);
				add3.TouchedDown(tmp.x, tmp.y);
				add4.TouchedDown(tmp.x, tmp.y);

				remove1.TouchedDown(tmp.x, tmp.y);
				remove2.TouchedDown(tmp.x, tmp.y);
				remove3.TouchedDown(tmp.x, tmp.y);
				remove4.TouchedDown(tmp.x, tmp.y);

				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				if (keycode == Keys.MENU || keycode == Keys.ENTER)
					GamePrefs.AddCoins(5);

				return false;
			}
		});

		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		viewport = new ExtendViewport(800, 480, 853, 533, camera);

		back = new CoolButton(GraphicsManager.Settings.backs, 106, 63);
		back.SetPosition(5, 5);

		bearings = new CoolButton(GraphicsManager.Shop.bearings, 220, 72);
		deck = new CoolButton(GraphicsManager.Shop.deck, 220, 72);
		wheels = new CoolButton(GraphicsManager.Shop.wheels, 220, 72);
		trucks = new CoolButton(GraphicsManager.Shop.trucks, 220, 72);

		bar1 = new CoolButton(GraphicsManager.Shop.bar, 220, 72);
		bar2 = new CoolButton(GraphicsManager.Shop.bar, 220, 72);
		bar3 = new CoolButton(GraphicsManager.Shop.bar, 220, 72);
		bar4 = new CoolButton(GraphicsManager.Shop.bar, 220, 72);

		add1 = new CoolButton(GraphicsManager.Shop.add, 72, 72);
		add2 = new CoolButton(GraphicsManager.Shop.add, 72, 72);
		add3 = new CoolButton(GraphicsManager.Shop.add, 72, 72);
		add4 = new CoolButton(GraphicsManager.Shop.add, 72, 72);

		remove1 = new CoolButton(GraphicsManager.Shop.remove, 72, 72);
		remove2 = new CoolButton(GraphicsManager.Shop.remove, 72, 72);
		remove3 = new CoolButton(GraphicsManager.Shop.remove, 72, 72);
		remove4 = new CoolButton(GraphicsManager.Shop.remove, 72, 72);

		float y1 = 380;
		float y2 = 280;
		float y3 = 180;
		float y4 = 80;

		bearings.SetPosition(110, y1);
		deck.SetPosition(110, y2);
		wheels.SetPosition(110, y3);
		trucks.SetPosition(110, y4);

		bar1.SetPosition(350, y1);
		bar2.SetPosition(350, y2);
		bar3.SetPosition(350, y3);
		bar4.SetPosition(350, y4);

		add1.SetPosition(590, y1);
		add2.SetPosition(590, y2);
		add3.SetPosition(590, y3);
		add4.SetPosition(590, y4);

		remove1.SetPosition(682, y1);
		remove2.SetPosition(682, y2);
		remove3.SetPosition(682, y3);
		remove4.SetPosition(682, y4);

	}

	@Override
	public void hide() {

	}

	@Override
	public void render(float delta) {
		update();

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			GameSettings.Stats1 = MathUtils.random();
			GameSettings.Stats2 = MathUtils.random();
			GameSettings.Stats3 = MathUtils.random();
			GameSettings.Stats4 = MathUtils.random();
		}

		camera.update();

		batch.enableBlending();

		Gdx.gl.glClearColor(0, 0, 0, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// bg render

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(GraphicsManager.Game.bg2,
				(800 - viewport.getWorldWidth()) / 2f,
				(480 - viewport.getWorldHeight()) / 2f, 853, 533);
		back.Draw(batch);
		bearings.Draw(batch);
		deck.Draw(batch);
		wheels.Draw(batch);
		trucks.Draw(batch);
		bar1.Draw(batch);
		bar2.Draw(batch);
		bar3.Draw(batch);
		bar4.Draw(batch);

		add1.Draw(batch);
		add2.Draw(batch);
		add3.Draw(batch);
		add4.Draw(batch);

		remove1.Draw(batch);
		remove2.Draw(batch);
		remove3.Draw(batch);
		remove4.Draw(batch);

		batch.draw(GraphicsManager.Shop.maro, 370.5f, 399.0f,
				GameSettings.Stats1 * 180f, 36);
		batch.draw(GraphicsManager.Shop.maro, 370.5f, 299.0f,
				GameSettings.Stats2 * 180f, 36);
		batch.draw(GraphicsManager.Shop.maro, 370.5f, 199.0f,
				GameSettings.Stats3 * 180f, 36);
		batch.draw(GraphicsManager.Shop.maro, 370.5f, 99.0f,
				GameSettings.Stats4 * 180f, 36);

		batch.end();

		//

	}

	private void update() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();

	}

}
