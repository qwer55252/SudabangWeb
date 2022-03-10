package com.example.sudabangweb.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.apache.xpath.operations.Bool;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    @CsvBindByName(column = "이름")
    private String name;

    @CsvBindByName(column = "출결")
    private String attendance;

    @CsvBindByName(column = "과제수행도")
    private String assignment_performance;

    @CsvBindByName(column = "플래너수행도")
    private String planner_performance;

    @CsvBindByName(column = "수업집중도")
    private String concentration;

    @CsvBindByName(column = "테스트결과")
    private String test_score;

    @CsvBindByName(column = "과제")
    private String assignment_comment;

    @CsvBindByName(column = "교재")
    private String textbook;

    @CsvBindByName(column = "진도")
    private String progress;

    @CsvBindByName(column = "날짜")
    private String date;

    @CsvBindByName(column = "월")
    private String month;

    @CsvBindByName(column = "주")
    private String week;

    @CsvBindByName(column = "테스트점수")
    private String[] test_scores;

    @CsvBindByName(column = "테스트평균점수")
    private String test_average;

    // 클리닉도 여기서 한꺼번에 처리

}