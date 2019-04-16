import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Set;


public class DataCollector {

    private String studentInfoDirect;
    private String testScoreDirect;
    private String retakeTestScoreDirect;
    private HashMap<Integer, Student> allStudents;


    private Iterator<Row> rowIterator;
    private Row row;
    private Iterator<Cell> cellIterator;


    //data fields for student info
    private int runningGradeTotal; //stores the sum of each students best test grade
    private int totalStudentCount; //total number of students
    private HashMap<Integer,Student> fCompSciStu;


    public DataCollector(String studentInfoDirect, String testScoreDirect, String retakeTestScoreDirect) {
        this.studentInfoDirect = studentInfoDirect;
        this.testScoreDirect = testScoreDirect;
        this.retakeTestScoreDirect = retakeTestScoreDirect;


        runningGradeTotal = 0;
        totalStudentCount = 0;
        fCompSciStu =  new HashMap<Integer, Student>();
    }


    public int calculateAverageGrade(){
        return runningGradeTotal/totalStudentCount;
    }



    public HashMap<Integer, Student> collectAllStudentData() {
        try {
            gatherStudentInfo();
            gatherTestScores();
            gatherTestRetakeScores();


        } catch (IOException uhoh) {
            System.out.println("Error in collectAllStudents: " + uhoh);
        }
        return allStudents;
    }


    public HashMap<Integer, Student> gatherStudentInfo() throws IOException {
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
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String colName = cell.toString();
            if (colName.contains("studentId")) {
                idColumn = colCount;
            } else if (colName.contains("major")) {
                majorColumn = colCount;

            } else if (colName.contains("gender")) {
                genderColumn = colCount;


            } else {
                System.out.println("unexpected Column in Student_Info.xlsx");
            }
            colCount++;
        }

        //todo: maybe put something here to ends the method if any of them are still -1?


        int studentID = -1;
        String studentMajor = "error";
        String studentGender = "?";
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                //System.out.println(cell.toString());
                if (colCount == idColumn) {
                    studentID = (int) cell.getNumericCellValue();
                    totalStudentCount++;
                } else if (colCount == majorColumn) {
                    studentMajor = cell.toString();
                } else if (colCount == genderColumn) {
                    studentGender = cell.toString();

                    //As the gender column is the final column in the row i'm adding students to the list here to avoid
                    //repeated variables
                    Student studentToAdd = new Student(studentID, studentMajor, studentGender);
                    allStudents.put(studentID, studentToAdd);

                    if(studentMajor.contains("computer science")&&studentGender.contains("F")){
                        fCompSciStu.put(studentID,studentToAdd);
                    }

                } else {
                    System.out.println("unexpected Column in Student_Info.xlsx");
                }


                colCount++;
            }
            //System.out.println();
        }

        workbook.close();
        fis.close();
        return allStudents;
    }

    public HashMap<Integer, Student> gatherTestScores() throws IOException {
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
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String colName = cell.toString();

            if (colName.contains("studentId")) {
                idColumn = colCount;
            } else if (colName.contains("score")) {
                scoreColumn = colCount;
            } else {
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
        Student nonStudent = new Student(-1, "moo", "f");
        Student currentStudent = nonStudent;

        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                if (colCount == idColumn) {
                    //checks if student is in hash
                    if (allStudents.containsKey((int) cell.getNumericCellValue())) {
                        currentStudent = allStudents.get((int) cell.getNumericCellValue());
                    } else {
                        currentStudent = nonStudent;
                    }
                } else if (colCount == scoreColumn) {
                    if (currentStudent.getStudentId() != -1) // assures that it is not the temporary student
                    {
                        //sets grade of current student
                        currentStudent.setScore((int) cell.getNumericCellValue());
                        updateRunningGradeTotal(currentStudent.getStudentId());
                    }
                } else {
                    System.out.println("unexpected column in Student Scores.xlxx");
                }

                colCount++;
            }
        }

        workbook.close();
        fis.close();
        return allStudents;


    }


    public HashMap<Integer, Student> gatherTestRetakeScores() throws IOException {
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
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String colName = cell.toString();

            if (colName.contains("studentId")) {
                idColumn = colCount;
            } else if (colName.contains("score")) {
                scoreColumn = colCount;
            } else {
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
        Student nonStudent = new Student(-1, "moo", "f");
        Student currentStudent = nonStudent;

        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            cellIterator = row.cellIterator();

            colCount = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                if (colCount == idColumn) {
                    //checks if student is in hash
                    if (allStudents.containsKey((int) cell.getNumericCellValue())) {
                        //sets current student to student in hash
                        currentStudent = allStudents.get((int) cell.getNumericCellValue());
                    } else {
                        currentStudent = nonStudent;
                    }
                } else if (colCount == scoreColumn) {
                    if (currentStudent.getStudentId() != -1) // assures that it is not the temporary non-student
                    {
                        //sets retake score of current student
                        currentStudent.setRetakeScore((int) cell.getNumericCellValue());
                        updateRunningGradeTotal(currentStudent.getStudentId());
                    }
                } else {
                    System.out.println("unexpected column in Test Retake Scores.xlxx");
                }

                colCount++;
            }
        }

        workbook.close();
        fis.close();
        return allStudents;


    }

    public void updateRunningGradeTotal(int studentID) {
        int ogTestScore = allStudents.get(studentID).getScore();
        int retakeScore = allStudents.get(studentID).getRetakeScore();

        //case where there isnt a retake
        if (retakeScore == -1) {
            runningGradeTotal += ogTestScore;
        } else if (retakeScore > ogTestScore) {
            runningGradeTotal += retakeScore - ogTestScore;
        }

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

    public int getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(int totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public HashMap<Integer, Student> getfCompSciStu() {
        return fCompSciStu;
    }

    public void setfCompSciStu(HashMap<Integer,Student> fCompSciStu) {
        this.fCompSciStu = fCompSciStu;
    }


}
