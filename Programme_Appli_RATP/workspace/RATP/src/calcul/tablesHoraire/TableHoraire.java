package calcul.tablesHoraire;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import application.gestionnaire.Parametres;
import bdd.connexion.Connecter;
import outils.Time;
import reseau.Arret;

/**
 * Cette classe gere la calcul integral de la table horaire.
 * @author Aelaig et Fanny
 *
 */
public class TableHoraire {
	
	/** Calcul de la Table temporaire des Arrets  
	 * 
	 * Cette methode permet de restreindre le nombre d'arrets consideres pour la creation de la Table Horaire.
	 * Elle repertorie dans une liste d'Arrets ceux compris entre l'heure de depart défini par la requete et une duree arbitraire definissant l'intervalle de temps a etudier.
	 * 
	 * @param heureDepart : heure de depart definie par la requete utilisateur - type : Time
	 * @return List<Arret> ArretsTempo : liste d'elements de type Arret repertoriant tous les arrets effectues dans l'intervalle de temps.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static ArrayList<Arret> calculerArretsTempo(Time heureDepart)  throws ClassNotFoundException, SQLException  {

		Connecter test = new Connecter() ;
		Connection connection = test.seConnecter() ; // Connexion à la BDD RATP

		System.out.println("\nVeuillez patienter...  -  Etape 1/5 : Mise a jour de l'etat du reseau");

		Statement ini = connection.createStatement();
		ini.executeUpdate("delete from arretstempo");		
		ini.executeUpdate("insert into arretstempo select arretstries.id_course , arretstries.heureDep , arretstries.id_station , enTravaux from arretstries join station on arretstries.id_station = station.id_station ");

		ini.close();

		System.out.println("Veuillez patienter...  -  Etape 2/5 : Recuperation des arrets");

		Statement state = connection.createStatement() ;
		ResultSet result0 = state.executeQuery("select * from arretstempo") ;

		while (result0.next() && new Time(result0.getString("heureDep")).compareTo(heureDepart)<0 ) {			
		}

		ArrayList<Arret> ArretsTempo = new ArrayList<Arret>();

		while (result0.next() && new Time(result0.getString("heureDep")).compareTo(Time.somme(heureDepart,Parametres.intervalle))<0) {	// recuperation des arrets ayant lieu dans le creneau horaire defini par Parametres.intervalle.

			if(!result0.getBoolean("enTravaux")) { // si et seulement si la station n'est pas en travaux !

				Arret a1 = new Arret(result0.getLong("id_course") ,new Time( result0.getString("heureDep")) , result0.getInt("id_station") );
				ArretsTempo.add(a1);
			}
		}
		state.close();
		test.seDeconnecter();		
		//System.out.println(" Liste ArretsTempo remplie ");
		Collections.sort(ArretsTempo);
		return ArretsTempo;
	}



	/**Cette methode permet de calculer les deux tables horaires (courses et correspondances) et les fusionne pour retourner la table horaire finale.
	 * Cette table horaire fait indirectement appel à la BDD RATP SQLite via , elle est donc à jour à chaque appel à la methode.
	 * 
	 * @param ArretsTempo : liste des arrets etudies  -  type : ArrayList<Arret>
	 * @return tableHoraire : liste des troncons/connexions a etudier  - type : ArrayList<Troncon>.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public static ArrayList<Troncon> calculer(ArrayList<Arret> ArretsTempo) throws ClassNotFoundException, SQLException {

		ArrayList<Troncon> Table1 = TableCourses.calculer(ArretsTempo);
		ArrayList<Troncon> Table2 = TableCorrespondances.calculer(ArretsTempo);

		// Fusion des deux tables 
		ArrayList<Troncon> TableHoraire = new ArrayList<Troncon>();
		TableHoraire.addAll(Table1);
		TableHoraire.addAll(Table2);

		//Tri de la table horaire selon les heures de départ croissante
		Collections.sort(TableHoraire);

		//System.out.println(TableHoraire.size());
		return TableHoraire;
	}

}
