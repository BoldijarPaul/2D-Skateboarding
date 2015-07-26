package Stuff;

import GameStuff.GameSettings;
import assets.GraphicsManager;
import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MenuWorld {

	float t = (1.0f / 60.0f);
	int v = 6;
	int p = 2;

	public World world;

	// private OrthographicCamera camera;
	public Body Box_Body, Ground_Body;
	private Body[] Bodies;
	private Sprite logo;
	public boolean Top_Added = false;
	public CoolButton[] buttons;

	public boolean Animation_Done() {

		return !Box_Body.isAwake() && Top_Added == false;
	}

	private void Add_Ground() {

		BodyDef ground_def = new BodyDef();
		ground_def.position.set(1, -.3f);
		ground_def.type = BodyType.StaticBody;
		FixtureDef ground_fixture = new FixtureDef();
		ground_fixture.density = 1;
		ground_fixture.friction = 0;
		ChainShape ground_shape = new ChainShape();
		ground_shape.createChain(new float[] { -1, 2f, 7f, 2 });
		ground_fixture.shape = ground_shape;
		Ground_Body = world.createBody(ground_def);
		Ground_Body.createFixture(ground_fixture);

	}

	private void AddTop() {
		BodyDef ground_def = new BodyDef();
		ground_def.position.set(0, 0);
		ground_def.type = BodyType.StaticBody;
		FixtureDef ground_fixture = new FixtureDef();
		ground_fixture.density = 1;
		ground_fixture.friction = 0;
		ChainShape ground_shape = new ChainShape();

		ground_shape = new ChainShape();
		ground_shape.createChain(new float[] { 8, 4.8f, 0, 4.8f });
		ground_fixture.shape = ground_shape;
		world.createBody(ground_def).createFixture(ground_fixture);

	}

	private void Add_Ground2() {
		BodyDef ground_def = new BodyDef();
		ground_def.position.set(0, 0);
		ground_def.type = BodyType.StaticBody;
		FixtureDef ground_fixture = new FixtureDef();
		ground_fixture.density = 1;
		ground_fixture.friction = 0;
		ChainShape ground_shape = new ChainShape();
		ground_shape.createChain(new float[] { 0, 0, 8, 0 });
		ground_fixture.shape = ground_shape;
		world.createBody(ground_def).createFixture(ground_fixture);

		ground_shape = new ChainShape();
		ground_shape.createChain(new float[] { 8, 0, 8f, 4.8f });
		ground_fixture.shape = ground_shape;
		world.createBody(ground_def).createFixture(ground_fixture);

		ground_shape = new ChainShape();
		ground_shape.createChain(new float[] { 0, 4.8f, 0, 0f });
		ground_fixture.shape = ground_shape;
		world.createBody(ground_def).createFixture(ground_fixture);

	}

	private void Add_Boxes() {
		Bodies = new Body[5];

		BodyDef b_def = new BodyDef();

		b_def.type = BodyType.StaticBody;
		FixtureDef b_fixture = new FixtureDef();
		b_fixture.density = 1;
		b_fixture.friction = 0;
		b_fixture.restitution = .6f;
		PolygonShape b_shape = new PolygonShape();
		b_shape.setAsBox(107 / 200f, 106 / 200f);
		b_fixture.shape = b_shape;

		float x = -92.5f;
		for (int i = 0; i <= 4; i++) {
			b_def.position.set(x / 100f + 107 / 44.75f, 30 / 100f + 106 / 200f);
			Bodies[i] = world.createBody(b_def);
			Bodies[i].createFixture(b_fixture);
			x += 20 + 107;
		}

	}

	private void Add_Body() {
		BodyDef box_def = new BodyDef();
		box_def.position.set(177 / 100f + 446 / 200f, 8);
		box_def.type = BodyType.DynamicBody;
		FixtureDef box_fixture = new FixtureDef();
		box_fixture.density = 1;
		box_fixture.friction = 0;
		box_fixture.restitution = .5f;
		PolygonShape box_shape = new PolygonShape();
		box_shape.setAsBox(446 / 200f, 283 / 200f);
		box_fixture.shape = box_shape;
		Box_Body = world.createBody(box_def);
		Box_Body.setUserData("logo");

		Box_Body.createFixture(box_fixture);
	}

	private void Add_Sprite() {
		logo = new Sprite(GraphicsManager.MainMenu.logo);
		logo.setSize(446 / 100f, 283 / 100f);
		logo.setOriginCenter();

		buttons = new CoolButton[5];
		buttons[0] = new CoolButton(GraphicsManager.MainMenu.play, 107 / 100f,
				106 / 100f);
		buttons[1] = new CoolButton(GraphicsManager.MainMenu.settings,
				107 / 100f, 106 / 100f);
		buttons[2] = new CoolButton(GraphicsManager.MainMenu.help, 107 / 100f,
				106 / 100f);
		buttons[3] = new CoolButton(GraphicsManager.MainMenu.fb, 107 / 100f,
				106 / 100f);
		buttons[4] = new CoolButton(GraphicsManager.MainMenu.quit, 107 / 100f,
				106 / 100f);

		logo.setOriginCenter();
		logo.setPosition(-100, -100);

		for (int i = 0; i <= 4; i++) {

		}
		Set_Sprite_Position();
	}

	private void Set_Sprite_Position() {

		float x = Box_Body.getPosition().x;
		float y = Box_Body.getPosition().y;
		logo.setPosition(x - 446 / 200f, y - 283 / 200f);
		logo.setRotation(Box_Body.getAngle() * MathUtils.radiansToDegrees);

		for (int i = 0; i <= 4; i++) {

			buttons[i].SetPosition(Bodies[i].getPosition().x - 107 / 200f,
					Bodies[i].getPosition().y - 106 / 200f);
			buttons[i].SetRotation(Bodies[i].getAngle());

		}

	}

	public MenuWorld(OrthographicCamera camera) {
		// this.camera = camera;
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				if (GameSettings.SoundON)
					SoundManager.menu_pop.play();
			}
		});

		Add_Ground();
		Add_Body();

		Add_Boxes();
		Add_Ground2();
		Add_Sprite();

	}

	private void EasterEgg() {
		if (Animation_Done()) {

			Box_Body.setAwake(true);
			if (Top_Added == false) {

				AddTop();
				Top_Added = true;
				for (Body b : Bodies)
					b.setType(BodyType.DynamicBody);
				world.destroyBody(Ground_Body);
			}
		}
	}

	public void Render(SpriteBatch batch) {
		for (Body b : Bodies)
			b.setAwake(true);
		float GRAVITY_EARTH = 9.81f;

		float xGrav = Gdx.input.getAccelerometerX() / GRAVITY_EARTH;
		float yGrav = Gdx.input.getAccelerometerY() / GRAVITY_EARTH;
		float zGrav = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;

		// gForce will be close to 1 when there is no movement.
		float gForce = (float) Math.sqrt((xGrav * xGrav) + (yGrav * yGrav)
				+ (zGrav * zGrav));

		if (gForce > 2.1f) {

			EasterEgg();

		}
		Set_Sprite_Position();

		if (Gdx.input.isKeyPressed(Keys.ENTER))
			EasterEgg();

		if (Top_Added)
			if (Gdx.input.getRotation() == 90)
				world.setGravity(new Vector2(3.5f * Gdx.input
						.getAccelerometerY(), -3.5f
						* Gdx.input.getAccelerometerX()));
			else {
				world.setGravity(new Vector2(-3.5f
						* Gdx.input.getAccelerometerY(), 3.5f * Gdx.input
						.getAccelerometerX()));
			}

		world.step(t, v, p);

		logo.draw(batch);

		batch.end();

		batch.begin();
		for (int i = 0; i <= 4; i++)
			buttons[i].Draw(batch);

	}

}
