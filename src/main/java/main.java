import java.util.HashMap;

public class main {
    public static void main(String args[]){
        DataCollector dataCollector = new DataCollector("Student_Data/Student Info.xlsx",
                "Student_Data/Test Scores.xlsx", "Student_Data/Test Retake Scores.xlsx" );

        HashMap allStudents = dataCollector.collectAllStudentData();
        System.out.println(dataCollector.calculateAverageGrade());
        for(Student temp:dataCollector.getfCompSciStu()){
            System.out.println(temp);
        }


        //dataCollector.collectAllStudentData();

        //System.out.println(Student.calculateAverageGrade());
        //80+85+80+93+92+75+99+100+98


    }
}
