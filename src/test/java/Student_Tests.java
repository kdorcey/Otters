import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import java.util.HashMap;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Student_Tests {
    Student testStudent;

    @Before
    public void createTestStudent(){
        testStudent = new Student(1234, "Goats", "F");
    }
    @Test
    public void testGetBestGrade(){
        testStudent.setScore(80);
        testStudent.setRetakeScore(90);

        assertEquals(testStudent.getBestScore(),90);

        testStudent.setScore(80);
        testStudent.setRetakeScore(22);
        assertEquals(testStudent.getBestScore(), 80);

        testStudent.setScore(22);
        testStudent.setRetakeScore(22);
        assertEquals(testStudent.getBestScore(),22);

    }


}
