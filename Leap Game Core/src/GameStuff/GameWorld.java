package GameStuff;

import Stuff.B2DContactListener;
import Stuff.CoinsStarsRenderer;
import Stuff.GamePrefs;
import Stuff.Skater;
import Stuff.SkaterRenderer;
import assets.GraphicsManager;
import assets.SoundManager;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameWorld {

	public World world;

	// private OrthographicCamera camera;

	public GameMap gameMap;

	public B2DContactListener contactlistener;

	public Skater skater;
	public static SkaterRenderer skr;
	public static CoinsStarsRenderer cr;

	public GameWorld(OrthographicCamera camera) {
		GameStats.Reset();
		// this.camera = camera;

		world = new World(new Vector2(0, -9.81f), true);

		contactlistener = new B2DContactListener();

		world.setContactListener(contactlistener);

		gameMap = new GameMap();
		cr = new CoinsStarsRenderer();
		gameMap.AddTiles(world);
		skater = new Skater(world);
		skr = new SkaterRenderer(20 / 50f, 34 / 50f);

	}

	public void AndroidInput() {
		float acc;
		if (Gdx.graphics.getWidth() > Gdx.graphics.getHeight())
			acc = Gdx.input.getAccelerometerY();
		else
			acc = -Gdx.input.getAccelerometerX();
		float bodyY = skater.playerBody.getLinearVelocity().y;
		if (Math.abs(acc) <= .5f) {
			skater.playerBody.setLinearVelocity(0, bodyY);
		} else {
			skater.playerBody.setLinearVelocity(acc, bodyY);
		}
	}

	public void DesktopInput() {
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			skater.playerBody.setLinearVelocity(new Vector2(2.5f,
					skater.playerBody.getLinearVelocity().y));
		else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			skater.playerBody.setLinearVelocity(new Vector2(-2.5f,
					skater.playerBody.getLinearVelocity().y));

		} else
			skater.playerBody.setLinearVelocity(new Vector2(0,
					skater.playerBody.getLinearVelocity().y));
	}

	public void Update() {

		skater.Update();
		world.step(1 / 60f, 6, 2);
		if (contactlistener.headhit) {
			contactlistener.headhit = false;
			skr.SetIdle();
		}
		if (Gdx.app.getType() == ApplicationType.Android)
			AndroidInput();
		if (Gdx.app.getType() == ApplicationType.Desktop)
			DesktopInput();

		if (GameStats.NeedToDeleteBody) {
			GameStats.NeedToDeleteBody = false;

			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (int i = 0; i < bodies.size; i++) {
				Object obj = bodies.get(i).getUserData();

				if (!(obj instanceof TileData)) {

					continue;
				}

				TileData tmp = (TileData) (bodies.get(i).getUserData());

				if (tmp.destroyed)
					if (tmp.tip.equals("star") || tmp.tip.equals("coin")) {
						if (tmp.tip.equals("coin")) {
							cr.coins.set(tmp.id, null);
							GameStats.GotCoin(tmp.id);

							GameStats.Coins++;
							if (GameSettings.SoundON)
								SoundManager.game_coin.play();

							if (GameSettings.FULL)
								GameStats.Coins++;

						}
						if (tmp.tip.equals("star")) {
							cr.stars.set(tmp.id, null);
							GameStats.Stars++;
							if (GameSettings.SoundON)
								SoundManager.game_star.play();
						}
						world.destroyBody(bodies.get(i--));
					}

			}
		}

	}

	private Rectangle viewBounds = new Rectangle();

	public void Render(OrthographicCamera camera, SpriteBatch batch) {

		if (Gdx.input.isKeyPressed(Keys.Q))
			camera.rotate(0.5f);
		if (Gdx.input.isKeyPressed(Keys.W))
			camera.rotate(-0.5f);
		camera.position.set(skater.playerBody.getPosition().x,
				skater.playerBody.getPosition().y, 0);

		batch.draw(GraphicsManager.Game.flag, GameMap.flagposition.x,
				GameMap.flagposition.y, 23.1f / 100f, 32 / 100f);

		cr.Draw(batch);

		final int layerWidth = GameTileRenderer.width;
		final int layerHeight = GameTileRenderer.height;

		float width = camera.viewportWidth * camera.zoom;
		float height = camera.viewportHeight * camera.zoom;

		viewBounds.set(camera.position.x - width / 2, camera.position.y
				- height / 2, width, height);

		int col1 = (int) (viewBounds.x / .32) - 1;
		int col2 = (int) ((viewBounds.x + viewBounds.width) / .32) + 1;
		int row1 = (int) (viewBounds.y / .32) - 1;
		int row2 = (int) ((viewBounds.y + viewBounds.height) / .32) + 1;

		col1 = Math.max(0, col1);
		row1 = Math.max(0, row1);
		col2 = Math.min(layerWidth - 1, col2);
		row2 = Math.min(layerHeight - 1, row2);

		for (int row = row2; row >= row1; row--) {

			for (int col = col1; col <= col2; col++) {
				int id = GameTileRenderer.tlz[col][row];

				if (id != -1) {
					batch.draw(GraphicsManager.Game.tiles[id], col * .32f,
							row * .32f, 0.32f, 0.32f);

				}
			}
		}
		skr.Draw(batch, skater.hatBody.getPosition());

		// batch.end();
		// renderer.render(world, camera.combined);
		// batch.begin();

	}

	public void dispose() {
		// TODO Auto-generated method stub

		world.dispose();
	}

}
