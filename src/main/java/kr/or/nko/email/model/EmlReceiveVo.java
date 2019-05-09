package kr.or.nko.email.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmlReceiveVo {
	private int eml_rec_sq; //받은메일번호
	private int eml_emp_send_sq; //보낸사원번호
	private int eml_emp_rec_sq; //받은사원번호
	private int eml_send_sq; //보낸메일번호
	private Date eml_rec_dt; //수신시간
	private String eml_rec_chk; //읽었는지표시
	private String eml_rec_nm; //제목
	private String eml_rec_con; //내용
	private String eml_rec_ipt; //중요표시
	private String eml_rec_del; //삭제여부
	private String eml_send_email; //보낸사람
}