package Stuff;

import GameStuff.GameStats;
import assets.GraphicsManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SkaterRenderer {

	float x, y, width, height;

	int oFrame = 0;
	int tFrame = 0;
	int iFrame = 0;
	int gFrame = 0;

	float oTime = 0f;
	float tTime = 0f;
	float iTime = 0f;
	float gTime = 0f;

	float oStep = 1 / 40f;
	float tStep = 1 / 55f;
	float iStep = 1 / 20f;
	float gStep = 1 / 22f;

	State skaterState;

	enum State {
		Ollie, Talpa, Idle, Grind;
	}

	public void SetOllie() {
		skaterState = State.Ollie;
		oFrame = 0;
		oTime = 0f;
	}

	public void SetTalpa(boolean right) {
		if (right)
			width = Math.abs(width);
		else
			width = -Math.abs(width);
		skaterState = State.Talpa;
		tFrame = 0;
		tTime = 0f;
	}

	public void SetIdle() {
		skaterState = State.Idle;
		iFrame = 0;
		iTime = 0f;
	}

	public void SetGrind() {
		skaterState = State.Grind;
		gFrame = 0;
		gTime = 0f;
	}

	private boolean eOllie() {
		return skaterState == State.Ollie;
	}

	private boolean eTalpa() {

		return skaterState == State.Talpa;
	}

	private boolean eIdle() {
		return skaterState == State.Idle;
	}

	private boolean eGrind() {
		return skaterState == State.Grind;
	}

	public SkaterRenderer(float width, float height) {
		x = 0;
		y = 0;
		this.width = width;
		this.height = height;
		skaterState = State.Idle;
		oFrame = 0;
		tFrame = 0;
		iFrame = 0;
		gFrame = 0;
		oTime = 0f;
		tTime = 0f;
		iTime = 0f;
		gTime = 0f;
	}

	public void Update(float x, float y) {
		float d = Gdx.graphics.getDeltaTime();
		this.x = x;
		this.y = y;

		if (!eOllie() && GameStats.OnRail() && !eGrind())
			SetGrind();
		else if (eGrind() && !GameStats.OnRail())
			SetIdle();

		if (eGrind()) {
			gTime += d;
			if (gTime > gStep) {
				gTime = 0;
				gFrame++;
			}
			if (gFrame >= GraphicsManager.Game.grindFrames)
				SetGrind();
		}
		if (eOllie()) {
			oTime += d;
			if (oTime > oStep) {
				oTime = 0;
				oFrame++;
			}
			if (oFrame >= GraphicsManager.Game.ollieFrames)
				SetIdle();
		}
		if (eTalpa()) {
			tTime += d;
			if (tTime > tStep) {
				tTime = 0;
				tFrame++;
			}
			if (tFrame >= GraphicsManager.Game.talpaFrames)
				SetIdle();
		}
		if (eIdle()) {
			iTime += d;
			if (iTime > iStep) {
				iTime = 0;
				iFrame++;
			}
			if (iFrame >= GraphicsManager.Game.idleFrames)
				SetIdle();
		}

	}

	public void Draw(SpriteBatch batch, Vector2 hatPos) {

		if (width < 0)
			x -= width;

		if (skaterState == State.Grind)
			batch.draw(GraphicsManager.Game.anim_grind[gFrame], x, y, width,
					height);
		if (skaterState == State.Idle)
			batch.draw(GraphicsManager.Game.anim_idle[iFrame], x, y, width,
					height);
		if (skaterState == State.Talpa)
			batch.draw(GraphicsManager.Game.anim_talpa[tFrame], x, y, width,
					height);
		if (skaterState == State.Ollie)
			batch.draw(GraphicsManager.Game.anim_ollie[oFrame], x, y, width,
					height);

		if (width < 0)
			x += width;

		float w = 0.28f;
		float h = 0.11f;

		float hy = Math.max(y + 0.96499205f, hatPos.y);
		batch.draw(GraphicsManager.Game.hat, x + .06f, hy - .35f, w, h);
	}
}
