package bdd.creationTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCorrespondance {
	
	/**
	 * Cette methode permet de creer la table correspondance dans la BDD.
	 * 
	 * @param connection : connexion a la BDD RATP
	 * @throws SQLException
	 */
	public static void creerTable(Connection connection) throws SQLException {
		String tableCorresp = "CREATE TABLE correspondance ( "
				+ "id_station_origine serial NOT NULL, id_station_destination serial NOT NULL,"
				+ "typeCorresp integer NOT NULL, tempsCorresp varying(256) NOT NULL);" ;

		String createTable = "DROP TABLE correspondance ;" + tableCorresp; //"DROP TABLE correspondance ;" +
		//System.out.println(createTable);
		Statement state = connection.createStatement() ;
		state.executeUpdate(createTable) ;
		System.out.println("\nTable correspondance creee.") ;
	}

}
