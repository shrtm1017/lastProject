package kr.or.nko.schedule.model;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ScheduleVo {
	private int scd_sq; //일정번호
	private int scd_emp_sq; //사원번호
	private String scd_div_sq; //일정구분번호
	private String scd_con; //내용
	private String scd_title; //제목
	
	@DateTimeFormat(pattern="yyyyMMdd") // 자동으로 Date type을 yyyyMMdd의 형태로 집어넣는다.
	private String scd_str_dt; //시작일
	private String scd_end_dt; //종료일
	private String scd_select; //컬러색상
}