package kr.or.nko.data.model;

import java.util.Date;

import lombok.Data;

@Data
public class DataVo {
	private int data_sq; //자료번호
	private int data_emp_sq; //사원번호
	private String data_nm; //제목
	private String data_con; //내용
	private int data_hits; //조회수
	private Date data_dt; //작성일
	private Date data_mod; //최종수정일
	private String data_man; //작성자명
}