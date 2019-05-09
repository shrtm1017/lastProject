package kr.or.nko.cmt.model;

import java.util.Date;

import lombok.Data;

@Data
public class CmtVo {
	private int cmt_sq; //출퇴근번호
	private int cmt_emp_sq; //사원번호
	private String cmt_srt_tm; //출근시간
	private String cmt_end_tm; //퇴근시간
	private String cmt_div; //퇴근시간
}