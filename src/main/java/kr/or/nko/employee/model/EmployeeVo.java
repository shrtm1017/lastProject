package kr.or.nko.employee.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeVo {
	private int emp_sq; 			// 사원번호
	private int emp_dpt; 			// 부서명 구분번호
	private int emp_grade; 			// 직급구분번호
	private String emp_nm; 			// 이름
	private String emp_pass; 		// 비밀번호
	private String emp_phone; 		// 전화번호
	private String emp_com_phone; 	// 회사전화번호
	private String emp_addr1; 		// 공통주소
	private String emp_addr2; 		// 상세주소
	private String emp_com_email; 	// 회사이메일
	private String emp_psn_email; 	// 개인이메일
	private Date emp_ent; 			// 입사일
	private Date emp_fnl_mod; 		// 최종수정일
	private String emp_img_path; 	// 사진파일경로
	private String emp_img_realpath; // 사진real파일명
	private String emp_flag; 		// 퇴사여부
	private int emp_annual; 		// 연차
	private String emp_sign; 		// 서명 이미지
	private String dpt_nm;
	private String emp_on;			//접속여부
}