package kr.or.nko.email.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmlSendVo {
	private int eml_send_sq; //보낸메일번호
	private int eml_emp_send_sq; //보낸사원번호
	private Date eml_send_dt; //발신시간
	private String eml_send_nm; //제목
	private String eml_send_con; //내용
	private String eml_send_rec; //받는사람
	private String eml_send_ipt; //중요표시
	private String eml_send_del; //삭제여부
}