package kr.or.nko.approval.model;

import lombok.Data;

@Data
public class ApprovalRefVo {
	private int apr_sq;		// 결재번호
	private int apr_emp_sq;	// 수신참조자
	private String apr_hit;	// 조회여부
}
