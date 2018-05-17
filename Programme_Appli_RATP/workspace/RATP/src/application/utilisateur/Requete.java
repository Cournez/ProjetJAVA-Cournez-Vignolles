package application.utilisateur;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;

import application.main.ApplicationRATP;
import outils.Time;
import reseau.Station;
import bdd.connexion.Connecter;
import calcul.dijkstra.Dijkstra;

/**
 * Cette classe permet de gerer l'interaction avec un voyageur ainsi que ces requetes. 
 * C'est cette classe qui lance le calcul des algorithmes d'itineraires.
 * 
 * @authors Aelaig et Fanny
 * 
 * @attribut statDep : station de depart du voyageur
 * @attribut statArr : station d'arrivee du voyageur
 * @attribut heureDep : heure de depart du voyageur
 * @attribut critere : choix de l'itineraire a calculer ( le + rapide ; le - marche ; le - correspondances )
 *
 */
public class Requete {
	
	public Station statDep ;
	public Station statArr ;
	public Time heure ;
	public int critere ;
	
	/**
	 * Cette methode permet d'acquerir l'identifiant exacte des stations choisies par le voyageur d'apres leur nom de station.
	 * Elle est donc utilisee deux fois lors d'une recherche d'itineraire : pour l'acquisition de la station de depart et de celle d'arrivee.
	 * 
	 * @param nomStation : nom de la station  -  type : String
	 * @param sc : scanner permettant d'interagir avec l'utilisateur  -  type : Scanner
	 * @return : identifiant de la station en question  -  type : int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static int acquerirIdStation(String nomStation) throws ClassNotFoundException, SQLException {
		Scanner sc = ApplicationRATP.scann;
		int idChoisi = 0;
		boolean condi = true ;
		while (condi) {
			System.out.println ("\nChoisissez un identifiant parmi les numeros de station suivants :");
			ArrayList<Integer> idStations = Station.nomToId_Station(nomStation);
			System.out.println ("\nidentifiant choisi : ?");
			idChoisi = sc.nextInt();
			
			if (idStations.contains(idChoisi)) {
				condi = false ;
			}
		}
		return idChoisi ;	
	}
	
	
	/**
	 * Constructeur de requete utilisateur. Cette methode permet d'interroger le voyageur sur les parametres de son trajet.
	 * 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Requete() throws ClassNotFoundException, SQLException {
		Station.afficherNomsStations();
		System.out.println("\n \n \nParametrez votre demande d'itineraire :") ;
		//On interroge directement l'utilisateur
		Scanner sc = ApplicationRATP.scann ;
	
		this.statDep = new Station() ;
		System.out.println("\n \nNom de votre station de depart : ?  (  Attention ! Ne pas choisir un nom de station comportant un espace - cas particulier non gere. ) ") ;
		String nomStationDep = sc.next();
		this.statDep.setId(acquerirIdStation(nomStationDep ));
		
		this.statArr = new Station() ;
		System.out.println("\nNom de votre station d'arrivee : ?  (  Attention ! Ne pas choisir un nom de station comportant un espace - cas particulier non gere. ) ") ;
		String nomStationArr = sc.next();
		this.statArr.setId(acquerirIdStation(nomStationArr ));
		
		boolean crit1 = true;
		while (crit1) {
			System.out.println("\nPartir maintenant ? (O ou N)") ;
			String partir = sc.next() ;
			if (partir.equalsIgnoreCase("N") || partir.equalsIgnoreCase("O")) {
				crit1=false;
				if (partir.equalsIgnoreCase("N")) {
					
					System.out.println("\nHeure de depart : ? (format hh:mm) ");
					String h = sc.next();
					this.heure = new Time(h) ; // A FAIRE : gerer les erreurs de saisie.
				}
				else {	// on recupere l'heure actuelle
					Calendar cal = Calendar.getInstance();
					int heure = cal.get(Calendar.HOUR_OF_DAY);
					int minute = cal.get(Calendar.MINUTE);
					int sec = minute*60 + heure*3600;
					this.heure = new Time(sec) ;
				}
			}
		}
		
		boolean Bcritere = true ;
		while (Bcritere) {
			System.out.println("\nCritere ? \n 1 : + rapide \n 2 : - de marche \n 3 : - de correspondance");
			this.critere = sc.nextInt() ;
			
			if (this.critere == 1 || this.critere == 2 || this.critere == 3) {
				Bcritere = false ;
			}
		}
	}
	
	
	/** Cette methode permet d'acquerir une nouvelle recherche d'itineraire, de lancer le calcul d'itineraire et d'afficher l'itineraire a l'utilisateur.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void nouvelItineraire() throws ClassNotFoundException, SQLException {
		ArrayList<Integer> itineraire = new ArrayList<Integer>() ;
		Requete requete = new Requete() ;
		
		if (requete.critere == 1) {
			itineraire = calcul.csa.AlgorithmeCSA.calculerItineraire(requete.statDep.getId(), requete.statArr.getId(), requete.heure) ;
			
			System.out.println("\nItineraire le plus rapide :");
		}
		else if (requete.critere == 2) {
			itineraire = calcul.csaTempsMarche.AlgorithmeCSAtempsMarche.calculerItineraire(requete.statDep.getId(), requete.statArr.getId(), requete.heure) ;
			
			System.out.println("\nItineraire comportant le moins temps de marche :");
		}
		else {
			itineraire = Dijkstra.calculerDijkstra(requete.statDep.getId(), requete.statArr.getId(), requete.heure) ;
			
			System.out.println("\nItineraire comptant le moins de correspondances :");
		}
		
		afficherItineraire(itineraire) ;
	}
	
	
	/**
	 * Cette methode permet d'afficher lisiblement l'itineraire a emprunter.
	 * Ainsi elle transforme l'itineraire sous forme d'identifiants de stations consecutifs en liste de noms de station consecutifs.
	 * 
	 * @param itineraire : liste des stations a parcourir  -  type : ArrayList<Integer>
	 * @return : affichage de l'itineraire  -  type : String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String afficherItineraire(ArrayList<Integer> itineraire) throws ClassNotFoundException, SQLException {
		String chaine = "" ;
		
		Connecter connexion = new Connecter() ;
		Connection connection = connexion.seConnecter() ;
		
		Iterator<Integer> it = itineraire.iterator() ;
		while (it.hasNext()) {
			int id_station = it.next() ;
			
			PreparedStatement preStatement = connection.prepareStatement("SELECT nomStation, ligne FROM station WHERE id_station = ? ;") ; // ligne
			
			preStatement.setInt(1, id_station);
			
			ResultSet result = preStatement.executeQuery() ;
			
			String nomStation = result.getString(1) ;
			String numLigne = result.getString(2) ;
			
			result.close();
			preStatement.close();
			
			chaine = chaine + "\nStation : " + id_station + " " + nomStation + " ligne " + numLigne ;
		}

		connexion.seDeconnecter();
		System.out.println(chaine) ;
		return chaine ;
	}
	

}
