package com.ensta.librarymanager.exception;

public class DaoException extends Exception{
    private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
    }
	public DaoException(String averti, Throwable cause) {
		super(averti, cause);
	}
	public DaoException(String averti) {
		super(averti);
	}
}
