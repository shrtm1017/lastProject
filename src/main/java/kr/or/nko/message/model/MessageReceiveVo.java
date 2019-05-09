package kr.or.nko.message.model;

import java.util.Date;

import lombok.Data;

@Data
public class MessageReceiveVo {
	private int msg_rec_sq; //받은쪽지번호
	private int msg_emp_send; //보낸사원번호
	private int msg_emp_rec; //받은사원번호
	private Date msg_dt; //수신시간
	private String msg_flag; //읽었는지표시
	private String msg_nm; //제목
	private String msg_con; //내용
	private String msg_ipt; //중요도
}
