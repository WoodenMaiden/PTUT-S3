package fr.univ_amu.DumbStages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import fr.univ_amu.DumbStages.donnees.Entreprise;
import fr.univ_amu.DumbStages.donnees.Etudiant;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet; //?
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecteurExcel {

    private XSSFWorkbook monExcel;
    private XSSFSheet monExcel2; //?
    private Vector<Entreprise> listeEntreprises;
    private Vector<Etudiant> listeEtudiants;
    private int nbEtudiants = 0;
    private int nbGroupes;

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }
    public XSSFSheet getmonExcel2() {
        return monExcel2;
    }//?

    public void AjouterEtudiant (fr.univ_amu.DumbStages.donnees.Etudiant etudiant, Sheet sheet) { //Deuxième point du forum de stage
        sheet.createRow(this.monExcel2.getLastRowNum()+1).createCell(0).setCellValue(etudiant.getNom() +
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
            String path = "D:/Entreprises PTUT.xlsx"; //entree.nextLine();
            LecteurExcel excelALire = new LecteurExcel(path);
            System.out.println("fichier d'entrée accédé !");

            System.out.println("entrez le chemin absolu vers votre fichier de SORTIE :");
            entree = new Scanner(System.in);
            path = "D:/test.html"; //entree.nextLine();
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

            Vector<fr.univ_amu.DumbStages.donnees.Etudiant> MesEtudiants = new Vector<fr.univ_amu.DumbStages.donnees.Etudiant>();

            System.out.println("Groupes créés");

            MesEtudiants.add(new Etudiant("Pomie", "Yann", "Groupe 1"));
            MesEtudiants.add(new Etudiant("Poirie", "Yang", "Groupe 1"));
            MesEtudiants.add(new Etudiant("DuBourgPalette", "Sacha", "Groupe 2"));
            MesEtudiants.add(new Etudiant("Bergudo", "Chassa", "Groupe 2"));
            MesEtudiants.add(new Etudiant("Apero", "Nicolas", "Groupe 3"));
            MesEtudiants.add(new Etudiant("Asta", "Vincent", "Groupe 3"));

            System.out.println("Etudiants créés");

            String filename = "D:/Tableau_Etudiant_Entreprises.xls";
            XSSFWorkbook workbook = new XSSFWorkbook();
            CellStyle borderedCellStyle = workbook.createCellStyle();
            borderedCellStyle.setBorderBottom(BorderStyle.THIN);
            borderedCellStyle.setBorderLeft(BorderStyle.THIN);
            borderedCellStyle.setBorderRight(BorderStyle.THIN);
            borderedCellStyle.setBorderTop(BorderStyle.THIN);
            borderedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            borderedCellStyle.setAlignment(HorizontalAlignment.CENTER);

            System.out.println("Workbook 2 créé");

            for (int j = 1; j <= 3; ++j) {
                System.out.println("Creation Feuille Groupe "+j);
                XSSFSheet sheet = workbook.createSheet("GROUPE "+ j);

                Row row = sheet.createRow((short) 0);
                row.createCell(0).setCellValue("2ème ANNEE");

                int sizeGroupe = 0;
                for (Etudiant etu : MesEtudiants) {
                    if (etu.getGroupe().equals("Groupe "+j)) sizeGroupe++;
                }

                row.createCell(1).setCellValue(sizeGroupe + " étudiants");

                row = sheet.createRow((short) 1);
                row.createCell(0).setCellValue("GROUPE "+ j);

                //row.setHeightInPoints(40);

                row = sheet.createRow((short) 2);
                row.createCell(0).setCellValue("Nom");
                row.createCell(1).setCellValue("Prénom");

                for (int i = 0; i < MesEntreprises.size(); ++i) {
                    System.out.println("i "+i);
                    row.createCell(i + 2).setCellValue(MesEntreprises.elementAt(i).getNom_en());
                    row.getCell(i + 2).setCellStyle(borderedCellStyle);
                    sheet.autoSizeColumn(i + 2);
                }

                int i = 0;
                for (int k = 0; k < MesEtudiants.size(); ++k) {
                    System.out.println("k "+k);
                    Etudiant monEtudiant = MesEtudiants.get(k);
                    if (monEtudiant.getGroupe().equals("Groupe "+j)) {
                        Row EtuRow = sheet.createRow((i + 3));
                        EtuRow.createCell(0).setCellValue(monEtudiant.getNom());
                        EtuRow.createCell(1).setCellValue(monEtudiant.getPrenom());
                        EtuRow.getCell(0).setCellStyle(borderedCellStyle);
                        EtuRow.getCell(1).setCellStyle(borderedCellStyle);
                        sheet.autoSizeColumn(0);
                        sheet.autoSizeColumn(1);
                        EtuRow.setHeightInPoints(40);
                        i++;
                    };
                }

            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Fichier généré!");

        } catch (Exception e) {
            System.out.println("erreur "+e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("programme terminé");
        }
    }


}
