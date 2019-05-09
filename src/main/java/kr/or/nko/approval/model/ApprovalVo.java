package kr.or.nko.approval.model;

import java.util.Date;

import lombok.Data;

@Data
public class ApprovalVo {
	private int apv_sq; 			//결재번호
	private int apv_sign; 			//승인자
	private String apv_sign_chk; 	//승인여부
	private String apv_refu; 		//반려사유
	private Date apv_date; 			//반려사유
}