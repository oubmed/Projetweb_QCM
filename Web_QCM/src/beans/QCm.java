package beans;

public class QCm {
	
	private Long   id_QCM;
    private String titre;
    private String matiere;
    private String filiere;
    private String date_fin;
    private String statu_qcm;
    private String telephone;
    private String id_professeur;
	public Long getId_QCM() {
		return id_QCM;
	}
	public void setId_QCM(Long id_QCM) {
		this.id_QCM = id_QCM;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	public String getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	public String getStatu_qcm() {
		return statu_qcm;
	}
	public void setStatu_qcm(String statu_qcm) {
		this.statu_qcm = statu_qcm;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getId_professeur() {
		return id_professeur;
	}
	public void setId_professeur(String id_professeur) {
		this.id_professeur = id_professeur;
	}
    

}
