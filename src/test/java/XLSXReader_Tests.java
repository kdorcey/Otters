import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class XLSXReader_Tests {
    //todo: figure out if you need that rule or not
    XLSXReader dataCollector;

    @Before
    public void createReaderObject(){
        dataCollector = new XLSXReader("Student_Data/Student Info.xlsx",
                "Student_Data/Test Scores.xlsx", "Student_Data/Test Retake Scores.xlsx" );


    }

    @Rule
    public ExpectedException thrown= ExpectedException.none();
    @Test
    public void testGatherStudentInfo(){
        try {

            HashMap<Integer,Student> testHash;
            testHash = dataCollector.gatherStudentInfo();
            assertEquals(testHash.size(),9);
            Student testStudent = testHash.get(80012);
            assertEquals(testStudent.getMajor(),"english");


        } catch(IOException uhoh){
            System.out.println("error in testReader: "+ uhoh);
        }
    }

    @Test
    public void testGatherTestScores(){
        try {
            HashMap<Integer,Student> testHash = new HashMap<Integer, Student>();
            Student testStudent = new Student(11211, "whatever","F");
            testHash.put(11211,testStudent);
            dataCollector.setAllStudents(testHash);

            HashMap<Integer,Student> returnedHash = dataCollector.gatherTestScores();
            Student updatedStudent = returnedHash.get(11211);
            assertEquals(updatedStudent.getScore(),100);
            assertEquals(returnedHash.size(), 1);



        } catch(IOException uhoh){
            System.out.println("error in testReader: "+ uhoh);
        }
    }

    @Test
    public void testGatherRetakeScores(){
        try {
            HashMap<Integer, Student> testHash = new HashMap<Integer, Student>();
            Student testStudent = new Student(77341, "whatever", "F");
            testHash.put(77341, testStudent);
            dataCollector.setAllStudents(testHash);

            HashMap<Integer, Student> returnedHash = dataCollector.gatherTestRetakeScores();
            Student updatedStudent = returnedHash.get(77341);

            assertEquals(updatedStudent.getRetakeScore(), 75);
            assertEquals(returnedHash.size(), 1);

        } catch(IOException uhoh) {
            System.out.println("error in testReader: " + uhoh);
        }

    }

    @Test
    public void testUpdateRunningSum(){

        HashMap<Integer, Student> testHash = new HashMap<Integer, Student>();
        Student testStudent = new Student(77341, "whatever", "F");
        Student testStudent2 = new Student(2242, "whatever", "M");
        testHash.put(77341, testStudent);
        testHash.put(2242,testStudent2);
        dataCollector.setAllStudents(testHash);

        testStudent.setScore(80);
        dataCollector.updateRunningGradeTotal(77341);
        testStudent.setRetakeScore(22);
        dataCollector.updateRunningGradeTotal(77341);




        assertEquals(dataCollector.getRunningGradeTotal(),80);
        testStudent2.setScore(40);
        dataCollector.updateRunningGradeTotal(2242);
        testStudent2.setRetakeScore(89);
        dataCollector.updateRunningGradeTotal(2242);
        assertEquals(dataCollector.getRunningGradeTotal(), 169);





    }

}
