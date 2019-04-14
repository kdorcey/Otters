import java.io.IOException;

public class main {
    public static void main(String args[]){
        XLSXReader dataCollector = new XLSXReader("Student_Data/Student Info.xlsx",
                "Student_Data/Test Scores.xlsx", "Student_Data/Test Retake Scores.xlsx" );

        dataCollector.collectAllStudentData();

        System.out.println(dataCollector.calculateAverageGrade());
        //80+85+80+93+92+75+99+100+98


    }
}
