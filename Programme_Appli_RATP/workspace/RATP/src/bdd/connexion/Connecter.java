package bdd.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe gere la connexion à la BDD RATP.
 * @author Aelaig et Fanny
 * 
 * @attributs connection : porte la connection à la BDD  -  type : Connection
 */

public class Connecter {
	
	public Connection connection ;
	
	public Connecter() {
		this.connection = null ;
	}
	
	
	/**
	 * Cette methode permet d'ouvrir la connection.
	 * 
	 * @return : la connection
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection seConnecter() throws SQLException, ClassNotFoundException {
		// on charge le driver sqlite-JDBC 
		Class.forName("org.sqlite.JDBC") ;
		
		this.connection = DriverManager.getConnection("jdbc:sqlite:RATP.db") ;
		
		//System.out.println("\nConnection etablie :)") ;
		
		return this.connection ;
	}
	
	
	/**
	 * Cette methode permet de fermer la connection.
	 * 
	 * @throws SQLException
	 */
	public void seDeconnecter() throws SQLException {
		this.connection.close();
		
		//System.out.println("\nDeconnection reussie, au revoir :)");
	}


}
