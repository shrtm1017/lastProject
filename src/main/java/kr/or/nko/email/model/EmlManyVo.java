package kr.or.nko.email.model;

import lombok.Data;

@Data
public class EmlManyVo {
	private int mn_send_sq; //보낸메일번호
	private int mn_rec_sq; //받을사원번호
}