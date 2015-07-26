import swipe.SwipeDetection;
import swipe.SwipeListener;
import swipe.SwipeType;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;

public class LeapMainTest {

	public static SwipeDetection detection;

	public static void main(String[] args) {
		Controller controller = new Controller();
		LeapListener leapListener = new LeapListener();
		detection = new SwipeDetection(new SwipeListener() {

			@Override
			public void onSwipe(SwipeType type, float amount) {
				System.out.println(type.toString() + " " + amount);

			}
		});
		controller.addListener(leapListener);
		//
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					detection.update();
				}

			}
		});
		thread.start();

		while (true) {
			// detection.update();
		}
	}

}

class LeapListener extends Listener {

	private SwipeListener listener;

	public void setListener(SwipeListener listener) {
		this.listener = listener;
	}

	// private SwipeDetection swipe = new SwipeDetection(this);
	//
	// public SwipeDetection getSwipe() {
	// return swipe;
	// }

	@Override
	public void onInit(Controller arg0) {
		// TODO Auto-generated method stub
		System.out.println("initialized");
	}

	@Override
	public void onConnect(Controller arg0) {
		// TODO Auto-generated method stub
		System.out.println("conected");
		arg0.enableGesture(Type.TYPE_CIRCLE);
		arg0.enableGesture(Type.TYPE_KEY_TAP);
		arg0.enableGesture(Type.TYPE_SWIPE);
		arg0.enableGesture(Type.TYPE_SCREEN_TAP);
	}

	int lastIndex = -1;

	private long minTime = 100;// minimum time between 2 different swipes
	private long lastTime = 0;

	@Override
	public void onFrame(Controller arg0) {
		super.onFrame(arg0);
		// TODO Auto-generated method stub
		// long time = System.currentTimeMillis();
		Frame frame = arg0.frame();
		GestureList list = frame.gestures();
		for (Gesture gesture : list) {
			// boolean isHorizontal = Math.abs(gesture.G) >
			// Math.abs(gesture.direction[1]);

			if (gesture.id() != lastIndex)
				if (gesture.state() == State.STATE_START) {
					if (gesture.type() == Type.TYPE_SWIPE) {
						lastIndex = gesture.id();

						if (Math.abs(lastTime - System.currentTimeMillis()) > minTime) {
							SwipeType swipeType = getSwipeType(gesture);
							if (listener != null) {
								listener.onSwipe(swipeType, 1f);
							}
						}
						lastTime = System.currentTimeMillis();

					}

				}
		}

	}

	private SwipeType getSwipeType(Gesture gesture) {
		SwipeGesture swipeGesture = new SwipeGesture(gesture);

		boolean isHorizontal = Math.abs(swipeGesture.direction().get(0)) > Math
				.abs(swipeGesture.direction().get(1));
		SwipeType swipeType = SwipeType.DOWM;
		if (isHorizontal) {
			if (swipeGesture.direction().get(0) > 0) {
				swipeType = SwipeType.RIGHT;
			} else {
				swipeType = SwipeType.LEFT;
			}
		} else { // vertical
			if (swipeGesture.direction().get(1) > 0) {
				swipeType = SwipeType.UP;
			} else {
				swipeType = SwipeType.DOWM;
			}
		}
		return swipeType;
	}

	@Override
	public void onDisconnect(Controller arg0) {
		System.out.println("On dissconect");
	}

	@Override
	public void onExit(Controller arg0) {
		System.out.println("On exited");

	}

}