package inputReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader implements Reader {

	@Override
	public List<String> read() {
		List<String> contents = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("input.txt")))) {

			String line = reader.readLine();
			while (line != null) {
				contents.add(line);
				line = reader.readLine();
			}

		} catch (IOException ex) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return contents;

	}

}
