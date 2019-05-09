package kr.or.nko.data.model;

import java.util.Date;

import lombok.Data;

@Data
public class DataRpyVo {
	private int rpy_sq; //리플번호
	private int rpy_emp_sq; //사원번호
	private int rpy_data_sq; //자료번호
	private String rpy_con; //리플내용
	private Date rpy_dt; //리플등록일
	private Date rpy_mod; //리플수정일
}