package fr.univ_amu.DumbStages;

//import fr.univ_amu.DumbStages.donnees.*;
import fr.univ_amu.DumbStages.donnees.Entreprise;

import java.io.*;

public class GenerateurHtml {
    private File Sortie;
    public String CodeHtml;

    GenerateurHtml(String strSortie) throws IOException { //Constructeur

        this.Sortie = new File(strSortie);

        if (this.Sortie.createNewFile()) {
            System.out.println("fichier créé !"); //Création de la première partie de l'html dans CodeHtml
        }
            this.CodeHtml = new String("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                    "<html><head>\n" +
                    "\t<meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">\n" +
                    "\t<title>Forum entreprise de 2020</title>\n" +
                    "\t<link rel=\"stylesheet\" href=\"forum_fichiers/uncss.css\" type=\"text/css\" media=\"screen\" title=\"no title\" charset=\"utf-8\">\n" +
                    "<style type= \" text/css \" >html, body { \n" +
                    "                        margin: 0; \n" +
                    "                        padding: 0; \n" +
                    "                        font-family: 'Work Sans', sans-serif; \n" +
                    "                        font-weight: 300; \n" +
                    "                        width: 100%; \n" +
                    "                        position: relative; \n" +
                    "                        background-color: white; \n" +
                    "                        color: #13163E; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    html { \n" +
                    "                        width: auto; \n" +
                    "                        margin: 0; \n" +
                    "                        min-height: 100%; \n" +
                    "                        top: 0; \n" +
                    "                        right: 0; \n" +
                    "                        left: 0; \n" +
                    "                        display: flex; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    header { \n" +
                    "                        flex-direction: row; \n" +
                    "                        height: 70px; \n" +
                    "                        display: flex; \n" +
                    "                        width: 100%; \n" +
                    "                        background-color: white; \n" +
                    "                        border: 0px; \n" +
                    "                        border-top: 0px; \n" +
                    "                        border-bottom: 1px; \n" +
                    "                        border-style: solid; \n" +
                    "                        justify-content: space-between; \n" +
                    "                        border-color: rgba(19, 22, 62, 0.103); \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    h1 { \n" +
                    "                        height: 70px; \n" +
                    "                        line-height: 70px; \n" +
                    "                        width: 200px; \n" +
                    "                        margin: 0; \n" +
                    "                        font-family: 'Poppins', sans-serif; \n" +
                    "                        background-color: white; \n" +
                    "                        font-size: 30px; \n" +
                    "                        margin-left: 5%; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    h1::after { \n" +
                    "                        content: 'Stage'; \n" +
                    "                        font-weight: 400; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    h2 { \n" +
                    "                        font-weight: 400; \n" +
                    "                        font-size: 15px; \n" +
                    "                        width: auto; \n" +
                    "                        font-family: 'Poppins', sans-serif; \n" +
                    "                        line-height: 70px; \n" +
                    "                        height: auto; \n" +
                    "                        margin: 0; \n" +
                    "                        padding: 0; \n" +
                    "                        margin-right: 5%; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    h3 { \n" +
                    "                        float: left; \n" +
                    "                        padding-bottom: 20px; \n" +
                    "                        padding-top: 20px; \n" +
                    "                        font-size: 20px; \n" +
                    "                        background-color: #13163E; \n" +
                    "                        height: 10px; \n" +
                    "                        font-size: 17px; \n" +
                    "                        color: white; \n" +
                    "                        text-align: center; \n" +
                    "                        line-height: 10px; \n" +
                    "                        font-weight: bold; \n" +
                    "                        width: 500px; \n" +
                    "                        border-radius: 25px; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    table { \n" +
                    "                        align-self: center; \n" +
                    "                        border-style: none; \n" +
                    "                        border-top-left-radius: 50px; \n" +
                    "                        border-collapse: collapse; \n" +
                    "                        border-color: rgba(19, 22, 62, 0.253); \n" +
                    "                        -webkit-animation: slide-top 0.8s ease-out both; \n" +
                    "                        animation: slide-top 0.8s ease-out both; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    th { \n" +
                    "                        border-spacing: 0px; \n" +
                    "                        color: white; \n" +
                    "                        background-color: #13163E; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    #thLeft { \n" +
                    "                        border-top-left-radius: 20px; \n" +
                    "                        border: 0px; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    #thRight { \n" +
                    "                        border-top-right-radius: 20px; \n" +
                    "                        border: 0px; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    td { \n" +
                    "                        background-color: white; \n" +
                    "                        border: 1px; \n" +
                    "                        border-style: solid; \n" +
                    "                        border-color: rgba(19, 22, 62, 0.103); \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    td:hover { \n" +
                    "                        background-color: rgba(19, 22, 62, 0.103); \n" +
                    "                        transition-duration: 100ms; \n" +
                    "                        cursor: pointer; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    div { \n" +
                    "                        margin-top: 50px; \n" +
                    "                        margin-bottom: 50px; \n" +
                    "                        display: flex; \n" +
                    "                        width: 100%; \n" +
                    "                        justify-content: center; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    footer { \n" +
                    "                        position: absolute; \n" +
                    "                        width: 100%; \n" +
                    "                        height: 50px; \n" +
                    "                        display: flex; \n" +
                    "                        justify-content: space-between; \n" +
                    "                        bottom: 0; \n" +
                    "                        border: 0px; \n" +
                    "                        border-top: 1px; \n" +
                    "                        border-bottom: 0px; \n" +
                    "                        align-content: center; \n" +
                    "                        border-style: solid; \n" +
                    "                        border-color: rgba(19, 22, 62, 0.103); \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    p { \n" +
                    "                        margin-left: 5%; \n" +
                    "                        margin-right: 5%; \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    @-webkit-keyframes slide-top { \n" +
                    "                        0% { \n" +
                    "                            opacity: 0%; \n" +
                    "                            -webkit-transform: translateY(150px); \n" +
                    "                            transform: translateY(150px); \n" +
                    "                        } \n" +
                    "                        50% { \n" +
                    "                            opacity: 100%; \n" +
                    "                        } \n" +
                    "                        100% { \n" +
                    "                            -webkit-transform: translateY(0px); \n" +
                    "                            transform: translateY(0px); \n" +
                    "                        } \n" +
                    "                    } \n" +
                    "                     \n" +
                    "                    @keyframes slide-top { \n" +
                    "                        0% { \n" +
                    "                            opacity: 0%; \n" +
                    "                            -webkit-transform: translateY(100px); \n" +
                    "                            transform: translateY(100px); \n" +
                    "                        } \n" +
                    "                        50% { \n" +
                    "                            opacity: 100%; \n" +
                    "                        } \n" +
                    "                        100% { \n" +
                    "                            -webkit-transform: translateY(0px); \n" +
                    "                            transform: translateY(0px); \n" +
                    "                        } \n" +
                    "                    } </style>"+
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<div style=\"text-align: center;\">\n" +
                    "    <big>   \n" +
                    "        Les Smarts Pointeurs<br>\n" +
                    "        <a href=\"mailto:marc.laporte@univ-amu.fr\">marc.laporte@univ-amu.fr </a> <br>\n" +
                    "        I.U.T. d'Aix-Marseille <br>\n" +
                    "        Département Informatique site d'Aix-en-Provence\n" +
                    "    </big>\n" +
                    "</div>\n" +
                    "\n" +
                    "<div style=\"text-align: center;\"><img style=\"width: 623px; height: 7px;\" alt=\"\" src=\"forum_fichiers/TraitCourant.gif\"></div>\n"+
                    "<h1><span style=\"color: rgb(51, 102, 255);\"> Forum entreprise du ");
        }



    public void setDate(String Date) {
        this.CodeHtml = CodeHtml + Date + "</span></h1>"
                +"\n" +
                " <table border=\"1\" cellpadding=\"10\">\n" +
                "    <caption> <h2>Liste des entreprises participant au forum</h2> </caption>\n" +
                "  <tbody>\n" +
                "    <tr>\n" +
                "        <th> Nom Entreprise </th>\n" +
                "        <th> Représentants Entreprise </th>\n" +
                "\t    <th> Lien Web Entreprise </th>+\n"+
                "<th> Zoom </th>";
    }//Insert dans Codehtml l'Html affichant la date. Début du tableau en Html
    // /!\ A APPELER EN PREMIER ET UNE SEULE FOIS



    public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise uneEntreprise){
        this.CodeHtml = this.CodeHtml + "    <tr>\n" +
                "        <td> "+uneEntreprise.getNom_en()+"</td> <td>";
        for (String rep : uneEntreprise.getRepresentants())
            this.CodeHtml = this.CodeHtml + rep +"</br>";
        this.CodeHtml = this.CodeHtml + "</td><td><a href=\""+uneEntreprise.getUrl()+"\">"+uneEntreprise.getNom_en()+"</a></td>" +
                "<td><a href="+uneEntreprise.getLienZoom()+"> </a> </br> Mot de passe: "+uneEntreprise.getMdpZoom()+"</td>\n</tr>";
    }//Insert dans CodeHtml une ligne du tableau contenant le nom de l'entreprise, des représentants, l'url de leur site, le lien et le mpd Zoom
    // A APPELER EN DEUXIEME (autant de fois qu'il le faut

    public void FinTableau () {
        this.CodeHtml = this.CodeHtml + " </tbody></table></body></html>";
    } //Insert dans CodeHtml la fin du tableau en html et termine l'html
    //A APPELER EN DERNIER ET UNE SEULE FOIS


    public void GenereLeFichierHtml() throws IOException {
        FileWriter FichierEcriture = new FileWriter(this.Sortie);
        FichierEcriture.write(CodeHtml);
        FichierEcriture.close();
    } //Ecrit Texte dans le fichier Sortie
    // /!\ Si utilisé deux fois sur le même fichier, le premier contenu sera remplacé par le deuxième

    public static void main(String[] args) throws IOException {
        String[] tab = new String[3];
        tab[0] = "Louis";
        tab[1] = "Othon";
        tab[2] = "Napoléon";
        fr.univ_amu.DumbStages.donnees.Entreprise entreprise1 = new Entreprise("Entreprise1", tab,
                "https://fr.wikipedia.org/wiki/Premier_Empire", "https://fr.wikipedia.org/wiki/Zoom", "*****");
        fr.univ_amu.DumbStages.donnees.Entreprise entreprise2 = new Entreprise("Entreprise1", tab,
                "https://fr.wikipedia.org/wiki/Premier_Empire", "https://fr.wikipedia.org/wiki/Zoom", "*****");
        fr.univ_amu.DumbStages.donnees.Entreprise entreprise3 = new Entreprise("Entreprise1", tab,
                "https://fr.wikipedia.org/wiki/Premier_Empire", "https://fr.wikipedia.org/wiki/Zoom", "*****");

        GenerateurHtml GH = new GenerateurHtml("");
        GH.setDate("1912");
        GH.AjouterEntreprise(entreprise1);
        GH.AjouterEntreprise(entreprise2);
        GH.AjouterEntreprise(entreprise3);
        GH.FinTableau();
        GH.GenereLeFichierHtml();
    }
}