package java.fr.univ_amu.DumbStages;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class GenerateurExcel {

    private XSSFWorkbook monExcel;

    public GenerateurExcel(String path) throws IOException, InvalidFormatException {
        this.monExcel = new XSSFWorkbook();



    }

}
