package test;

public class Route {
	private Villes villeDepart ; //ville de départ pour la route
	private Villes villeFin ;  //ville d'arrivée de la route
	
	//constructeur
	public Route(Villes villeDepart, Villes villeFin) {
		this.villeDepart = villeDepart ;
		this.villeFin = villeFin ;
	}
	
	// Méthode pour obtenir la ville de départ de la route
	public Villes getVilleDepart() {
		return villeDepart ;
	}
	// Méthode pour obtenir la ville d'arrivée de la route
	public Villes getVilleFin() {
		return villeFin ;
	}

}

