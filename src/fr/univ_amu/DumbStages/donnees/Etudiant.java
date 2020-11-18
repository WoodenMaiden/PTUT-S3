package fr.univ_amu.DumbStages.donnees;

public class Etudiant {
    private String Nom;
    private String Prenom;
    private String Groupe;

    public Etudiant(String nom, String prenom, String groupe) {
        Nom = nom;
        Prenom = prenom;
        Groupe = groupe;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getGroupe() {
        return Groupe;
    }
}
