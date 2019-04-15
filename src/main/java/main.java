import java.io.IOException;
import java.util.HashMap;

public class main {
    public static void main(String args[]){
        XLSXReader dataCollector = new XLSXReader("Student_Data/Student Info.xlsx",
                "Student_Data/Test Scores.xlsx", "Student_Data/Test Retake Scores.xlsx" );

        HashMap allStudents = dataCollector.collectAllStudentData();
        System.out.println(Student.calculateAverageGrade());
        for(Student temp:Student.fCompSciStu){
            System.out.println(temp);
        }


        //dataCollector.collectAllStudentData();

        //System.out.println(Student.calculateAverageGrade());
        //80+85+80+93+92+75+99+100+98


    }
}
