package screens;

import GameStuff.GameSettings;
import Stuff.MenuWorld;
import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class MainMenuScreen implements Screen {

	private MainClass game;

	private SpriteBatch batch;
	private OrthographicCamera camera, b2dcamera;
	private Viewport viewport, b2dviewport;
	private MenuWorld menuWorld;

	private Sprite mario;
	private Vector2 mdp = new Vector2(445, 320);
	private Vector2 msp = new Vector2(580, 320);

	private boolean NoTouch = true;

	public MainMenuScreen(MainClass c) {
		game = c;
	}

	private void CreateMario() {
		mario = new Sprite(GraphicsManager.MainMenu.mario);
		mario.setSize(134, 121);
		mario.setPosition(mdp.x, mdp.y);
	}

	private Vector3 tmp = new Vector3();

	private QueryCallback qc = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			// TODO Auto-generated method stub
			if (fixture.testPoint(tmp.x, tmp.y)) {

				if (fixture.getBody().getUserData() != null) {
					if (fixture.getBody().getUserData().toString()
							.equals("logo"))
						fixture.getBody().applyForceToCenter(
								MathUtils.random(1000, 9000),
								MathUtils.random(1000, 9000), true);
				}

			}

			return false;
		}
	};

	@Override
	public void show() {

		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				tmp.set(screenX, screenY, 0);
				b2dcamera.unproject(tmp);

				// joint = new MouseJoint();

				for (int i = 0; i <= 4; i++) {
					menuWorld.buttons[i].TouchedUp(tmp.x, tmp.y);
				}
				if (NoTouch) {
					NoTouch = false;
					return false;

				}
				if (menuWorld.buttons[0].TouchedUp(tmp.x, tmp.y))
					game.SetScreen(Screens.LevelMenu);
				if (menuWorld.buttons[1].TouchedUp(tmp.x, tmp.y))
					game.SetScreen(Screens.Settings);
				if (menuWorld.buttons[2].TouchedUp(tmp.x, tmp.y))
					game.SetScreen(Screens.Help);
				if (menuWorld.buttons[3].TouchedUp(tmp.x, tmp.y))
					Gdx.net.openURI("https://www.facebook.com/fainosagstudio");
				if (menuWorld.buttons[4].TouchedUp(tmp.x, tmp.y))
					Gdx.app.exit();
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				tmp.set(screenX, screenY, 0);
				b2dcamera.unproject(tmp);
				for (int i = 0; i <= 4; i++) {
					menuWorld.buttons[i].TouchedDown(tmp.x, tmp.y);
				}

				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub
				game.androidHandler.ShowAds(true);
				NoTouch = false;
				Vector3 vec = new Vector3(screenX, screenY, 0);
				b2dcamera.unproject(vec);
				System.out.println(pointer);
				if (menuWorld.Top_Added) {
					menuWorld.world.QueryAABB(qc, tmp.x, tmp.y, tmp.x, tmp.y);
					if (pointer >= 1) {
						menuWorld.Box_Body.applyAngularImpulse(
								MathUtils.random(100, 1000), true);
					}
				}

				for (int i = 0; i <= 4; i++) {
					menuWorld.buttons[i].TouchedDown(vec.x, vec.y);
				}

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

		CreateMario();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		viewport = new ExtendViewport(800, 480, 853, 533, camera);

		b2dcamera = new OrthographicCamera(800 / 100f, 480 / 100f);
		b2dcamera.position.set(400 / 100f, 240 / 100f, 0);
		b2dviewport = new ExtendViewport(800 / 100f, 480 / 100f, 853 / 100f,
				533 / 100f, b2dcamera);

		menuWorld = new MenuWorld(b2dcamera);

		if (!GameSettings.FULL) {
			int RandomNumber = MathUtils.random(0, 15);
			System.out.println("Random number de la 0 la 15 a fost "
					+ RandomNumber);
			if (RandomNumber == 1)
				game.androidHandler.ShowBuyDialog();
			if (RandomNumber == 2)
				game.androidHandler.ShowFBDialog();
			if (RandomNumber == 3)
				game.androidHandler.ShowRateDialog();
			game.androidHandler.ShowAds(true);
		}

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
		b2dcamera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(GraphicsManager.Game.bg2,
				(800 - viewport.getWorldWidth()) / 2f,
				(480 - viewport.getWorldHeight()) / 2f, 853, 533);
		if (menuWorld.Animation_Done())
			mario.draw(batch);
		batch.end();

		// box2d world render
		batch.setProjectionMatrix(b2dcamera.combined);
		batch.begin();
		menuWorld.Render(batch);
		batch.end();
		//

		// UI render
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.end();
		//

	}

	private void update() {
		if (Gdx.input.isTouched()) {
			if (menuWorld.Animation_Done()) {
				if (mario.getX() < msp.x)
					mario.setX(mario.getX() + Gdx.graphics.getDeltaTime() * 150);
				else
					mario.setX(msp.x);
			}
		} else {
			if (mario.getX() > mdp.x)
				mario.setX(mario.getX() - Gdx.graphics.getDeltaTime() * 150);
			else
				mario.setX(mdp.x);

		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		b2dviewport.update(width, height);

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
