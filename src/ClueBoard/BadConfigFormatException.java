package ClueBoard;

public class BadConfigFormatException extends Exception{
	
	public BadConfigFormatException()
	{
		super("Error");
	}
	
	public BadConfigFormatException(String message)
	{
		super(message);
	}

}
