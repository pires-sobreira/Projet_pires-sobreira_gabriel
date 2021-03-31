package com.ensta.librarymanager.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl {
    //Singleton
    private static MembreDaoImpl instance;
    private MembreDaoImpl(){};
    public static MembreDaoImpl getInstance(){
        if (instance == null) instance = new MembreDaoImpl();
        return instance;
    }

    private final String SelectAllMembreQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
	private final String SelectIDMembreQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id=?;";
	private final String CreateMembreQuery = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
	private final String UpdateMembreQuery = "UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private final String DeleteMembreQuery = "DELETE FROM membre WHERE id=?;";
	private final String CounterMembreQuery = "SELECT COUNT(id) AS count FROM membre;";


    public List<Membre> getList() throws DaoException{
        List<Membre> membre = new ArrayList<Membre>();

        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectAllMembreQuery);
            ResultSet result = preparedStatement.executeQuery();) {

            while (result.next()){
                membre.add(new Membre(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), result.getString("adresse"), Abonnement.valueOf(result.getString("abonnement"))));
            }
        }catch (SQLException e) {
            throw new DaoException("Erreur lors du téléchargement de la liste des membres de la base de données", e);
        }

        return membre;
    }

    public ResultSet GetByIdStatement(PreparedStatement preparedStatement, int id) throws SQLException {
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

	public Membre getById(int id) throws DaoException{
        Membre membre = new Membre();
        
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SelectIDMembreQuery);
            ResultSet result = GetByIdStatement(preparedStatement, id);){
            
            if (result.next()) {
                membre = new Membre(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), result.getString("adresse"), Abonnement.valueOf(result.getString("abonnement")));
            }
        } catch (SQLException e) {
            throw new DaoException("Error while uploading a membre whose id is " + id, e);
        }

        return membre;
    }

    public ResultSet CreateStatement(PreparedStatement preparedStatement, String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws SQLException {
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setString(3, adresse);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, telephone);
        preparedStatement.setString(6, abonnement.toString());
        preparedStatement.executeUpdate();
        return preparedStatement.getGeneratedKeys();
    }

	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws DaoException{
        int id = -1;
        
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CreateMembreQuery, Statement.RETURN_GENERATED_KEYS);
             ResultSet result = CreateStatement(preparedStatement, nom, prenom, adresse, email, telephone, abonnement);) { 

            if (result.next()) {
                id = result.getInt(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Erreur. Impossible de créer le membre " + nom, e);
        }
        return id;
    }

	public void update(Membre membre_) throws DaoException{
        try (Connection connection = ConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UpdateMembreQuery);) {
       
            preparedStatement.setString(1, membre_.getNom());
            preparedStatement.setString(2, membre_.getPrenom());
            preparedStatement.setString(3, membre_.getAdress());
            preparedStatement.setString(4, membre_.getEmail());
            preparedStatement.setString(5, membre_.getTelephone());
            preparedStatement.setString(6, membre_.getAbonement().toString());
            preparedStatement.setInt(7, membre_.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Erreur lors de la mise à jour d'un membre " + membre_, e);
        }
    }

	public void delete(int id) throws DaoException{
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DeleteMembreQuery);) {
            
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
		} catch (SQLException e) {
			throw new DaoException("Error while deleting a Membre whose id is " + id, e);
		}
    }

	public int count() throws DaoException{
        int n = -1;

        try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CounterMembreQuery);
             ResultSet result = preparedStatement.executeQuery();) {

            if (result.next()) {
                n = result.getInt(1);
                
            }
        } catch (SQLException e) {
            throw new DaoException("Erreur lors du comptage du nombre de membres dans la base de données", e);
        } 
        
        return n;
    }
}