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

public interface MembreService {

	public List<Membre> getList() throws ServiceException;
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException;
	public Membre getById(int id) throws ServiceException;
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws ServiceException;
	public void update(Membre membre) throws ServiceException;
	public void delete(int id) throws ServiceException;
	public int count() throws ServiceException;

}
