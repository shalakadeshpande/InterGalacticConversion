package inputReader;

public class InputReaderFactory {

	public static Reader getInputReader(String type) {
		return "FILE".equalsIgnoreCase(type) ? new FileReader() : null;
	}

}
