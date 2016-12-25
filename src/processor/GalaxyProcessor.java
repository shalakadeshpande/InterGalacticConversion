package processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import converter.GalaxyToRomanConverter;
import converter.RomanToNumericConverter;
import domain.Metal;
import domain.MetalNames;
import exceptions.IdontKnowException;

public class GalaxyProcessor implements Processor {
	private Map<String, String> galacticToRomanUnits = new HashMap<String, String>();
	private List<Metal> metalInfoList = new ArrayList<>();

	public Map<String, String> getGalacticToRomanUnits() {
		return galacticToRomanUnits;
	}

	public void setGalacticToRomanUnits(Map<String, String> galacticToRomanUnits) {
		this.galacticToRomanUnits = galacticToRomanUnits;
	}

	public List<Metal> getMetalInfoList() {
		return metalInfoList;
	}

	public void setMetalInfoList(List<Metal> metalInfoList) {
		this.metalInfoList = metalInfoList;
	}

	@Override
	public String process(List<String> fileContents) {
		StringBuilder outputToFile = new StringBuilder();
		String outputEachLine;

		for (String eachLine : fileContents) {
			try {
				if (hasQuestion(eachLine)) {
					outputEachLine = handleQuery(eachLine);
					
					if (outputToFile.length() != 0) {
						outputToFile.append(System
								.getProperty("line.separator"));
					}
					outputToFile.append(outputEachLine);
				} else if (hasMetalNames(eachLine)) {
					populateMetalInfo(eachLine);
				} else {
					populateGalacticToRomanMap(eachLine);
				}
			} catch (IdontKnowException e) {
				outputToFile.append(System
						.getProperty("line.separator"));
				outputEachLine = e.getMessage() + " " + e.getInput();
				outputToFile.append(outputEachLine);
				
			}

		}
		return outputToFile.toString();
	}

	private boolean hasQuestion(String eachLine) {
		String inputLine = eachLine.trim();
		return inputLine.endsWith("?");
	}

	private void populateGalacticToRomanMap(String eachLine)
			throws IdontKnowException {

		String[] tokens = eachLine.split(" ");
		int indexOfIs = getIndexOfIs(eachLine);

		String[] galaxyUnit = getTokensBeforeIs(tokens, indexOfIs);
		String[] romanUnit = getTokensAfterIs(tokens, indexOfIs);
		validateUnit(eachLine, galaxyUnit);
		validateUnit(eachLine, romanUnit);
		galacticToRomanUnits.put(galaxyUnit[0], romanUnit[0]);

	}

	private void validateUnit(String eachLine, String[] unit) {
		// TODO : Handle corner case - what if glob is II?
		// TODO : Create custom UnitsCalibrationException
		// TODO : Verify against valid Roman digits?

		if (unit.length != 1) {
			throw new RuntimeException(
					"Invalid Roman Digit - I do not know what is this - "
							+ Arrays.toString(unit));
		}

	}

	private void populateMetalInfo(String eachLine) throws IdontKnowException {
		Metal metal = new Metal();
		String[] tokens = eachLine.split(" ");
		int indexOfIs = getIndexOfIs(eachLine);
		String[] galaxyCreditInfoTokens = getTokensBeforeIs(tokens, indexOfIs);
		String[] numericCreditInfoTokens = getTokensAfterIs(tokens, indexOfIs);
		Map<Integer, String> metalsInfoMap = extractMetalsInfo(galaxyCreditInfoTokens);
		int indexOfMetalName = 0;
		for (Entry<Integer, String> entry : metalsInfoMap.entrySet()) {
			indexOfMetalName = entry.getKey();
			metal.setName(entry.getValue());
		}
		String galacticCredit = toString(Arrays.copyOfRange(
				galaxyCreditInfoTokens, 0, indexOfMetalName));
		int numericCredit = Integer.parseInt(toString(numericCreditInfoTokens)
				.replaceAll("[^0-9]", ""));

		metal.setGalacticCredit(galacticCredit);
		metal.setNumericCredit(numericCredit);

		// TODO validateMetalInfo(metal);

		metalInfoList.add(metal);
	}

	private int calculateGalacticCredits(String galaxyCredits) {
		String galaxyCreditsInRoman = GalaxyToRomanConverter.decode(
				galaxyCredits, galacticToRomanUnits);
		return RomanToNumericConverter.decode(galaxyCreditsInRoman);

	}

	private int calculateGalacticCredits(String galaxyCredits, String metalName) throws IdontKnowException {
		String givenGalaxyCredits = null;
		int givenNumericCredit = 0, numeric = 0;
		for (Metal metal : metalInfoList) {
			if (metal.getName().equalsIgnoreCase(metalName)) {
				givenGalaxyCredits = metal.getGalacticCredit();
				givenNumericCredit = metal.getNumericCredit();
				break;
			}

		}
		if(givenGalaxyCredits==null){
			throw new IdontKnowException("I do not know what is this ", metalName);
		}
		numeric = ruleOfThree(givenGalaxyCredits, givenNumericCredit,
				galaxyCredits);
		return numeric;
	}

	private int ruleOfThree(String givenGalaxyCredits, int givenNumericCredit,
			String galaxyCredits) {

		int valueA = RomanToNumericConverter.decode(GalaxyToRomanConverter
				.decode(givenGalaxyCredits, getGalacticToRomanUnits()));
		int valueB = givenNumericCredit;

		int valueC = RomanToNumericConverter.decode(GalaxyToRomanConverter
				.decode(galaxyCredits, getGalacticToRomanUnits()));

		int valueD = valueC * valueB / valueA;

		return valueD;
	}

	private Map<Integer, String> extractMetalsInfo(
			String[] galaxyCreditInfoTokens) {
		Map<Integer, String> metalsInfoMap = new HashMap<>();
		for (String token : galaxyCreditInfoTokens) {
			// TODO make generic for metal names : what if new metal gets added
			// to enum?
			if (token.equalsIgnoreCase(MetalNames.GOLD.name())
					|| token.equalsIgnoreCase(MetalNames.SILVER.name())
					|| token.equalsIgnoreCase(MetalNames.IRON.name())) {
				metalsInfoMap.put(
						(Arrays.asList(galaxyCreditInfoTokens)).indexOf(token),
						token);

			}

		}
		return metalsInfoMap;
	}

	private String handleQuery(String eachLine) throws IdontKnowException {

		String[] tokens = eachLine.split(" ");
		int indexOfIs = getIndexOfIs(eachLine);

		String queryType = toString(getTokensBeforeIs(tokens, indexOfIs));
		String[] tokensAfterIs = getTokensAfterIs(tokens, indexOfIs);
		String queryParams = toString(tokensAfterIs);
		String queryParamsTrim = queryParams.replaceAll("\\?", "").trim();

		// TODO think what if other question pattern gets added - decouple this
		int numeric = 0;
		String metalName = null;
		if (queryType.toLowerCase().startsWith("how much")) {

			numeric = calculateGalacticCredits(queryParamsTrim);
			return prepareOutputLine(queryParamsTrim, numeric, false);
		} else {
			Map<Integer, String> metalsInfo = extractMetalsInfo(tokensAfterIs);
			int indexOfMetalName = 0;
			for (Entry<Integer, String> entry : metalsInfo.entrySet()) {
				indexOfMetalName = entry.getKey();
				metalName = entry.getValue();
			}
			String galacticCredit = toString(Arrays.copyOfRange(tokensAfterIs,
					0, indexOfMetalName));
			numeric = calculateGalacticCredits(galacticCredit, metalName);

			return prepareOutputLine(queryParamsTrim, numeric, true);
		}

	}

	// utility for galaxy
	private int getIndexOfIs(String eachLine) throws IdontKnowException {
		String[] tokens = eachLine.split(" ");
		int indexOfIs = (Arrays.asList(tokens)).indexOf("is");
		if (indexOfIs < 0) { // TODO : Create custom UnitsCalibrationException
			throw new IdontKnowException("I do Not Know what is this",
					eachLine);
		}
		return indexOfIs;
	}

	private String[] getTokensAfterIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, indexOfIs + 1, tokens.length);
	}

	private String[] getTokensBeforeIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, 0, indexOfIs);
	}

	private static String toString(String[] values) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (i != 0)
				sb.append(" ");
			sb.append(values[i].toString());
		}
		return sb.toString();

	}

	private String prepareOutputLine(String queryParamsTrim, int numeric,
			boolean appendCrdits) {
		StringBuilder output = new StringBuilder();
		output = output.append(queryParamsTrim).append(" is ").append(numeric);
		if (appendCrdits) {
			output.append(" Credits");
		}
		return output.toString();
	}

	private boolean hasMetalNames(String eachLine) {
		String input = eachLine.toUpperCase();
		// TODO make generic for metal names : what if new metal gets added to
		// enum?
		boolean hasMetalName = input.contains(MetalNames.GOLD.name())
				|| input.contains(MetalNames.SILVER.name())
				|| input.contains(MetalNames.IRON.name());
		return hasMetalName;
	}

}
