package GameStuff;

import java.util.Arrays;
import java.util.Iterator;

import Stuff.B2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class GameMap {

	private ShapeRenderer sr;
	public static Vector2 flagposition;
	public OrthogonalTiledMapRenderer renderer;

	public GameMap() {

		// tiles = new Array<Vector2>();

		sr = new ShapeRenderer();
		sr.setColor(Color.MAGENTA);
		LoadMap();
	}

	private void LoadMap() {

	}

	public void LoadTilesWithoutBody(TiledMap tileMap, World world) {

		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(
				"Empty");

		float tileSize = layer.getTileHeight();

		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);

				if (cell == null || cell.getTile() == null)
					continue;

				// create

				float x = col * tileSize / B2D.PPM;
				float y = row * tileSize / B2D.PPM;

				int tileType = cell.getTile().getId();
				tileType--;

				if (tileType == 15) {
					GameStats.GameSpawnPosition = new Vector2(x, y);
					Cell c = new Cell();
					layer.setCell(col, row, c);
				}

				if (tileType <= 14) {
					// e element normal
					int id = tileType;
					GameTileRenderer.tlz[col][row] = id;

				}
			}
		}
	}

	public ChainShape createShape(float tileSize) {
		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[5];
		v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		v[1] = new Vector2(-tileSize / 2 / B2D.PPM, tileSize / 2 / B2D.PPM);
		v[2] = new Vector2(tileSize / 2 / B2D.PPM, tileSize / 2 / B2D.PPM);
		v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		shape.createChain(v);
		return shape;
	}

	public ChainShape createSpikeShape(float tileSize) {
		ChainShape shape = new ChainShape();
		Vector2[] v = new Vector2[5];
		v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		v[1] = new Vector2(-tileSize / 2 / B2D.PPM, 0 / B2D.PPM);
		v[2] = new Vector2(tileSize / 2 / B2D.PPM, 0 / B2D.PPM);
		v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2 / B2D.PPM);
		shape.createChain(v);
		return shape;
	}

	public void LoadGameTiles(TiledMap tileMap, World world) {
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(
				"Tiles");
		BodyDef def = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		def.type = BodyType.StaticBody;
		FixtureDef sensorDef = new FixtureDef();
		sensorDef.isSensor = true;

		int coinID = 0;
		int starID = 0;
		float tileSize = layer.getTileHeight();

		GameTileRenderer.width = layer.getWidth();
		GameTileRenderer.height = layer.getHeight();
		GameTileRenderer.tlz = new int[GameTileRenderer.width][GameTileRenderer.height];
		for (int[] row : GameTileRenderer.tlz)
			Arrays.fill(row, -1);

		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);
				if (cell == null || cell.getTile() == null)
					continue;

				// create

				def.position.set((col + 0.5f) * tileSize / B2D.PPM,
						(row + 0.5f) * tileSize / B2D.PPM);
				float x = col * tileSize / B2D.PPM;
				float y = row * tileSize / B2D.PPM;

				int tileType = cell.getTile().getId();
				tileType--;

				if (tileType == 11) {
					GameStats.GameSpawnPosition = new Vector2(x, y);
					Cell c = new Cell();
					layer.setCell(col, row, c);
				}
				if (tileType == 12) {
					Cell c = new Cell();
					layer.setCell(col, row, c);
					GameMap.flagposition = new Vector2(x, y);
					def.position.set((col + 0.5f) * tileSize / B2D.PPM,
							(row + 0.5f) * tileSize / B2D.PPM);
					sensorDef.shape = createShape(tileSize);
					world.createBody(def).createFixture(sensorDef)
							.setUserData("finish");
				}
				if (tileType == 8) {
					// e spike
					int id = tileType;

					GameTileRenderer.tlz[col][row] = id;
					fdef.friction = 0;
					fdef.shape = createSpikeShape(tileSize);
					world.createBody(def).createFixture(fdef)
							.setUserData("die");

				}
				if (tileType == 0 || tileType == 4) {
					// e element normal
					int id = tileType;

					GameTileRenderer.tlz[col][row] = id;
					fdef.friction = 0;
					fdef.shape = createShape(tileSize);
					world.createBody(def).createFixture(fdef)
							.setUserData("tile");

				}
				if ((tileType >= 1 && tileType <= 3)
						|| (tileType >= 5 && tileType <= 7)) {
					// e bara
					int id = tileType;

					GameTileRenderer.tlz[col][row] = id;
					fdef.friction = 0;
					fdef.shape = createShape(tileSize);
					world.createBody(def).createFixture(fdef)
							.setUserData("rail");

				}
				if (tileType == 10) {
					// coin
					Cell c = new Cell();
					layer.setCell(col, row, c);
					if (GameStats.AddTheCoin(coinID)) {

						GameWorld.cr.coins.add(new Vector2(x, y));

						def.position.set((col + 0.5f) * tileSize / B2D.PPM,
								(row + 0.5f) * tileSize / B2D.PPM);

						sensorDef.shape = createShape(tileSize);

						TileData tmp = new TileData(coinID, "coin");
						coinID++;
						world.createBody(def).createFixture(sensorDef)
								.setUserData(tmp);

					} else {
						GameWorld.cr.coins.add(null);
						coinID++;
					}

				}
				if (tileType == 9) {
					// coin

					Cell c = new Cell();
					layer.setCell(col, row, c);
					GameWorld.cr.stars.add(new Vector2(x, y));

					def.position.set((col + 0.5f) * tileSize / B2D.PPM,
							(row + 0.5f) * tileSize / B2D.PPM);

					sensorDef.shape = createShape(tileSize);

					TileData tmp = new TileData(starID, "star");
					starID++;
					world.createBody(def).createFixture(sensorDef)
							.setUserData(tmp);

				}
			}
		}
	}

	public void LoadTilesWithBody(TiledMap tileMap, World world) {
		TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get(
				"Body");
		BodyDef def = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		def.type = BodyType.StaticBody;
		FixtureDef sensorDef = new FixtureDef();
		sensorDef.isSensor = true;

		int coinID = 0;
		int starID = 0;
		float tileSize = layer.getTileHeight();

		GameTileRenderer.width = layer.getWidth();
		GameTileRenderer.height = layer.getHeight();
		GameTileRenderer.tlz = new int[GameTileRenderer.width][GameTileRenderer.height];
		for (int[] row : GameTileRenderer.tlz)
			Arrays.fill(row, -1);

		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				Cell cell = layer.getCell(col, row);
				if (cell == null || cell.getTile() == null)
					continue;

				// create

				def.position.set((col + 0.5f) * tileSize / B2D.PPM,
						(row + 0.5f) * tileSize / B2D.PPM);
				float x = col * tileSize / B2D.PPM;
				float y = row * tileSize / B2D.PPM;

				int tileType = cell.getTile().getId();
				tileType--;
				if (tileType <= 14) {
					// e element normal
					int id = tileType;

					GameTileRenderer.tlz[col][row] = id;
					ChainShape shape = new ChainShape();
					Vector2[] v = new Vector2[5];
					if (tileType < 14) {
						v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[1] = new Vector2(-tileSize / 2 / B2D.PPM, tileSize
								/ 2 / B2D.PPM);
						v[2] = new Vector2(tileSize / 2 / B2D.PPM, tileSize / 2
								/ B2D.PPM);
						v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
					} else {
						v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[1] = new Vector2(-tileSize / 2 / B2D.PPM, 0 / B2D.PPM);
						v[2] = new Vector2(tileSize / 2 / B2D.PPM, 0 / B2D.PPM);
						v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
					}
					shape.createChain(v);
					fdef.friction = 0;
					fdef.shape = shape;
					if (tileType < 14)
						world.createBody(def).createFixture(fdef)
								.setUserData("tile");
					else
						world.createBody(def).createFixture(fdef)
								.setUserData("die");

				}

				if (tileType == 16) {
					// coin
					Cell c = new Cell();
					layer.setCell(col, row, c);
					if (GameStats.AddTheCoin(coinID)) {

						GameWorld.cr.coins.add(new Vector2(x, y));

						def.position.set((col + 0.5f) * tileSize / B2D.PPM,
								(row + 0.5f) * tileSize / B2D.PPM);

						ChainShape shape = new ChainShape();
						Vector2[] v = new Vector2[5];
						v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[1] = new Vector2(-tileSize / 2 / B2D.PPM, tileSize
								/ 2 / B2D.PPM);
						v[2] = new Vector2(tileSize / 2 / B2D.PPM, tileSize / 2
								/ B2D.PPM);
						v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize
								/ 2 / B2D.PPM);
						shape.createChain(v);

						sensorDef.shape = shape;

						TileData tmp = new TileData(coinID, "coin");
						coinID++;
						world.createBody(def).createFixture(sensorDef)
								.setUserData(tmp);
					} else {
						GameWorld.cr.coins.add(null);
						coinID++;
					}

				}
				if (tileType == 17) {
					Cell c = new Cell();
					layer.setCell(col, row, c);
					GameWorld.cr.stars.add(new Vector2(x, y));

					def.position.set((col + 0.5f) * tileSize / B2D.PPM,
							(row + 0.5f) * tileSize / B2D.PPM);

					ChainShape shape = new ChainShape();
					Vector2[] v = new Vector2[5];
					v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					v[1] = new Vector2(-tileSize / 2 / B2D.PPM, tileSize / 2
							/ B2D.PPM);
					v[2] = new Vector2(tileSize / 2 / B2D.PPM, tileSize / 2
							/ B2D.PPM);
					v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					shape.createChain(v);

					sensorDef.shape = shape;

					TileData tmp = new TileData(starID, "star");
					starID++;
					world.createBody(def).createFixture(sensorDef)
							.setUserData(tmp);

				}
				if (tileType == 18) {
					Cell c = new Cell();
					layer.setCell(col, row, c);
					GameMap.flagposition = new Vector2(x, y);
					def.position.set((col + 0.5f) * tileSize / B2D.PPM,
							(row + 0.5f) * tileSize / B2D.PPM);

					ChainShape shape = new ChainShape();
					Vector2[] v = new Vector2[5];
					v[0] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					v[1] = new Vector2(-tileSize / 2 / B2D.PPM, tileSize / 2
							/ B2D.PPM);
					v[2] = new Vector2(tileSize / 2 / B2D.PPM, tileSize / 2
							/ B2D.PPM);
					v[3] = new Vector2(tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					v[4] = new Vector2(-tileSize / 2 / B2D.PPM, -tileSize / 2
							/ B2D.PPM);
					shape.createChain(v);

					sensorDef.shape = shape;

					world.createBody(def).createFixture(sensorDef)
							.setUserData("finish");

				}
				// tiles.add(new Vector2(x, y));

			}
		}
	}

	public void LoadNewMethod(World world) {
		// load boxr

		Parameters params = new Parameters();
		params.textureMinFilter = TextureFilter.Linear;
		params.textureMagFilter = TextureFilter.Linear;

		TiledMap tileMap = new TmxMapLoader().load("Maps/level"
				+ GameStats.Level + ".tmx");

		for (TiledMapTileSet tileSet : tileMap.getTileSets()) {
			Iterator<TiledMapTile> it = tileSet.iterator();
			while (it.hasNext()) {

				it.next().getTextureRegion().getTexture()
						.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
		}
		// LoadTilesWithBody(tileMap, world);
		// LoadTilesWithoutBody(tileMap, world);
		LoadGameTiles(tileMap, world);
		renderer = new OrthogonalTiledMapRenderer(tileMap, 1 / 100f);

		// tileMap.dispose();

	}

	public void Draw(OrthographicCamera camera) {
		renderer.setView(camera);
		// renderer.render();
	}

	public void AddTiles(World world) {

		LoadNewMethod(world);
		/*
		 * AddTilesLayerToWorld("Tile1", world); AddTilesLayerToWorld("Tile2",
		 * world); LoadSpawnPosition(); LoadCoins(world); LoadStars(world);
		 * LoadEndPosition();
		 */
	}

}
