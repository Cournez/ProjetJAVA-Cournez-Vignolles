package bdd.creationTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableArret {
	
	/**
	 * Cette methode permet de creer la table arret dans la BDD.
	 * 
	 * @param connection : connexion a la BDD RATP
	 * @throws SQLException
	 */
	public static void creerTable(Connection connection) throws SQLException {
		String tableArret = "CREATE TABLE arret( "
				+ "id_course serial NOT NULL, heureArr character varying(256), heureDep character varying(256), id_station serial NOT NULL, "
				+ "stopSeq seriel NOT NULL, stopHeadSign serial, shapeDistTraveled serial);" ;

		String createTable = "DROP TABLE arret ;" + tableArret; //"DROP TABLE arret ;" +
		//System.out.println(createTable);
		Statement state = connection.createStatement() ;
		state.executeUpdate(createTable) ;
		System.out.println("\nTable arret creee.") ;
	}

}
