package kr.or.nko.approval.model;

import lombok.Data;

@Data
public class ApprovalDivVo {
	private int div_apv_sq; 		// 결재 구분 번호
	private String div_apv_nm; 		// 결재 구분
	private String div_apv_form; 	// 결재 문서 양식
}