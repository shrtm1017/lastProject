package kr.or.nko.message.model;

import lombok.Data;

@Data
public class MessageManyVo {
	private int mn_msg_send_sq; //보낸쪽지번호
	private int mn_emp_rec_sq; //받는사원번호
}
