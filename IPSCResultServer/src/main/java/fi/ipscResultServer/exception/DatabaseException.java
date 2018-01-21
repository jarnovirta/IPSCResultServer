package fi.ipscResultServer.exception;

public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Exception wrappedException;
	
	public DatabaseException(Exception e) {
		super("Error during database operation: " + e.getMessage());
	}
}
