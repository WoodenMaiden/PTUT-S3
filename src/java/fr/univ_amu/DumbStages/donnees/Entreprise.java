package fr.univ_amu.DumbStages.donnees;

public class Entreprise {

    private String nom_en;
    private String[] representants;
    private String url;

    public Entreprise(String nom, String[] rpz, String url){
        this.nom_en = nom;
        this.representants = rpz;
        this.url = url;
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

    public void show()
    {
        System.out.println("Nom: "+this.getNom_en());
        System.out.println("Représentants: "+this.getRepresentants()[0]);
        System.out.println("URL: "+this.getUrl());
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
