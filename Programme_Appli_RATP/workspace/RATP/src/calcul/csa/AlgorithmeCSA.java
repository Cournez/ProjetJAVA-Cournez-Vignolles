package calcul.csa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

//import application.utilisateur.Requete;
import calcul.tablesHoraire.TableHoraire;
import calcul.tablesHoraire.Troncon;
import outils.Time;
import reseau.Arret;
import reseau.Station;

/**
 * Cette classe permet de mettre en oeuvre le calcul d'itineraire d'apres le critere "trajet le plus rapide".
 * Elle se compose de differents 6 attributs et de 4 methodes.
 * @author Aelaig et Fanny
 * 
 * @attribut ArretTempo : liste des arrets a etudier  -  type : ArrayList<Arret>
 * @attribut listeStation : liste des identifiants des stations  -  type : ArrayList<Integer>
 * @attribut horaireOpti : liste initialisee a l'infini qui repertorie l'horaire d'arrivee optimale a la station associee  -  type : ArrayList<Time>
 * @attribut tronconOpti : liste des troncons issus de la table horaire permettant d'acceder a la station associee a cette horaire optimale  - type : ArrayList<Troncon>
 * @attribut Maxi : horaire infini permettant d'initialiser la liste horaireOpti  -  type : int
 * @attribut tableHoraire : liste des connexions/troncons a etudier  -  type : ArrayList<Troncon> .
 * 
 */

public class AlgorithmeCSA {
	
	public static ArrayList<Arret> ArretTempo = new ArrayList<Arret>();
	private static ArrayList<Integer> listeStation = new ArrayList<Integer>() ;
	private static ArrayList<Time> horaireOpti = new ArrayList<Time>() ;
	private static ArrayList<Troncon> tronconOpti = new ArrayList<Troncon>() ;
	private static Time Maxi = new Time("100:00:00") ;
	public static ArrayList<Troncon> tableHoraire = new ArrayList<Troncon>();

	/**
	 * Cette methode permet de scanner la table horaire afin de determiner l'horaire optimale d'acces a toutes les stations presentes dans la listeStation.
	 * Elle sauvegarde egalement les troncons associes a ces horaires d'arrivee afin de pouvoir reconstituer le trajet.
	 * 
	 * Pour cela, elle a besoin de la station de depart et de l'heure de depart que le voyageur impose lors de sa requete.
	 * Cette methode ne retourne aucun resultat, elle modifie les attributs horaireOpti et tronconOpti.
	 * 
	 * /!\ la liste ArretTempo et la table horaire doivent etre prealablement calculees.
	 * 
	 * @param stationDep : identifiant de la station de depart de l'itineraire  -  type : int
	 * @param heureDep : heure de depart de l'itineraire  -  type : Time
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void calculerTrajetsOpti ( int stationDep , Time heureDep ) throws ClassNotFoundException, SQLException {
		
		listeStation = Station.listerStation(stationDep, ArretTempo);


		// Initialisation des listes d'horaires optimales et des troncons associes	
		
		horaireOpti.add(heureDep); // gestion cas arrivee a la station de depart
		tronconOpti.add(new Troncon(stationDep , stationDep , heureDep , heureDep , 0));
		
		for (int k = 0 ; k<listeStation.size()-1 ; k++) {
			horaireOpti.add(Maxi);
			tronconOpti.add(new Troncon ( 0 , 0 , -1)); // troncon null
		}
		// Algorithme de calcul des horaires optimales
		for (Troncon t : tableHoraire) {

			int depart = t.getStationDep();
			int arrivee = t.getStationArr();
			int indiceDep = listeStation.indexOf(depart);
			int indiceArr = listeStation.indexOf(arrivee); //si = -1 alors la station d'arriv�e du troncon n'existe pas dans la listeStation

			if (indiceDep != -1 && indiceArr != -1) {
				if ( horaireOpti.get(indiceDep).compareTo(t.getHeureDep())<=0 && horaireOpti.get(indiceArr).compareTo(t.getHeureArr())==1 ) {
					horaireOpti.set(indiceArr, t.getHeureArr());
					tronconOpti.set(indiceArr, t);
				}
			}
		}
	}
	
	/**
	 * Cette methode permet de retrouver l'itineraire -donc la succession des stations- a emprunter afin de rejoindre la staion de depart a la station d'arrivee en une duree optimale.
	 * Au passage cette methode affiche la succession des troncons a emprunter afin de connaitre les horaires.
	 * 
	 * @param stationArr : identifiant de la station d'arrivee  -  type : int
	 * @return itineraireOpti : liste des troncons a emprunter pour un trajet optimise en temps  -  type : ArrayList<Troncon> .
	 */

	public static ArrayList<Integer> retrouverIti(int stationArr){
		
		int arrivee = stationArr; 
		ArrayList<Troncon> itineraireOpti = new ArrayList<Troncon>() ; 
		
		
		while ( arrivee != listeStation.get(0)) { // tant que l'arrivee != du depart

			int indiceArr = listeStation.indexOf(arrivee);
			Troncon t = tronconOpti.get(indiceArr);
			if (t.getType() == -1) {
				System.out.println("Station n " + arrivee + " non accessible.");
				arrivee = listeStation.get(0);
			}
			else {
				itineraireOpti.add(t);	// on ajoute les troncons empruntes en partant de la fin
				arrivee = t.getStationDep();
			}
		}
		Collections.reverse(itineraireOpti);	// on retrouve l'ordre chronologique des troncons
		
		System.out.println("\nHoraires de l'itineraire :\n");
		System.out.println(itineraireOpti);
		
		ArrayList<Integer> itiStations = new ArrayList<Integer>();
		itiStations.add(itineraireOpti.get(0).getStationDep());
		for (Troncon t : itineraireOpti) {
			itiStations.add(t.getStationArr());
		}

		return itiStations ; 
	}
	
	
	/**
	 * Cette methode finale lance les methodes precedentes afin d'obtenir la liste des stations a parcourir selon les criteres du voyageur.
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
		ArretTempo = TableHoraire.calculerArretsTempo(heureDep);
		tableHoraire = TableHoraire.calculer(ArretTempo);
		
		System.out.println("Veuillez patienter...  -  Etape 5/5 : Calcul de l'itineraire via l'algorithme CSA");
		
		calculerTrajetsOpti(stationDep , heureDep);
		return retrouverIti(stationArr) ; 
	}

	
	
	/*		// TEST ALGORITHME CSA //
	 * 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> iti = calculerItineraire(2200 , 2498 , new Time("06:00:00"));
		System.out.println(retrouverIti(2498));
		Requete.afficherItineraire(iti);
	}
	*/


}
