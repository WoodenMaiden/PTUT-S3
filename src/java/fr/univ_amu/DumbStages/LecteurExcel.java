package fr.univ_amu.DumbStages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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

            // Entree D:\Entreprises PTUT.xlsx

            System.out.println("entrez le chemin absolu vers votre fichier d'ENTREE :");
            Scanner entree = new Scanner(System.in);
            String path = entree.nextLine();
            LecteurExcel excelALire = new LecteurExcel(path);
            System.out.println("fichier d'entrée accédé !");

            System.out.println("entrez le chemin absolu vers votre fichier de SORTIE :");
            entree = new Scanner(System.in);
            path = entree.nextLine();
            entree.close();
            System.out.println("fichier de sortie accédé !");

            XSSFWorkbook fichier = excelALire.getFichier();

            XSSFSheet mySheet = fichier.getSheetAt(0);

            Vector<fr.univ_amu.DumbStages.donnees.Entreprise> MesEntreprises = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();

            for(Row row: mySheet)
            {
                if (row.getRowNum() != 0)
                {
                    String[] representants = new String[1];
                    representants[0] = "Inconnu";
                    String nom_en = "Inconnu";
                    String url = "Inconnu";
                    for (Cell cell: row) {
                        if (cell.getColumnIndex() == 0) nom_en = cell.getStringCellValue();
                        else if (cell.getColumnIndex() == 1) representants[0] = cell.getStringCellValue();
                        else if (cell.getColumnIndex() == 2) url = cell.getStringCellValue();
                    }
                    fr.univ_amu.DumbStages.donnees.Entreprise ent = new fr.univ_amu.DumbStages.donnees.Entreprise(nom_en, representants, url);
                    MesEntreprises.add(ent);
                    //ent.show();
                }
            }

            fr.univ_amu.DumbStages.GenerateurHtml html = new fr.univ_amu.DumbStages.GenerateurHtml(path);
            html.setDate("date au pif");
            html.DebutTableau();

            for (fr.univ_amu.DumbStages.donnees.Entreprise ent: MesEntreprises) {
                html.AjouterEntreprise(ent);
            }

            html.FinTableau ();
            html.setFinHtml();

            html.EcritDansFichier(html.CodeHtml);

            // Sortie D:/Tableau_Etudiant_Entreprises.xls

            String filename = "D:/Tableau_Etudiant_Entreprises.xls" ;
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("FirstSheet");

            CellStyle borderedCellStyle = workbook.createCellStyle();
            borderedCellStyle.setBorderBottom(BorderStyle.THIN);
            borderedCellStyle.setBorderLeft(BorderStyle.THIN);
            borderedCellStyle.setBorderRight(BorderStyle.THIN);
            borderedCellStyle.setBorderTop(BorderStyle.THIN);
            borderedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            borderedCellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row row = sheet.createRow((short)0);
            row.setHeightInPoints(40);

            for (int i = 0 ; i < MesEntreprises.size(); ++i) {
                row.createCell(i+1).setCellValue(MesEntreprises.elementAt(i).getNom_en());
                row.getCell(i + 1).setCellStyle(borderedCellStyle);
                sheet.autoSizeColumn(i+1);
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Fichier généré!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        } finally {
            System.out.println("programme terminé");
        }
    }


}
