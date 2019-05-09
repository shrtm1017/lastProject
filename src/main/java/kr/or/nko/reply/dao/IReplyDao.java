package kr.or.nko.reply.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.reply.model.ReplyVo;

public interface IReplyDao {
	int reply_cnt(int cnt);
	int reply_insert(ReplyVo replyVo);
	List <ReplyVo> replyPageingList (Map<String,Object>map);
	List <ReplyVo> replyAllList (Integer rpy_ntc_sq);
	int updateLevel (ReplyVo rpy_ntc_sq);
	int delete_reply(ReplyVo rpy_ntc_sq);
	int clear_reply(Integer rpy_ntc_sq);

}
