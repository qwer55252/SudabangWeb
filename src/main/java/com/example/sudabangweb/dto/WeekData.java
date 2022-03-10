package com.example.sudabangweb.dto;
import java.util.*;

public class WeekData { // 같은 주차의 데이터들만 존재
    private ArrayList<StudentDTO> weekData;
    private String date;
    private String week;

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public ArrayList<StudentDTO> getWeekData() {
        return weekData;
    }
    public void setWeekData(ArrayList<StudentDTO> weekData) {
        this.weekData = weekData;
    }

    public WeekData() {

    }
    public void addData(StudentDTO studentData) {
        weekData.add(studentData);
    }
}
