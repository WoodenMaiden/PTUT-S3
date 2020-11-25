package java.fr.univ_amu.DumbStages;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

public class GenerateurEdt {

    public File Entree;
    public File RepertSortie; // on va (très probablement) générer plusieurs fichiers, autant les mettre dans un répertoire non ?


    public GenerateurEdt(String chemin, String repertoire) throws SecurityException {
        this.Entree = new File(chemin);
        this.RepertSortie = new File(repertoire);

        if (!this.Entree.exists() || !RepertSortie.mkdir()) throw new SecurityException ("Une erreur d'entrée sortie est survenue :(.");
        
    }


}
