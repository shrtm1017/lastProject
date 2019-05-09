package kr.or.nko.email.model;

import lombok.Data;

@Data
public class EmlFileVo {
	private int eml_fl_sq; //메일파일번호
	private int eml_send_sq; //보낸메일번호
	private String eml_fl_nm; //파일명
	private String eml_fl_path; //파일경로
	private int eml_rec_sq; //받은메일번호
}