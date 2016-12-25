package converter;

import java.util.List;

import processor.Processor;
import processor.ProcessorFactory;
import InputReader.InputReaderFactory;
import InputReader.Reader;

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

		System.out.println(outputToFile);
	}
}
