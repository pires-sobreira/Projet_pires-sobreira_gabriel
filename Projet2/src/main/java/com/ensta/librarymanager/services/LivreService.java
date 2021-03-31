package com.ensta.librarymanager.services;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.model.*;

public interface LivreService {

	public List<Livre> getList() throws ServiceException;
	public List<Livre> getListDispo() throws ServiceException;
	public Livre getById(int id) throws ServiceException;
	public int create(String titre, String auteur, String isbn) throws ServiceException;
	public void update(Livre livre) throws ServiceException;
	public void delete(int id) throws ServiceException;
	public int count() throws ServiceException;
}
