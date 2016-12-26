package converter;

import java.util.Map;

public class GalaxyToRomanConverter {
	public static String decode(String galactic,
			Map<String, String> galacticToRomanUnits) {
		String[] tokens = galactic.split(" ");
		StringBuilder roman = new StringBuilder("");
		for (String token : tokens) {
			String romanStr = getRoman(galacticToRomanUnits, token);
			roman = roman.append(romanStr);
		}

		return roman.toString();

	}

	private static String getRoman(Map<String, String> galacticToRomanUnits,
			String token) {
		String romanStr = galacticToRomanUnits.get(token);
		if (romanStr == null) {
			throw new RuntimeException(
					"Callibration Error - Non Single Digit Unit - I do not know what is this - "
							+ romanStr);
		}
		return romanStr;
	}
}
