package utility;

import java.util.Arrays;

import exceptions.IdontKnowException;

public class GalaxyUtility {
	public static int getIndexOfIs(final String eachLine) throws IdontKnowException {
		String[] tokens = eachLine.split(" ");
		int indexOfIs = (Arrays.asList(tokens)).indexOf("is");
		if (indexOfIs < 0) {
			throw new IdontKnowException(eachLine);
		}
		return indexOfIs;
	}

	public static String[] getTokensAfterIs(final String[] tokens, final int indexOfIs) {
		return Arrays.copyOfRange(tokens, indexOfIs + 1, tokens.length);
	}

	public static String[] getTokensBeforeIs(final String[] tokens,final  int indexOfIs) {
		return Arrays.copyOfRange(tokens, 0, indexOfIs);
	}

	public static String toString(final String[] values) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i != 0)
				sb.append(" ");
			sb.append(values[i].toString());
		}
		return sb.toString();

	}

	public static String prepareOutputLine(final String queryParamsTrim,final  int numeric,
			final boolean appendCrdits) {
		StringBuilder output = new StringBuilder();
		output = output.append(queryParamsTrim).append(" is ").append(numeric);
		if (appendCrdits) {
			output.append(" Credits");
		}
		return output.toString();
	}

	public static boolean hasMetalNames(final String eachLine) {
		String input = eachLine.toUpperCase();
		boolean hasMetalName = false;
		for (String metalName : Arrays.asList(Constants.METALS)) {
			if (input.contains(metalName)) {
				hasMetalName = true;
				break;
			}

		}
		return hasMetalName;
	}

}
