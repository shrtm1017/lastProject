package kr.or.nko.approval.model;

import java.util.Date;

import lombok.Data;

@Data
public class ApprovalEleVo {
	private int apv_sq; 		// 결재번호
	private int apv_div_sq; 	// 결재구분번호
	private int apv_emp_sq; 	// 기안자
	private String apv_nm; 		// 제목
	private String apv_fl_path; // 파일경로
	private String apv_fl_nm; 	// 파일명
	private Date apv_str_dt; 	// 결재시작날짜
	private Date apv_end_dt; 	// 결재종료날짜
	private Date apv_dt; 		// 결재등록날짜
	private int apv_hit; 		// 조회수
	private String apv_state; 	// 상태
	private String apv_depart; 	// 기안부서
	private int apv_executer;	// 시행자
	private String apv_con; 	// 내용
	private String apv_anu_str; //근태시작날짜
	private String apv_anu_end; //근태종료날짜
	private String apv_anu_div; //근태구분
}