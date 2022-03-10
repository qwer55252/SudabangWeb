package com.example.sudabangweb.dto;
import java.util.*;

public class WeekData { // 같은 주차의 데이터들만 존재
    private ArrayList<OneDayData> weekData;
    private String week;

    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public ArrayList<OneDayData> getWeekData() {
        return weekData;
    }
    public void setWeekData(ArrayList<OneDayData> weekData) {
        this.weekData = weekData;
    }

    public WeekData() {

    }
    public void addData(OneDayData oneDayData) {
        weekData.add(oneDayData);
    }
}
