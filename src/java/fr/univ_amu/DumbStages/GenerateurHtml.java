package fr.univ_amu.DumbStages;

import fr.univ_amu.DumbStages.donnees.*;
import java.io.*;

public class GenerateurHtml {
    private File Entree;
    private File Sortie;
    private String DebutHtml;
    private String FinHtml;
    public String CodeHtml;

    GenerateurHtml(String strSortie) throws IOException { //Constructeur

        this.Sortie = new File(strSortie);

        if (this.Sortie.createNewFile()) {
            System.out.println("fichier créé !"); //Création de la première partie de l'html dans DebutHtml
            this.DebutHtml = new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                    "<html><head>\n" +
                    "\t<meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">\n" +
                    "\t<title>Forum entreprise du 23 janvier 2020</title>\n" +
                    "\t<link rel=\"stylesheet\" href=\"forum_fichiers/uncss.css\" type=\"text/css\" media=\"screen\" title=\"no title\" charset=\"utf-8\">\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "    <big>   \n" +
                    "        M. Laporte<br>\n" +
                    "        <a href=\"mailto:marc.laporte@univ-amu.fr\">marc.laporte@univ-amu.fr </a> <br>\n" +
                    "        I.U.T. d'Aix-Marseille <br>\n" +
                    "        Département Informatique site d'Aix-en-Provence\n" +
                    "    </big>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div style=\"text-align: center;\"><img style=\"width: 623px; height: 7px;\" alt=\"\" src=\"forum_fichiers/TraitCourant.gif\"></div>\n");
            this.FinHtml = new String("</body></html>"); //Création de la première partie de l'html dans FinHtml
            this.CodeHtml = new String ("<h1><span style=\"color: rgb(51, 102, 255);\"> Forum entreprise du ");//Création du reste du code dans CodeHtml
        }
    }

    public void setDate(String Date) {
        this.CodeHtml = CodeHtml + Date + " 23 janvier 2020</span></h1>";
    }//Insert dans Codehtml l'Html affichant le titre de la page

    public void DebutTableau () {
        this.CodeHtml = CodeHtml + "\n" +
                "\n" +
                " <table border=\"1\" cellpadding=\"10\">\n" +
                "    <caption> <h2>Liste des entreprises participant au forum</h2> </caption>\n" +
                "  <tbody>\n" +
                "    <tr>\n" +
                "        <th> Nom Entreprise </th>\n" +
                "        <th> Repr&eacute;sentant Entreprise </th>\n" +
                "\t    <th> Lien Web Entreprise </th>";
    } //Insert dans CodeHtml le début du tableau en html

    public void FinTableau () {
        this.CodeHtml = this.CodeHtml + " </tbody></table>";
    } //Insert dans CodeHtml la fin du tableau en html

    public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise uneEntreprise){
        this.CodeHtml = this.CodeHtml + "    <tr>\n" +
                "        <td> "+uneEntreprise.getNom_en()+"</td> <td>";
        for (String rep : uneEntreprise.getRepresentants())
            this.CodeHtml = this.CodeHtml + rep +"</br>";
        this.CodeHtml = this.CodeHtml + "</td><td><a href=\""+uneEntreprise.getUrl()+"\">"+uneEntreprise.getNom_en()+"</a></td>\n</tr>";
    }//Insert dans CodeHtml une ligne du tableau contenant le nom de l'entreprise, des représentants, ainsi que l'url de leur site

    public void setFinHtml() {
        this.CodeHtml = this.CodeHtml + this.FinHtml;
    }

    public String getDebutHtml() {
        return DebutHtml;
    }

    public String getFinHtml() {
        return FinHtml;
    }

    public void EcritDansFichier(String Texte) throws IOException {
        FileWriter FichierEcriture = new FileWriter(this.Sortie);
        FichierEcriture.write(Texte);
        FichierEcriture.close();
    } //Ecrit Texte dans le fichier Sortie /!\ Si utilisé deux fois sur le même fichier, le premier contenu sera remplacé par le deuxième

    /*public static void main(String[] args) throws IOException {
        GenerateurHtml gen = new GenerateurHtml("/amuhome/d19002305/Bureau/Logo.png", "/amuhome/d19002305/Bureau/masortie3.html");
        gen.EcritDansFichier(gen.getDebutHtml() + "<h1>TEST</h1>" + gen.getFinHtml());
    }*/
}
