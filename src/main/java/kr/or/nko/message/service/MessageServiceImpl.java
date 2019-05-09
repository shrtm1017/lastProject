package kr.or.nko.message.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.message.dao.IMessageDao;
import kr.or.nko.message.model.MessageManyVo;
import kr.or.nko.message.model.MessageReceiveVo;
import kr.or.nko.message.model.MessageSendVo;

@Service("messageService")
public class MessageServiceImpl implements IMessageService{
	
	@Resource(name="messageDao")
	private IMessageDao messageDao;

	@Override
	public int insertMessageSend(MessageSendVo svo) {
		return messageDao.insertMessageSend(svo);
	}

	@Override
	public int insertMessageReceive(MessageReceiveVo rvo) {
		return messageDao.insertMessageReceive(rvo);
	}

	@Override
	public int insertMessageMany(MessageManyVo mvo) {
		return messageDao.insertMessageMany(mvo);
	}

	@Override
	public List<MessageSendVo> selectSeq(String str) {
		return messageDao.selectSeq(str);
	}

	@Override
	public List<MessageSendVo> selectSend(String str) {
		return messageDao.selectSend(str);
	}

	@Override
	public List<MessageReceiveVo> selectRec(String str) {
		return messageDao.selectRec(str);
	}

	@Override
	public List<MessageReceiveVo> myselectRec(String str) {
		return messageDao.myselectRec(str);
	}

	@Override
	public List<MessageReceiveVo> selectimpRec(String str) {
		return messageDao.selectimpRec(str);
	}

	@Override
	public List<Map<String, Object>> selectSends(String str) {
		return messageDao.selectSends(str);
	}

	@Override
	public List<MessageReceiveVo> selectMessageReceivePaging(Map<String, Object> map) {
		return messageDao.selectMessageReceivePaging(map);
	}

	@Override
	public List<MessageReceiveVo> selectMessageMyPaging(Map<String, Object> map) {
		return messageDao.selectMessageMyPaging(map);
	}

	@Override
	public List<MessageSendVo> selectMessageSendPaging(Map<String, Object> map) {
		return messageDao.selectMessageSendPaging(map);
	}

	@Override
	public List<MessageReceiveVo> selectMessageCheckPaging(Map<String, Object> map) {
		return messageDao.selectMessageCheckPaging(map);
	}

	@Override
	public int deleteReceive(String str) {
		return messageDao.deleteReceive(str);
	}

	@Override
	public int updateReceive(String str) {
		return messageDao.updateReceive(str);
	}
	
}
