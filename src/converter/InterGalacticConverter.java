package converter;

import inputReader.InputReaderFactory;
import inputReader.Reader;

import java.util.List;

import processor.Processor;
import processor.ProcessorFactory;
import writer.WriterFactory;

public class InterGalacticConverter {

	public String parseInputNotes() {
		Reader reader = InputReaderFactory.getInputReader("FILE");
		List<String> fileContents = reader.read();

		Processor processor = ProcessorFactory.getProcessor("GALAXY");
		String outputToFile = processor.process(fileContents);

		return outputToFile;
	}

	public static void main(String[] args) {
		InterGalacticConverter interGalacticConverter = new InterGalacticConverter();
		String outputToFile = interGalacticConverter.parseInputNotes();

		boolean success = writeToFile(outputToFile);

		if (success) {
			System.out
					.println("SuccessFully Written output to output.txt file");
		} else {
			System.out.println("Failed to write output to file");
		}
	}

	private static boolean writeToFile(String outputToFile) {
		return WriterFactory.getWriter("FILE").write(outputToFile);
	}
}
