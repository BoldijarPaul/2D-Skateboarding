package Stuff;

import GameStuff.GameStats;
import GameStuff.TileData;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class B2DContactListener implements ContactListener {

	public static int FootAndRailCount = 0;
	public static int FootAndTileCount = 0;
	public boolean dead = false;
	public boolean done = false;
	public boolean headhit = false;

	// tile & foot

	public boolean IsRail(Fixture f) {
		if (f == null)
			return false;
		if (f.getUserData() == null)
			return false;
		if (f.getUserData().toString().contains("rail"))
			return true;
		return false;
	}

	public boolean IsTile(Fixture f) {
		if (f == null)
			return false;
		if (f.getUserData() == null)
			return false;
		if (f.getUserData().toString().contains("tile"))
			return true;
		return false;
	}

	public boolean IsFoot(Fixture f) {
		if (f == null)
			return false;
		if (f.getUserData() == null)
			return false;
		if (f.getUserData().toString().contains("foot"))
			return true;
		return false;
	}

	private void CheckDeathAndDone(Fixture f1, Fixture f2) {
		if (f1 == null || f2 == null)
			return;
		if (f1.getUserData() == null || f2.getUserData() == null)
			return;
		if (f1.getUserData().toString().equals("skater")
				&& f2.getUserData().toString().equals("die")) {
			dead = true;
		}
		if (f2.getUserData().toString().equals("skater")
				&& f1.getUserData().toString().equals("die")) {
			dead = true;
		}

		if (f1.getUserData().toString().equals("skater")
				&& f2.getUserData().toString().equals("finish")) {
			done = true;
		}
		if (f2.getUserData().toString().equals("skater")
				&& f1.getUserData().toString().equals("finish")) {
			done = true;
		}
	}

	private void CheckBeginFootTile(Fixture f1, Fixture f2) {
		if (f1 == null || f2 == null)
			return;
		if (f1.getUserData() == null || f2.getUserData() == null)
			return;

		if (f1.getUserData().toString().equals("cap")
				&& f2.getUserData().toString().equals("tile")) {
			headhit = true;

		}
		if (f1.getUserData().toString().equals("tile")
				&& f2.getUserData().toString().equals("cap")) {
			headhit = true;

		}
		if (IsFoot(f1) && IsTile(f2)) {

			FootAndTileCount++;

		}
		if (IsFoot(f2) && IsTile(f1)) {

			FootAndTileCount++;

		}
		
		
		if (IsFoot(f1) && IsRail(f2)) {

			FootAndRailCount++;

		}
		if (IsFoot(f2) && IsRail(f1)) {

			FootAndRailCount++;

		}
	}

	private void CheckEndFootTile(Fixture f1, Fixture f2) {
		if (f1 == null || f2 == null)
			return;
		if (f1.getUserData() == null || f2.getUserData() == null)
			return;
		if (IsFoot(f1) && IsTile(f2)) {

			FootAndTileCount--;

		}
		if (IsFoot(f2) && IsTile(f1)) {

			FootAndTileCount--;

		}

		if (IsFoot(f1) && IsRail(f2)) {

			FootAndRailCount--;

		}
		if (IsFoot(f2) && IsRail(f1)) {

			FootAndRailCount--;

		}

	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();

		// coin

		/*
		 * if (FixtureToString(f1).equals("coin")) SetDelete(f1); if
		 * (FixtureToString(f2).equals("coin")) SetDelete(f2);
		 */

		CheckBeginFootTile(f1, f2);
		CoinCheck(f1);
		CoinCheck(f2);
		StarCheck(f1);
		StarCheck(f2);
		CheckDeathAndDone(f1, f2);

	}

	private void CoinCheck(Fixture fixture) {
		if (fixture.getUserData() == null)
			return;
		if (fixture.getUserData() instanceof TileData) {

			TileData data = (TileData) fixture.getUserData();

			if (data.tip.equals("coin")) {
				// e moneda

				data.destroyed = true;
				fixture.getBody().setUserData(data);

				GameStats.NeedToDeleteBody = true;
			}
		}
	}

	private void StarCheck(Fixture fixture) {
		if (fixture.getUserData() == null)
			return;
		if (fixture.getUserData() instanceof TileData) {

			TileData data = (TileData) fixture.getUserData();

			if (data.tip.equals("star")) {
				// e moneda

				data.destroyed = true;
				fixture.getBody().setUserData(data);

				GameStats.NeedToDeleteBody = true;

			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		CheckEndFootTile(f1, f2);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
