package converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import InputReader.InputReaderFactory;
import InputReader.MyReadable;
import domain.Metal;
import domain.MetalNames;

public class InterGalacticConverter {
	
	//TODO warn: this file is becoming too large!!

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

	private void populateGalacticToRomanMap(String eachLine) {

		String[] tokens = eachLine.split(" ");
		int indexOfIs = getIndexOfIs(tokens);
		if (indexOfIs < 0) { // TODO : Create custom UnitsCalibrationException
			throw new RuntimeException(
					"Callibration Error - Non Single Digit Unit - I do not know what is this - "
							+ eachLine);
		}
		String[] galaxyUnit = getTokensBeforeIs(tokens, indexOfIs);
		String[] romanUnit = getTokensAfterIs(tokens, indexOfIs);
		validateUnit(eachLine, galaxyUnit);
		validateUnit(eachLine, romanUnit);
		galacticToRomanUnits.put(galaxyUnit[0], romanUnit[0]);

	}

	private String[] getTokensAfterIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, indexOfIs + 1, tokens.length);
	}

	private String[] getTokensBeforeIs(String[] tokens, int indexOfIs) {
		return Arrays.copyOfRange(tokens, 0, indexOfIs);
	}

	private int getIndexOfIs(String[] tokens) {
		return (Arrays.asList(tokens)).indexOf("is");
	}

	private void validateUnit(String eachLine, String[] unit) {
		// TODO : Handle corner case - what if glob is II?
		// TODO : Create custom UnitsCalibrationException
		// TODO : Verify against valid Roman digits?

		if (unit.length != 1) {
			throw new RuntimeException(
					"Callibration Error - Non Single Digit Unit - I do not know what is this - "
							+ eachLine);
		}

	}

	private void populateMetalInfo(String eachLine) {
		Metal metal = new Metal();
		String[] tokens = eachLine.split(" ");
		int indexOfIs = getIndexOfIs(tokens);
		if (indexOfIs < 0) {
			throw new RuntimeException(
					"Callibration Error - Non Single Digit Unit - I do not know what is this - "
							+ eachLine);
		}
		String[] galaxyCreditInfoTokens = getTokensBeforeIs(tokens, indexOfIs);
		String[] numericCreditInfoTokens = getTokensAfterIs(tokens, indexOfIs);
		Map<Integer, String> metalsInfoMap = extractMetalsInfo(galaxyCreditInfoTokens);
		int indexOfMetalName = 0;
		for (Entry<Integer, String> entry : metalsInfoMap.entrySet()) {
			indexOfMetalName = entry.getKey();
			metal.setName(entry.getValue());
		}
		String galacticCredit = Arrays.toString(Arrays.copyOfRange(
				galaxyCreditInfoTokens, 0, indexOfMetalName));
		int numericCredit = Integer.parseInt(Arrays.toString(
				numericCreditInfoTokens).replaceAll("[^0-9]", ""));

		metal.setGalacticCredit(galacticCredit);
		metal.setNumericCredit(numericCredit);

		// TODO validateMetalInfo(metal);

		metalInfoList.add(metal);
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
				// Arrays.binarySearch(galaxyCreditInfoTokens, token)

			}

		}
		return metalsInfoMap;
	}

	private void handleQuery(String eachLine) {
		System.out.println("handle query for " + eachLine);
	}

	public void parseInputNotes() {
		MyReadable reader = InputReaderFactory.getInputReader("FILE");
		List<String> fileContents = reader.read();
		for (String eachLine : fileContents) {
			if (!hasQuestionMark(eachLine) && hasMetalNames(eachLine)) {
				populateMetalInfo(eachLine);
			} else if (hasQuestionMark(eachLine)) {
				handleQuery(eachLine);
			} else {
				populateGalacticToRomanMap(eachLine);
			}

		}

	}

	private boolean hasQuestionMark(String eachLine) {
		String inputLine = eachLine.trim();
		return inputLine.indexOf('?') == inputLine.length() - 1;
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

	public static void main(String[] args) {
		InterGalacticConverter interGalacticConverter = new InterGalacticConverter();
		interGalacticConverter.parseInputNotes();
		System.out.println(interGalacticConverter.getGalacticToRomanUnits());
		System.out.println(interGalacticConverter.getMetalInfoList());
	}
}
