package vstup_test;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class ExcelStudentReader {

    private boolean running=false;

    private static ExcelStudentReader instance=new ExcelStudentReader();

    public static synchronized void setMarksFromExcel(String filePathOnDisk){
        if (instance.running){throw new RuntimeException("function for passing mark is started by you or other user");}
        else{
            instance.running=true;
            File inputMarksFile=new File(filePathOnDisk);
            if (!inputMarksFile.exists()){throw new RuntimeException("Internal Server Error file not found on server for path="+filePathOnDisk);}
            else{

                try {

                    // Creating a Workbook from an Excel file (.xls or .xlsx)
                    Workbook workbook = WorkbookFactory.create(inputMarksFile);

                    // Retrieving the number of sheets in the Workbook
                    int sheetsNum=workbook.getNumberOfSheets();
                    System.out.println("Workbook has " +  sheetsNum+ " Sheets : ");

                    if (sheetsNum<1){throw new RuntimeException("Internal Server Error Invalid sheets count in excel document. Pages found="+sheetsNum);}

                    Sheet markSheet=workbook.getSheetAt(0);

                    // Create a DataFormatter to format and get each cell's value as String
                    DataFormatter dataFormatter = new DataFormatter();

                    System.out.println("last row num="+markSheet.getLastRowNum());
                    System.out.println("physical number of rows="+markSheet.getPhysicalNumberOfRows());

                    int i=0;
                    Student s=new Student();
                    for (Row row: markSheet) {
                        //create student
                        if (i>=1) {

                            Cell keyProp=row.getCell(0);
                            String keyPropStr=dataFormatter.formatCellValue(keyProp);
                            if (keyPropStr.equals("")){/*check user by property and set error to errors list;*/continue;}
                            s.setKeyProp(keyPropStr);

                            Cell markValue=row.getCell(1);//
                            String markStrValue=dataFormatter.formatCellValue(markValue);
                            if (markStrValue.equals("")){/*set error to errors list;*/continue;}
                            s.setMark(Integer.parseInt(markStrValue));

                            System.out.print(i+" row "+s + "\t");
                        }

                        System.out.println();
                        i++;
                    }


                } catch (IOException | InvalidFormatException e) {
                    e.printStackTrace();
                }

            }
        }
        instance.running=false;
    }

    public static void main(String[] args) {

        ExcelStudentReader.setMarksFromExcel("./student_marks.xlsx");

    }
}
