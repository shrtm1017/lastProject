package kr.or.nko.research.model;

import lombok.Data;

@Data
public class AttendVo {
	private int att_que_sq; //질문번호
	private int att_ans_sq; //답변번호
	private int att_emp_sq; //참여한사원번호
}