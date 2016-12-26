package writer;

import java.io.IOException;
import java.io.PrintWriter;

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
			// do something
		}
		return status;
	}

}
