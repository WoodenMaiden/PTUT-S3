package fr.univ_amu.DumbStages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
    private int nbEtudiants = 0;
    private Vector<Integer> groupes;
    private static String desktopPath;

    public static Vector<fr.univ_amu.DumbStages.donnees.Entreprise> mesEntreprisesMatin = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();
    public static Vector<fr.univ_amu.DumbStages.donnees.Entreprise> mesEntreprisesApresMidi = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();
    public static Vector<fr.univ_amu.DumbStages.donnees.Etudiant> mesEtudiants = new Vector<fr.univ_amu.DumbStages.donnees.Etudiant>();

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
        this.groupes = new Vector<Integer>();
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }

    public void setFichier(XSSFWorkbook monExcel) {
        this.monExcel = monExcel;
    }

    public void addGroupe( int grp ) {
        this.groupes.add(grp);
    }

    public int getGroupesSize() {
        return this.groupes.size();
    }

    public int findGroupe(int value) {
        if (this.groupes.size() == 0) return -1;

        int k = 0;
        while (k < this.groupes.size()) {
            if (this.groupes.get(k) == value) return this.groupes.get(k);
            ++k;
        }
        return -1;
    } //findGroupe

    public void verifierEtIncrementerNombreDeGroupe(int value) {
        if (value > 0)
            if (this.findGroupe(value) != value) {
                this.addGroupe(value);
            }
    } //verifierEtIncrementerNombreDeGroupe

    public static void generateMesEntreprises(Vector<Entreprise> mesEntreprises, LecteurExcel lecteur, String desktopPathHtml,int sheetNumber) throws IOException {
        XSSFSheet mySheet = lecteur.getFichier().getSheetAt(sheetNumber);

        for(Row row: mySheet)
        {
            if (row.getRowNum() != 0)
            {
                String[] representants = new String[1];
                representants[0] = "Inconnu";
                String nom_en = "Inconnu";
                String url = "Inconnu";
                String lienzoom = "Inconnu";
                String mdpzoom_string = "Aucun";

                for (Cell cell: row) {
                    if (cell.getColumnIndex() == 0) nom_en = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 1) representants[0] = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 2) url = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 3) lienzoom = cell.getStringCellValue();
                    else if (cell.getColumnIndex() == 4)  {
                        mdpzoom_string = "";
                        mdpzoom_string += (int) cell.getNumericCellValue();
                    }
                }
                Entreprise ent = new Entreprise(nom_en, representants, url);
                ent.setLienZoom(lienzoom);
                ent.setMdpZoom(mdpzoom_string);
                mesEntreprises.add(ent);
            }
        }
    }   //GenerateMesEntreprises

    public static void generateEtudiantsFromExcel(LecteurExcel lecteur) {
        XSSFSheet mySheet = lecteur.getFichier().getSheetAt(2);

        int i = 0;
        for(Row row: mySheet)
        {
            System.out.println(i++);
            if (row.getRowNum() > 0)
            {
                Etudiant etu = new Etudiant(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), "Groupe "+(int) row.getCell(2).getNumericCellValue());
                mesEtudiants.add(etu);
                lecteur.verifierEtIncrementerNombreDeGroupe((int) row.getCell(2).getNumericCellValue());
                System.out.println(etu.getNom()+" "+etu.getPrenom()+" "+etu.getGroupe());
            }
        }
    }   //GenerateEtudiantsFromExcel

    public static void generateFiles(Vector<Entreprise> mesEntreprises, LecteurExcel monLecteur,String endFile) throws IOException {

        //Génération des étudiants dans le vecteur mesEtudiants
        LecteurExcel.generateEtudiantsFromExcel(monLecteur);
        System.out.println("Etudiants créés");

        String desktopPathExcel = desktopPath + "\\Tableau Etudiant Entreprises "+ endFile +".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Ajoute d'un style de case pré-défini avec bordure
        CellStyle borderedCellStyle = workbook.createCellStyle();
        borderedCellStyle.setBorderBottom(BorderStyle.THIN);
        borderedCellStyle.setBorderLeft(BorderStyle.THIN);
        borderedCellStyle.setBorderRight(BorderStyle.THIN);
        borderedCellStyle.setBorderTop(BorderStyle.THIN);
        borderedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        borderedCellStyle.setAlignment(HorizontalAlignment.CENTER);

        System.out.println("Workbook de sortie créé");

        //Remplissage du Excel de sortie: Tableau_Etudiant_Entreprise.xlsx
        for (int j = 1; j <= monLecteur.getGroupesSize(); ++j) {
            //Création d'une feuille par groupe
            System.out.println("Creation Feuille Groupe "+j);
            XSSFSheet sheet = workbook.createSheet("GROUPE "+ j);

            Row row = sheet.createRow((short) 0);
            row.createCell(0).setCellValue("2ème ANNEE");

            //Définition de la taille d'un groupe selon le nombre d'étudiant dans le même groupe
            int sizeGroupe = 0;
            for (Etudiant etu : mesEtudiants) {
                if (etu.getGroupe().equals("Groupe "+j)) sizeGroupe++;
            }


            //remplissage des cases de légende
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

        //Création et écriture d'un fichier de sortie
        FileOutputStream fileOut = new FileOutputStream(desktopPathExcel);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("Fichier généré!");
    }

    public static void FirstStep() {
        try {

            //Réinitialisation des variables d'entreprises et d'étudiants pour une nouvelle génération
            mesEntreprisesMatin = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();
            mesEntreprisesApresMidi = new Vector<fr.univ_amu.DumbStages.donnees.Entreprise>();
            mesEtudiants = new Vector<fr.univ_amu.DumbStages.donnees.Etudiant>();

            System.out.println("Excel en cours d'accès");
            // Variable chemin du bureau

            //Creation du Lecteur d'Excel
            LecteurExcel monLecteur = new LecteurExcel(Step1Controler.path);
            System.out.println("Excel d'entrée accédé !");

            FileSystemView fsv = FileSystemView.getFileSystemView(); // Recuperation du chemin du bureau
            File desktopFile = fsv.getHomeDirectory();

            desktopPath = desktopFile.getAbsolutePath(); // Ajout du chemin dans la variable fait pour
            String desktopPathHtml = desktopPath + "\\Forum Stage.html";

            //Generation des entreprises dans le vecteur mesEntreprises
            LecteurExcel.generateMesEntreprises(mesEntreprisesMatin,monLecteur, desktopPathHtml,0);
            LecteurExcel.generateMesEntreprises(mesEntreprisesApresMidi,monLecteur, desktopPathHtml,1);



            System.out.println(Step1Controler.localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            GenerateurHtml html = new GenerateurHtml(desktopPathHtml,Step1Controler.localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            generateFiles(mesEntreprisesMatin,monLecteur,"Matin");
            generateFiles(mesEntreprisesApresMidi,monLecteur,"Apres-Midi");

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
}