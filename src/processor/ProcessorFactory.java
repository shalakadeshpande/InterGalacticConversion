package processor;

public class ProcessorFactory {

	public static Processor getProcessor(String type) {
		return type.equalsIgnoreCase("GALAXY")?new GalaxyProcessor():null;

	}

}
