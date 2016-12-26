package converter;

import org.junit.Assert;
import org.junit.Test;

public class InterGalacticConverterTest {
	InterGalacticConverter interGalacticConverter = new InterGalacticConverter();

	@Test
	public void testParseInputNotes() {
		String output = interGalacticConverter.parseInputNotes();
		StringBuilder expectedOutput = new StringBuilder("").
				append("pish tegj glob glob is 42").
				append(System.getProperty("line.separator")).
				append("glob prok Silver is 68 Credits").
				append(System.getProperty("line.separator")).
				append("glob prok Gold is 57800 Credits").
				append(System.getProperty("line.separator")).
				append("glob prok Iron is 782 Credits").
				append(System.getProperty("line.separator")).
				append("I do Not Know what is this how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");

		Assert.assertEquals(output, expectedOutput.toString());
	}
}
