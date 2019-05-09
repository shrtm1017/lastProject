package kr.or.nko.message.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.message.model.MessageManyVo;
import kr.or.nko.message.model.MessageReceiveVo;
import kr.or.nko.message.model.MessageSendVo;

public interface IMessageService {
	public int insertMessageSend(MessageSendVo svo);
	public int insertMessageReceive(MessageReceiveVo rvo);
	public int insertMessageMany(MessageManyVo mvo);
	public List<MessageSendVo> selectSeq(String str);
	public List<MessageSendVo> selectSend(String str);
	public List<Map<String, Object>> selectSends(String str);
	public List<MessageReceiveVo> selectimpRec(String str);
	public List<MessageReceiveVo> selectRec(String str);
	public List<MessageReceiveVo> myselectRec(String str);
	
	public List<MessageReceiveVo> selectMessageReceivePaging(Map<String, Object> map);
	public List<MessageReceiveVo> selectMessageMyPaging(Map<String, Object> map);
	public List<MessageSendVo> selectMessageSendPaging(Map<String, Object> map);
	public List<MessageReceiveVo> selectMessageCheckPaging(Map<String, Object> map);
	
	public int deleteReceive(String str);
	public int updateReceive(String str);
}
