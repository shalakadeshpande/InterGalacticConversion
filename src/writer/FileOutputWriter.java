package writer;

import inputReader.FileReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOutputWriter implements Writer {

	@Override
	public boolean write(String content) {
		boolean status = false;
		try {
			PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
			writer.print(content);
			writer.close();
			status = true;
		} catch (IOException e) {
			Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return status;
	}

}
