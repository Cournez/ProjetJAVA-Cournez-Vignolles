package outils;

/**
 * Cette classe Time permet d'avoir accès à des objets de mesure du temps qui repondent aux attentes specifiques de ce projet.
 * Elle permet de gerer les horaires issus de la BDD RATP ainsi que les calcul de duree ...
 * @author Aelaig et Fanny
 * 
 * @attributs horaireMinute : valeur en minute d'un horaire -> exemple: 08h20 correspond a 500 minutes  -  type : int.
 */

public class Time implements Comparable<Object> {
	
	public int horaireMinute ;
	
	/**
	 * Constructeur de Time
	 * @param horaire : l'horaire dans le format de la BDD -> hh:mm:ss  -  type : String
	 * les secondes etant tout le temps a 00, on ne les implemente pas dans notre representation
	 */
	
	public Time(String horaire) {
		String[] decoupe = horaire.split(":") ;
		int heureToMinute = Integer.parseInt(decoupe[0]) * 60 ;
		int minute = Integer.parseInt(decoupe[1]) ;
		
		this.horaireMinute = heureToMinute + minute ;
	}
	
	
	/**
	 * Constructeur de Time
	 * @param temps : le temps en seconde necessaire dans le cas du traitement des temps de correspondances  -  type : int
	 */
	
	public Time(int temps) {
		
		int tempsMinute = Math.round(temps/60);
		
		this.horaireMinute =  tempsMinute ;
	}
	
	public Time() {
		// TODO Auto-generated constructor stub
	}
	
			
	// Accesseur de horaireMinute
	public int getHoraireMinute() {
		return horaireMinute;
	}


	/**
	 * Methode pour transformer une duree en minute en une chaine de caractere au format hh:mm
	 * @param duree : duree en min  -  type : int
	 * @return : duree en hh:mm  -  type : String .
	 */
	public static String minuteToString(int duree) {
		int heure = (int) (duree / 60) ;	
		int minute = (int) (duree % 60) ;
		
		String minuteString = new String() ;
		String heureString = new String() ;
		
		if (minute < 10) {
			minuteString = "0" + Integer.toString(minute) ;
		}
		else {
			minuteString = Integer.toString(minute) ;
		}
		if (heure < 10) {
			heureString = "0" + Integer.toString(heure) ;
		}
		else {
			heureString = Integer.toString(heure) ;
		}
		
		return heureString + ":" + minuteString;
	}
	

	/**
	 * Methode pour retourner un horaire ou un temps au format hh:mm
	 */
	public String toString() {
		return minuteToString(this.horaireMinute) ;
	}
	
	
	/**
	 * Methode pour obtenir le temps ecoule entre deux horaires
	 * @param t1 : horaire depart  - type : Time
	 * @param t2 : horaire arrivee  - type : Time
	 * @return temps exprime en minute  -  type : int .
	 */
	public static int calculDuree(Time t1, Time t2) {
		int duree = t2.horaireMinute - t1.horaireMinute ;
		return duree ;
	}
	
	
	/**
	 * Methode pour transformer une duree (en minute) en un objet Time
	 * @param duree : un temps en minute  -  type : int
	 * @return : temps   -  type : Time
	 */
	public static Time dureeToTime(int duree) {
		String dureeString = minuteToString(duree);
		
		Time temps = new Time(dureeString) ;
		return temps ;
	}
	

	/**
	 * Methode permettant de calculer une somme de Time
	 * @param t1 : un temps en minute  -  type : Time
	 * @param t2 : un temps en minute  -  type : Time
	 * @return somme : temps en minute  -  type : int
	 */
	public static Time somme(Time t1 , Time t2) {
		Time Somme = new Time();
		int somme = t1.horaireMinute + t2.horaireMinute;
		Somme.horaireMinute = somme;
		return Somme;
	}
	
	
	/** Methode qui redefinit le comparateur
	 * 	pour pouvoir classer les horaires chronologiquement conformemant à la demarche de la creation de la table horaire 
	 */
	@Override
	public int compareTo(Object o) {	// a savoir ! : t1.compareTo(t2) = 1 si t1>t2 ; = 0 si t1=t2 ; et = -1 si t1<t2.
		if (o.getClass().equals(Time.class)) {
			Time time = (Time) o;
			Integer i = Integer.valueOf(time.getHoraireMinute());
			return Integer.valueOf(this.horaireMinute).compareTo(i);
		}
		return -1;
	}
	
	
	/** Methode qui redefinit le comparateur equals
	 * 	pour pouvoir utiliser la methode  ArrayList<Time>.contains (Time)
	 */
	@Override
	public boolean equals(Object time){
		
		   return (this.horaireMinute == ((Time)time).getHoraireMinute()  );
		}

}
