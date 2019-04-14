import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.*;

import java.util.HashMap;


public class XLSXReader {

    private String studentInfoDirect;
    private String testScoreDirect;
    private String retakeTestScoreDirect;
    private HashMap<Integer, Student> allStudents;
    private int runningGradeTotal; //storing
    private Iterator<Row> rowIterator;
    private Row row;
    private Iterator<Cell> cellIterator;


    public XLSXReader(String studentInfoDirect, String testScoreDirect, String retakeTestScoreDirect){
        this.studentInfoDirect = studentInfoDirect;
        this.testScoreDirect = testScoreDirect;
        this.retakeTestScoreDirect = retakeTestScoreDirect;
        this.runningGradeTotal = 0;
    }



    public HashMap<Integer, Student> gatherStudentInfo() throws IOException{
        //hashmap that will be returned
        allStudents = new HashMap<Integer, Student>();

        File studentInfo = new File(studentInfoDirect);
        FileInputStream fis = new FileInputStream(studentInfo);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        //identifying what information will be in which column
        rowIterator = sheet.iterator();
        row = rowIterator.next();
        cellIterator = row.cellIterator();

        int idColumn = -1;
        int majorColumn = -1;
        int genderColumn = -1;

        int colCount = 0;
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String colName = cell.toString();
            System.out.println(colName);
            if(colName.contains("studentId")){
                idColumn = colCount;
            }else if(colName.contains("major")){
                majorColumn = colCount;

            }else if(colName.contains("gender")){
                genderColumn = colCount;

            }else{
                System.out.println("unexpected Column in Student_Info.xlsx");
            }
            colCount++;
        }
        System.out.println(idColumn+" "+ majorColumn+" "+ genderColumn);

        //todo: maybe put something here to ends the method if any of them are still -1?


        int studentID = -1;
        String studentMajor = "error";
        String studentGender = "?";
        while(rowIterator.hasNext()){
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount =0;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                //System.out.println(cell.toString());
                if(colCount==idColumn){
                    studentID = (int) cell.getNumericCellValue();
                } else if (colCount == majorColumn){
                    studentMajor = cell.toString();
                } else if (colCount == genderColumn){
                    studentGender = cell.toString();
                }else{
                    System.out.println("unexpected Column in Student_Info.xlsx");
                }
                Student studentToAdd = new Student(studentID, studentMajor, studentGender);
                allStudents.put(studentID, studentToAdd);

                colCount++;
            }
            //System.out.println();
        }

        workbook.close();
        fis.close();
        return allStudents;
    }

    public HashMap<Integer, Student> gatherTestScores() throws IOException{
        File testScoreFile = new File(testScoreDirect);
        FileInputStream fis = new FileInputStream(testScoreFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        //iterators for traversing the excel file
        rowIterator = sheet.iterator();
        row = rowIterator.next();
        cellIterator = row.cellIterator();

        //identifying what information will be in which column
        int idColumn = -1;
        int scoreColumn = -1;

        int colCount = 0;
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String colName = cell.toString();

            if(colName.contains("studentId")){
                idColumn = colCount;
            }else if(colName.contains("score")) {
                scoreColumn = colCount;
            }else{
                System.out.println("woof Column in Student Scores.xlsx");
            }
            colCount++;
        }


        //updating the Student objects in the allStudents hash
        //todo: maybe add something that prints an error if a score is -1?
        int studentID = -1;
        int score = -1;
        colCount = 0;

        //todo: IMPROVE THIS
        Student nonStudent = new Student(-1,"moo", "f");
        Student currentStudent = nonStudent;

        while(rowIterator.hasNext()){
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount = 0;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if(colCount == idColumn) {
                    //checks if student is in hash
                    if(allStudents.containsKey((int) cell.getNumericCellValue())) {
                        currentStudent = allStudents.get((int) cell.getNumericCellValue());
                    }else{
                        currentStudent = nonStudent;
                    }
                }
                else if(colCount == scoreColumn){
                    if(currentStudent.getStudentId() != -1) // assures that it is not the temporary student
                    {
                        currentStudent.setScore((int) cell.getNumericCellValue());
                    }
                }else{
                    System.out.println("unexpected column in Student Scores.xlxx");
                }

                colCount++;
            }
        }

        workbook.close();
        fis.close();
        return allStudents;


    }


    public HashMap<Integer, Student> gatherTestRetakeScores() throws IOException{
        File retakeFile = new File(retakeTestScoreDirect);
        FileInputStream fis = new FileInputStream(retakeFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        //iterators for traversing the excel file
        rowIterator = sheet.iterator();
        row = rowIterator.next();
        cellIterator = row.cellIterator();

        //identifying what information will be in which column
        int idColumn = -1;
        int scoreColumn = -1;

        int colCount = 0;
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            String colName = cell.toString();

            if(colName.contains("studentId")){
                idColumn = colCount;
            }else if(colName.contains("score")) {
                scoreColumn = colCount;
            }else{
                System.out.println("unexpected column in Test Retake Scores.xlsx");
            }
            colCount++;
        }


        //updating the Student objects in the allStudents hash
        //todo: maybe add something that prints an error if a score is -1?
        int studentID = -1;
        int score = -1;
        colCount = 0;

        //todo: IMPROVE THIS
        Student nonStudent = new Student(-1,"moo", "f");
        Student currentStudent = nonStudent;

        while(rowIterator.hasNext()){
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount = 0;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();

                if(colCount == idColumn) {
                    //checks if student is in hash
                    if(allStudents.containsKey((int) cell.getNumericCellValue())) {
                        currentStudent = allStudents.get((int) cell.getNumericCellValue());
                    }else{
                        currentStudent = nonStudent;
                    }
                }
                else if(colCount == scoreColumn){
                    if(currentStudent.getStudentId() != -1) // assures that it is not the temporary student
                    {
                        currentStudent.setRetakeScore((int) cell.getNumericCellValue());
                    }
                }else{
                    System.out.println("unexpected column in Test Retake Scores.xlxx");
                }

                colCount++;
            }
        }

        workbook.close();
        fis.close();
        return allStudents;


    }

    public String getStudentInfoDirect() {
        return studentInfoDirect;
    }

    public void setStudentInfoDirect(String studentInfoDirect) {
        this.studentInfoDirect = studentInfoDirect;
    }

    public String getTestScoreDirect() {
        return testScoreDirect;
    }

    public void setTestScoreDirect(String testScoreDirect) {
        this.testScoreDirect = testScoreDirect;
    }

    public String getRetakeTestScoreDirect() {
        return retakeTestScoreDirect;
    }

    public void setRetakeTestScoreDirect(String retakeTestScoreDirect) {
        this.retakeTestScoreDirect = retakeTestScoreDirect;
    }

    public HashMap<Integer, Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(HashMap<Integer, Student> allStudents) {
        this.allStudents = allStudents;
    }

    public int getRunningGradeTotal() {
        return runningGradeTotal;
    }

    public void setRunningGradeTotal(int runningGradeTotal) {
        this.runningGradeTotal = runningGradeTotal;
    }
}
