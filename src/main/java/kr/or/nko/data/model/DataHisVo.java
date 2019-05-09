package kr.or.nko.data.model;

import java.util.Date;

import lombok.Data;

@Data
public class DataHisVo {
	private int data_sq; //자료번호
	private int data_emp_sq; //다운사원번호
	private Date data_dt; //다운시간
	private String data_nm; // 사원이름
}