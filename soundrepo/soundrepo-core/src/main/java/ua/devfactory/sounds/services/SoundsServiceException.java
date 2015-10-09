package ua.devfactory.sounds.services;

@SuppressWarnings("serial")
public class SoundsServiceException extends Exception {

	public SoundsServiceException() {
		super();
	}
	
	public SoundsServiceException (String message){
		super(message);
	}
	
	public SoundsServiceException (String message, Throwable cause){
		super(message, cause);
	}
}
