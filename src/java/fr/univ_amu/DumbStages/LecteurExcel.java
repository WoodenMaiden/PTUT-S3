package fr.univ_amu.DumbStages;


import java.fr.univ_amu.DumbStages.donnees.Etudiant;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet; //?
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecteurExcel {

    private XSSFWorkbook monExcel;
    private XSSFSheet maFeuille; //?

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }
    public XSSFSheet getmaFeuille() {
        return maFeuille;
    }//?

    public void AjouterEtudiant (Etudiant etudiant) { //Deuxième point du forum de stage
        this.maFeuille.createRow(this.maFeuille.getLastRowNum()+1).createCell(0).setCellValue(etudiant.getNom() +
                etudiant.getPrenom() +", "+ etudiant.getGroupe());
    } //?

        public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise entreprise) { //Deuxième point du forum de stage
        this.maFeuille.getRow(0).createCell(this.maFeuille.getRow(0).getLastCellNum()+1).setCellValue(
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
