package InputReader;

public class InputReaderFactory {

	public static MyReadable getInputReader(String type) {
		MyReadable reader = null;
		switch (type) {
		case "FILE":
			reader = new FileReader();
			break;

		default:
			System.out.println("No Such Reader available- " + type);
			break;
		}

		return reader;

	}

}
