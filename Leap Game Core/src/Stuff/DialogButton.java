package Stuff;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class DialogButton {
	public float x, y;
	public TextureRegion[] texture;
	private int id = 0;

	
	public DialogButton(float x, float y, TextureRegion[] t) {
		this.x = x;
		this.y = y;
		texture = t;
	}

	public boolean CheckTouchDown(float x, float y) {
		if (new Rectangle(this.x, this.y, 104, 104).contains(x, y)) {
			id = 1;
			return true;
		} else {

			return false;
		}
	}

	public boolean CheckTouchUp(float x, float y) {
		if (new Rectangle(this.x, this.y, 104, 104).contains(x, y)) {
			// next
			id = 0;
			return true;
		} else {
			id = 0;
			return false;
		}
	}

	public void Draw(SpriteBatch batch) {
		batch.draw(texture[id], x, y, 104, 104);
	}
}
