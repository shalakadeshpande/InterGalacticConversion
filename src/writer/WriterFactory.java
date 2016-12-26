package writer;


public class WriterFactory {
	
	public static Writer getWriter(String type) {
		Writer writer = null;
		switch (type) {
		case "FILE":
			writer = new FileOutputWriter();
			break;

		default:
			System.out.println("No Such writer available- " + type);
			break;
		}

		return writer;

	}

}
