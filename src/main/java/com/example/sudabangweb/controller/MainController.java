package com.example.sudabangweb.controller;

import com.example.sudabangweb.dto.StudentDTO;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/main")
    public String dragAndDrop() {
        return "mainPage";
    }

    public static boolean isRowEmpty(Row row) {
        int c = row.getFirstCellNum()+2;
        Cell cell = row.getCell(c);
        if (cell != null && cell.getCellType() != CellType.BLANK) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/main/post") //ajax에서 호출하는 부분
    @ResponseBody
    public String upload(MultipartHttpServletRequest multipartRequest) { //Multipart로 받는다.

        Iterator<String> itr =  multipartRequest.getFileNames();

        String filePath = "C:/Users/home/Desktop/SudabangWeb/src/main/resources/static/excel";

        while (itr.hasNext()) { //받은 파일들을 모두 돌린다.

            /* 기존 주석처리
            MultipartFile mpf = multipartRequest.getFile(itr.next());
            String originFileName = mpf.getOriginalFilename();
            System.out.println("FILE_INFO: "+originFileName); //받은 파일 리스트 출력'
            */

            MultipartFile mpf = multipartRequest.getFile(itr.next());

            assert mpf != null;
            String originalFilename = mpf.getOriginalFilename(); //파일명 -> 수정 해야될 듯

            String fileFullPath = filePath+"/"+originalFilename; //파일 전체 경로



            try {

                //파일 저장
                mpf.transferTo(new File(fileFullPath));

                System.out.println("originalFilename => "+originalFilename);
                System.out.println("fileFullPath => "+fileFullPath);


                // ----- 엑셀 파일 분석 후 csv로 저장하기 ----- //

                // 저장할 csv 파일 열기
                File f = new File("C:/Users/home/Desktop/SudabangWeb/src/main/resources/static/db/StudentDB.csv");
                System.out.println("파일 존재? : "+f.exists());
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8"));
                //true를 하지 않으면 파일을 덮어씀

                PrintWriter pw = new PrintWriter(bw,true);
                //뒤에 이어서 쓰기

                // 파일 열기
                FileInputStream file = new FileInputStream(new File(filePath, originalFilename));

                // 엑셀 파일로 Workbook instance를 생성한다.
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                // workbook의 첫번째 sheet를 가저온다. -> 학습 관리표
                XSSFSheet sheet = workbook.getSheetAt(0);

                // 만약 특정 이름의 시트를 찾는다면 workbook.getSheet("찾는 시트의 이름");
                // 만약 모든 시트를 순회하고 싶으면
                // for(Integer sheetNum : workbook.getNumberOfSheets()) {
                //      XSSFSheet sheet = workbook.getSheetAt(i);
                // }
                // 아니면 Iterator<Sheet> s = workbook.iterator() 를 사용해서 조회해도 좋다.

                // 모든 행(row)들을 조회한다. -> 첫 번째 행은 무시(학생 이름)
                int rowCnt = 0;
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (rowCnt == 0 || rowCnt == 1) {
                        rowCnt++;
                        continue;
                    }
                    if (isRowEmpty(row)) break; //빈 행이 나오면 정지 -> 실수로 빈 행이 나오는 경우엔 어떻게 해야할까?

                    // 각각의 행에 존재하는 모든 열(cell)을 순회한다.
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int cellCnt = 0; //14번째 셀 까지만
                    while (cellIterator.hasNext() && cellCnt != 14) {
                        Cell cell = cellIterator.next();
                        if (cellCnt == 0) { //순번 무시
                            cellCnt++;
                            continue;
                        }
                        String value = null; //저장할 값
                        // cell의 타입을 확인 하고, 값을 가져온다.
                        switch (cell.getCellType()) {
                            case NUMERIC -> {
                                if (DateUtil.isCellDateFormatted(cell) && cellCnt == 1) { //문제 해결: 날짜 포멧인 경우엔 예외로 처리해줘야함
                                    Date date = cell.getDateCellValue();
                                    value = new SimpleDateFormat("yyyy.MM.dd").format(date); //저장할 날짜 포맷
                                    System.out.print("<" + value + ">");
                                } else if (cellCnt == 3) { //출결
                                    SimpleDateFormat time = new SimpleDateFormat("a h:mm"); //엑셀 서식에 따라 지정해줘야 함
                                    value = time.format(cell.getDateCellValue());
                                    System.out.print("<" + value + ">");
                                } else {
                                    value = Integer.toString((int) cell.getNumericCellValue()); //Numeric은 기본적으로 정수를 반환하지 않기 때문에 정수로 강제 형 변환 후 문자열로 파싱
                                    System.out.print("<" + (int) cell.getNumericCellValue() + ">"); //getNumericCellValue 메서드는 기본으로 double형 반환
                                }
                            }
                            case STRING -> {
                                value = cell.getStringCellValue();
                                System.out.print("<" + cell.getStringCellValue() + ">");
                            }
                            case FORMULA -> { //셀에 수식이 있는 경우엔 FormulaEvaluator를 사용하면 결과값을 얻을 수 있다.
                                FormulaEvaluator formulaEval = workbook.getCreationHelper().createFormulaEvaluator();
                                value = formulaEval.evaluate(cell).formatAsString();
                                value = value.substring(0, value.length() - 2);
                                System.out.print("<" + value + ">");
                            }
                        }
                        if(value!=null) {
                            if (value.contains("\n")) {
                                value = value.replace("\n", "<br>");
                            }
                        }
                        value = "\""+value+"\"";
                        if(cellCnt!=13) value+=",";
                        pw.write(value);
                        cellCnt++;
                    }
                    pw.write("\n");
                    rowCnt++;
                }
                pw.flush(); //flush 안하면 수정 안됨
                pw.close();
                file.close();
            } catch (Exception e) {
                System.out.println("postTempFile_ERROR======>"+fileFullPath);
                e.printStackTrace();
            }
        }

        return "success";
    }

    @GetMapping("/main/successPage")
    public String success(){
        return "successPage";
    }

    @GetMapping("/main/weekTable")
    public String weekTable(@RequestParam int month, @RequestParam int week, Model model) throws FileNotFoundException {
        List<String> name = new ArrayList<String>();
        List<StudentDTO> print = new ArrayList<StudentDTO>();

        List<StudentDTO> student = new CsvToBeanBuilder<StudentDTO>(new FileReader("C:/Users/home/Desktop/SudabangWeb/src/main/resources/static/db/StudentDB.csv"))
                .withType(StudentDTO.class)
                .build()
                .parse();
//        student.forEach(System.out::println);

        for(int i=0;i<student.size();i++){
            if(!name.contains(student.get(i).getName())){
                name.add(student.get(i).getName());
            }
        }


        for(int i=0;i<student.size();i++){
            if(name.get(0).equals(student.get(i).getName())){
                print.add(student.get(i));
            }
        }


        model.addAttribute("month",month);
        model.addAttribute("week",week);
        model.addAttribute("info",print);
        model.addAttribute("studentName",name.get(0));
        model.addAttribute("num",print.size()+1);

        return "weekTable";
    }


}
