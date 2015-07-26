package screens;

import GameStuff.GameSettings;
import Stuff.GamePrefs;
import Stuff.Languages;
import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class SplashScreen implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	private Sprite sprite;
	private float scale = 1f;

	private boolean done = false;
	private float alpha = 1;
	private float waitTime = .5f;

	public SplashScreen(MainClass c) {
		game = c;
	}

	@Override
	public void show() {

		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);

		viewport = new ExtendViewport(800, 480, camera);

		sprite = new Sprite(GraphicsManager.Splash.logo);
		sprite.setScale(1);
		sprite.setPosition(400 - sprite.getWidth() / 2f,
				240 - sprite.getHeight() / 2f);
		sprite.setOriginCenter();

		if (GamePrefs.first_time) {
			if (GameSettings.FULL)
				game.androidHandler.ShowInfoDialog(
						Languages.GetString("mesaj_welcome_full"),
						Languages.GetString("mesaj_binevenit"));
			else
				game.androidHandler.ShowInfoDialog(
						Languages.GetString("mesaj_welcome_free"),
						Languages.GetString("mesaj_binevenit"));
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
		sprite.draw(batch);
		batch.end();
		if (Gdx.graphics.getFramesPerSecond() < 15)
			return;

		if (scale > .5f) {
			scale -= Gdx.graphics.getDeltaTime() * 1.1f;
			sprite.setScale(scale);
			sprite.setPosition(400 - sprite.getWidth() / 2f,
					240 - sprite.getHeight() / 2f);
			sprite.setOriginCenter();
		} else
			done = true;

		if (done)
			waitTime -= Gdx.graphics.getDeltaTime();
		if (done && waitTime <= 0) {
			sprite.setAlpha(alpha);
			alpha -= Gdx.graphics.getDeltaTime() / 1.3f;
			if (alpha <= 0)
				game.SetScreen(Screens.MainMenu);
		}
		// game.SetScreen(Screens.MainMenu);

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
		GraphicsManager.Splash.logo.getTexture().dispose();

	}
}
