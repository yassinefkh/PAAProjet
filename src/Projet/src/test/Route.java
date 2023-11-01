package test;

public class Route {
	private Villes villeDepart ;
	private Villes villeFin ;
	
	public Route(Villes villeDepart, Villes villeFin) {
		this.villeDepart = villeDepart ;
		this.villeFin = villeFin ;
	}
	
	public Villes getVilleDepart() {
		return villeDepart ;
	}
	public Villes getVilleFin() {
		return villeFin ;
	}

}
