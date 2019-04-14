import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class XLSXReader_Tests {
    //todo: figure out if you need that rule or not

    @Rule
    public ExpectedException thrown= ExpectedException.none();
    @Test
    public void testGatherStudentInfo(){
        try {
            HashMap<Integer,Student> testHash;
            testHash = XLSXReader.gatherStudentInfo();
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

            HashMap<Integer,Student> returnedHash = XLSXReader.gatherTestScores(testHash);
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

            HashMap<Integer, Student> returnedHash = XLSXReader.gatherTestRetakeScores(testHash);
            Student updatedStudent = returnedHash.get(77341);

            assertEquals(updatedStudent.getRetakeScore(), 75);
            assertEquals(returnedHash.size(), 1);

        } catch(IOException uhoh) {
            System.out.println("error in testReader: " + uhoh);
        }

    }

}
