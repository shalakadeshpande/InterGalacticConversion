package converter;

import org.junit.Assert;
import org.junit.Test;

public class RomanToNumericConverterTest {

	@Test
	public void shouldReturnNumericRepresentationOFRoman() {
		Assert.assertEquals(RomanToNumericConverter.decode("IV"), 4);
		Assert.assertEquals(RomanToNumericConverter.decode("VIII"), 8);
	}
	@Test
	public void shouldReturnZeroForInvaliRoman() {
		Assert.assertEquals(RomanToNumericConverter.decode("SS"), 0);
	}
}
