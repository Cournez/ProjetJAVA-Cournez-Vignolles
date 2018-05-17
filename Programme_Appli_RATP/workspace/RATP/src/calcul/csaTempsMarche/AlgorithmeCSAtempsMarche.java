package calcul.csaTempsMarche;

import java.sql.SQLException;
import java.util.ArrayList;

//import application.utilisateur.Requete;
import calcul.tablesHoraire.TableHoraire;
import calcul.tablesHoraire.Troncon;
import outils.Time;


/**
 * Cette classe permet de mettre en oeuvre le calcul d'itineraire dans le cas ou le voyageur choisi de selectionner son trajet d'apres le critere : " trajet comportant le minimum de temps de trajet a pieds "
 * Elle ne comporte que 2 methodes car elle se refere a l'algorithme deja implemente du csa.
 * @author Aelaig et Fanny
 * 
 * @attribut tableHoraire : liste des connexions/troncons a etudier  -  type : ArrayList<Troncon> .
 */

public class AlgorithmeCSAtempsMarche {
	
	private static ArrayList<Troncon> tableHoraire = new ArrayList<Troncon>();

	
	/**
	 * Cette methode permet de modifier la table horaire afin d'annuler les temps de trajet lies aux courses.
	 * Ainsi on ne considerera que les durees liees aux correspondances et donc les temps de marche.
	 */
	public static void modifTable () {
		
		for (Troncon t : tableHoraire) {
			if (t.getType()==0) {	// si le troncon est une course
				t.setHeureArr(t.getHeureDep());	// alors je modifie l'heure d'arrivee = heure depart ainsi le trajet en sorti du csa ne dependra que de la duree des coorespondances.
			}
		}		
	}

	/**
	 * Cette methode est similaire a la methode csa.AlgorithmeCSA.calculerItineraire, seulement elle modifie la table horaire au prealable pour ne compter que la duree parcourue a pieds lors de correspondances.
	 * 
	 * @param stationDep : identifiant de la station de depart  type : int
	 * @param stationArr : identifiant de la station d'arrivee  type : int
	 * @param heureDep : heure de depart souhaitee  type : Time
	 * @return : la liste des stations a parcourir
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static ArrayList<Integer> calculerItineraire (int stationDep , int stationArr , Time heureDep) throws ClassNotFoundException, SQLException {
		
		// Calcul de la table horaire
		calcul.csa.AlgorithmeCSA.ArretTempo = TableHoraire.calculerArretsTempo(heureDep);
		tableHoraire = TableHoraire.calculer(calcul.csa.AlgorithmeCSA.ArretTempo);
		modifTable();
		calcul.csa.AlgorithmeCSA.tableHoraire = tableHoraire ;
		
		System.out.println("Veuillez patienter...  -  Etape 5/5 : Calcul de l'itineraire via l'algorithme CSA Temps de Marche");
		
		calcul.csa.AlgorithmeCSA.calculerTrajetsOpti(stationDep , heureDep );
		return calcul.csa.AlgorithmeCSA.retrouverIti(stationArr) ; 
	}
	
	/*		// TEST ALGORITHME CSA MODIFIE //
	 * 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> iti = calculerItineraire(2200 , 2498 , new Time("06:00:00"));

		Requete.afficherItineraire(iti);
	}
	*/
	

}
