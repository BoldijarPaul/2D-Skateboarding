package swipe;

public class SwipePoint {
	private float x, y;
	private long time;

	public SwipePoint(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		time = System.currentTimeMillis();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public float distance(SwipePoint point) {
		float x1 = x;
		float y1 = y;
		float x2 = point.x;
		float y2 = point.y;
		final float x_d = x2 - x1;
		final float y_d = y2 - y1;
		return (float) Math.sqrt(x_d * x_d + y_d * y_d);
	}

	public long time(SwipePoint point) {
		return Math.abs((time - point.time));
	}
}
