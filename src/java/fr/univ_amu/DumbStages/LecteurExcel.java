package fr.univ_amu.DumbStages;


import fr.univ_amu.DumbStages.donnees.Entreprise;
import fr.univ_amu.DumbStages.donnees.Etudiant;
import java.io.*;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet; //?
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecteurExcel {

    private XSSFWorkbook monExcel;
    private Sheet mesFeuilles[]; //?


    // https://stackoverflow.com/questions/4537980/equivalent-to-push-or-pop-for-arrays
    private static Sheet[] push(Sheet[] array, Sheet push) {
        Sheet[] longer = new Sheet[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
        for (Sheet S : monExcel){
            mesFeuilles = push(mesFeuilles, S);
        }
    }


    public XSSFWorkbook getFichier(){
        return monExcel;
    }
    public Sheet getmaFeuille(int index) throws RuntimeException {return mesFeuilles[index];}

    public void AjouterEtudiant (fr.univ_amu.DumbStages.donnees.Etudiant etudiant, XSSFSheet maFeuille) { //Deuxième point du forum de stage
        maFeuille.createRow(maFeuille.getLastRowNum()+1).createCell(0).setCellValue(etudiant.getNom() +
                etudiant.getPrenom() +", "+ etudiant.getGroupe());
    } //?

    public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise entreprise, XSSFSheet maFeuille) { //Deuxième point du forum de stage
    maFeuille.getRow(0).createCell(maFeuille.getRow(0).getLastCellNum()+1).setCellValue(
            entreprise.getNom_en());
    } //?


    public static void main(String[] args) {
        try {
            System.out.println("entrez le chemin absolu vers votre fichier :");
            Scanner entree = new Scanner(System.in);
            String path = entree.nextLine();
            LecteurExcel excelALire = new LecteurExcel(path);
            entree.close();
            System.out.println("fichier accédé !");

            Etudiant YP = new Etudiant("POMIE", "Yann");
            Entreprise ENT = new Entreprise("TESTENNTREPRISE", new String[] {"ZIT Zitoune", "AIR Wick"});




        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }

        finally {
            System.out.println("programme terminé");
        }
    }


}
