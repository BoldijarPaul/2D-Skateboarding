package Stuff;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class B2D {

	public static final float PPM = 100f;

	public static final int JumpKey = Keys.SPACE;
	public static final int LeftKey = Keys.LEFT;
	public static final int RightKey = Keys.RIGHT;
	public static final int DownKey = Keys.DOWN;

	public static BodyDef CreateBodyDef(float x, float y, BodyType type) {
		BodyDef def = new BodyDef();
		def.position.set(x, y);
		def.type = type;
		return def;
	}

	public static PolygonShape CreateBoxShape(float width, float height) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		return shape;
	}

	public static PolygonShape CreateBoxShape(float width, float height,
			Vector2 position) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height, position, 0);
		return shape;
	}

	public static FixtureDef CreateFixtureDef(float density, float restitution,
			float friction) {
		FixtureDef def = new FixtureDef();
		def.density = density;
		def.restitution = restitution;
		def.friction = friction;
		return def;
	}

	public static FixtureDef CreateFixtureDef(float density, float restitution,
			float friction, Shape shape) {
		FixtureDef def = new FixtureDef();
		def.density = density;
		def.restitution = restitution;
		def.friction = friction;
		def.shape = shape;
		return def;
	}
}
