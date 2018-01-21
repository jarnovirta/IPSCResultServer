package fi.ipscResultServer.exception;

public class DatabaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Exception wrappedException;
	
	public DatabaseException(String message, Exception e) {
		super(message);
		this.wrappedException = e;
	}
}
