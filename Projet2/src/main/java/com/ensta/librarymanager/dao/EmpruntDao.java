package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.persistence.*;
import com.ensta.librarymanager.model.*;
import com.ensta.librarymanager.utils.*;

public interface EmpruntDao {
	public List<Emprunt> getList() throws DaoException;
	public List<Emprunt> getListCurrent() throws DaoException;
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public Emprunt getById(int id) throws DaoException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException;
	public void update(Emprunt emprunt_) throws DaoException;
	public int count() throws DaoException;
}
