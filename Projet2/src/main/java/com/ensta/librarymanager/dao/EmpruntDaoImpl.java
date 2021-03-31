package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.model.Abonnement;

public class EmpruntDaoImpl implements EmpruntDao{

    //Singleton
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl(){};
    public static EmpruntDaoImpl getInstance(){
        if (instance == null) instance = new EmpruntDaoImpl();
        return instance;
    }
    
    private final String SelectAllEmpruntQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private final String SelectNoReturnedEmpruntQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private final String SelectNotReturnedMemEmpruntQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private final String SelectNotReturnedLivEmpruntQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private final String SelectIDEmpruntQuery = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private final String CreateEmpruntQuery = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private final String UpdateEmpruntQuery = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
    private final String CountEmpruntQuery = "SELECT COUNT(*) AS count FROM emprunt WHERE idMembre IN (SELECT id FROM membre) and idLivre IN (SELECT id FROM livre);";
    

    public List<Emprunt> getList() throws DaoException{
        List<Emprunt> emprunt = new ArrayList<Emprunt>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectAllEmpruntQuery);
             ResultSet result = preparedStatement.executeQuery();) {
            
                MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
                LivreDao livreDao = LivreDaoImpl.getInstance();
                while (result.next()){
                    emprunt.add(new Emprunt(result.getInt("id"), membreDaoImpl.getById(result.getInt("idMembre")).getId(), livreDao.getById(result.getInt("idLivre")).getId(), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate())); 
                }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des emprunts de la base de données", e);
        }

        return emprunt;
    }



    public List<Emprunt> getListCurrent() throws DaoException{
        List<Emprunt> current = new ArrayList<Emprunt>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectNoReturnedEmpruntQuery);
             ResultSet result = preparedStatement.executeQuery();) {
            
                MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
                LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
                while (result.next()){
                    current.add(new Emprunt(result.getInt("id"), membreDaoImpl.getById(result.getInt("idMembre")).getId(), livreDao.getById(result.getInt("idLivre")).getId(), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate())); 
                }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des emprunts current de la base de données", e);
        }
        return current;
    }

    public ResultSet GetByMembreStatement(PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        List<Emprunt> membrEmprunts = new ArrayList<Emprunt>();
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectNotReturnedMemEmpruntQuery);
             ResultSet result = GetByMembreStatement(preparedStatement, idMembre);) {
            
                MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
                LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
                while (result.next()){
                    membrEmprunts.add(new Emprunt(result.getInt("id"), membreDaoImpl.getById(result.getInt("idMembre")).getId(), livreDao.getById(result.getInt("idLivre")).getId(), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate())); 
                }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des emprunts current par membre de la base de données", e);
        }

        return membrEmprunts;
    }

    public ResultSet GetByLivreStatement(PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<Emprunt> livreEmprunts = new ArrayList<Emprunt>();
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectNotReturnedLivEmpruntQuery);
             ResultSet result = GetByLivreStatement(preparedStatement, idLivre);) {
            
                MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
                LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
                while (result.next()){
                    livreEmprunts.add(new Emprunt(result.getInt("id"), membreDaoImpl.getById(result.getInt("idMembre")).getId(), livreDao.getById(result.getInt("idLivre")).getId(), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate())); 
                }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des emprunts current par livre de la base de données", e);
        }

        return livreEmprunts;
    }

    public Emprunt getById(int id) throws DaoException{
        List<Emprunt> idEmprunts = new ArrayList<Emprunt>();
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectIDEmpruntQuery);
             ResultSet result = GetByMembreStatement(preparedStatement, id);) {
            
                MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
                LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
                while (result.next()){
                    idEmprunts.add(new Emprunt(result.getInt("id"), membreDaoImpl.getById(result.getInt("idMembre")).getId(), livreDao.getById(result.getInt("idLivre")).getId(), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate())); 
                }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des emprunts par Id de la base de données", e);
        }

        return idEmprunts.get(0);
    }

    public ResultSet CreateStatement(PreparedStatement preparedStatement, int idMembre, int idLivre, LocalDate dateEmprunt) throws SQLException {
        preparedStatement.setInt(1, idMembre);
        preparedStatement.setInt(2, idLivre);
        preparedStatement.setString(3, dateEmprunt + "");
        preparedStatement.setDate(4, null);
        preparedStatement.executeUpdate();
        return preparedStatement.getGeneratedKeys();
    }

    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
        int id;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CreateEmpruntQuery, Statement.RETURN_GENERATED_KEYS);
             ResultSet result = CreateStatement(preparedStatement, idMembre, idLivre, dateEmprunt);) {
                if (result.next()) {
                    id = result.getInt(1);
                }
        } catch (SQLException e) {
            throw new DaoException("Erreur. Impossible de créer l'emprunt ", e);
        }
        
    }

    public void update(Emprunt emprunt_) throws DaoException{
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UpdateEmpruntQuery);) {
       
            preparedStatement.setInt(1, emprunt_.getIdMembre());
            preparedStatement.setInt(2, emprunt_.getIdLivre());
            preparedStatement.setString(3, emprunt_.getDateEmprunt()+"");
            preparedStatement.setString(4, emprunt_.getDateRetour()+"");
            preparedStatement.setInt(5, emprunt_.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise à jour d'un livre " + emprunt_, e);
        }
    }

    public int count() throws DaoException{
        int nombreEmprunts = -1;

        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CountEmpruntQuery);
            ResultSet result = preparedStatement.executeQuery();) {

            if (result.next()) {
                nombreEmprunts = result.getInt(1);   
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage du nombre de emprunts dans la base de données", e);
        } 

        return nombreEmprunts;
    }
}
