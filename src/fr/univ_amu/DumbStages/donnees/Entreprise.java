package fr.univ_amu.DumbStages.donnees;

public class Entreprise {

    private String nom_en;
    private String[] representants;
    private String url;
    private String contactEmail;
    private String lienZoom;

    public Entreprise(String nom, String[] rpz, String url) {
        this.nom_en = nom;
        this.representants = rpz;
        this.url = url;
    }

    public String getNom_en() {
        return this.nom_en;
    }

    public void setNom_en(String nom) {
        this.nom_en = nom;
    }

    public String[] getRepresentants() {
        return this.representants;
    }

    public void setRepresentants(String rpz) {
        this.representants[0] = rpz;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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
