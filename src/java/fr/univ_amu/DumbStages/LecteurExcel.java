package fr.univ_amu.DumbStages;


import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LecteurExcel {

    private XSSFWorkbook monExcel;

    public LecteurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook(new java.io.File(path));
    }

    public XSSFWorkbook getFichier(){
        return monExcel;
    }


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
