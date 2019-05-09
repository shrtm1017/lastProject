package kr.or.nko.webhard.model;

import lombok.Data;

@Data
public class WebhardVo {
	private int webh_sq; //파일번호
	private int webh_hr_sq; //상위폴더
	private int webh_emp_sq; //사원번호
	private String webh_fl_path; //파일경로
	private String webh_fl_nm; //real파일명
	private String webh_nm; //제목
}