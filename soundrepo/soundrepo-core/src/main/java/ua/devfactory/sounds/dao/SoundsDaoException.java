package ua.devfactory.sounds.dao;

@SuppressWarnings("serial")
public class SoundsDaoException extends Exception {

	public SoundsDaoException() {
		super();
	}
	
	public SoundsDaoException (String message){
		super(message);
	}
	
	public SoundsDaoException (String message, Throwable cause){
		super(message, cause);
	}
}
