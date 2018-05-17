package calcul.tablesHoraire;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bdd.connexion.Connecter;
import outils.Time;
import reseau.Arret;
import reseau.Correspondance;

/**
 * Cette classe permet de calculer une partie de la table Horaire liee aux connexions entre stations realisees par des correspondances.
 * @author Aelaig et Fanny
 *
 */

public class TableCorrespondances {

	/** Cette methode retourne la table horaire des correspondances restreinte � la liste des Arrets Temporaires definie sur un intervalle de temps.
	 * 
	 * @param ArretsTempo : liste des arrets evalues - type : List<Arret>
	 * @return : table horaire des courses - type : List<Troncon>
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static ArrayList<Troncon> calculer(ArrayList<Arret> ArretsTempo) throws ClassNotFoundException, SQLException {
		
		System.out.println("Veuillez patienter...  -  Etape 4/5 : Calcul de la table horaire des correspondances");

		Connecter test = new Connecter() ;
		Connection connection = test.seConnecter() ; // Connexion a la BDD RATP
		Statement state = connection.createStatement() ;
		

		// Creation d'une liste comprenant toutes les correspondances
		ArrayList<Correspondance> ListCorresp = new ArrayList<Correspondance>(); 
		
		ResultSet result = state.executeQuery("select * from correspondance order by id_station_origine") ;

		while (result.next()) {	
			
			Correspondance c1 = new Correspondance(result.getInt("id_station_origine"),result.getInt("id_station_destination"),new Time(result.getInt("tempsCorresp")));
			ListCorresp.add(c1);
			
		}
		result.close(); 
		state.close();
		test.seDeconnecter();
		//System.out.println(" Liste des correspondances remplie, taille : " + ListCorresp.size());
		
		// Creation de la Table Horaire des correspondances
		
		ArrayList<Troncon> TableHoraire = new ArrayList<Troncon>(); // Creation de la TableHoraire

		for (Correspondance e : ListCorresp){ // parcourt toutes les correspondances et a chaque arrets sur l'une des stations d'une d'elles, creation d'une connexion/troncon dans la table horaire.
			
			for (Arret a : ArretsTempo) {
				if (a.getStation() == e.getStation1()) {	// cas n 1 : arret a la station1 de la Correspondance
					int stationDep = e.getStation1();
					int stationArr = e.getStation2();
					Time heureDep = a.getHoraire();
					Time heureArr = Time.somme(heureDep , e.getTemps());

					Troncon t1 = new Troncon ( stationDep , stationArr , heureDep , heureArr, 1 ) ; // Creation d'une nouvelle connexion
					if (!TableHoraire.contains(t1)) {
					TableHoraire.add(t1); // Ajout de la nouvelle connexion a la TableHoraire
					//System.out.println("Remplissage Table Correspondances " + t1.getHeureDep());
					}
				
				}
				else if (a.getStation() == e.getStation2()){	// cas n 2 : arret a la station2 de la Correspondance
					int stationDep = e.getStation2();
					int stationArr = e.getStation1();
					Time heureDep = a.getHoraire();
					Time heureArr = Time.somme(heureDep , e.getTemps());

					Troncon t1 = new Troncon ( stationDep , stationArr , heureDep , heureArr, 1 ) ; // Creation d'une nouvelle connexion
					if (true) { // !TableHoraire.contains(t1)
					TableHoraire.add(t1); // Ajout de la nouvelle connexion a la TableHoraire
					//System.out.println("Remplissage Table Correspondances " + t1.getHeureDep());
					}
				}
			}	
		}
		//System.out.println("Taille de la Table Horaire des Correspondances : " + TableHoraire.size());
		return TableHoraire;
	}
}


