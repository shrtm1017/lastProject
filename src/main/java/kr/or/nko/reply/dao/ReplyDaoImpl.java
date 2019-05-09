package kr.or.nko.reply.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.reply.model.ReplyVo;

@Repository("replyDao")
public class ReplyDaoImpl implements IReplyDao{
	@Resource(name="sqlSessionTemplate")
private SqlSessionTemplate SqlSessionTemplate;
	
	
	@Override
	public int reply_cnt(int cnt) {
		int reply_cnt= SqlSessionTemplate.selectOne("reply.Ntc_replyCnt",cnt);
		return reply_cnt;
	}

	@Override
	public int reply_insert(ReplyVo replyVo) {
		int reply_insert = SqlSessionTemplate.insert("reply.reply_Register",replyVo);
		return reply_insert ;
	}

	@Override
	public List<ReplyVo> replyPageingList(Map<String, Object> map) {
		List<ReplyVo> replyPageingList = SqlSessionTemplate.selectList("reply.select_reply_List",map);
		return replyPageingList;
	}

	@Override
	public List<ReplyVo> replyAllList(Integer rpy_ntc_sq) {
		List<ReplyVo> replyAllList = SqlSessionTemplate.selectList("reply.reply_AllList",rpy_ntc_sq);
		return replyAllList;
	}

	@Override
	public int updateLevel(ReplyVo rpy_ntc_sq) {
		int updateLevel =SqlSessionTemplate.update("reply.updateLevel",rpy_ntc_sq);
		return updateLevel;
	}

	@Override
	public int delete_reply(ReplyVo rpy_ntc_sq) {
		int delete_reply = SqlSessionTemplate.delete("reply.delete_reply",rpy_ntc_sq);
		return delete_reply;
	}

	@Override
	public int clear_reply(Integer rpy_ntc_sq) {
		int clear_reply = SqlSessionTemplate.delete("reply.clear_reply",rpy_ntc_sq);
		return clear_reply;
	}

}
