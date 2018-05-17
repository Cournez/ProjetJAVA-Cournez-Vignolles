package calcul.tablesHoraire;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import outils.Time;
import reseau.Arret;

/**
 * Cette classe permet de calculer une partir de la table horaire composee des connexions entre stations realisees par des courses donc par trajet en train.
 * @author Aelaig et Fanny
 *
 */

public class TableCourses {


	/** Cette methode retourne la table horaire des courses restreinte à la liste des Arrets Temporaires definie sur un intervalle de temps.
	 * 
	 * @param ArretsTempo : liste des arrets evalues - type : List<Arret>
	 * @return : table horaire des courses - type : List<Troncon>
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static ArrayList<Troncon> calculer(ArrayList<Arret> ArretsTempo) throws ClassNotFoundException, SQLException {
		
		System.out.println("Veuillez patienter...  -  Etape 3/5 : Calcul de la table horaire des courses");
		
		// Liste des identifiants des courses
		ArrayList<Long> ListCourses = new ArrayList<Long>();
		
		for (Arret e:ArretsTempo) {
			if (!ListCourses.contains(e.getCourse())) {
				ListCourses.add(e.getCourse());
			}	
		}
		Collections.sort(ListCourses);
		//System.out.println(" Liste Courses remplie " + ListCourses.size());
		
		
		ArrayList<Troncon> TableHoraire = new ArrayList<Troncon>(); // Creation de la TableHoraire des Courses
		
		for (long e : ListCourses){
			
			ArrayList<Arret> ListArrets = new ArrayList<Arret>();  // repertorie tous les arrets realises par une course (vu que ArretsTempo est triee selon les heureDep ,les listes ListArrets sont chronologiques.)
			for (Arret a : ArretsTempo) {
				if (a.getCourse()==e) {
					ListArrets.add(a);
				}
			}
			for (int i = 0 ; i<ListArrets.size()-1 ; i++) {		// parcours la liste des arrets d'une course et instancie les connexions/troncons.
				
				int stationDep = ListArrets.get(i).getStation() ; 
				Time heureDep = ListArrets.get(i).getHoraire() ;
				int stationArr = ListArrets.get(i+1).getStation();
				Time heureArr = ListArrets.get(i+1).getHoraire();
				
				Troncon t1 = new Troncon ( stationDep , stationArr , heureDep , heureArr, 0 ) ; // Creation d'une nouvelle connexion
				if (true) { // !TableHoraire.contains(t1)
				TableHoraire.add(t1); // Ajout de la nouvelle connexion à la TableHoraire
				//System.out.println("Remplissage de TableCourses " + t1.getHeureDep());
				}
			}
		}		
		//System.out.println("Taille de la Table Horaire des Courses : " + TableHoraire.size());
		return TableHoraire;
	}	
}