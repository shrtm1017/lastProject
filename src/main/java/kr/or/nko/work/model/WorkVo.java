package kr.or.nko.work.model;

import java.util.Date;

import lombok.Data;

@Data
public class WorkVo {
	private int wk_sq; //업무번호
	private int wk_hr_sq; //상위업무번호
	private String wk_nm; //제목
	private String wk_con; //내용
	private Date wk_str_dt; //등록일
	private Date wk_end_dt; //종료예정일
	private Date wk_fnl_dt; //종료일
	private String wk_type; //업무유형
	private String wk_state; //업무상태
	private int wk_progress; //업무진행상황
	private int pro_sq; //프로젝트번호
	private Date wk_upd_dt; //수정일
	
	public WorkVo(){}
	public WorkVo(int wk_hr_sq, String wk_nm, String wk_con, Date wk_str_dt, Date wk_end_dt,
			String wk_type, String wk_state, int wk_progress, int pro_sq) {
		this.wk_hr_sq = wk_hr_sq;
		this.wk_nm = wk_nm;
		this.wk_con = wk_con;
		this.wk_str_dt = wk_str_dt;
		this.wk_end_dt = wk_end_dt;
		this.wk_type = wk_type;
		this.wk_state = wk_state;
		this.wk_progress = wk_progress;
		this.pro_sq = pro_sq;
	}
}