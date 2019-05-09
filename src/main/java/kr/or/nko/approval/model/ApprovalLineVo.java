package kr.or.nko.approval.model;

import lombok.Data;

@Data
public class ApprovalLineVo {
	private int apa_sq;			// 결재라인번호
	private int apa_div_apv;	// 결재구분번호
	private int apa_emp;		// 사원번호
	private String apa_code;	// 결재코드
	private String emp_nm;		// 사원명
}
