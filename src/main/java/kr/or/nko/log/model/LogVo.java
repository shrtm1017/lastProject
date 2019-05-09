package kr.or.nko.log.model;

import java.util.Date;

import lombok.Data;

@Data
public class LogVo {
	private int log_sq; //로그번호
	private int log_id; //로그ID
	private String session_id; //세션아이디
	private String log_str_dt; //로그시간
	private String log_end_dt; //로그시간
	private String log_ip; //로그시간
}