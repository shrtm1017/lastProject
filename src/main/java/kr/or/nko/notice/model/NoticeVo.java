package kr.or.nko.notice.model;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeVo {
	private int ntc_sq; //게시글번호
	private int ntc_emp_sq; //사원번호
	private int ntc_div; //게시판구분
	private String ntc_nm; //제목
	private String ntc_con; //내용
	private Date ntc_dt; //등록날짜
	private int ntc_hits; //조회수
	private String ntc_fl_nm; //파일명
	private String ntc_fl_path; //파일경로
	private String emp_nm; //파일경로

}