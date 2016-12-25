package InputReader;

public class InputReaderFactory {

	public static Reader getInputReader(String type) {
		Reader reader = null;
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
