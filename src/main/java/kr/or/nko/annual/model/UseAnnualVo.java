package kr.or.nko.annual.model;

import lombok.Data;

@Data
public class UseAnnualVo {
	private int anu_sq; //연차번호
	private int anu_emp_sq; //사원번호
	private double anu_use; //사용연차일수
	private double anu_remain; //사용연차일수
	private String anu_str_dt; //연차 시작일
	private String anu_end_dt; //연차 종료일
	private String anu_div; //연반차구분
}