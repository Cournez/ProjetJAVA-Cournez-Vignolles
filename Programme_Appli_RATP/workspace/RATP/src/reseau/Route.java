package reseau;

/** Cette classe permet d'instancier des routes.
 * 
 * Une route est definie comme un "branche" d'une ligne, soit une serie d'arrets.
 * Une route est parcourue une multitude de fois dans une journee.
 * 
 * @attribut id : identifiant de la route  - type : long
 * @attribut nom : nom de la route  - type : String
 * @attribut origine  :  station de depart = extremite 1  - type : Station
 * @attribut destination  :  station d'arrivee = extremite 2  - type : Station
 * @attribut ligne : ligne a laquelle appartient la route  - type : Ligne
 */

public class Route {
	
	private int id;
	private String nom;
	private Station origine;
	private Station destination;
	private Ligne ligne;
	
	
	
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
	
	
	
	//Accesseur et Mutateur pour l'attribut origine
	public Station getOrigine() {
		return origine;
	}
	public void setOrigine(Station origine) {
		this.origine = origine;
	}
	
	
	
	//Accesseur et Mutateur pour l'attribut destination
	public Station getDestination() {
		return destination;
	}
	public void setDestination(Station destination) {
		this.destination = destination;
	}
	
	
	//Accesseur et Mutateur pour l'attribut ligne
	public Ligne getLigne() {
		return ligne;
	}
	public void setLigne(Ligne ligne) {
		this.ligne = ligne;
	}


}
