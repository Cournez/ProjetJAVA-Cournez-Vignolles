package reseau;


import outils.Time;

/** Cette classe permet d'instancier des arrets.
 * @author Aelaig et Fanny
 * 
 * Un arret est defini par une course arretee a une station a une horaire donnee.
 * 
 * @attribut course : identifiant de la course  - type : long
 * @attribut station : identifiant de la station  - type : int
 * @attribut horaire  :  heure d'arret de la course a cette station  - type : Time
 */

public class Arret implements Comparable<Object>{

	private Long course;
	private int station;
	private Time horaire;
	
	// Accesseur et Mutateur de l'attribut course
	public Long getCourse() {
		return course;
	}
	public void setCourse(Long course) {
		this.course = course;
	}
	
	// Accesseur et Mutateur de l'attribut station
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	
	// Accesseur et Mutateur de l'attribut horaire
	public Time getHoraire() {
		return horaire;
	}
	public void setHoraire(Time horaire) {
		this.horaire = horaire;
	}
	

	// Constructeur
	public Arret(Long course, Time horaire, int station) {
		super();
		this.course = course;
		this.station = station;
		this.horaire = horaire;
	}

	
	/** Cette methode redefinit le comparateur
	 * 	pour pouvoir classer les arrets chronologiquement selon leur horaire (necessaire dans le calcul de la table horaire)
	 * 
	 * cf Collections.sort(ArretsTempo) dans TableHoraire.calculerArretsTempo
	 */
	@Override
	public int compareTo(Object o) {
		if (o.getClass().equals(Arret.class)) {
			Arret arret = (Arret) o;
			return this.horaire.compareTo(arret.getHoraire());
		}
		return -1;
	}
	
	/**
	 * Cette methode permet de redefinir la methode equals afin d'utiliser ArrayList<Arret>.contains(Arret)
	 */
	
	@Override
	public boolean equals(Object arret){
		
		   return (this.course == ((Arret)arret).getCourse() && this.station == ((Arret)arret).getStation() && this.horaire == ((Arret)arret).getHoraire());
		}

}
