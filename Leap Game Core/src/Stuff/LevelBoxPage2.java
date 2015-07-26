package Stuff;

import screens.LevelMenuScreen2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class LevelBoxPage2 {

	public Array<LevelBox2> levelBoxes;

	public LevelBoxPage2(Array<LevelBox2> lvb) {

		levelBoxes = new Array<LevelBox2>();

		for (int i = 0; i < lvb.size; i++) {

			lvb.get(i).button.SetPosition(LevelMenuScreen2.positions[i].x,
					LevelMenuScreen2.positions[i].y);
			lvb.get(i).x = LevelMenuScreen2.positions[i].x;
			lvb.get(i).y = LevelMenuScreen2.positions[i].y;
			levelBoxes.add(lvb.get(i));

		}
	}

	public void TouchedDown(float x, float y) {
		for (LevelBox2 b : levelBoxes) {
			b.TouchedDown(x, y);
		}
	}

	public int TouchedUpLevel(float x, float y) {
		for (LevelBox2 b : levelBoxes) {
			if (b.TouchedUp(x, y))
				return b.level;
		}
		return -1;
	}

	public void Draw(SpriteBatch batch) {

		for (int i = 0; i < levelBoxes.size; i++) {

			levelBoxes.get(i).Draw(batch);
		}
	}
}
