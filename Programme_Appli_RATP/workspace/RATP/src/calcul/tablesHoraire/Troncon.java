package calcul.tablesHoraire;

import java.util.List;

import outils.Time;

/**
 * Cette classe permet de creer une connexion (ici appelee troncon).
 * @author Aelaig et Fanny
 * 
 * Une connexion est une liaison realisee entre 2 stations soit par une course (trajet en train)
 * soit lors d'une correspondance (trajet a pied).
 * 
 * Une connexion est caracterisee par 4 arguments : 
 * 
 * @param stationDep : identifiant de la station de depart  - type : int
 * @param stationArr : idenfifiant de la station d'arrivee  - type : int
 * @param heureDep   : heure de depart  - type : Time 
 * @param heureArr   : heure d'arrivee  - type : Time
 * @param type		 : type de la connexion = 1 si correspondance / 0 si course  - type : int
 * 
 */

public class Troncon implements Comparable<Object>{
	
	private int stationDep;
	private int stationArr;
	private Time heureDep;
	private Time heureArr;
	private int type;
	
	
	// Accesseur et Mutateur pour l'attribut stationDep
	public int getStationDep() {
		return stationDep;
	}

	public void setStationDep(int stationDep) {
		this.stationDep = stationDep;
	}


	// Accesseur et Mutateur pour l'attribut stationArr
	public int getStationArr() {
		return stationArr;
	}

	public void setStationArr(int stationArr) {
		this.stationArr = stationArr;
	}


	// Accesseur et Mutateur pour l'attribut heureDep
	public Time getHeureDep() {
		return heureDep;
	}

	public void setHeureDep(Time heureDep) {
		this.heureDep = heureDep;
	}


	// Accesseur et Mutateur pour l'attribut heureArr
	public Time getHeureArr() {
		return heureArr;
	}

	public void setHeureArr(Time heureArr) {
		this.heureArr = heureArr;
	}

	
	// Accesseur pour l'attribut type
	public int getType() {
		return type;
	}

	// Constructeurs
	
	public Troncon(int stationDep, int stationArr, int type) { // Troncon Dijkstra
		super();
		this.stationDep = stationDep;
		this.stationArr = stationArr;
		this.type = type;
	}
	
	public Troncon(int stationDep, int stationArr, Time heureDep, Time heureArr, int type) {
		super();
		this.stationDep = stationDep;
		this.stationArr = stationArr;
		this.heureDep = heureDep;
		this.heureArr = heureArr;
		this.type = type;
	}
	


	/** Methode redefinissant l'affichage d'un Troncon */	
	@Override
	public String toString(){
		return " Depart station n " + this.stationDep + " a " + this.heureDep + " -> Arrivee station n " + this.stationArr + " a " + this.heureArr + "\n";
	}
	
	
	/** Methode qui redefinit le comparateur
	 * 	pour pouvoir classer les objets Troncon chronologiquement d'apres leur heure de depart conformemant a la demarche de la table horaire 
	 */
	@Override
	public int compareTo(Object o) {
		if (o.getClass().equals(Troncon.class)) {
			Troncon troncon = (Troncon) o;
			return this.heureDep.compareTo(troncon.getHeureDep());
		}
		return -1;
	}

	
	/**
	 * Cette methode permet de rechercher un troncon dans une liste de troncon d'apres ses stations de depart et d'arrivee.
	 * (cf methode calculerDijkstra )
	 * 
	 * @param stationDep : identifiant de la station de depart - type : int
	 * @param stationArr : identifiant de la station d'arriv�e - type : int
	 * @param TableHoraire : liste des troncons a analyser - type : List<Troncon>
	 * @return troncon identifie - type Troncon
	 */
	
	public static Troncon rechercherTroncon (int stationDep , int stationArr , List<Troncon> TableHoraire) { // fonctionne 
		Troncon t = new Troncon(stationDep,stationArr,-1);
		for (Troncon e : TableHoraire) {
			if (e.getStationDep()==stationDep && e.getStationArr()==stationArr) {
				//System.out.println("bouh");
				t = e ;
				break;				
			}
		}
		return t;
	}
	
	
	/** Cette methode permet de determiner si un troncon est null
	 * (necessaire pour la methode rechercherTroncon : signifie que le troncon n'existe pas)
	 * 
	 * @param t : Troncon etudie
	 * @return : boolean 
	 */
	
	public boolean isNull () {
		if (this.getType()==-1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Cette methode permet de redefinir la methode equals afin de pouvoir utiliser l'outils ArrayList<Troncon> .contains(Troncon).
	 */
	
	@Override
	public boolean equals(Object troncon){
		
		   return (this.stationDep == ((Troncon)troncon).getStationDep() && this.stationArr == ((Troncon)troncon).getStationArr() && this.heureDep.equals(((Troncon)troncon).getHeureDep()) && this.heureArr.equals(((Troncon)troncon).getHeureArr()) );
		}

}


