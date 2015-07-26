package Stuff;

import assets.GraphicsManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CoinsStarsRenderer {
	public Array<Vector2> coins = new Array<Vector2>();
	public Array<Vector2> stars = new Array<Vector2>();

	public CoinsStarsRenderer() {
		coins.clear();
		stars.clear();
	}

	public void Draw(SpriteBatch batch) {
		for (int i = 0; i < coins.size; i++)
			if (coins.get(i) != null)
				batch.draw(GraphicsManager.Game.tiles[10], coins.get(i).x,
						coins.get(i).y, 0.32f, 0.32f);

		for (int i = 0; i < stars.size; i++) {

			if (stars.get(i) != null) {

				batch.draw(GraphicsManager.Game.tiles[9], stars.get(i).x,
						stars.get(i).y, 0.32f, 0.32f);
			}
		}
	}
}
