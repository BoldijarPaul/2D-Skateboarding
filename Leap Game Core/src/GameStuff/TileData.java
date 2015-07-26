package GameStuff;

public class TileData {

	public int id;
	public boolean destroyed = false;
	public String tip;

	public TileData(int id, String tip) {
		this.id = id;
		this.destroyed = false;
		this.tip = tip;
	}
}
