package uk.ac.city;


public class Utils {
	public static String toSentenceCase(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		input = input.toLowerCase();
		StringBuilder sb = new StringBuilder();
		boolean capitalizeNext = true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isWhitespace(c)) {
				capitalizeNext = true;
				sb.append(c);
			} else if (capitalizeNext) {
				sb.append(Character.toUpperCase(c));
				capitalizeNext = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}

		return sb.toString();
	}
}
