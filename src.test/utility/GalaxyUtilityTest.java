package utility;

import org.junit.Assert;
import org.junit.Test;

import exceptions.IdontKnowException;

public class GalaxyUtilityTest {

	@Test
	public void shouldTesGetIndexOfIs() throws IdontKnowException {
		int index = GalaxyUtility.getIndexOfIs("glob is I");
		Assert.assertEquals(index, 1);
	}

	@Test(expected = IdontKnowException.class)
	public void shouldTesGetIndexOfIsInvalidCase() throws IdontKnowException {
		int index = GalaxyUtility.getIndexOfIs("glob = I");
		Assert.assertEquals(index, -1);
	}

	@Test
	public void shouldTestGetTokensAfterIs() {
		String[] tokens = GalaxyUtility.getTokensAfterIs(
				"pish pish Iron is 3910 Credits".split(" "), 3);
		Assert.assertEquals(tokens.length, 2);
		Assert.assertEquals(tokens[0], "3910");
		Assert.assertEquals(tokens[1], "Credits");
	}

	@Test
	public void shouldTestGetTokensBeforeIs() {
		String[] tokens = GalaxyUtility.getTokensBeforeIs(
				"pish pish Iron is 3910 Credits".split(" "), 3);
		Assert.assertEquals(tokens.length, 3);
		Assert.assertEquals(tokens[0], "pish");
		Assert.assertEquals(tokens[1], "pish");
		Assert.assertEquals(tokens[2], "Iron");
	}

	@Test
	public void shouldTestToString() {
		String[] arrayStr = { "glob", "glob" };
		String str = GalaxyUtility.toString(arrayStr);
		Assert.assertEquals(str, "glob glob");
	}

	@Test
	public void shouldTestPrepareOutputLine() {
		String output = GalaxyUtility.prepareOutputLine("glob prok Wood", 12,
				true);
		Assert.assertEquals(output, "glob prok Wood is 12 Credits");
		GalaxyUtility.prepareOutputLine("pish tegj glob glob", 42, false);
		Assert.assertEquals(output, "glob prok Wood is 12 Credits");
	}

}
