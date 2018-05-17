package reseau;

import java.util.ArrayList;

/** Cette classe permet d'instancier des courses.
 * @author Aelaig et Fanny
 * 
 * Une course est definie comme un trajet realise par un trajet sur une route a une horaire particuliere.
 * Une course est unique dans une journee.
 * 
 * @attribut id : identifiant de la course  - type : long
 * @attribut route : route sur laquelle la course circule  - type : int
 * @attribut arrets  :  liste des arrets réalises par cette course
 */

public class Course {
		
	private int id;
	private Route route;
	private ArrayList<Arret> arrets;
	
	// Accesseur et Mutateur pour l'attribut id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	// Accesseur et Mutateur pour l'attribut route
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	
	// Accesseur et Mutateur pour l'attribut arrets
	public ArrayList<Arret> getArrets() {
		return arrets;
	}
	public void setArrets(ArrayList<Arret> arrets) {
		this.arrets = arrets;
	}

	
}
