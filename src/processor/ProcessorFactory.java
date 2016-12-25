package processor;

public class ProcessorFactory {

	public static Processor getProcessor(String type) {
		Processor processor = null;
		switch (type) {
		case "GALAXY":
			processor = new GalaxyProcessor();
			break;

		default:
			System.out.println("No Such processor available- " + type);
			break;
		}

		return processor;

	}

}
