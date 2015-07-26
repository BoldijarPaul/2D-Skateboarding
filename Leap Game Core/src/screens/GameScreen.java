package screens;

import static Stuff.B2D.PPM;
import swipe.SwipeListener;
import swipe.SwipeType;
import GameStuff.GameDialogs;
import GameStuff.GameGUI;
import GameStuff.GameSettings;
import GameStuff.GameStats;
import GameStuff.GameTileRenderer;
import GameStuff.GameWorld;
import Stuff.B2D;
import Stuff.GameDialog;
import Stuff.GamePrefs;
import Stuff.Skater;
import assets.GraphicsManager;
import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fainosag.marioskate.MainClass;
import com.fainosag.marioskate.MainClass.Screens;

public class GameScreen implements Screen, SwipeListener {

	MainClass game;

	public GameScreen(MainClass game) {
		this.game = game;
	}

	private Box2DDebugRenderer renderer;
	private Rectangle bounds = new Rectangle();
	private float initialScale = 1f;
	private SpriteBatch batch;
	private OrthographicCamera camera, screencamera;
	private Viewport viewport1, viewport2;
	private boolean background1;
	private GameGUI gameGUI;
	private GameWorld gameWorld;

	private FPSLogger loger = new FPSLogger();

	private GameDialogs dialogs;

	private void LoadGame() {
		gameWorld = new GameWorld(camera);
		renderer=new Box2DDebugRenderer();

	}

	public static GameTileRenderer gtr;

	private BitmapFont debugBF;

	private void RestartGame() {
		gameWorld.dispose();

		gameWorld = new GameWorld(camera);
		background1 = MathUtils.randomBoolean();
	}

	@Override
	public void show() {

		debugBF = new BitmapFont();
		debugBF.scale(2);
		gtr = new GameTileRenderer();

		batch = new SpriteBatch();
		camera = new OrthographicCamera(800 / PPM, 480 / PPM);
		camera.position.set(400 / PPM, 240 / PPM, 0);
		screencamera = new OrthographicCamera(800 / PPM, 480 / PPM);
		screencamera.position.set(400, 240, 0);
		viewport1 = new ExtendViewport(800 / PPM, 480 / PPM, 853 / PPM,
				533 / PPM, camera);
		viewport2 = new ExtendViewport(800, 480, 853, 533, screencamera);

		camera.zoom = 0.7f;
		gameGUI = new GameGUI(viewport2.getWorldWidth(),
				viewport2.getWorldWidth());
		LoadGame();

		dialogs = new GameDialogs();

		HandleInput();

		if (GameSettings.FULL == false)
			game.androidHandler.ShowAds(GameSettings.InGameAds);

	}

	private void HandleInput()

	{
		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(new GestureDetector(new GestureListener() {

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				initialScale = camera.zoom;

				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) {
				// TODO Auto-generated method stub

				return false;
			}

			@Override
			public boolean zoom(float initialDistance, float distance) {
				// TODO Auto-generated method stub

				if (GameSettings.FULL) {
					if (GameStats.GameState == 0) {
						float ratio = initialDistance / distance;
						camera.zoom = initialScale * ratio;
					}
				}

				return false;
			}

			@Override
			public boolean pinch(Vector2 initialPointer1,
					Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
				// TODO Auto-generated method stub
				// camera.zoom -= 0.1f;
				// System.out.println("pinch");

				return false;
			}

			@Override
			public boolean panStop(float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub

				return false;
			}

			@Override
			public boolean pan(float x, float y, float deltaX, float deltaY) {
				// TODO Auto-generated method stub

				return false;
			}

			@Override
			public boolean longPress(float x, float y) {
				// TODO Auto-generated method stub

				return false;
			}

			@Override
			public boolean fling(float velocityX, float velocityY, int button) {
				// TODO Auto-generated method stub

				if (GameStats.GameState == 0) {
					if (Math.abs(velocityX) > Math.abs(velocityY)) {
						if (velocityX < 0)
							gameWorld.skater.Left();
						else
							gameWorld.skater.Right();
					} else if (velocityY < 0)
						gameWorld.skater.Jump();
					else {
						gameWorld.skater.Stop();
					}
				}
				return false;
			}

		}));

		im.addProcessor(new InputProcessor() {

			Vector3 vec = new Vector3(0, 0, 0);

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				vec.set(screenX, screenY, 0);
				screencamera.unproject(vec);

				if (GameStats.GameState != 0)
					dialogs.TouchUp(vec.x, vec.y);

				if (GameStats.GameState == 0) {
					if (gameGUI.PauseTouched(vec.x, vec.y)) {
						GameStats.GameState = 1;
						dialogs.gamePauseDialog.Show();
					}
				}
				if (GameStats.GameState == 1) {
					if (dialogs.gamePauseDialog.PlayTouch(vec.x, vec.y)) {
						GameStats.GameState = 0;
						dialogs.gamePauseDialog.Hide();
					}
					if (dialogs.gamePauseDialog.MenuTouch(vec.x, vec.y)) {
						game.SetScreen(Screens.LevelMenu);
					}
					if (dialogs.gamePauseDialog.RetryTouch(vec.x, vec.y)) {
						RestartGame();
						dialogs.gamePauseDialog.Hide();
					}

				}
				if (GameStats.GameState == 3) {

					if (dialogs.gameFinishDialog.MenuTouch(vec.x, vec.y)) {
						game.SetScreen(Screens.LevelMenu);
					}
					if (dialogs.gameFinishDialog.RetryTouch(vec.x, vec.y)) {
						RestartGame();
						dialogs.gameFinishDialog.Hide();

					}
					if (dialogs.gameFinishDialog.NextTouch(vec.x, vec.y)) {
						GameStats.Level++;
						RestartGame();
						dialogs.gameFinishDialog.Hide();

					}
				}
				if (GameStats.GameState == 2) {
					if (dialogs.gameFailDialog.MenuTouch(vec.x, vec.y)) {
						game.SetScreen(Screens.LevelMenu);
					}
					if (dialogs.gameFailDialog.RetryTouch(vec.x, vec.y)) {

						GameStats.GameState = 0;
						dialogs.gameFailDialog.Hide();
						RestartGame();
					}
				}

				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				vec.set(screenX, screenY, 0);
				screencamera.unproject(vec);

				if (GameStats.GameState != 0)
					dialogs.TouchDown(vec.x, vec.y);
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				// TODO Auto-generated method stub

				vec.set(screenX, screenY, 0);
				screencamera.unproject(vec);
				if (GameStats.GameState != 0)
					dialogs.TouchDown(vec.x, vec.y);

				// skater.Jump();
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				camera.zoom += amount / 100f;
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

				if (keycode == Keys.ESCAPE) {
					if (GameStats.GameState == 0) {
						GameStats.GameState = 1;
						dialogs.gamePauseDialog.Show();

					} else {
						GameStats.GameState = 0;
						dialogs.gamePauseDialog.Hide();
					}
				}
				if (keycode == B2D.JumpKey) {
					gameWorld.skater.Jump();

				}
				if (keycode == B2D.DownKey) {
					gameWorld.skater.Stop();

				}

				if (keycode == B2D.LeftKey) {
					gameWorld.skater.Left();

				}
				if (keycode == B2D.RightKey) {
					gameWorld.skater.Right();

				}

				return false;
			}

		});
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void hide() {

	}

	private void update() {

		loger.log();
		dialogs.Update();

		if (GameStats.GameState == 0) {
			if (GameStats.OnRail()) {
				if (GameSettings.SoundON) {
					if (SoundManager.game_wood.isPlaying() == false)
						SoundManager.game_wood.play();
				}
			} else {
				if (SoundManager.game_wood.isPlaying())
					SoundManager.game_wood.stop();
			}
		} else if (SoundManager.game_wood.isPlaying())
			SoundManager.game_wood.stop();
		if (GameStats.GameState == 0) {
			gameWorld.Update();
			if (gameWorld.skater.playerBody.getPosition().y < -5f
					|| gameWorld.contactlistener.dead) {

				GameStats.GameState = 2;
				dialogs.gameFailDialog.Show();

				if (GameSettings.FULL == false) {
					int randomNumber = MathUtils.random(0, 5);
					System.out.println("Random number de la 0 la 5 este "
							+ randomNumber);
					if (randomNumber == 1)
						game.androidHandler.ShowInterestial();
				}
			}

			/*
			 * if (GameStats.OnRail() && Skater.Speed <= 0.005f) {
			 * 
			 * GameStats.GameState = 2; dialogs.gameFailDialog.Show(); }
			 */
			if (gameWorld.contactlistener.done) {
				GameStats.GameState = 3;
				dialogs.gameFinishDialog.Show();
				GamePrefs.AddCoins(GameStats.Coins);
				GamePrefs.UnlockNextLevel(GameStats.Level);

				if (GameSettings.FULL == false) {
					int randomNumber = MathUtils.random(0, 5);
					System.out.println("Random number de la 0 la 5 este "
							+ randomNumber);
					if (randomNumber == 1)
						game.androidHandler.ShowInterestial();
				}
			}
		}
		// skr.Update(0, 0);

	}

	public void BeginScrissor(Rectangle clipBounds, Camera camera) {
		Rectangle scissors = new Rectangle();

		ScissorStack.calculateScissors(screencamera,
				batch.getTransformMatrix(), clipBounds, scissors);
		ScissorStack.pushScissors(scissors);
	}

	public void EndScrissor() {
		ScissorStack.popScissors();
		batch.flush();
	}

	private Vector2 tmp2 = new Vector2();
	private Vector3 tmp3 = new Vector3();

	public Vector2 unProject(float x, float y) {
		tmp3.set(x, y, 0);
		camera.unproject(tmp3);
		tmp2.set(tmp3.x, tmp3.y);
		return tmp2;
	}

	private void updateBounds() {
		float offset = 0f;
		bounds.setWidth(viewport2.getWorldWidth() + offset);
		bounds.setHeight(viewport2.getWorldHeight() + offset);
		Vector2 leftBottom = unProject(0, Gdx.graphics.getHeight());
		bounds.setX(leftBottom.x - offset);
		bounds.setY(leftBottom.y - offset);
	}

	@Override
	public void render(float delta) {

		update();
		camera.update();
		screencamera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(screencamera.combined);
		batch.begin();
		// System.out.println(Gdx.graphics.getFramesPerSecond());

		if (background1) {
			batch.draw(GraphicsManager.Game.bg_4,
					(800 - viewport2.getWorldWidth()) / 2f,
					(480 - viewport2.getWorldHeight()) * 2f, 853, 533);
		} else {
			batch.draw(GraphicsManager.Game.bg2,
					(800 - viewport2.getWorldWidth()) / 2f,
					(480 - viewport2.getWorldHeight()) * 2f, 853, 533);
		}

		batch.end();

		batch.setProjectionMatrix(camera.combined);

		// renderer.setProjectionMatrix(screencamera.combined);
		// renderer.begin(ShapeType.Filled);
		// renderer.rect(0, 0, 800, 480f);
		// renderer.end();

		gameWorld.gameMap.Draw(camera);
		batch.begin();

		// gtr.Render(batch, camera);
		//

		gameWorld.Render(camera, batch);
		batch.end();

		batch.setProjectionMatrix(screencamera.combined);
		batch.begin();
		gameGUI.Draw(batch);
		dialogs.Draw(batch);
		// debugBF.draw(batch, "" + Gdx.graphics.getFramesPerSecond(), 0, 100);

		batch.end();
		
		
		if(Gdx.input.isKeyPressed(Keys.F2))
		renderer.render(gameWorld.world,camera.combined);

	}

	@Override
	public void resize(int width, int height) {
		viewport1.update(width, height);
		viewport2.update(width, height);
		gameGUI.Resize(viewport2.getWorldWidth(), viewport2.getWorldHeight());

		GameStats.WorldWidth = viewport2.getWorldWidth();
		GameStats.WorldHeight = viewport2.getWorldHeight();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void onSwipe(SwipeType type, float amount) {
		if (type == SwipeType.DOWM) {
			gameWorld.skater.Stop();
		}
		if (type == SwipeType.UP) {
			gameWorld.skater.Jump();
		}
		if (type == SwipeType.LEFT) {
			gameWorld.skater.LeftLeap();
		}
		if (type == SwipeType.RIGHT) {
			gameWorld.skater.RightLeap();
		}

	}

}
