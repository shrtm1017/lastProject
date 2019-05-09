package kr.or.nko.message.model;

import lombok.Data;

@Data
public class MessageTempVo {
	private int msg_sq; //쪽지번호
	private String msg_nm; //제목
	private String msg_con; //내용
}