package writer;

public class WriterFactory {

	public static Writer getWriter(String type) {

		return type.equalsIgnoreCase("FILE") ? new FileOutputWriter() : null;

	}

}
