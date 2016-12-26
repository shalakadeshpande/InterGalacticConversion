package converter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GalaxyToRomanConverterTest {

	Map<String, String> galacticToRomanUnits = new HashMap<String, String>();

	@Before
	public void setup() {

		galacticToRomanUnits.put("glob", "I");
		galacticToRomanUnits.put("prok", "V");
		galacticToRomanUnits.put("pish", "X");
		galacticToRomanUnits.put("tegj", "L");

	}

	@Test
	public void shouldTestDecode() {

		String roman = GalaxyToRomanConverter.decode("glob glob",
				galacticToRomanUnits);
		Assert.assertEquals(roman, "II");
	}

	@Test(expected = RuntimeException.class)
	public void shouldTestDecodeException() {

		GalaxyToRomanConverter
				.decode("glob invalid prok", galacticToRomanUnits);

	}

}
