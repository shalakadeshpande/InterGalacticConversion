package processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utility.Constants;
import utility.GalaxyUtility;
import converter.GalaxyToRomanConverter;
import converter.RomanToNumericConverter;
import domain.Metal;
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
				} else if (GalaxyUtility.hasMetalNames(eachLine)) {
					populateMetalInfo(eachLine);
				} else {
					populateGalacticToRomanMap(eachLine);
				}
			} catch (IdontKnowException e) {
				outputToFile.append(System.getProperty("line.separator"));
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
		int indexOfIs = GalaxyUtility.getIndexOfIs(eachLine);

		String[] galaxyUnit = GalaxyUtility
				.getTokensBeforeIs(tokens, indexOfIs);
		String[] romanUnit = GalaxyUtility.getTokensAfterIs(tokens, indexOfIs);
		validateUnit(eachLine, galaxyUnit);
		validateUnit(eachLine, romanUnit);
		galacticToRomanUnits.put(galaxyUnit[0], romanUnit[0]);

	}

	private void validateUnit(String eachLine, String[] unit) {
		if (unit.length != 1) {
			throw new RuntimeException(
					"Invalid Roman Digit - I do not know what is this - "
							+ Arrays.toString(unit));
		}
	}

	private void populateMetalInfo(String eachLine) throws IdontKnowException {
		Metal metal = new Metal();
		String[] tokens = eachLine.split(" ");
		int indexOfIs = GalaxyUtility.getIndexOfIs(eachLine);
		String[] galaxyCreditInfoTokens = GalaxyUtility.getTokensBeforeIs(
				tokens, indexOfIs);
		String[] numericCreditInfoTokens = GalaxyUtility.getTokensAfterIs(
				tokens, indexOfIs);
		Map<Integer, String> metalsInfoMap = extractMetalsInfo(galaxyCreditInfoTokens);
		int indexOfMetalName = 0;
		for (Entry<Integer, String> entry : metalsInfoMap.entrySet()) {
			indexOfMetalName = entry.getKey();
			metal.setName(entry.getValue());
		}

		String galacticCredit = GalaxyUtility.toString(Arrays.copyOfRange(
				galaxyCreditInfoTokens, 0, indexOfMetalName));
		int numericCredit = Integer.parseInt(GalaxyUtility.toString(
				numericCreditInfoTokens).replaceAll("[^0-9]", ""));

		metal.setRate(calculateRate(galacticCredit, numericCredit));

		metalInfoList.add(metal);
	}

	private int calculateRate(String galacticCredit, int numericCredit) {
		int galacticCreditToNumeric = RomanToNumericConverter
				.decode(GalaxyToRomanConverter.decode(galacticCredit,
						galacticToRomanUnits));

		return numericCredit / galacticCreditToNumeric;
	}

	private int calculateGalacticCredits(String galaxyCredits) {
		String galaxyCreditsInRoman = GalaxyToRomanConverter.decode(
				galaxyCredits, galacticToRomanUnits);
		return RomanToNumericConverter.decode(galaxyCreditsInRoman);

	}

	private int calculateGalacticCredits(String galaxyCredits, String metalName)
			throws IdontKnowException {
		int numeric = 0, rate = 0;
		for (Metal metal : metalInfoList) {
			if (metal.getName().equalsIgnoreCase(metalName)) {
				rate = metal.getRate();
				break;
			}

		}
		if (rate == 0) {
			throw new IdontKnowException(metalName);
		}
		numeric = (RomanToNumericConverter.decode(GalaxyToRomanConverter
				.decode(galaxyCredits, galacticToRomanUnits))) * rate;
		return numeric;
	}

	private Map<Integer, String> extractMetalsInfo(
			String[] galaxyCreditInfoTokens) throws IdontKnowException {
		Map<Integer, String> metalsInfoMap = new HashMap<>();
		for (String token : galaxyCreditInfoTokens) {
			if (Arrays.asList(Constants.METALS).contains(token.toUpperCase())) {
				metalsInfoMap.put(
						(Arrays.asList(galaxyCreditInfoTokens)).indexOf(token),
						token);
			}
		}
		if (metalsInfoMap.isEmpty()) {
			throw new IdontKnowException(
					GalaxyUtility.toString(galaxyCreditInfoTokens));
		}
		return metalsInfoMap;
	}

	private String handleQuery(String eachLine) throws IdontKnowException {

		String[] tokens = eachLine.split(" ");
		int indexOfIs = GalaxyUtility.getIndexOfIs(eachLine);

		String queryType = GalaxyUtility.toString(GalaxyUtility
				.getTokensBeforeIs(tokens, indexOfIs));
		String[] tokensAfterIs = GalaxyUtility.getTokensAfterIs(tokens,
				indexOfIs);
		String queryParams = GalaxyUtility.toString(tokensAfterIs);
		String queryParamsTrim = queryParams.replaceAll("\\?", "").trim();

		int numeric = 0;
		String metalName = null;
		if (queryType.toLowerCase().startsWith("how much")) {

			numeric = calculateGalacticCredits(queryParamsTrim);
			return GalaxyUtility.prepareOutputLine(queryParamsTrim, numeric,
					false);
		} else {
			Map<Integer, String> metalsInfo = extractMetalsInfo(tokensAfterIs);
			int indexOfMetalName = 0;
			for (Entry<Integer, String> entry : metalsInfo.entrySet()) {
				indexOfMetalName = entry.getKey();
				metalName = entry.getValue();
			}
			String galacticCredit = GalaxyUtility.toString(Arrays.copyOfRange(
					tokensAfterIs, 0, indexOfMetalName));
			numeric = calculateGalacticCredits(galacticCredit, metalName);

			return GalaxyUtility.prepareOutputLine(queryParamsTrim, numeric,
					true);
		}

	}

}
