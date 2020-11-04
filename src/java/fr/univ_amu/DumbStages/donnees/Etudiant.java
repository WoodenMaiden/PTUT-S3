package fr.univ_amu.DumbStages.donnees;

import java.lang.*;


public class Etudiant {

    private String nom_et;
    private String prenom_et;
    private String groupe_et;

    public Etudiant(String nom, String prenom){
        //formatage du nom et prenom
        this.nom_et = nom.toUpperCase();



        char premiereLettre = Character.toUpperCase(prenom.charAt(0));
        String reste = prenom.substring(1);

        this.prenom_et = premiereLettre + reste.toLowerCase();
    }

    public String getNom() {
        return nom_et;
    }

    public String getPrenom() {
        return prenom_et;
    }

    public void setNom(String nom){
        this.nom_et = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom_et = prenom;
    }

    public String getGroupe() {
        return groupe_et;
    }

    public void setGroupe(String groupe_et) {
        this.groupe_et = groupe_et;
    }
}
