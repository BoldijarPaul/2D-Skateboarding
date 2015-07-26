import swipe.SwipeDetection;
import swipe.SwipeListener;
import swipe.SwipeType;
import Stuff.AndroidHandler;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fainosag.marioskate.MainClass;
import com.leapmotion.leap.Controller;

public class GameMain implements AndroidHandler {
	private static GameMain gameMain;

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 480;
		config.width = 800;
		
		
		config.height = 720;
		config.width = 1200;
		if (gameMain == null)
			gameMain = new GameMain();
		MainClass mainClass = new MainClass(gameMain);
 
		new LwjglApplication(mainClass, config);

		Controller controller = new Controller();
		LeapListener leapListener = new LeapListener();
		controller.addListener(leapListener);
		leapListener.setListener(mainClass);

		while (true) {

		}
	}

	@Override
	public void ShowAds(boolean show) {
		// TODO Auto-generated method stub

	}

	@Override
	public void LoadInterestial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowInterestial() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowInfoDialog(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowInfoDialog(String text, String title) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowBuyConfirmation(String text, int nr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowFBDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowRateDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowBuyDialog() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ShowExitDialog() {
		// TODO Auto-generated method stub

	}
}
