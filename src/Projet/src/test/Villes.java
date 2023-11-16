package test;


public class Villes {
	private String nom ; //nom de la villa
	private boolean zoneRecharge ; 
	
	public Villes(String nom) {
		this.nom = nom ;
		this.zoneRecharge = false ; //la ville ne possède pas de borne de recharge par défaut
	}
	
	//methode pour obtenir le nom de la ville
	public String getNom() {
		return nom ;
	}
	 // Méthode pour définir si la ville possède une zone de recharge
	public void setZoneRecharge(boolean zoneRecharge) {
		this.zoneRecharge = zoneRecharge ;
	}
	
	// Méthode pour obtenir l'état de la zone de recharge de la ville
	public boolean getZoneRecharge() {
		return zoneRecharge ;
	}
	
}
