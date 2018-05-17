package bdd.modificationTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bdd.connexion.Connecter;

/**
 * Cette classe ne contient qu'une methode qui permet de lier chaque station a sa ligne associee.
 * @author Aelaig et Fanny
 *
 */
public class LienStationLigne {
	
	 /**
	 * Cette methode permet d'ajouter dans la BDD le numero de la ligne correspondant respectivement a la station.
	 * RAPPEL : une station est assimilee a un quai, elle est donc associee a une unique ligne.
	 * 
	 * Cette methode eut donc un usage unique de modification de la BDD.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void addNumLinesToStations() throws ClassNotFoundException, SQLException {
		int nb_station_pb = 0 ;
		
		Connecter connexion = new Connecter() ;
		Connection connection = connexion.seConnecter() ;
		
		//Creation de la colonne "ligne"
		Statement state = connection.createStatement();
		
		String sql = "ALTER TABLE station ADD ligne character varying(256) ;" ;
		
		state.executeUpdate(sql) ;
		System.out.println("Colonne ligne ajoutee.") ;
		
		state.close();
		
		//Recuperation des id_stations
		Statement statement = connection.createStatement() ;
		
		ResultSet result = statement.executeQuery("SELECT id_station FROM station ;") ;
		
		while (result.next()) {
			int id_station = result.getInt(1);
			
			//Recuperation du numero de ligne
			PreparedStatement preStatement = connection.prepareStatement("SELECT COUNT(*),nom_ligne FROM route "
					+ "JOIN course ON route.id_route = course.id_route WHERE id_course = "
								+ "(SELECT id_course FROM arret WHERE id_station = ?) ;") ;
			preStatement.setInt(1,id_station) ;
			ResultSet preResult = preStatement.executeQuery() ;
			
			int truc = preResult.getInt(1) ;	// gestion du probleme de la ligne 6 non ajoutees
			if (truc != 0) {
				String numLigne = preResult.getString(2) ;
				
				//Ajout du numero de la ligne correspondante dans la table
				
				PreparedStatement preState = connection.prepareStatement("UPDATE station SET ligne = ? WHERE id_station = ? ;") ;
				preState.setString(1, numLigne);
				preState.setInt(2, id_station);
				
				preState.executeUpdate() ;
				
				preState.close();
				
				System.out.println("Numero de ligne " + numLigne + " de la station " + id_station + " ajoute.");
			}
			else {
				nb_station_pb ++ ;
				System.out.println("Probleme avec la station " + id_station) ;
			}
			
			preResult.close();
			preStatement.close();
			
		}
		
		result.close();
		statement.close();
		
		connexion.seDeconnecter();
		
		System.out.println(nb_station_pb + " stations ont un probleme.") ;
	}
	

}
