package kr.or.nko.work.model;

import lombok.Data;

@Data
public class ProjectVo {
	private int pro_sq; //프로젝트번호
	private String pro_nm; //프로젝트이름
	private String pro_exp; //프로젝트설명
	private String pro_open; //프로젝트 공개여부
}