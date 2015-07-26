package Stuff;

import static Stuff.B2D.PPM;
import GameStuff.GameSettings;
import GameStuff.GameStats;
import GameStuff.GameWorld;

import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

public class Skater {
	public Body playerBody;

	public static float Speed = 0f;

	private float MaxSpeed = 5f, MinSpeed = -5f;
	private float StepSpeed = 1;
	private final float GroundDrag = 4f;
	private final float RailDrag = 12f;
	private final float AirDrag = 18f;

	private PrismaticJoint joint;
	public Body hatBody;

	public void AddHat(World world) {

		BodyDef hatbodyDef = new BodyDef();
		hatbodyDef.type = BodyType.DynamicBody;
		hatbodyDef.position.set(playerBody.getPosition().x,
				playerBody.getPosition().y + 100 / 100f);

		hatBody = world.createBody(hatbodyDef);
		FixtureDef def = B2D.CreateFixtureDef(.01f,
				0.3f * (1 - GameSettings.Stats4), 0);
		System.out.println("Restitution: " + def.restitution);
		def.shape = B2D.CreateBoxShape(10 / PPM, 10 / PPM, new Vector2(0, -22
				/ PPM));
		hatBody.createFixture(def);

		PrismaticJointDef pdef = new PrismaticJointDef();
		pdef.bodyA = playerBody;
		pdef.bodyB = hatBody;
		pdef.collideConnected = true;

		pdef.localAnchorA.y += 50 / 100f;
		pdef.localAxisA.set(0, 1);
		pdef.enableLimit = true;
		joint = (PrismaticJoint) world.createJoint(pdef);
		joint.setLimits(0, .15f);

	}

	public void Jump() {
		if (GameStats.OnGround() || GameStats.OnRail()) {
			if (GameSettings.SoundON)
				SoundManager.game_ollie.play();
			playerBody.applyForceToCenter(new Vector2(0,
					45 + 10 * GameSettings.Stats2), true);
			GameWorld.skr.SetOllie();
		}

	}

	public Skater(World world) {
		Skater.Speed = 0f;
		CreatePlayer(world);
		AddHat(world);
		System.out.println("new skater");
		playerBody.setLinearVelocity(0, 0);

	}

	public void Right() {
		if (GameStats.OnGround()) {
			if (Speed < MaxSpeed)
				Speed += StepSpeed;
			GameWorld.skr.SetTalpa(true);
		}
	}

	public void RightLeap() {
		if (GameStats.OnGround()) {
			if (Speed < MaxSpeed)
				Speed += StepSpeed * 1.5f;
			GameWorld.skr.SetTalpa(true);
		}
	}

	public void Stop() {
		if (GameStats.OnGround()) {

			float stopSpeed = 0.8f - (0.8f * GameSettings.Stats3);
			System.out.println(stopSpeed);
			if (Speed > 0) {
				if (Speed > stopSpeed)
					Speed = stopSpeed;
			} else {
				if (Speed < -stopSpeed)
					Speed = -stopSpeed;
			}
		}

	}

	public void Left() {
		if (GameStats.OnGround()) {
			if (Speed > MinSpeed)
				Speed -= StepSpeed;
			GameWorld.skr.SetTalpa(false);
		}
	}

	public void LeftLeap() {
		if (GameStats.OnGround()) {
			if (Speed > MinSpeed)
				Speed -= StepSpeed * 1.5f;
			GameWorld.skr.SetTalpa(false);
		}
	}

	public void SetPositionz(float x, float y) {
		Speed = 0f;
		playerBody.setTransform(x, y, 0);
	}

	public void Restart() {
		Speed = 0f;

		playerBody.setTransform(GameStats.GameSpawnPosition.x,
				GameStats.GameSpawnPosition.y, 0);
		playerBody.setLinearVelocity(0, 0);
	}

	public void Update() {

		playerBody.setLinearVelocity(Speed, playerBody.getLinearVelocity().y);
		playerBody.getFixtureList().get(0)
				.setRestitution(0.3f * (1 - GameSettings.Stats4));
		MaxSpeed = 5 + 5 * GameSettings.Stats1;
		MinSpeed = -MaxSpeed;

		float drag = 0;
		if (GameStats.OnGround())
			drag = GroundDrag;
		else if (GameStats.OnRail())
			drag = RailDrag;
		else
			drag = AirDrag;
		if (Speed > 0)
			Speed -= Gdx.graphics.getDeltaTime() / drag;
		if (Speed < 0)
			Speed += Gdx.graphics.getDeltaTime() / drag;

		GameWorld.skr.Update(playerBody.getPosition().x - 20 / 100f,
				playerBody.getPosition().y - 34 / 100f);

	}

	private void CreatePlayer(World world) {

		BodyDef playerDef = B2D.CreateBodyDef(GameStats.GameSpawnPosition.x,
				GameStats.GameSpawnPosition.y, BodyType.DynamicBody);
		playerDef.fixedRotation = true;
		playerBody = world.createBody(playerDef);
		PolygonShape playerShape = B2D.CreateBoxShape(13 / PPM, 29 / PPM);
		FixtureDef playerFD = B2D.CreateFixtureDef(1, 0.3f, 1, playerShape);
		playerBody.createFixture(playerFD).setUserData("skater");

		// sensor
		FixtureDef sensorDef = new FixtureDef();
		Shape shape = B2D.CreateBoxShape(10 / PPM, 10 / PPM, new Vector2(0, -22
				/ PPM));

		sensorDef.shape = shape;
		sensorDef.isSensor = true;
		playerBody.createFixture(sensorDef).setUserData("foot");

		// cap
		shape = B2D.CreateBoxShape(12 / PPM, 5 / PPM, new Vector2(0, 25 / PPM));

		sensorDef.shape = shape;
		sensorDef.isSensor = true;
		playerBody.createFixture(sensorDef).setUserData("cap");
	}

}
