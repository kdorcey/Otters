import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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



    public int getBestScore(){
        if(score>retakeScore){
            return score;
        }else if(retakeScore>score){
            return retakeScore;
        }
        else{
            return score;
        }

    }


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


    public String toString(){
        return ""+studentId;
    }


}
