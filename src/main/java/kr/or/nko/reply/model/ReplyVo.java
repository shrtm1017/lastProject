package kr.or.nko.reply.model;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVo {
	private Integer rpy_sq; //리플번호
	private Integer rpy_ntc_sq; //게시글번호
	private Integer rpy_emp_sq; //사원번호
	private String rpy_con; //내용
	private Date rpy_dt; //작성일
	private Date rpy_fnl_mod; //최종수정일
	private Integer rpy_parent_sq; //댓글 부모번호
	private int rpy_cnt; //댓글 갯수
	private String rpy_level; //계층번호
	private String rpy_del_if; //삭제여부
}
