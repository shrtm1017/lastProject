package kr.or.nko.approval.model;

import lombok.Data;

@Data
public class SignalVo {
	private int sig_sq;		// 알림번호
	private int sig_apv_sq; // 결재번호
	private int sig_emp_sq; // 사원번호
	private String sig_con;	// 알림내용
	private String sig_div; // 알림구분
}
