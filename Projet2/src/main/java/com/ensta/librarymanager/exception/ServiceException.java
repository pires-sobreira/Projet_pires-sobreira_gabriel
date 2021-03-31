package com.ensta.librarymanager.exception;

public class ServiceException extends Exception{
    private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
    }
    public ServiceException(String averti, Throwable cause) {
		super(averti, cause);
		System.out.println("\n");
		cause.printStackTrace();
	}
	public ServiceException(String averti) {
		super(averti);
	}
}
