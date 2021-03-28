package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;

public class EmpruntDaoImpl implements EmpruntDao{
    private List<Emprunt> emprunt = new ArrayList<Emprunt>();

    public List<Emprunt> getList() throws DaoException{
        return this.emprunt;
    }
    public List<Emprunt> getListCurrent() throws DaoException{
        List<Emprunt> current = new ArrayList<Emprunt>();
        
    }
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        
    }
}
