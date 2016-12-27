package main;

import inputReader.FileReader;
import inputReader.InputReaderFactory;
import inputReader.Reader;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import processor.Processor;
import processor.ProcessorFactory;
import writer.WriterFactory;

public class MerchantsGuide {

	public String readNotesFromFile() {
		Reader reader = InputReaderFactory.getInputReader("FILE");
		List<String> fileContents = (reader != null) ? reader.read() : null;

		if (fileContents == null || fileContents.isEmpty()) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					"No such reader available");
			return null;
		}
		Processor processor = ProcessorFactory.getProcessor("GALAXY");
		String outputToFile = (processor != null) ? processor
				.process(fileContents) : null;

		return outputToFile;
	}
	
	public boolean writeOutputToFile(String outputToFile) {
		return WriterFactory.getWriter("FILE").write(outputToFile);
	}

	public static void main(String[] args) {
		
		MerchantsGuide interGalacticConverter = new MerchantsGuide();
		
		String outputToFile = interGalacticConverter.readNotesFromFile();

		if (outputToFile == null) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					"No such processor available");

		}
		boolean success = interGalacticConverter.writeOutputToFile(outputToFile);

		if (success) {
			Logger.getLogger(FileReader.class.getName()).log(Level.INFO,
					"SuccessFully Written output to file - output.txt");
		} else {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					"Failed to write output to file");
		}
	}
	
}
