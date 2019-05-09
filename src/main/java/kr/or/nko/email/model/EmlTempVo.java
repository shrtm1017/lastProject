package kr.or.nko.email.model;

import java.util.Date;

import lombok.Data;

@Data
public class EmlTempVo {
	private int eml_temp_sq; //임시메일번호
	private String eml_temp_nm; //제목
	private String eml_temp_con; //내용
	private String eml_temp_rec; //받는사람
	private Date eml_temp_dt; //작성시간
	private int eml_temp_emp_sq; //보낸사원번호
	private String eml_temp_chk; //읽었는지 표시
}