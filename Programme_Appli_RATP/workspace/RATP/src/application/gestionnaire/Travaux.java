package application.gestionnaire;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import application.main.ApplicationRATP;
import bdd.connexion.Connecter;

/**
 * Cette classe permet de declarer et de gerer des stations en travaux donc inaccessibles.
 * Cette focntionnalite est accessible aux gestionnaires du reseau de la RATP.
 * 
 * @author Aelaig et Fanny
 */

public class Travaux {



	/**
	 * Cette methode permet au gestionnaire de definir la station ou les stations d'une ligne dont l'etat en travaux est a modifier.
	 * 
	 * @return : liste des identifiants des stations dont l'etat doit etre modifie  -  type : ArrayList<Integer>
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	public static ArrayList<Integer> DeclarerTravaux() throws ClassNotFoundException, SQLException {

		ArrayList<Integer> stationsEnTravaux = new ArrayList<Integer>();

		Scanner sc = ApplicationRATP.scann;

		boolean Bcritere = true ;

		while (Bcritere) {

			System.out.println("Station ou Ligne : ?   ( S - Station ; L - Ligne )") ;
			String travaux = sc.next();

			if (travaux.equalsIgnoreCase("S") || travaux.equalsIgnoreCase("L")) {
				Bcritere = false ;

				if (travaux.equalsIgnoreCase("S")) {
					System.out.println("Numero de la station : ?") ;
					stationsEnTravaux.add(sc.nextInt());	// on recupere l'identifiant de la station
				}

				else {
					System.out.println("Numero de la ligne : ?") ;
					String numLigne = sc.next();			// on recupere le numero de la ligne

					Connecter connexion = new Connecter() ;
					Connection connection = connexion.seConnecter() ;

					// on recherche les stations situees sur cette cette ligne
					PreparedStatement preState = connection.prepareStatement("select id_station from station where ligne = ?") ;

					preState.setString(1, "\"" + numLigne + "\"" );

					ResultSet result = preState.executeQuery();

					while (result.next()) {
						stationsEnTravaux.add(result.getInt("id_station"));
					}
					result.close();
					preState.close();
					connexion.seDeconnecter();
				}
			}
		}
		// on retourne la liste des stations a declarer
		return stationsEnTravaux ;
	}


	/**
	 * Cette methode permet de mettre a jour l'etat des stations declarees en travaux par un gestionnaire du reseau de la RATP
	 * @param stationsEntravaux : liste des identifiants des stations declarees en travaux  -  type : ArrayList<Integer>
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void upDateTravaux( ArrayList<Integer> stationsEnTravaux) throws ClassNotFoundException, SQLException {

		Connecter connexion = new Connecter() ;
		Connection connection = connexion.seConnecter() ;

		for ( int statEnTravaux : stationsEnTravaux) {

			if (etatStation(statEnTravaux)) {
				System.out.println("Station " + statEnTravaux + " deja declaree en travaux.");
			}
			else {
				
				PreparedStatement preState = connection.prepareStatement("UPDATE station SET enTravaux = 1 WHERE id_station = ? ;") ;

				preState.setInt(1, statEnTravaux);

				preState.executeUpdate() ;

				System.out.println("Station " + statEnTravaux + " declaree en travaux, mise a jour enregistree.") ;

				preState.close();
				
			}
		}
		connexion.seDeconnecter();
	}



	/**
	 * Cette methode permet de mettre a jour l'etat des stations declarees accessibles par un gestionnaire du reseau de la RATP
	 * @param stationsEntravaux : liste des identifiants des stations declarees nouvellement accessibles  -  type : ArrayList<Integer>
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void upDateFinTravaux( ArrayList<Integer> stationsEnTravaux) throws ClassNotFoundException, SQLException {

		Connecter connexion = new Connecter() ;
		Connection connection = connexion.seConnecter() ;

		for ( int statEnTravaux : stationsEnTravaux) {

			if (!etatStation(statEnTravaux)) {
				System.out.println("Station " + statEnTravaux + " deja declaree sans travaux.");
			}
			else {

				PreparedStatement preState = connection.prepareStatement("UPDATE station SET enTravaux = 0 WHERE id_station = ? ;") ;

				preState.setInt(1, statEnTravaux);

				preState.executeUpdate() ;

				System.out.println("Station " + statEnTravaux + " declaree accessible suite a la fin des travaux, mise a jour enregistree.") ;

				preState.close();	
			}
		}
		connexion.seDeconnecter();
	}

	/**
	 * Cette methode permet de definir la demande du gestionnaire : declaration de travaux ou declaration de fin de travaux.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */


	public static void gestionReseau() throws ClassNotFoundException, SQLException {

		System.out.println("Gestion du Reseau :") ;
		Scanner sc = ApplicationRATP.scann ;
		
		boolean Bcritere = true ;

		while (Bcritere) {

			System.out.println("\nSouhaitez-vous declarer des travaux ou la fin de travaux : ?   ( T - Travaux ; F - FinTravaux )") ;
			String gestion = sc.next();

			if (gestion.equalsIgnoreCase("T") || gestion.equalsIgnoreCase("F")) {
				Bcritere = false ;
				if (gestion.equalsIgnoreCase("T")) {
					System.out.println("\nDeclaration de Travaux :") ;
					upDateTravaux(DeclarerTravaux());

				}
				else {
					System.out.println("\nDeclaration de la Fin de Travaux :") ;
					upDateFinTravaux(DeclarerTravaux());
				}
			}
		}
	}

	/**
	 * Cette methode permet de retrouver l'etat d'une station connue par son identifiant.
	 * 
	 * @param station : identifiant de la station  -  type : int
	 * @return : booleen qui retourne true si la station est en travaux, false sinon  -  type : boolean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static boolean etatStation (int station) throws ClassNotFoundException, SQLException {

		Connecter connexion = new Connecter() ;
		Connection connection = connexion.seConnecter() ;
		PreparedStatement preState = connection.prepareStatement("select enTravaux from station where id_station = ? ") ;
		preState.setInt(1, station);
		boolean enTravaux = false ;

		ResultSet result = preState.executeQuery();
		try {
			enTravaux = result.getBoolean("enTravaux");


			if (enTravaux) {
				System.out.println("La station " + station + " est declaree en travaux.");
			}
			else {
				System.out.println("La station " + station + " est accessible.");
			}
		}catch(SQLException e) {
			System.out.println("\nLa station " + station + " n'existe pas. Veuillez reessayer.\n");
		}

		result.close();
		preState.close();
		connexion.seDeconnecter();

		return enTravaux ;	
	}

}
