package fr.univ_amu.DumbStages.donnees;

public class Entreprise {

    private String nom_en;
    private String[] representants;
    private String url;

    Entreprise(String nom, String[] rpz){
        this.nom_en = nom;
        this.representants = rpz;
    }

    public String getNom_en() {
        return this.nom_en;
    }

    public String[] getRepresentants() {
        return this.representants;
    }

    public String getUrl() {
        return this.url;
    }

/*
    private String RequeteWHOIS(){
        String os = System.getProperty("os.name");

        switch (os) {
            case "Windows" :

        }

    }
*/
}
