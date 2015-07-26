package screens;

import GameStuff.GameSettings;
import GameStuff.GameStats;
import Stuff.CoolButton;
import Stuff.GamePrefs;
import Stuff.LevelBox2;
import Stuff.LevelBoxPage2;
import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class LevelMenuScreen2 implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	private CoolButton leftButton, rightButton, shop, back;

	public static Vector2[] positions;

	private Array<LevelBoxPage2> pages = new Array<LevelBoxPage2>();

	public LevelMenuScreen2(MainClass c) {
		game = c;
	}

	private int page = 0;

	private void Input() {
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {

				Vector3 vec = new Vector3(screenX, screenY, 0);
				camera.unproject(vec);
				if (leftButton.TouchedUp(vec.x, vec.y))

					if (page > 0)
						page--;
				if (rightButton.TouchedUp(vec.x, vec.y))
					if (page < pages.size - 1)
						page++;

				shop.TouchedUp(vec.x, vec.y);

				if (shop.TouchedUp(vec.x, vec.y))
					game.SetScreen(Screens.Shop);

				if (back.TouchedUp(vec.x, vec.y))
					game.SetScreen(Screens.MainMenu);

				int levelClicked = pages.get(page).TouchedUpLevel(vec.x, vec.y);
				if (levelClicked != -1) {
					GameStats.Level = levelClicked;
					game.SetScreen(Screens.Game);
				}

				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub

				Vector3 vec = new Vector3(screenX, screenY, 0);
				camera.unproject(vec);
				leftButton.TouchedDown(vec.x, vec.y);
				rightButton.TouchedDown(vec.x, vec.y);
				shop.TouchedDown(vec.x, vec.y);
				back.TouchedDown(vec.x, vec.y);
				pages.get(page).TouchedDown(vec.x, vec.y);
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				Vector3 vec = new Vector3(screenX, screenY, 0);
				camera.unproject(vec);
				leftButton.TouchedDown(vec.x, vec.y);
				rightButton.TouchedDown(vec.x, vec.y);
				shop.TouchedDown(vec.x, vec.y);
				back.TouchedDown(vec.x, vec.y);
				pages.get(page).TouchedDown(vec.x, vec.y);
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

				return false;
			}
		});
	}

	@Override
	public void show() {
		if (!GameSettings.FULL) {
			game.androidHandler.ShowAds(true);
		}
		Input();
		positions = new Vector2[10];

		float beginX = 110;
		for (int i = 0; i < 5; i++) {
			positions[i] = new Vector2(beginX, 300);
			beginX += 99 + 20;
		}

		beginX = 110;
		for (int i = 5; i <= 9; i++) {
			positions[i] = new Vector2(beginX, 150);
			beginX += 99 + 20;
		}

		leftButton = new CoolButton(GraphicsManager.LevelMenu2.left, 59, 63);
		leftButton.SetPosition(16, 208.5f);
		rightButton = new CoolButton(GraphicsManager.LevelMenu2.right, 59, 63);
		rightButton.SetPosition(800 - 16 - 63, 208.5f);
		back = new CoolButton(GraphicsManager.LevelMenu2.back, 122, 73);
		shop = new CoolButton(GraphicsManager.LevelMenu2.shop, 122, 73);
		back.SetPosition(268, 50);
		shop.SetPosition(410, 50);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);

		viewport = new ExtendViewport(800, 480, 853, 533, camera);

		Array<LevelBox2> boxes = new Array<LevelBox2>();
		int aux = 0;
		for (int i = 1;; i++) {
			if (Gdx.files.internal("Maps/level" + i + ".tmx").exists()) {
				aux++;

				boxes.add(GamePrefs.CreateLevelBox(i));

				if (aux == 10) {
					aux = 0;
					pages.add(new LevelBoxPage2(boxes));
					boxes.clear();
				}
			} else {

				pages.add(new LevelBoxPage2(boxes));
				break;

			}
		}

	}

	@Override
	public void hide() {

	}

	@Override
	public void render(float delta) {

		camera.update();

		batch.enableBlending();

		Gdx.gl.glClearColor(1, 1, 1, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(GraphicsManager.Game.bg2,
				(800 - viewport.getWorldWidth()) / 2f,
				(480 - viewport.getWorldHeight()) / 2f, 853, 533);
		leftButton.Draw(batch);
		rightButton.Draw(batch);
		shop.Draw(batch);
		back.Draw(batch);
		// for (LevelBox2 b : buttons)
		// b.Draw(batch);
		pages.get(page).Draw(batch);

		batch.end();

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
