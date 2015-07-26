package Stuff;

import GameStuff.GameSettings;
import GameStuff.GameStats;
import assets.GraphicsManager;
import assets.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameDialog {

	private final float DialogWidth = 514, DialogHeight = 391;
	public float x, y;
	public float normalY = 9;
	public boolean showing = false;

	public CoolButton button1, button2, button3;

	private float buttonY = -50f;
	private float b1x = 200, b2x = 350, b3x = 500;

	private float textX = 319, textY = 0, textY2 = 320;
	private float logoX = 295, logoY = 0, logoY2 = 70;

	private float star2x = 352, star1x = 352 - 130, star3x = 352 + 130,
			starY = 0, starY2 = 90;
	int Tip = 0;

	private void CalculateLocations() {
		button1.SetPosition(b1x, y + buttonY);
		button2.SetPosition(b2x, y + buttonY);
		button3.SetPosition(b3x, y + buttonY);
		textY = y + textY2;
		logoY = y + logoY2;
		starY = y + starY2;
	}

	public boolean MenuTouch(float x, float y) {
		return button1.TouchedUp(x, y);
	}

	public boolean NextTouch(float x, float y) {
		return button3.TouchedUp(x, y);
	}

	public boolean PlayTouch(float x, float y) {
		if (Tip == 1 || Tip == 2)
			return button2.TouchedUp(x, y);
		else
			return false;
	}

	public boolean RetryTouch(float x, float y) {
		if (Tip == 3)
			return button2.TouchedUp(x, y);
		else
			return button3.TouchedUp(x, y);
	}

	public GameDialog(String tip) {
		if (tip.equals("pauza")) {
			Tip = 1;
			button1 = new CoolButton(GraphicsManager.Game.dialog_menu, 121, 119);
			button2 = new CoolButton(GraphicsManager.Game.dialog_play, 121, 119);
			button3 = new CoolButton(GraphicsManager.Game.dialog_retry, 121,
					119);
		}
		if (tip.equals("fail")) {
			Tip = 2;
			button1 = new CoolButton(GraphicsManager.Game.dialog_menu, 121, 119);
			button2 = new CoolButton(GraphicsManager.Game.dialog_play, 121, 119);
			button3 = new CoolButton(GraphicsManager.Game.dialog_retry, 121,
					119);
		}
		if (tip.equals("end")) {
			Tip = 3;
			button1 = new CoolButton(GraphicsManager.Game.dialog_menu, 121, 119);
			button2 = new CoolButton(GraphicsManager.Game.dialog_retry, 121,
					119);
			button3 = new CoolButton(GraphicsManager.Game.dialog_next, 121, 119);
		}
		CalculateLocations();
	}

	public void Show() {
		x = 143;
		y = 480;
		showing = true;
		if (Tip == 2)
			if (GameSettings.SoundON)
				SoundManager.game_fail.play();
		if (Tip == 3)
			if (GameSettings.SoundON)
				SoundManager.game_done.play();

	}

	public void TouchDown(float x, float y) {
		button1.TouchedDown(x, y);
		button2.TouchedDown(x, y);
		button3.TouchedDown(x, y);
	}

	public void TouchUp(float x, float y) {
		button1.TouchedUp(x, y);
		button2.TouchedUp(x, y);
		button3.TouchedUp(x, y);
	}

	public void Hide() {
		showing = false;
	}

	public void Draw(SpriteBatch batch) {

		normalY = GameStats.WorldHeight - 391;

		if (!showing)
			return;
		CalculateLocations();
		batch.draw(GraphicsManager.Game.dialog_dialog, x, y, DialogWidth,
				DialogHeight);
		button1.Draw(batch);
		button2.Draw(batch);
		button3.Draw(batch);
		TextureRegion region = new TextureRegion();
		if (Tip == 1)
			region = GraphicsManager.Game.dialog_paused;
		if (Tip == 2)
			region = GraphicsManager.Game.dialog_fail;
		if (Tip == 3)
			region = GraphicsManager.Game.dialog_congrats;

		if (Tip == 1)
			batch.draw(GraphicsManager.Game.dialog_cofee, logoX, logoY, 210,
					183f);
		if (Tip == 2)
			batch.draw(GraphicsManager.Game.dialog_skull, logoX, logoY, 210,
					183f);
		batch.draw(region, textX, textY, 167.4f, 40.5f);

		if (Tip == 3) {
			int stars = GameStats.Stars;
			if (stars == 0) {
				batch.draw(GraphicsManager.Game.dialog_star0, star1x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star0, star2x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star0, star3x, starY,
						116, 119);
			}
			if (stars == 1) {
				batch.draw(GraphicsManager.Game.dialog_star1, star1x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star0, star2x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star0, star3x, starY,
						116, 119);
			}
			if (stars == 2) {
				batch.draw(GraphicsManager.Game.dialog_star1, star1x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star1, star2x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star0, star3x, starY,
						116, 119);
			}
			if (stars == 3) {
				batch.draw(GraphicsManager.Game.dialog_star1, star1x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star1, star2x, starY,
						116, 119);
				batch.draw(GraphicsManager.Game.dialog_star1, star3x, starY,
						116, 119);
			}
		}

		if (y > normalY)
			y -= Gdx.graphics.getDeltaTime() * 420;
		else
			y = normalY;

	}
}
