package kr.or.nko.work.model;

import lombok.Data;

@Data
public class WorkFileVo {
	private int wk_fl_sq; //첨부파일번호
	private int wk_sq; //업무번호
	private String wk_fl_nm; //파일명
	private String wk_fl_path; //파일경로(실제파일이름)
}