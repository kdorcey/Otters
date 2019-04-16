import java.util.HashMap;

public class main {
    public static void main(String args[]) throws Exception{
        DataCollector dataCollector = new DataCollector("Student_Data/Student Info.xlsx",
                "Student_Data/Test Scores.xlsx", "Student_Data/Test Retake Scores.xlsx" );

        HashMap allStudents = dataCollector.collectAllStudentData();

        JsonPost test = new JsonPost(dataCollector.getfCompSciStu(), dataCollector.calculateAverageGrade());
        test.post();

    }
}
