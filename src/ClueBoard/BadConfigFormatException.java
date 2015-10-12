package ClueBoard;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	private String errorLog = "ErrorLog.txt";
	private String errorMessage;
	public BadConfigFormatException()
	{
		super("Error");
	}
	
	public BadConfigFormatException(String message)
	{
		super(message);
		errorMessage = message;
		try {
			PrintWriter out = new PrintWriter(errorMessage + ".txt");
			out.println(toString());
			out.close();
		} catch(FileNotFoundException e) {
			System.out.print(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return errorMessage;
	}
	

}
