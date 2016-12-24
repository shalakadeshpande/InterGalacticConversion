package InputReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader implements MyReadable {

	@Override
	public List<String> read() {
		List<String> contents = new ArrayList<String>();
		
		System.out.println("Read file - platform independent code here..");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("/Users/deshpash/input.txt")))) {

			// reading file content line by line
			String line = reader.readLine();
			while (line != null) {
				contents.add(line);
				line = reader.readLine();
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		
		return contents;

	}
	
}
