package screens;

import GameStuff.GameSettings;
import Stuff.CoolButton;

import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class SettingsScreen implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private boolean NoTouch = true;

	private CoolButton back, button1, button2, button3;

	public SettingsScreen(MainClass c) {
		game = c;
	}

	private Vector3 tmp = new Vector3();

	@Override
	public void show() {

		GameSettings.Load();
		if (!GameSettings.FULL) {
			game.androidHandler.ShowAds(true);
		}
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);

				// joint = new MouseJoint();
				back.TouchedUp(tmp.x, tmp.y);
				button1.TouchedUp(tmp.x, tmp.y);
				button2.TouchedUp(tmp.x, tmp.y);
				button3.TouchedUp(tmp.x, tmp.y);
				if (NoTouch) {
					NoTouch = false;
					return false;

				}
				if (back.TouchedDown(tmp.x, tmp.y)) {
					game.SetScreen(Screens.MainMenu);
					if (GameSettings.FULL == false) {
						int randomNumber = MathUtils.random(0, 5);
						System.out.println("Random number de la 0 la 5 este "
								+ randomNumber);
						if (randomNumber == 1)
							game.androidHandler.ShowInterestial();
					}
				}
				if (button1.TouchedUp(tmp.x, tmp.y)) {
					if (GameSettings.GQ <= 2)
						GameSettings.GQ++;
					else
						GameSettings.GQ = 0;

					if (GameSettings.GQ == 0)
						button1.SetRegion(GraphicsManager.Settings.ld);
					if (GameSettings.GQ == 1)
						button1.SetRegion(GraphicsManager.Settings.md);
					if (GameSettings.GQ == 2)
						button1.SetRegion(GraphicsManager.Settings.hd);
					if (GameSettings.GQ == 3)
						button1.SetRegion(GraphicsManager.Settings.xhd);
					GameSettings.Save();
				}
				if (button2.TouchedUp(tmp.x, tmp.y)) {
					GameSettings.SoundON = !GameSettings.SoundON;
					if (GameSettings.SoundON == true)
						button2.SetRegion(GraphicsManager.Settings.sound_on);
					if (GameSettings.SoundON == false)
						button2.SetRegion(GraphicsManager.Settings.sound_off);
					GameSettings.Save();

				}
				if (button3.TouchedUp(tmp.x, tmp.y)) {
					GameSettings.InGameAds = !GameSettings.InGameAds;
					if (GameSettings.InGameAds == true)
						button3.SetRegion(GraphicsManager.Settings.checked);
					if (GameSettings.InGameAds == false)
						button3.SetRegion(GraphicsManager.Settings.unchecked);
					GameSettings.Save();

				}
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);
				back.TouchedDown(tmp.x, tmp.y);
				button1.TouchedDown(tmp.x, tmp.y);
				button2.TouchedDown(tmp.x, tmp.y);
				button3.TouchedDown(tmp.x, tmp.y);
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
				button1.TouchedDown(tmp.x, tmp.y);
				button2.TouchedDown(tmp.x, tmp.y);
				button3.TouchedDown(tmp.x, tmp.y);
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

		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		viewport = new ExtendViewport(800, 480, 853, 533, camera);

		back = new CoolButton(GraphicsManager.Settings.backs, 106, 63);
		back.SetPosition(10, 10);

		button1 = new CoolButton(GraphicsManager.Settings.ld, 96, 97);
		if (GameSettings.GQ == 0)
			button1.SetRegion(GraphicsManager.Settings.ld);
		if (GameSettings.GQ == 1)
			button1.SetRegion(GraphicsManager.Settings.md);
		if (GameSettings.GQ == 2)
			button1.SetRegion(GraphicsManager.Settings.hd);
		if (GameSettings.GQ == 3)
			button1.SetRegion(GraphicsManager.Settings.xhd);

		button2 = new CoolButton(GraphicsManager.Settings.sound_on, 96, 97);
		if (GameSettings.SoundON == true)
			button2.SetRegion(GraphicsManager.Settings.sound_on);
		if (GameSettings.SoundON == false)
			button2.SetRegion(GraphicsManager.Settings.sound_off);

		button3 = new CoolButton(GraphicsManager.Settings.checked, 96, 97);
		if (GameSettings.InGameAds == true)
			button3.SetRegion(GraphicsManager.Settings.checked);
		if (GameSettings.InGameAds == false)
			button3.SetRegion(GraphicsManager.Settings.unchecked);

		button1.SetPosition(550, 355);
		button2.SetPosition(550, 237);
		button3.SetPosition(550, 120);
	}

	@Override
	public void hide() {

	}

	@Override
	public void render(float delta) {
		update();
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
		batch.draw(GraphicsManager.Settings.graphics, 120, 370, 377, 69);
		batch.draw(GraphicsManager.Settings.sound, 120, 250, 377, 69);
		batch.draw(GraphicsManager.Settings.ingameads, 120, 130, 377, 69);
		button1.Draw(batch);
		button2.Draw(batch);
		button3.Draw(batch);
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
