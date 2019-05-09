package kr.or.nko.work.model;

import java.util.Date;

import lombok.Data;

@Data
public class WorkHistoryVo {
	private int his_sq; //작업내역번호
	private int his_wk_sq; //업무번호
	private int his_emp_sq; //사원번호
	private String his_nm; //제목
	private String his_con; //내용 
	private Date his_dt; //시간 
}