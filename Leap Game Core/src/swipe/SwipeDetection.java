package swipe;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.leapmotion.leap.Vector;

public class SwipeDetection {

	private static final int MAX_POINTS = 100;

	private static final long MIN_SWIPE_TIME = 1;
	private static final long MAX_SWIPE_TIME = 500;

	private static final long MAX_TIME_NO_POINT = 100;
	private static float MIN_SWIPE_DISTANCE = 20f;
	private static float SWIPE_WAIT_TIME = 1f;

	private SwipeListener listener;
	private Array<SwipePoint> points;

	public SwipeDetection(SwipeListener listener) {
		this.listener = listener;
		points = new Array();
	}

	/**
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public void addPoint(float x, float y) {
		points.add(new SwipePoint(x, y));
		if (points.size > MAX_POINTS) {
			points.removeIndex(0);
		}
	}

	/**
	 * this method will detect any gestures.
	 */
	public void update() {
		if (points.size < 5)
			return;

		SwipePoint startPoint = points.get(points.size - 1); // last point added
		if (Math.abs(startPoint.getTime() - System.currentTimeMillis()) >= MAX_TIME_NO_POINT) {
			points.clear();
			return;
		}
		for (int i = points.size - 2; i >= 0; i--) {
			SwipePoint point = points.get(i); // last point added
			float xDiff = Math.abs(startPoint.getX() - point.getX());
			long totalTime = startPoint.getTime() - point.getTime();
			if (xDiff > MIN_SWIPE_DISTANCE && totalTime < MIN_SWIPE_TIME) {
				listener.onSwipe(SwipeType.DOWM, 1f);
				points.clear();
				return;
			}
			System.out.println(xDiff);
		}

	}
}
