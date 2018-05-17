package application.gestionnaire;

import outils.Time;

/**
 * Cette classe permet de gerer certains parametres lies a l'application par le programmeur. 
 * 
 * @author Aelaig et Fanny
 * 
 * @attribut intervalle : ce parametre definit l'intervalle de temps dans lequel on restreint le calcul d'itineraire.
 * Il peut etre ajuster en fonction du trajet : il doit etre ni trop restreint (= itineraire inaccessible ) ni trop etendu ( = duree des calculs trop long ).
 */

public class Parametres {
	
	public static Time intervalle = new Time ("00:30:00");

	public static void setIntervalle(Time intervalle) {
		Parametres.intervalle = intervalle;
	}
	
	

}
