package test;

public class Villes {
	private String nom ;
	private boolean zoneRecharge ;
	
	public Villes(String nom, boolean zoneRecharge) {
		this.nom = nom ;
		this.zoneRecharge = false ;
	}
	
	public String getNom() {
		return nom ;
	}
	
	public void setZoneRecharge(boolean zoneRecharge) {
		this.zoneRecharge = zoneRecharge ;
	}
	
	public boolean estZoneRecharge() {
        return zoneRecharge;
    }
}