package kr.or.nko.research.model;

import lombok.Data;

@Data
public class AnswerVo {
	private int ans_sq; 	// 질문번호
	private int ans_que_sq; // 질문번호
	private int ans_peo; 	// 선택인원
	private String ans_con; // 답변내용
	
	private int ans_cnt;	// 설문결과
}
