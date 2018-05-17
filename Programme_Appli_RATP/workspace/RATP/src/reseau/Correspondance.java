package reseau;

import outils.Time;

/** Cette classe permet d'instancier des correspondances.
 * @author Aelaig et Fanny
 * 
 * Une correspondance est definie comme la liaison de 2 stations.
 * 
 * @attribut station1 : identifiant de la station d'origine  - type : int
 * @attribut station2 : identifiant de la station de destination - type : int
 * @attribut temps  :  temps de trajet à pied entre les 2 stations  - type : Time
 */

public class Correspondance {
	
	private int station1; 
	private int station2;
	private Time temps;
	
	// Accesseur et Mutateur pour l'attribut station1
	public int getStation1() {
		return station1;
	}
	public void setStation1(int station1) {
		this.station1 = station1;
	}
	
	// Accesseur et Mutateur pour l'attribut station2
	public int getStation2() {
		return station2;
	}
	public void setStation2(int station2) {
		this.station2 = station2;
	}
	
	// Accesseur et Mutateur pour l'attribut temps
	public Time getTemps() {
		return temps;
	}
	public void setTemps(Time temps) {
		this.temps = temps;
	}
	
	
	// Constructeur
	public Correspondance(int station1, int station2, Time temps) {
		super();
		this.station1 = station1;
		this.station2 = station2;
		this.temps = temps;
	}

}
