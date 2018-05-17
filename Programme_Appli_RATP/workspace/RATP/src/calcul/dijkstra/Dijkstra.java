package calcul.dijkstra;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//import application.utilisateur.Requete;
import calcul.tablesHoraire.TableHoraire;
import calcul.tablesHoraire.Troncon;
import outils.Time;
import reseau.Arret;
import reseau.Station;

/**
 * Cette classe permet la mise en oeuvre de l'algorithme de Dijkstra afin de determiner le trajet comprenant le moins de correspondances.
 * @author Aelaig et Fanny
 *
 */

public class Dijkstra {
	
	
	/**
	 * Cette methode permet d'identifier le poid minimal dans une liste afin de determiner une des stations les plus proches (en terme de correspondances)
	 * 
	 * @param statDep : identifiant de la station de depart  -  type : int
	 * @param poidsStationNonVisitees  : liste des poids a trier  -  type : ArrayList<Integer>
	 * @param stationsNonVisitees  : liste des identifiant des stations associees aux poids a trier  -  type : ArrayList<Integer>
	 * @param listStation   :  liste complete des identifiants de stations  -  type : ArrayList<Integer> .
	 * @return : identifiant de la station dont le poids est minimal  -  type : int.
	 */
	public static int chercherMin(int statDep, ArrayList<Integer> poidsStationNonVisitees, ArrayList<Integer> stationsNonVisitees, ArrayList<Integer> listStation) {
		// Initialisation
		boolean enRecherche = true ;
		int poidMin = 0 ;
		int i_poid = 0 ;
		
		while (enRecherche) {
			i_poid = poidsStationNonVisitees.indexOf(poidMin) ;	//retourne -1 si poidMin pas present et sinon son indice
			if (i_poid == -1) {	//si le poidMin n'est pas present
				poidMin ++ ;		//on incremente
			}
			else {	//on a trouve une station avec poidMin en valeur de poid
				enRecherche = false ;
			}
		}
		int id_station = stationsNonVisitees.get(i_poid) ;	//le vrai id de la station
		
		//System.out.println("Poids min : " + poidMin + " station n " + id_station) ;
		
		return id_station ;
	}
	
	
 
	/**
	 * Cette methode permet de calculer l'itineraire comprenant le moins de correspondances d'apres l'algorithme du plus court chemin de Dijkstra
	 * Ainsi on attribue un poids aux correspondances = 1 et aux courses = 0. 
	 * 
	 * 
	 * @param statDep : station de depart // voir si on ne pas mettre identifiant directement
	 * @param statArr : station d'arrivee
	 * @param heureDepart : heure de depart  -  type : Time
	 * @return : liste des identifiants des stations parcourues  - type : ArrayList<Integer>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Integer> calculerDijkstra(int id_statDep, int id_statArr, Time heureDepart) throws ClassNotFoundException, SQLException {
		
		//Initialisation 
		
		ArrayList<Arret> ArretTempo = TableHoraire.calculerArretsTempo(heureDepart);
		List<Troncon> tableHoraire = TableHoraire.calculer(ArretTempo);
		ArrayList<Integer> listStation = Station.listerStation(id_statDep , ArretTempo) ;
		
		System.out.println("Veuillez patienter...  -  Etape 5/5 : Calcul de l'itineraire via l'algorithme de Dijkstra");
		
				//a l'indice i on trouve l'identifiant de la station de poid min menant a la station d'indice i de listStation
		ArrayList<Integer> stationPrecedente = new ArrayList<Integer>() ; 
		
		ArrayList<Integer> stationsVisitees = new ArrayList<Integer>() ; //station vue
		stationsVisitees.add(id_statDep) ;	//la station de depart est forcement vue
		ArrayList<Integer> stationsNonVisitees = new ArrayList<Integer>() ; //station non vue encore
		
		//Initialisation de la liste de poids initiaux avec a la case i, le poid de correspondance entre la statDep et la stat i de listStation
		//System.out.println("Initialisation") ;
		ArrayList<Integer> poids = new ArrayList<Integer>() ;
		Iterator<Integer> it = listStation.iterator() ;
		while (it.hasNext()) {
			int id_statDestination = it.next() ;
			if (id_statDestination == id_statDep) {
				poids.add(0) ;
				stationPrecedente.add(id_statDep) ;
			}
			else {
				Troncon t = Troncon.rechercherTroncon(id_statDep, id_statDestination, tableHoraire); // on recherche le troncon dans la table horaire utilisee
				if (!t.isNull()) {						// s'il existe, on recupere son poids ( =1 si correspondance / =0 si course) 
					int poidTroncon = t.getType() ;
					poids.add(poidTroncon) ;
					//System.out.println("troncon : " + t + " poids : " + t.getType()) ;
					stationPrecedente.add(id_statDep) ; //troncon existant entre cette station et celle de depart, donc celle de depart est la precedente
				}
				else {	//si pas de troncon, station inaccessible directement
					poids.add(-1) ;
					stationPrecedente.add(-1) ; //on ne connait pas encore la station precedente
				}
				stationsNonVisitees.add(id_statDestination) ;	//Initialisation des stations non traitees
			}
		}
		
		//System.out.println(listStation + "\nPoids : " + poids + "\nPrecedente : " + stationPrecedente) ;
		
		// Boucle
		boolean enRecherche = !stationsNonVisitees.isEmpty() ;
		while (enRecherche) {
			//System.out.println("\nboucle en recherche") ;
			
			//on recupere seulement les poids des stations non visitees pour faire le chercherMin() apres
			ArrayList<Integer> poidsStationsNonVisitees = new ArrayList<Integer>() ;
			Iterator<Integer> iterateur = stationsNonVisitees.iterator() ;
			while (iterateur.hasNext()) {
				//System.out.println("boucle iteration") ;
				int indice = listStation.indexOf(iterateur.next()) ;
				poidsStationsNonVisitees.add(poids.get(indice)) ;
			}
			
			//on recupere la station statPoidMin la moins loin de notre depart
			int id_statPoidMin = chercherMin(id_statDep, poidsStationsNonVisitees, stationsNonVisitees, listStation) ;	//id de la station de poid minimal
			int pos_statPoidMin = listStation.indexOf(id_statPoidMin) ;

			if (id_statArr == id_statPoidMin) {	//on a trouve le plus court chemin pour aller a statArr
				//System.out.println(stationsVisitees) ;
				stationsNonVisitees.remove(Integer.valueOf(id_statPoidMin)) ;
				stationsVisitees.add(id_statPoidMin) ;
				//System.out.println("Poids final :" + poids.get(pos_statPoidMin)) ;
				enRecherche = false ;
			}
			else {	//statArr non encore atteinte
				// on traite statDestination donc on le retire de la liste des non visitees et l'ajoute aux visitees
				stationsNonVisitees.remove(Integer.valueOf(id_statPoidMin)) ;
				poidsStationsNonVisitees.remove(Integer.valueOf(id_statPoidMin)) ;
				stationsVisitees.add(id_statPoidMin) ;

				//mise a jour des poids depuis la station avec le poid minimal aux autres
				Iterator<Integer> iter = stationsNonVisitees.iterator() ;
				while (iter.hasNext()) {		//pour toutes stations stat non encore visitees
					int id_stat = iter.next();	//recupere l'identifiant de la station

					//on cherche le chemin le moins lourd depuis la station statPoidMin
					Troncon t = Troncon.rechercherTroncon(id_statPoidMin, id_stat, tableHoraire) ;
					int poidTroncon = t.getType() ;
					
					if (t.isNull()) {	//aucun troncon entre statPoidMin et stat
					}
					else {
						int pos_statNonVisitee = listStation.indexOf(Integer.valueOf(id_stat)) ;
						int poid_statNonVisitee = poids.get(pos_statNonVisitee) ;
						int poidFromStatMin = poids.get(pos_statPoidMin) + poidTroncon ;
						int newPoid ;
						if (poidFromStatMin < poid_statNonVisitee) {
							newPoid = poidFromStatMin ;
							//le nouveau chemin le plus rapide pour atteindre  la station est en passant par la statPoidMin
							stationPrecedente.set(pos_statNonVisitee, id_statPoidMin) ;	
							//System.out.println("La station precedente " + id_stat + " est " + id_statPoidMin);
						}
						else if (poid_statNonVisitee <0){
							newPoid = poidFromStatMin ;
							stationPrecedente.set(pos_statNonVisitee, id_statPoidMin) ;	
							//System.out.println("La station precedente " + id_stat + " est " + id_statPoidMin);
						}
						else {
							newPoid = poid_statNonVisitee ;
						}
						//System.out.println("poids\nNon visitees : " + poid_statNonVisitee + "\nVisitee : " + poidFromStatMin + "\nNew : " + newPoid);

						poids.set(pos_statNonVisitee, newPoid) ;
					}
				}
				//System.out.println(stationsVisitees) ;
				enRecherche = !stationsNonVisitees.isEmpty() ;	//s'il n'y a plus de stations a voir alors on a fini de chercher
			}
		}
		//System.out.println("Fin");
		
		ArrayList<Integer> trajet = retracerChemin(id_statDep, id_statArr, listStation, stationPrecedente);
		
		int nb_station = stationsVisitees.size() ;
		int poid_trajet = poids.get(listStation.indexOf(stationsVisitees.get(nb_station -1))) ;
		System.out.println("\nnb Station Visitees : " + nb_station + "\nItineraire : " + trajet + "\nNombre de correspondances : " + poid_trajet);
		
		return trajet ;  // return bien un ArrayList<Integer>
	}
	
	/**
	 * Cette methode permet de retrouver l'itineraire a parcourir.
	 * 
	 * @param statDep : identifiant de la station de depart  -  type : int
	 * @param statArr : identifiant de la station d'arrivee  -  type : int
	 * @param listStation : liste complete des stations  -  type : ArrayList<Integer>
	 * @param stationPrecedente : liste des stations precedentes  -  type : ArrayList<Integer>
	 * @return : liste des stations a parcourir dans l'ordre chronologique  -  type : ArrayList<Integer> .
	 */
	
	public static ArrayList<Integer> retracerChemin(int statDep, int statArr, ArrayList<Integer> listStation, ArrayList<Integer> stationPrecedente) {
		//System.out.println("Retracer Chemin");
		//Initialisation
		ArrayList<Integer> trajetInverse = new ArrayList<Integer>() ;
		trajetInverse.add(statArr) ;
		
		//on retrace le chemin a l'envers
		int stat_traitee = statArr ;
		while (stat_traitee != statDep){
			//System.out.println(stat_traitee);
			int pos_stat_traitee = listStation.indexOf(stat_traitee) ;
			int newStat = stationPrecedente.get(pos_stat_traitee) ;
			
			trajetInverse.add(newStat) ;
			stat_traitee = newStat ;
		}
		
		Collections.reverse(trajetInverse);
		
		return trajetInverse ;	//trajet
	}

	
	/*		// TEST ALGORITHME CALCUL MOINS DE CORRESPONDANCES //
	 * 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Station s1 = new Station();
		Station s2 = new Station();
		s1.setId(2443);
		s2.setId(1746);
		
		ArrayList<Integer> trajet = calculerDijkstra(s1.getId(),s2.getId(),new Time("08:00:00"));
		System.out.println("Trajet : " + trajet);
		Requete.afficherItineraire(trajet) ;
		
		
		ArrayList<Integer> listStation = new ArrayList<Integer>() ;
		listStation.add(1526) ;
		listStation.add(1527) ;
		listStation.add(1528) ;
		listStation.add(1530) ;
		listStation.add(1529) ;
		listStation.add(1531) ;
		listStation.add(1533) ;
		listStation.add(1532) ;
		listStation.add(1534) ;
		
		ArrayList<Integer> trajet = new ArrayList<Integer>() ;
		
		ArrayList<Integer> poids = new ArrayList<Integer>() ;
		poids.add(0) ;
		poids.add(0) ;
		poids.add(2) ;
		
		
		ArrayList<Integer> stationsNonVisitees = new ArrayList<Integer>() ;
		stationsNonVisitees.add(1530) ;
		stationsNonVisitees.add(1531) ;
		stationsNonVisitees.add(1532) ;
		ArrayList<Integer> poidsStationsNonVisitees = new ArrayList<Integer>() ;
		Iterator<Integer> iterateur = stationsNonVisitees.iterator() ;
		while (iterateur.hasNext()) {
			int indice = stationsNonVisitees.indexOf(iterateur.next()) ;
			poidsStationsNonVisitees.add(poids.get(indice)) ;
		}
		
		System.out.println(stationsNonVisitees) ;
		System.out.println(poidsStationsNonVisitees) ;
		
		//Test chercherMin()
		System.out.println(chercherMin(s1.getId(), poidsStationsNonVisitees, stationsNonVisitees, listStation)) ;
		
	}
	*/
}
