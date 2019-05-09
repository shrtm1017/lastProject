package kr.or.nko.email.model;

import lombok.Data;

@Data
public class EmlTempFileVo {
	private int eml_temp_fl_sq; //임시파일번호
	private int eml_temp_sq; //임시메일번호
	private String eml_temp_fl_nm; //파일명
	private String eml_temp_fl_path; //파일경로
}