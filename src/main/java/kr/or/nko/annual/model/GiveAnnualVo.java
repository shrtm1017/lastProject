package kr.or.nko.annual.model;

import lombok.Data;

@Data
public class GiveAnnualVo {
	private int anu_sq; //연차번호
	private int anu_emp_sq; //사원번호
	private int anu_days; //연차일수
	private double anu_remains; //연차일수
	private String anu_give_dt; //지급날짜
}