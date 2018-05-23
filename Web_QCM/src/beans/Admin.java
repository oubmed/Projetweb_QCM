package beans;

import java.io.Serializable;

public class Admin implements Serializable  {
	
	private Long   id_Admin;
    private String nom;
    private String prenom;
    private String mdp;
	public Long getId_Admin() {
		return id_Admin;
	}
	public void setId_Admin(Long id_Admiin) {
		this.id_Admin = id_Admin;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

}
