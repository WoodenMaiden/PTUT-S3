package fr.univ_amu.DumbStages;

import fr.univ_amu.DumbStages.donnees.*;

import java.awt.*;
import java.io.*;

public class GenerateurHtml {
    private File Entree;
    private File Html;
    private String FinHtml;
    private String CodeHtml;

    GenerateurHtml(String strSortie) throws IOException { //Constructeur
        this.Html = new File(strSortie);

        if (this.Html.createNewFile()) {
            System.out.println("Fichier HTML créé !"); //Création de la première partie de l'html dans DebutHtml
            this.CodeHtml = new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">\n" +
                    "  <title>IUT Stage</title>\n" +
                    "  <link href=\"https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&family=Poppins:wght@400;600&display=swap\" rel=\"stylesheet\">\n" +
                    "  <link href=\"https://fonts.googleapis.com/css2?family=Work+Sans:wght@300;500&display=swap\" rel=\"stylesheet\">\n" +
                    "  <link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\" media=\"screen\" title=\"no title\" charset=\"utf-8\">\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "  <header>\n" +
                    "    <h1>IUT </h1>\n" +
                    "    <h2> Forum entreprise du ");
            this.FinHtml = new String(" </div>\n" +
                    "\n" +
                    "\n" +
                    "  <footer>\n" +
                    "    <p>\n" +
                    "      M. Laporte\n" +
                    "      <a href=\"mailto:marc.laporte@univ-amu.fr\">marc.laporte@univ-amu.fr </a>\n" +
                    "    </p>\n" +
                    "\n" +
                    "    <p>\n" +
                    "      I.U.T. d'Aix-Marseille\n" +
                    "      Département Informatique site d'Aix-en-Provence\n" +
                    "    </p>\n" +
                    "  </footer>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>"); //Création de la première partie de l'html dans FinHtml
        }
    }

    public void setDate(String Date) {
        this.CodeHtml = CodeHtml + Date + "</h2>\n" +
                "  </header>\n" +
                "\n" +
                "  <div>";
    }//Insert dans Codehtml l'Html affichant le titre de la page

    public void DebutTableau () {
        this.CodeHtml = CodeHtml + "<table border=\"1\" cellpadding=\"15\">\n" +
                "\n" +
                "      <caption>\n" +
                "        <h3>\n" +
                "          Liste des entreprises participant au forum\n" +
                "        </h3>\n" +
                "      </caption>\n" +
                "\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <th id=\"thLeft\"> Nom Entreprise </th>\n" +
                "          <th> Repr&eacute;sentant Entreprise </th>\n" +
                "          <th id=\"thRight\"> Lien Web Entreprise </th>";
    } //Insert dans CodeHtml le début du tableau en html

    public void FinTableau () {
        this.CodeHtml = this.CodeHtml + " </tbody></table>";
    } //Insert dans CodeHtml la fin du tableau en html

    public void AjouterEntreprise (Entreprise uneEntreprise){
        this.CodeHtml = this.CodeHtml + "    <tr>\n" +
                "        <td> "+uneEntreprise.getNom_en()+"</td> <td>";
        for (String rep : uneEntreprise.getRepresentants())
            this.CodeHtml = this.CodeHtml + rep +"</br>";
        this.CodeHtml = this.CodeHtml + "</td><td><a href=\""+uneEntreprise.getUrl()+"\">"+uneEntreprise.getNom_en()+"</a></td>\n</tr>";
    }//Insert dans CodeHtml une ligne du tableau contenant le nom de l'entreprise, des représentants, ainsi que l'url de leur site

    public void setFinHtml() {
        this.CodeHtml = this.CodeHtml + this.FinHtml;
    }

    public String getFinHtml() {
        return FinHtml;
    }

    public void EcritDansFichier() throws IOException {
        FileWriter FichierEcriture = new FileWriter(Html);
        FichierEcriture.write(CodeHtml);
        FichierEcriture.close();
    } //Ecrit Texte dans le fichier Sortie /!\ Si utilisé deux fois sur le même fichier, le premier contenu sera remplacé par le deuxième

    public void OuvrirFichier() throws IOException {
        Desktop.getDesktop().browse(Html.toURI());
    }
}