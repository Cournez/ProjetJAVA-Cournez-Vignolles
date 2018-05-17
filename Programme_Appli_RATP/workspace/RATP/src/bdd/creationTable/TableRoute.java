package bdd.creationTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRoute {
	
	/**
	 * Cette methode permet de creer la table route dans la BDD.
	 * 
	 * @param connection : connexion a la BDD RATP
	 * @throws SQLException
	 */
	public static void creerTable(Connection connection) throws SQLException {
		String tableRoute = "CREATE TABLE route( "
				+ "id_route serial NOT NULL, id_agency serial NOT NULL, nom_ligne character varying(256) NOT NULL, trajet character varying(256) NOT NULL,"
				+ "route_desc serial NOT NULL, route_type serial NOT NULL, route_url serial NOT NULL, route_color character varying(256) NOT NULL,"
				+ " route_text_color seriel NOT NULL, CONSTRAINT route_pkey PRIMARY KEY (id_route) );" ;

		String createTable = "DROP TABLE route;" + tableRoute ;	//"DROP TABLE route;" +
		//System.out.println(createTable);
		Statement state = connection.createStatement() ;
		state.executeUpdate(createTable) ;
		System.out.println("\nTable route creee.") ;
	}

}
