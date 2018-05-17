package reseau;

/** Cette classe permet d'instancier des lignes.
 * @author Aelaig et Fanny
 * 
 * Une ligne est definie comme un ensemble de routes portant le meme nom (par exemple RER A) .
 * 
 * @attribut id : identifiant de la ligne  - type : int
 * @attribut nom : nom de la ligne  - type : String
 * @attribut enTravaux  :  etat de la ligne (enTravaux) - type : booleen
 */

public class Ligne {
	
	private int id;
	private String nom;
	private boolean enTravaux;
	
	// Accesseur et Mutateur pour l'attribut id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	// Accesseur et Mutateur pour l'attribut nom
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	// Accesseur et Mutateur pour l'attribut enTravaux
	public boolean isEnTravaux() {
		return enTravaux;
	}
	public void setEnTravaux(boolean enTravaux) {
		this.enTravaux = enTravaux;
	}
	
}
