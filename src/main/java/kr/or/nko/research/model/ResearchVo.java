package kr.or.nko.research.model;

import java.util.Date;

import lombok.Data;

@Data
public class ResearchVo {
	private int res_sq; //설문조사번호
	private int res_emp_sq; //작성사원
	private String res_nm; //제목
	private String res_con; //내용
	private Date res_str_dt; //설문시작일
	private Date res_end_dt; //설문마감일
	private String res_state; //상태
	private int res_hit; //조회수
}
