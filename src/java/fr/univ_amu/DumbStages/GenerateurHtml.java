package fr.univ_amu.DumbStages;

import fr.univ_amu.DumbStages.donnees.*;
import java.io.*;

public class GenerateurHtml {
    private File Entree;
    private File Sortie;
    private String DebutHtml;
    private String FinHtml;
    public String CodeHtml;

    GenerateurHtml(String strEntree, String strSortie) throws IOException {
        this.Entree = new File(strEntree);
        this.Sortie = new File(strSortie);
        if (this.Sortie.createNewFile()) {
            System.out.println("fichier créé !");
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
            this.FinHtml = new String("</body></html>");
            this.CodeHtml = new String ("<h1><span style=\"color: rgb(51, 102, 255);\"> Forum entreprise du");
        }
    }

    public void setDate(String Date) {
        this.CodeHtml = CodeHtml + Date + " 23 janvier 2020</span></h1>";
    }

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
    }

    public void FinTableau () {
        this.CodeHtml = this.CodeHtml + " </tbody></table>";
    }

    public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise uneEntreprise){
        this.CodeHtml = this.CodeHtml + "    <tr>\n" +
                "        <td> "+uneEntreprise.getNom_en()+"</td>";
    }

    public void AjouterRepresentants (fr.univ_amu.DumbStages.donnees.Entreprise uneEntreprise) {
        this.CodeHtml = this.CodeHtml + "<td>";
        for (String rep : uneEntreprise.getRepresentants())
            this.CodeHtml = this.CodeHtml + rep +"</br>";
        this.CodeHtml = this.CodeHtml + "</td>";

    }

    public void AjouterUrl (fr.univ_amu.DumbStages.donnees.Entreprise uneEntreprise)
    {
        this.CodeHtml = this.CodeHtml+"<td><a href=\""+uneEntreprise.getUrl()+"\">"+uneEntreprise.getNom_en()+"</a></td>\n" +
            "    </tr>";
    }

    public String getDebutHtml() {
        return DebutHtml;
    }

    public String getFinHtml() {
        return FinHtml;
    }

    public void EcritDansFichier() throws IOException {
        FileWriter FichierEcriture = new FileWriter(this.Sortie);
        FichierEcriture.write(this.CodeHtml);
        FichierEcriture.close();
    }

    public void EcritDansFichierTest(String Texte) throws IOException {
        FileWriter FichierEcriture = new FileWriter(this.Sortie);
        FichierEcriture.write(Texte);
        FichierEcriture.close();
    }

    public static void main(String[] args) throws IOException {
        GenerateurHtml gen = new GenerateurHtml("/amuhome/d19002305/Bureau/Logo.png", "/amuhome/d19002305/Bureau/masortie3.html");
        gen.EcritDansFichierTest(gen.getDebutHtml() + "<h1>TEST</h1>" + gen.getFinHtml());
    }
}
