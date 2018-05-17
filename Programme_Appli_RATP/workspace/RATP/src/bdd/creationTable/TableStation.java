package bdd.creationTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableStation {
	
	/**
	 * Cette methode permet de creer la table station dans la BDD.
	 * 
	 * @param connection : connexion a la BDD RATP
	 * @throws SQLException
	 */
	public static void creerTable(Connection connection) throws SQLException {
		String tableStation = "CREATE TABLE station( "
				+ "id_station serial NOT NULL, stop_code serial, nomStation character varying(256) NOT NULL, adresse character varying(256) NOT NULL,"
				+ "coordlat float NOT NULL, coordlong float NOT NULL, locationType serial, parentStation serial, enTravaux boolean DEFAULT false, "
				+ "CONSTRAINT station_pkey PRIMARY KEY (id_station) );" ;

		String createTable = "DROP TABLE station ;" + tableStation; //
		//System.out.println(createTable);
		Statement state = connection.createStatement() ;
		state.executeUpdate(createTable) ;
		System.out.println("\nTables station creee.") ;
	}
	
}
