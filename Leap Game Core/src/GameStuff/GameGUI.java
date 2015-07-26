package GameStuff;

import assets.GraphicsManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameGUI {

	float pauseX, pauseY, pauseWidth = 25, pauseHeight = 36;

	public GameGUI(float w, float h) {
		Resize(w, h);
	}

	private Rectangle tmpRect = new Rectangle();

	public void Resize(float width, float height) {

		pauseX = width - (width - 800) / 2f - 60f;
		pauseY = height - (height - 480) / 2f - 60f;

	}

	public boolean PauseTouched(float x, float y) {
		tmpRect.set(pauseX, pauseY, pauseWidth + 15, pauseHeight + 15);
		return tmpRect.contains(x, y);
	}

	public void Draw(SpriteBatch batch) {

		if (GameStats.GameState == 0)
			batch.draw(GraphicsManager.Game.pause, pauseX, pauseY, pauseWidth,
					pauseHeight);
	}

}
