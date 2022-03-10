package com.example.sudabangweb.dto;

import java.util.*;


public class ClassData {
    private ArrayList<StudentDTO> classList; // 월별 수업 배열(엑셀 전체 데이터)
    private ArrayList<WeekData> weeklyList;// 주차별 수업 배열
    private ArrayList<OneDayData> oneDayList; // 하루치 수업 배열
    private ArrayList<StudentDTO>[] List_per_Student; // 학생별 수업 배열

    public ArrayList<StudentDTO> getClassList() {
        return classList;
    }
    public void setClassList(ArrayList<StudentDTO> classList) {
        this.classList = classList;
    }
    public ArrayList<WeekData> getWeeklyList() {
        return weeklyList;
    }
    public void setWeeklyList(ArrayList<WeekData> weeklyList) {
        this.weeklyList = weeklyList;
    }
    public ArrayList<OneDayData> getOneDayList() {
        return oneDayList;
    }
    public void setOneDayList(ArrayList<OneDayData> oneDayList) {
        this.oneDayList = oneDayList;
    }
    public ArrayList<StudentDTO>[] getList_per_Student() {
        return List_per_Student;
    }
    public void setList_per_Student(ArrayList<StudentDTO>[] list_per_Student) {
        List_per_Student = list_per_Student;
    }


    public ClassData(ArrayList<StudentDTO> classData) {
        this.classList = classData;

        String prevDate = "init";
        String prevWeek = "init";
        OneDayData oneDayData = new OneDayData();
        WeekData weekData = new WeekData();

        for(StudentDTO studentData : classData) { // 데이터가 존재할때까지
            // 전 데이터랑 같은날이거나 첫 시도이면
            if (studentData.getDate().equals(prevDate) || prevDate.equals("init")) {
                // 아무것도 안하고 밑에서 데이터만 추가
            }
            // 전 데이터랑 다른 날인데 주차는 같으면
            else if (studentData.getWeek().equals(prevWeek)) {
                // 하루치 데이터가 모두 들어갔으므로 평균을 구한다
                oneDayData.setTestAverage();
                oneDayData.setDate(prevDate);
                oneDayData.setWeek(prevWeek);
                oneDayList.add(oneDayData); // 지금까지 쌓아 놓은 oneDayList에 add

                oneDayData = new OneDayData(); // 하루치 데이터 새로 선언
            }
            // 전 데이터랑 날도 다르고 주차도 다르면
            else {
                oneDayData.setTestAverage();
                oneDayData.setDate(prevDate);
                oneDayData.setWeek(prevWeek);
                weekData.setDate(prevDate);
                weekData.setWeek(prevWeek);
                oneDayList.add(oneDayData); // 지금까지 쌓아 놓은 oneDayData를 oneDayList에 add
                weeklyList.add(weekData); // 지금까지 쌓아 놓은 weekData를 weeklyList에 add

                oneDayData = new OneDayData(); // 하루치 데이터 새로 선언
                weekData = new WeekData(); // 한 주차 데이터 새로 선언
            }
            oneDayData.addData(studentData);
            weekData.addData(studentData);


            prevDate = studentData.getDate();
            prevWeek = studentData.getWeek();
        }


    }


    public void addData(StudentDTO studentDTO) {
        classList.add(studentDTO);
    }

}
