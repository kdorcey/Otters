import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.*;


public class Student {
    private int studentId;
    private String major;
    private int score;
    private int retakeScore;
    private String gender;


    public Student(int studentId, String major, int score, int retakeScore, String gender) {
        this.studentId = studentId;
        this.major = major;
        this.score = score;
        this.retakeScore = retakeScore;
        this.gender = gender;
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
}
