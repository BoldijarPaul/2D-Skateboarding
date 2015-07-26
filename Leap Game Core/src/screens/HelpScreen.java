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

import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class HelpScreen implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private boolean NoTouch = true;
	private CoolButton back;

	float y = 150;
	int c = 1;

	public HelpScreen(MainClass c) {
		game = c;
	}

	private Vector3 tmp = new Vector3();

	@Override
	public void show() {

		GameSettings.Load();
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);

				// joint = new MouseJoint();
				back.TouchedUp(tmp.x, tmp.y);

				if (NoTouch) {
					NoTouch = false;
					return false;

				}
				if (back.TouchedDown(tmp.x, tmp.y)) {
					game.SetScreen(Screens.MainMenu);
				}

				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				tmp.set(screenX, screenY, 0);
				camera.unproject(tmp);
				back.TouchedDown(tmp.x, tmp.y);

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

		y += Gdx.graphics.getDeltaTime() * 160 * c;
		if (y >= 1100)
			c = -c;
		if (y <= 149)
			c = -c;
		batch.draw(GraphicsManager.Help.text[0], 130, y, 660, 228);
		batch.draw(GraphicsManager.Help.text[1], 130, y - 250, 660, 228);
		batch.draw(GraphicsManager.Help.text[2], 130, y - 250 * 2, 660, 228);
		batch.draw(GraphicsManager.Help.text[3], 130, y - 250 * 3, 660, 228);
		batch.draw(GraphicsManager.Help.text[4], 130, y - 250 * 4, 660, 228);
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
