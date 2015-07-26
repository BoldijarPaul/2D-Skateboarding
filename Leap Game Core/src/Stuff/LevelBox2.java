package Stuff;

import assets.GraphicsManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelBox2 {

	float x, y;
	int locked;
	int stars;
	public int level;
	public CoolButton button;

	// 0 = locked
	// 1 = unlocked

	public boolean TouchedDown(float x, float y) {
		return button.TouchedDown(x, y);
	}

	public boolean TouchedUp(float x, float y) {
		return button.TouchedUp(x, y);
	}

	public LevelBox2(float x, float y, boolean locked, int stars, int level) {
		this.x = x;
		this.y = y;
		this.locked = (locked == true) ? 0 : 1;
		this.stars = stars;
		this.level = level;
		if (locked)
			button = new CoolButton(GraphicsManager.LevelMenu2.box[4], 99, 120);
		else
			button = new CoolButton(GraphicsManager.LevelMenu2.box[stars], 99,
					120);
		button.SetPosition(x, y);
	}

	public void Draw(SpriteBatch batch) {

		button.Draw(batch);
		if (locked == 1) {

			int width = GraphicsManager.LevelMenu2.NumberWidth(level);

			GraphicsManager.LevelMenu2.DrawNumber(level, batch, x + 99 / 2
					- width / 2f, y + 50);

		}
	}

	public boolean Touched(Vector3 vec) {
		if (locked == 0)
			return false;
		return new Rectangle(x, y, 120, 120).contains(vec.x, vec.y);
	}
}
