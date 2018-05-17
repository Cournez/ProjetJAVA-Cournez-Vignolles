package reseau;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bdd.connexion.Connecter;

/** Cette classe permet d'instancier des stations.
 * @author Aelaig et Fanny
 * 
 * Une station est un quai pour lequelle un train s'arrete.
 * 
 * @attribut id : identifiant de la station  - type : int
 * @attribut nom : nom de la station  - type : String
 * @attribut enTravaux  :  etat de la station (Travaux)  - type : booleen
 */

public class Station {
	
	private int id;
	private String nom;
	private boolean enTravaux;
	
	
	//Accesseur et Mutateur pour l'attribut id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//Accesseur et Mutateur pour l'attribut nom
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	//Accesseur et Mutateur pour l'attribut enTravaux
	public boolean isEnTravaux() {
		return enTravaux;
	}
	public void setEnTravaux(boolean enTravaux) {
		this.enTravaux = enTravaux;
	}
	
	
	/**
	 * Cette methode cree la liste des stations identifiees avec leur id_station qui sera utilisee dans la methode plusCourtChemin()
	 * on place la station de depart en premier element pour répondre aux besoins de la methode plusCourtChemin()
	 * 
	 *  
	 * @param statDep : identifiant de la station de depart  -  type : int
	 * @return : liste des stations  -  type : ArrayList<Integer>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ArrayList<Integer> listerStation(int statDep , List<Arret> ArretsTempo) throws ClassNotFoundException, SQLException {
		int id_stationDep = statDep ; // modif statDep type : Station
		
		//initialisation de la liste des stations
		ArrayList<Integer> listStation = new ArrayList<Integer>() ;
		listStation.add(id_stationDep) ; //on place la station de départ en premier element pour simplifier
		
		
		for ( Arret a : ArretsTempo) {
			if ( ! listStation.contains(a.getStation()))
			listStation.add(a.getStation());
		}

		return listStation;
	}
	
	
	/**
	 * Cette methode affiche tous les noms des stations du reseau afin que l'utilisateur puisse y retrouver l'orthographe exacte.
	 */
	
	public static void afficherNomsStations() throws ClassNotFoundException, SQLException {
		Connecter test = new Connecter() ;
		Connection connection = test.seConnecter() ; // Connexion à la BDD RATP
		Statement state = connection.createStatement() ;
		ResultSet result = state.executeQuery("select distinct nomStation from station order by nomStation") ; 
		while (result.next()) {
			System.out.println ( result.getString("nomStation"));	
		}
		result.close();
		state.close();
		test.seDeconnecter();
	}
	
	
	/**
	 * Cette methode retrouve les identifiants d'une station connue grace a son nom.
	 * 
	 * @param nomStation : nom de la station  -  type : String
	 * @return : liste des identifiants de stations associes  -  type : ArrayList<Integer>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static ArrayList<Integer> nomToId_Station(String nomStation) throws ClassNotFoundException, SQLException{
		
		ArrayList<Integer> identifiantStation = new ArrayList<Integer>();
		ArrayList<String> ligneStation = new ArrayList<String>();
		
		Connecter test = new Connecter() ;
		Connection connection = test.seConnecter() ; // Connexion à la BDD RATP
		
		PreparedStatement preStatement = connection.prepareStatement("SELECT id_station , ligne from station where nomStation = ?");
		preStatement.setString(1,"\"" + nomStation +"\"") ; // on recupere les stations portant le nom en question
		ResultSet preResult = preStatement.executeQuery() ;
		
		while ( preResult.next()) {
			int id = preResult.getInt("id_station");
			String ligne = preResult.getString("ligne");
			identifiantStation.add(id);
			ligneStation.add(ligne);
			System.out.println("Station numero : " + id + ", ligne : " + ligne); // Affichage de la liste afin de choisir lors de la requete utilisateur
		}
		
		preResult.close();
		preStatement.close();
		test.seDeconnecter();
		return identifiantStation ;
	}
	
	
	/*   // TESTS //
	 * 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Ligne m1 = new Ligne();
		m1.setId(01);
		m1.setNom("métro1");
		m1.setEnTravaux(false);
		
		Route r1 = new Route();
		r1.setId(001);
		r1.setNom("La Défense - Chateau de Vincennes");
		r1.setLigne(m1);
		System.out.println("la route " + r1.getNom() + " appartient à la ligne " + r1.getLigne().getNom());
		
		
		System.out.println(nomToId_Station("Nation"));
	}
	*/

}
