package kr.or.nko.message.model;

import java.util.Date;
import lombok.Data;

@Data
public class MessageSendVo {
	private int msg_rec_sq; //보낸쪽지번호
	private int msg_emp_send; //보낸사원번호
	private Date msg_send_dt; //발신시간
	private String msg_nm; //제목
	private String msg_con; //내용
}