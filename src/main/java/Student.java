

public class Student {
    private int studentId;
    private String major;
    private int score;
    private int retakeScore;
    private String gender;


    public Student(int studentId, String major, String gender) {
        this.studentId = studentId;
        this.major = major;
        this.gender = gender;
        score = -1;
        retakeScore = -1;
    }



    public static String moo(){
        return "moo";
    }

    //Grabs StudentID, Major, and Gender from the Student_Info.xlsx


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRetakeScore() {
        return retakeScore;
    }

    public void setRetakeScore(int retakeScore) {
        this.retakeScore = retakeScore;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
