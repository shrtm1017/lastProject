package kr.or.nko.todolist.model;

import java.util.Date;

import lombok.Data;

@Data
public class TodolistVo {
	private int tdl_sq; //오늘의할일번호
	private int tdl_emp_sq; //사원번호
	private String tdl_con; //내용
	private Date tdl_dt; //작성날짜
	private String tdl_comp;
}
