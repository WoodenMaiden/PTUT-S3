package fr.univ_amu.DumbStages.donnees;

public class Etudiant {
    private String nom;
    private String prenom;
    private String groupe;

    public Etudiant(String nom, String prenom, String groupe) {
        this.nom = nom;
        this.prenom = prenom;
        this.groupe = groupe;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getGroupe() {
        return groupe;
    }
}
