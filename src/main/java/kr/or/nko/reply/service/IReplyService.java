package kr.or.nko.reply.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.reply.model.ReplyVo;
import kr.or.nko.util.model.PageVo;

public interface IReplyService {
	int reply_cnt(int cnt);
	int reply_insert(ReplyVo replyVo);
	Map<String, Object>replyPageingList (int rpy_ntc_sq, PageVo pageVo);
	List <ReplyVo> replyAllList (Integer rpy_ntc_sq);
	int updateLevel (ReplyVo rpy_ntc_sq);
	int delete_reply(ReplyVo rpy_ntc_sq);
	int clear_reply(Integer rpy_ntc_sq);
}
