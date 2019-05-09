package kr.or.nko.work.model;

import lombok.Data;

@Data
public class WorkRelationVo {
	private int wk_sq; //업무번호
	private int wk_emp_sq; //사원번호
	private String wk_code; //책임코드
}