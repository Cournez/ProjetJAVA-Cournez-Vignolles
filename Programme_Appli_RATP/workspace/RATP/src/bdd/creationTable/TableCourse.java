package bdd.creationTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCourse {
	
	/**
	 * Cette methode permet de creer la table course dans la BDD.
	 * 
	 * @param connection : connexion a la BDD RATP
	 * @throws SQLException
	 */
	public static void creerTable(Connection connection) throws SQLException {
		String tableCourse = "CREATE TABLE course( "
				+ "id_route serial NOT NULL, id_service serial NOT NULL, id_course serial NOT NULL, "
				+ "nom_course character varying(256), nomCourtCourse character varying(256), id_direction serial NOT NULL,"
				+ "shape_id serial);" ;

		String createTable = "DROP TABLE course ;" + tableCourse; //
		//System.out.println(createTable);
		Statement state = connection.createStatement() ;
		state.executeUpdate(createTable) ;
		System.out.println("\nTable course creee.") ;
	}


}
