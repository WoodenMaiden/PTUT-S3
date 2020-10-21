package fr.univ_amu.DumbStages;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet; //?
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecteurExcel {

    private XSSFWorkbook monExcel;
    private XSSFSheet monExcel2; //?

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }
    public XSSFSheet getmonExcel2() {
        return monExcel2;
    }//?

    public void AjouterEtudiant (fr.univ_amu.DumbStages.donnees.Etudiant etudiant) { //Deuxième point du forum de stage
        this.monExcel2.createRow(this.monExcel2.getLastRowNum()+1).createCell(0).setCellValue(etudiant.getNom() +
                etudiant.getPrenom() +", "+ etudiant.getGroupe());
    } //?

        public void AjouterEntreprise (fr.univ_amu.DumbStages.donnees.Entreprise entreprise) { //Deuxième point du forum de stage
        this.monExcel2.getRow(0).createCell(this.monExcel2.getRow(0).getLastCellNum()+1).setCellValue(
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

            XSSFWorkbook fichier = excelALire.getFichier();

            XSSFSheet mySheet = fichier.getSheetAt(0);

            Vector<fr.univ_amu.DumbStages.donnees.Entreprise> MesEntreprises = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();

            for(Row row: mySheet)
            {
                if (row.getRowNum() != 0)
                {
                    String[] representants = new String[1];
                    representants[0] = row.getCell(1).getStringCellValue();
                    fr.univ_amu.DumbStages.donnees.Entreprise ent = new fr.univ_amu.DumbStages.donnees.Entreprise(row.getCell(0).getStringCellValue(), representants, row.getCell(2).getStringCellValue());
                    MesEntreprises.add( ent );
                    ent.show();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        } finally {
            System.out.println("programme terminé");
        }
    }


}
