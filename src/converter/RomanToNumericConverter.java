package converter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RomanToNumericConverter {
	
	static Map<Character, Integer> romans;
	static {
		Map<Character, Integer> tempRomans = new HashMap<>();
		tempRomans.put('M', 1000);
		tempRomans.put('D', 500);
		tempRomans.put('C', 100);
		tempRomans.put('L', 50);
		tempRomans.put('X', 10);
		tempRomans.put('V', 5);
		tempRomans.put('I', 1);
		romans = Collections.unmodifiableMap(tempRomans);
	}

	public static int decode(String roman) {
		int result = 0;
		String uRoman = roman.toUpperCase();
		for (int i = 0; i < uRoman.length() - 1; i++) {
			int decodeSingle = romans.get(uRoman.charAt(i));
			int decodeSingleNext = romans.get(uRoman.charAt(i + 1));
			if (decodeSingle < decodeSingleNext) {
				result -= decodeSingle;
			} else {
				result += decodeSingle;
			}
		}
		result += romans.get(uRoman.charAt(uRoman.length() - 1));
		return result;
	}

}
