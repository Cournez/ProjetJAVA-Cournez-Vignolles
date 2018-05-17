package application.main;

import java.sql.SQLException;
import java.util.Scanner;
import application.utilisateur.Requete;

/**
 * Cette classe permet de lancer l'application RATP = point de depart = interface avec l'utilisateur 
 * @author Aelaig et Fanny
 *
 */

public class ApplicationRATP {
	
	public static Scanner scann = new Scanner(System.in);
	
	/**
	 * Cette methode permet de lancer l'application pour une unique requete
	 * 
	 * @param sc : scanner permettant de reccueillir les consignes de l'utilisateur
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static void relance_Appli_RATP() throws ClassNotFoundException, SQLException {
		
		boolean Bcritere = true ;

		while (Bcritere) {

			System.out.println("Etes-vous un voyageur ou un gestionnaire du reseau RATP ?  ( V - Voyageur  /  G - Gestionnaire) ");
			String utilisateur = scann.next();

			if (utilisateur.equalsIgnoreCase("V") || utilisateur.equalsIgnoreCase("G")) {
				Bcritere = false ;

				if (utilisateur.equalsIgnoreCase("V")) {
					Requete.nouvelItineraire();
				}

				else {
					application.gestionnaire.Travaux.gestionReseau();
				}
			}
		}
		
	}
	
	/**
	 * Cette methode permet de lancer l'acquisition de plusieurs requetes vers l'application RATP
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public static void lancement_Appli_RATP() throws ClassNotFoundException, SQLException {
		System.out.println("\nBienvenue sur l'application RATP \n");
		relance_Appli_RATP();
		
		boolean nouvelleRequete = true ;
		while (nouvelleRequete) {
			
			boolean condi = true ;
			while (condi) {
				

				System.out.println ("\nVoulez-vous lancer une nouvelle requete : ?  ( O - oui  / N - non)");
				
				String demande = scann.next();

				if (demande.equalsIgnoreCase("O") || demande.equalsIgnoreCase("N")) {
					condi = false ;
					if (demande.equalsIgnoreCase("O")) {
						
						relance_Appli_RATP();
					}
					else {
						nouvelleRequete = false ;
						System.out.println ("\n \n \nMerci d'avoir utilise l'application RATP \nA bientot ! \nAelaig et Fanny");
						
					}
				}
			}
		}
	}

}