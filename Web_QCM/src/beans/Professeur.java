package beans;

import java.io.Serializable;

public class Professeur implements Serializable  {
	
	private Long   id_Professeur;
    private String nom;
    private String prenom;
    private String filiere;
    private String mdp;
    private String adresse;
    private String telephone;
    private String email;
    private String image;
	public Long getId_Professeur() {
		return id_Professeur;
	}
	public void setId_Professeur(Long id_Professeur) {
		this.id_Professeur = id_Professeur;
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
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
