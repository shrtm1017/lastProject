package kr.or.nko.department.model;

import lombok.Data;

@Data
public class DepartmentVo {
	private int dpt_sq; //부서명 구분번호
	private int dpt_hr_sq; //상위부서코드
	private String dpt_nm; //부서명
}