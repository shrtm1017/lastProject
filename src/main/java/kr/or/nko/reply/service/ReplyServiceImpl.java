package kr.or.nko.reply.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.reply.dao.IReplyDao;
import kr.or.nko.reply.model.ReplyVo;
import kr.or.nko.util.model.PageVo;

@Service("replyService")
public class ReplyServiceImpl implements IReplyService{
	@Resource(name="replyDao")
	private IReplyDao replyDao;

	@Override
	public int reply_cnt(int cnt) {
		int reply_cnt = replyDao.reply_cnt(cnt);
		return reply_cnt ;
	}

	@Override
	public int reply_insert(ReplyVo replyVo) {
		int reply_insert =replyDao.reply_insert(replyVo);
		return reply_insert;
	}

	@Override
	public Map<String, Object> replyPageingList(int rpy_ntc_sq, PageVo pageVo) {
		Map<String,Object>Map = new HashMap<>();
		Map.put("page", pageVo.getPage());
		Map.put("pageSize", pageVo.getPageSize());
		Map.put("rpy_ntc_sq", rpy_ntc_sq);
		Map.put("replyList", replyDao.replyPageingList(Map));
		Map.put("replyCnt", replyDao.reply_cnt(rpy_ntc_sq));
		
		return Map;
	}

	@Override
	public List<ReplyVo> replyAllList(Integer rpy_ntc_sq) {
		List<ReplyVo> replyAllList = replyDao.replyAllList(rpy_ntc_sq);
		return replyAllList;
	}

	@Override
	public int updateLevel(ReplyVo rpy_ntc_sq) {
		int updateLevel = replyDao.updateLevel(rpy_ntc_sq);
		return updateLevel;
	}

	@Override
	public int delete_reply(ReplyVo rpy_ntc_sq) {
		int delete_reply = replyDao.delete_reply(rpy_ntc_sq);
		return delete_reply ;
	}

	@Override
	public int clear_reply(Integer rpy_ntc_sq) {
		int clear_reply = replyDao.clear_reply(rpy_ntc_sq);
		return clear_reply;
	}

}
