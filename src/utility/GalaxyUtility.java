package utility;

import java.util.Arrays;

import domain.MetalNames;
import exceptions.IdontKnowException;

public class GalaxyUtility {
	public static int getIndexOfIs(String eachLine) throws IdontKnowException {
		String[] tokens = eachLine.split(" ");
		int indexOfIs = (Arrays.asList(tokens)).indexOf("is");
		if (indexOfIs < 0) {
			throw new IdontKnowException("I do Not Know what is this", eachLine);
		}
		return indexOfIs;
	}

	public static String[] getTokensAfterIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, indexOfIs + 1, tokens.length);
	}

	public static String[] getTokensBeforeIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, 0, indexOfIs);
	}

	public static String toString(String[] values) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i != 0)
				sb.append(" ");
			sb.append(values[i].toString());
		}
		return sb.toString();

	}

	public static String prepareOutputLine(String queryParamsTrim, int numeric,
			boolean appendCrdits) {
		StringBuilder output = new StringBuilder();
		output = output.append(queryParamsTrim).append(" is ").append(numeric);
		if (appendCrdits) {
			output.append(" Credits");
		}
		return output.toString();
	}

	public static boolean hasMetalNames(String eachLine) {
		String input = eachLine.toUpperCase();
		// TODO make generic for metal names : what if new metal gets added to
		// enum?
		boolean hasMetalName = input.contains(MetalNames.GOLD.name())
				|| input.contains(MetalNames.SILVER.name())
				|| input.contains(MetalNames.IRON.name());
		return hasMetalName;
	}

}
