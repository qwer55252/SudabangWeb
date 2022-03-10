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

        String nextDate;
        String nextWeek;

        OneDayData oneDayData = new OneDayData();
        WeekData weekData = new WeekData();

        // prev랑 비교하는 이유 마지막 라인 처

        for(int i=0;i<classData.size();i++) { // 데이터가 존재할때까지
            StudentDTO studentData = classData.get(i);
            // 마지막 줄이면
            if (i == classData.size()-1) {
                oneDayData.addData(studentData);
                weekData.addData(oneDayData);
                oneDayList.add(oneDayData);
                weeklyList.add(weekData);
                break;
            } // 이외는 다음 줄이 존재

            nextDate = classData.get(i+1).getDate();
            nextWeek = classData.get(i+1).getWeek();

            oneDayData.addData(studentData);

            // 다음 데이터랑 다른 날인데 주차는 같으면
            if ( !studentData.getDate().equals(nextDate) && studentData.getWeek().equals(nextWeek)) {
                // 하루치 데이터가 모두 들어갔으므로 평균을 구하고, 며칠 데이터인지, 몇주차 데이터인지 정보 set
                oneDayData.setTestAverage();
                oneDayData.setDate(studentData.getDate());
                oneDayData.setWeek(studentData.getWeek());

                weekData.addData(oneDayData); // 완성된 하루치 데이터 add

                oneDayList.add(oneDayData); // 완성된 하루치 데이터 oneDayList에 add

                oneDayData = new OneDayData(); // 새로운 날 객체 생성
            }
            // 다음 데이터랑 날도 다르고 주차도 다르면
            else if (!studentData.getDate().equals(nextDate) && !studentData.getWeek().equals(nextWeek)){
                oneDayData.setTestAverage();
                oneDayData.setDate(studentData.getDate());
                oneDayData.setWeek(studentData.getWeek());

                weekData.addData(oneDayData);
                weekData.setWeek(studentData.getWeek());

                oneDayList.add(oneDayData); // 완성된 하루치 데이터 add
                weeklyList.add(weekData); // 완성된 일주일 데이터 add

                oneDayData = new OneDayData(); // 하루치 데이터 새로 선언
                weekData = new WeekData(); // 한 주차 데이터 새로 선언
            }
        }


    }


    public void addData(StudentDTO studentDTO) {
        classList.add(studentDTO);
    }

}
