package com.example.sudabangweb.dto;
import java.util.*;
public class OneDayData { // 여기에 있는 data 들은 모두 같은 날임
    private ArrayList<StudentDTO> oneDayData;
    private String date;
    private String week;
    private String[] avg;

    public ArrayList<StudentDTO> getOneDayData() {
        return oneDayData;
    }
    public void setOneDayData(ArrayList<StudentDTO> oneDayData) {
        this.oneDayData = oneDayData;
    }
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
    public void addData(StudentDTO studentData) {
        oneDayData.add(studentData);
    }
    public String[] getAvg() {
        return avg;
    }
    public void setAvg(String[] avg) {
        this.avg = avg;
    }

    public String[] setTestAverage(){
        int studentNum = oneDayData.size();
        Integer[][] scoreArray = new Integer[studentNum][10]; // 하루에 시험을 10번보진 않겠지?

        // 테스트 횟수 구하는 과정
        String score_tmp = oneDayData.get(0).getTest_score();
        String[] arr_tmp = score_tmp.split("/|\n");
        int testNum = arr_tmp.length / 2;


        for (int i=0;i<studentNum;i++) { // 하루치 수업 각 학생들에 대하여

            String score = oneDayData.get(i).getTest_score();
            // 아래처럼 스플릿하면 두번 시험보면 50/100\n70/100 이 [50, 100, 70, 100] 이렇게 저장됨
            String[] testSplitArray = score.split("/|\n");
            for (int j=0;j<testSplitArray.length;j++) { // 10 / 100 이렇게 입력하는 경우도 있어서 빈칸 제거
                testSplitArray[i] = testSplitArray[i].replaceAll("\\s", "");
            }
            // testSplitArray에서 짝수 인덱스가 찐점수 홀수 인덱스는 총점
            for (int j=0;j<testSplitArray.length;j=j+2) {
                scoreArray[i][j/2] = Integer.parseInt(testSplitArray[j]);
            }
        }

        avg = new String[testNum]; // 소수점 아래 버릴 예정
        int sum=0;
        for (int i=0;i<testNum;i++) {
            sum = 0;
            for (int j=0;j<studentNum;j++) {
                sum += scoreArray[j][i];
            }
            avg[i] = Integer.toString(sum/studentNum);
        }

        return avg;
    }



}
