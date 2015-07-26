package GameStuff;

import Stuff.GameDialog;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDialogs {
	// public GameFinishDialog gameFinishDialog;
	// public GamePauseDialog gamePauseDialog;
	// public GameFailDialog gameFailDialog;

	public GameDialog gameFinishDialog;
	public GameDialog gamePauseDialog;
	public GameDialog gameFailDialog;

	public GameDialogs() {
		// gameFinishDialog = new GameFinishDialog();
		// gamePauseDialog = new GamePauseDialog();
		// gameFailDialog = new GameFailDialog();
		// gameFinishDialog.Show();
		gamePauseDialog = new GameDialog("pauza");
		gameFinishDialog = new GameDialog("end");
		gameFailDialog = new GameDialog("fail");

	}

	public void Update() {
		// if (GameStats.GameState == 3)
		// gameFinishDialog.Update();
		// if (GameStats.GameState == 2)
		// gameFailDialog.Update();
		// if (GameStats.GameState == 1)
		// gamePauseDialog.Update();
	}

	public void TouchDown(float x, float y) {
		gameFinishDialog.TouchDown(x, y);
		gamePauseDialog.TouchDown(x, y);
		gameFailDialog.TouchDown(x, y);
	}

	public void TouchUp(float x, float y) {
		gameFinishDialog.TouchUp(x, y);
		gamePauseDialog.TouchUp(x, y);
		gameFailDialog.TouchUp(x, y);
	}

	public void Draw(SpriteBatch batch) {
		if (GameStats.GameState == 3)
			gameFinishDialog.Draw(batch);
		if (GameStats.GameState == 2)
			gameFailDialog.Draw(batch);
		if (GameStats.GameState == 1)
			gamePauseDialog.Draw(batch);
	}
}
