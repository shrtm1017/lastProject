package kr.or.nko.certificate.model;

import java.util.Date;

import lombok.Data;

@Data
public class CertificateVo {
	private int crt_sq; //증명서번호
	private int crt_emp_sq; //사원번호
	private int crt_div_sq; //증명서구분번호
	private int crt_emp_app_sq; //승인사원번호
	private Date crt_dt; //증명서요청일자
	private String crt_whether; //승인여부
	private Date crt_sign_dt; //승인일자
	private String crt_con; //내용
	private int crt_emp_dpt; // 사원부서 번호
	private int crt_emp_grade; // 사원직급
	private String crt_emp_phone; // 사원핸드폰번호
	private String crt_emp_nm; // 사원 이름
	private Date crt_dos_str; // 입사일
	private Date crt_dos_end; // 퇴사일
	private String crt_emp_addr1; // 주소
	private String crt_emp_addr2; // 상세주소
	private String crt_submission; // 제출처
	private Date crt_subdt; // 제출예정일
}