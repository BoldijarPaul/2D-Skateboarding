package Stuff;

import GameStuff.GameSettings;
import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class CoolButton {
	Sprite sprite;
	private boolean isTouched = false;
	private float scale = 1f;

	public CoolButton(TextureRegion texture, float width, float height) {
		sprite = new Sprite(texture);
		sprite.setPosition(0, 0);
		sprite.setSize(width, height);
		sprite.setOriginCenter();
	}

	public void SetRegion(TextureRegion region) {
		sprite.setRegion(region);
	}

	public void SetPosition(float x, float y) {
		sprite.setPosition(x, y);
	}

	public void SetRotation(float rad) {
		sprite.setRotation(rad * MathUtils.radiansToDegrees);
	}

	public void Draw(SpriteBatch batch) {
		if (isTouched) {
			if (scale < 1.12f)
				scale += Gdx.graphics.getDeltaTime();
			else
				scale = 1.12f;

		} else {
			if (scale > 1f)
				scale -= Gdx.graphics.getDeltaTime();
			else
				scale = 1f;
		}
		sprite.setScale(scale);
		sprite.draw(batch);
	}

	public boolean TouchedDown(float x, float y) {
		boolean oldTouched = isTouched;
		isTouched = sprite.getBoundingRectangle().contains(x, y);
		if (!oldTouched && isTouched) {
			if (GameSettings.SoundON) {
				if (SoundManager.ui_down.isPlaying() == false)
					SoundManager.ui_down.play();

			}
		}
		return isTouched;
	}

	public boolean TouchedUp(float x, float y) {
		if (sprite.getBoundingRectangle().contains(x, y)) {

			isTouched = false;

			return true;
		}
		return false;
	}

}
