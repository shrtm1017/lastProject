package kr.or.nko.callingcard.model;

import lombok.Data;

@Data
public class CallingCardVo {
	private int cal_sq; //명함번호
	private int cal_emp_sq; //사원번호
	private String cal_nm; //이름
	private String cal_phone; //전화번호
	private String cal_com; //회사명
	private String cal_mail; //이메일
	private String cal_com_phone; //회사번호
	private String cal_grade; //직책
	private String cal_addr; // 명함주소
}