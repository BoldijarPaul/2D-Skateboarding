package Stuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Languages {

	public static JsonValue obj;
	public static String limba;
	public static int lindex = 1;

	// 0 romana
	// 1 eng

	public static void Load() {
		String txt = Gdx.files.internal("languages.json").readString();

		obj = new JsonReader().parse(txt);
		limba = GetLanguages()[lindex];

	}

	public static String[] GetLanguages() {
		int count = obj.get("langs").size;
		String[] result = new String[count];
		int i = 0;
		for (JsonValue s : obj.get("langs")) {
			result[i++] = s.toString();
			// System.out.println(s.toString());

		}
		return result;
	}

	public static String GetString(String limb, String name) {
		return obj.get(limb).getString(name);
	}

	public static String GetString(String name) {
		System.out.println("Incerc sa iau textul " + name + " din limba "
				+ limba);

		return obj.get(limba).getString(name);
	}
}
