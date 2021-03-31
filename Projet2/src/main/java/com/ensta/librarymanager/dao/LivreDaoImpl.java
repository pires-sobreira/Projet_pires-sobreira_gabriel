package com.ensta.librarymanager.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;


public class LivreDaoImpl implements LivreDao{
    private static LivreDaoImpl instance;
    private LivreDaoImpl(){};
    public static LivreDaoImpl getInstance(){
        if (instance == null) instance = new LivreDaoImpl();
        return instance;
    }

    private static final String SelectAllLivreQuery = "SELECT id, titre, auteur, isbn FROM livre ";
    private static final String SelectIDLivreQuery = "SELECT id, titre, auteur, isbn FROM livre WHERE id=?;";
    private static final String CreateLivreQuery = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String UpdateLivreQuery = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
	private static final String DeleteLivreQuery = "DELETE FROM livre WHERE id=?;";
    private static final String CounterLivreQuery= "SELECT count(*) AS count FROM livre";

    public List<Livre> getList() throws DaoException{
        List<Livre> livre = new ArrayList<Livre>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SelectAllLivreQuery);
             ResultSet result = preparedStatement.executeQuery();) {

            while (result.next()){
                livre.add(new Livre(result.getInt("id"), result.getString("titre"), result.getString("auteur"), result.getString("isbn")));
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des livres de la base de données", e);
        }

        return livre;
    }

    public ResultSet GetByIdStatement(PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public Livre getById(int id) throws DaoException{
        Livre livre = new Livre();
        
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectIDLivreQuery);
            ResultSet result = GetByIdStatement(preparedStatement, id);){
            
            if (result.next()) {
                livre = new Livre(result.getInt("id"), result.getString("titre"), result.getString("auteur"), result.getString("isbn"));
            }
        } catch (SQLException e) {
            throw new DaoException("Error while uploading a books whose id is " + id + " from the database", e);
        }

        return livre;
    }

    public ResultSet CreateStatement(PreparedStatement preparedStatement, String titre, String auteur, String isbn ) throws SQLException {
        preparedStatement.setString(1, titre);
        preparedStatement.setString(2, auteur);
        preparedStatement.setString(3, isbn);
        preparedStatement.executeUpdate();
        return preparedStatement.getGeneratedKeys();
    }

    public int create(String titre, String auteur, String isbn) throws DaoException{
        int id = -1;
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CreateLivreQuery, Statement.RETURN_GENERATED_KEYS);
             ResultSet result = CreateStatement(preparedStatement, titre, auteur, isbn);) { 

            if (result.next()) {
                id = result.getInt(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur. Impossible de créer le livre " + titre, e);
        }
        return id;
    }

    public void update(Livre livre_) throws DaoException{
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UpdateLivreQuery);) {
       
       preparedStatement.setString(1, livre_.getTitre());
       preparedStatement.setString(2, livre_.getAuteur());
       preparedStatement.setString(3, livre_.getIsbn());
       preparedStatement.setInt(4, livre_.getId());
       preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise à jour d'un livre " + livre_, e);
        }
    }

    public void delete(int id) throws DaoException{
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DeleteLivreQuery);) {
            
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
		} catch (SQLException e) {
			throw new DaoException("Error while deleting a book whose id is " + id + " from the database", e);
		}
    }

    public int count() throws DaoException{
        int n = -1;

        try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CounterLivreQuery);
             ResultSet result = preparedStatement.executeQuery();) {

            if (result.next()) {
                n = result.getInt(1);
                
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage du nombre de livres dans la base de données", e);
        } 
        
        return n;
    }
}

