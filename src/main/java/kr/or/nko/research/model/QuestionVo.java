package kr.or.nko.research.model;

import lombok.Data;

@Data
public class QuestionVo {
	private int que_sq; //질문번호
	private int que_res_sq; //설문조사번호
	private String que_con; //질문내용
}
