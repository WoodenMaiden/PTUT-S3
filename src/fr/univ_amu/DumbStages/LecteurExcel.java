package fr.univ_amu.DumbStages;

import java.io.File;
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

import javax.swing.filechooser.FileSystemView;

public class LecteurExcel {

    private XSSFWorkbook monExcel;
    private XSSFSheet monExcel2; //?
    private int nbEtudiants = 0;
    private int nbGroupes;

    public static Vector<fr.univ_amu.DumbStages.donnees.Entreprise> mesEntreprises = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();
    public static Vector<fr.univ_amu.DumbStages.donnees.Etudiant> mesEtudiants = new Vector<fr.univ_amu.DumbStages.donnees.Etudiant>();


    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }

    public static void GenerateHTMLAndMesEntreprises(XSSFWorkbook fichier, String desktopPathHtml) throws IOException {
        XSSFSheet mySheet = fichier.getSheetAt(0);

        for(Row row: mySheet)
        {
            if (row.getRowNum() != 0)
            {
                String[] representants = new String[1];
                representants[0] = "Inconnu";
                String nom_en = "Inconnu";
                String url = "Inconnu";
                String lienzoom = "Inconnu";
                String mdpzoom = "Inconnu";

                for (Cell cell: row) {
                    if (cell.getColumnIndex() == 0) nom_en = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 1) representants[0] = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 2) url = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 3) lienzoom = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 4) mdpzoom = cell.getStringCellValue();
                }
                Entreprise ent = new Entreprise(nom_en, representants, url);
                mesEntreprises.add(ent);
                //ent.show();
            }
        }
    }   //GenerateHTMLAndReturnEntreprises

    public static void GenerateEtudiantsFromExcel(XSSFWorkbook fichier) {
        XSSFSheet mySheet = fichier.getSheetAt(1);

        int i = 0;
        for(Row row: mySheet)
        {

            System.out.println(i++);
            if (row.getRowNum() > 0)
            {
                Etudiant etu = new Etudiant(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), "Groupe "+(int) row.getCell(2).getNumericCellValue());
                mesEtudiants.add(etu);
                System.out.println(etu.getNom()+" "+etu.getPrenom()+" "+etu.getGroupe());
            }
        }
    }   //GenerateEtudiantsFromExcel

    public static void FirstStep() {
        try {

            System.out.println("Excel en cours d'accès");
            String desktopPath = ""; // Variable chemin du bureau
            LecteurExcel monLecteur = new LecteurExcel(SceneControler.path);
            System.out.println("Excel d'entrée accédé !");

            FileSystemView fsv = FileSystemView.getFileSystemView(); // Recuperation du chemin du bureau
            File desktopFile = fsv.getHomeDirectory();

            desktopPath = desktopFile.getAbsolutePath(); // Ajout du chemin dans la variable fait pour
            String desktopPathHtml = desktopPath + "\\index.html";

            //Recupération du fichier Excel
            XSSFWorkbook fichier = monLecteur.getFichier();

            LecteurExcel.GenerateHTMLAndMesEntreprises(fichier, desktopPathHtml);

            GenerateurHtml html = new GenerateurHtml(desktopPathHtml,"04/12/2020");

            LecteurExcel.GenerateEtudiantsFromExcel(fichier);
            System.out.println("Etudiants créés");

            String desktopPathExcel = desktopPath + "\\Tableau_Etudiant_Entreprises.xls";
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
                for (Etudiant etu : mesEtudiants) {
                    if (etu.getGroupe().equals("Groupe "+j)) sizeGroupe++;
                }

                row.createCell(1).setCellValue(sizeGroupe + " étudiants");

                row = sheet.createRow((short) 1);
                row.createCell(0).setCellValue("GROUPE "+ j);

                row = sheet.createRow((short) 2);
                row.createCell(0).setCellValue("Nom");
                row.createCell(1).setCellValue("Prénom");

                //Génération des entreprises sur la première ligne
                for (int i = 0; i < mesEntreprises.size(); ++i) {
                    row.createCell(i + 2).setCellValue(mesEntreprises.elementAt(i).getNom_en());
                    row.getCell(i + 2).setCellStyle(borderedCellStyle);
                    sheet.autoSizeColumn(i + 2);
                }

                //Génération des étudiants dans le Excel selon leur groupe sur la première colonne
                int i = 0;
                for (int k = 0; k < mesEtudiants.size(); ++k) {
                    Etudiant monEtudiant = mesEtudiants.get(k);
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

            FileOutputStream fileOut = new FileOutputStream(desktopPathExcel);
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
    }   //FirstStep

    public static void start() {
        FirstStep();
    }

    public static void main(String[] args) {
        App.launch(App.class,args);
    }
}