package fr.univ_amu.DumbStages;

import fr.univ_amu.DumbStages.donnees.*;

import java.awt.*;
import java.io.*;

//Generateur du fichier HTML de l'Etape 1
public class GenerateurHtml {
    private File html;
    private String finHtml;
    private String codeHtml;
    private String journey;
    private File favicon = new File("resources/Icon.png");
    private File current = new File ("");
    GenerateurHtml(String strSortie,String date) throws IOException { //Constructeur
        codeHtml="";
        html = new File(strSortie);
        if (html.exists())html.delete();
        GenerateurCss.css(); // Genere le string script avec le code css
        if (html.createNewFile()) {
            System.out.println("Fichier HTML créé !"); //Création de la première partie de l'html dans DebutHtml
            codeHtml = new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">\n" +
                    //"  <link rel=\"icon\" type=\"image/png\" href=\"" + getClass().getClassLoader().getResource("").getPath() + "Icon.png" + "\" />" +
                    "  <title>IUT Stage</title>\n" +
                    "  <link href=\"https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&family=Poppins:wght@400;600&display=swap\" rel=\"stylesheet\">\n" +
                    "  <link href=\"https://fonts.googleapis.com/css2?family=Work+Sans:wght@300;500&display=swap\" rel=\"stylesheet\">\n" +
                    "  <style>\n" +
                            GenerateurCss.script +
                    "  </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "  <header>\n" +
                    "    <h1>IUT </h1>\n" +
                    "    <h2> Forum entreprise du ");
            finHtml = new String(" </div>\n" +
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
                    "      Departement Informatique site d'Aix-en-Provence\n" +
                    "    </p>\n" +
                    "  </footer>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>"); //Création de la première partie de l'html dans FinHtml
        }
        setDate(date);
        debutTableau("le matin");

        for (fr.univ_amu.DumbStages.donnees.Entreprise ent: LecteurExcel.mesEntreprisesMatin) {
            ajouterEntreprise(ent);
        }

        finTableau ();

        debutTableau("l'apres-midi");
        for (fr.univ_amu.DumbStages.donnees.Entreprise ent: LecteurExcel.mesEntreprisesApresMidi) {
            ajouterEntreprise(ent);
        }
        finTableau();

        setFinHtml();
        ecritDansFichier();
        ouvrirFichier();
    }

    public void setDate(String date) {
        codeHtml = codeHtml + date + "</h2>\n" +
                "  </header>\n" +
                "\n" +
                "  <div>";
    }//Insert dans Codehtml l'Html affichant le titre de la page

    public void debutTableau (String journey) {
        codeHtml = codeHtml + "<table border=\"1\" cellpadding=\"15\">\n" +
                "\n" +
                "      <caption>\n" +
                "        <h3>\n" +
                "          Liste des entreprises participant au forum " + journey + "\n" +
                "        </h3>\n" +
                "      </caption>\n" +
                "\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <th id=\"thLeft\"> Nom Entreprise </th>\n" +
                "          <th> Repr&eacute;sentant Entreprise </th>\n" +
                "          <th> Lien Web Entreprise </th>\n" +
                "          <th id=\"thRight\"> Zoom </th>";
    } //Insert dans codeHtml le début du tableau en html

    public void finTableau () {
        codeHtml = codeHtml + " </tbody></table>";
    } //Insert dans CodeHtml la fin du tableau en html

    public void ajouterEntreprise (Entreprise uneEntreprise){
        codeHtml = codeHtml + "    <tr>\n" +
                "        <td> "+uneEntreprise.getNom_en()+"</td> <td>";
        for (String rep : uneEntreprise.getRepresentants())
            this.codeHtml = this.codeHtml + rep +"</br>";
        this.codeHtml = this.codeHtml + "</td><td><a href=\""+uneEntreprise.getUrl()+"\">"+uneEntreprise.getNom_en()+"</a></td>" +
                "<td><a href="+uneEntreprise.getLienZoom()+">Lien Zoom</a> </br> Mot de passe: "+uneEntreprise.getMdpZoom()+"</td>\n</tr>";
    }//Insert dans CodeHtml une ligne du tableau contenant le nom de l'entreprise, des représentants, ainsi que l'url de leur site

    public void setFinHtml() {
        codeHtml = codeHtml + finHtml;
    }

    public String getFinHtml() {
        return finHtml;
    }

    public void ecritDansFichier() throws IOException {
        FileWriter FichierEcriture = new FileWriter(html);
        FichierEcriture.write(codeHtml);
        FichierEcriture.close();
    } //Ecrit Texte dans le fichier Sortie /!\ Si utilisé deux fois sur le même fichier, le premier contenu sera remplacé par le deuxième

    /*public static void main(String[] args) throws IOException {
        GenerateurHtml gen = new GenerateurHtml("/amuhome/d19002305/Bureau/Logo.png", "/amuhome/d19002305/Bureau/masortie3.html");
        gen.EcritDansFichier(gen.getDebutHtml() + "<h1>TEST</h1>" + gen.getFinHtml());
    }*/
    public void ouvrirFichier() throws IOException {
        Desktop.getDesktop().browse(html.toURI());
    }
}