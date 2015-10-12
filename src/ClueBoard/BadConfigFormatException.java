package ClueBoard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
		
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(errorLog, true)))) {
		    out.println(errorMessage);
		}catch (IOException e) {
		    e.getMessage();
		}
	}

	@Override
	public String toString() {
		return errorMessage;
	}
	

}
