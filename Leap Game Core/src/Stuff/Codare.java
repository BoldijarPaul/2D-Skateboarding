package Stuff;

public class Codare {

	public static String Codeaza(String text) {
		String out = "";
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char aux = chars[i];
			aux += (i + 3) * 1;
			out += aux;
		}
		return out;
	}

	public static String DeCodeaza(String text) {
		String out = "";
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char aux = chars[i];
			aux -= (i + 3) * 1;
			out += aux;
		}
		return out;
	}
}
