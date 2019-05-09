package kr.or.nko.message.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.message.model.MessageManyVo;
import kr.or.nko.message.model.MessageReceiveVo;
import kr.or.nko.message.model.MessageSendVo;

@Repository("messageDao")
public class MessageDaoImpl implements IMessageDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertMessageSend(MessageSendVo svo) {
		return sqlSessionTemplate.insert("message.insertMessageSend", svo);
	}

	@Override
	public int insertMessageReceive(MessageReceiveVo rvo) {
		return sqlSessionTemplate.insert("message.insertMessageReceive", rvo);
	}

	@Override
	public int insertMessageMany(MessageManyVo mvo) {
		return sqlSessionTemplate.insert("message.insertMessageMany", mvo);
	}

	@Override
	public List<MessageSendVo> selectSeq(String str) {
		return sqlSessionTemplate.selectList("message.selectSeq",str);
	}

	@Override
	public List<MessageSendVo> selectSend(String str) {
		return sqlSessionTemplate.selectList("message.selectSend",str);
	}

	@Override
	public List<MessageReceiveVo> selectRec(String str) {
		return sqlSessionTemplate.selectList("message.selectRec",str);
	}

	@Override
	public List<MessageReceiveVo> myselectRec(String str) {
		return sqlSessionTemplate.selectList("message.myselectRec",str);
	}

	@Override
	public List<MessageReceiveVo> selectimpRec(String str) {
		return sqlSessionTemplate.selectList("message.selectimpRec",str);
	}

	@Override
	public List<Map<String, Object>> selectSends(String str) {
		return sqlSessionTemplate.selectList("message.selectSends",str);
	}

	@Override
	public List<MessageReceiveVo> selectMessageReceivePaging(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("message.selectMessageReceivePaging",map);
	}

	@Override
	public List<MessageReceiveVo> selectMessageMyPaging(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("message.selectMessageMyPaging",map);
	}

	@Override
	public List<MessageSendVo> selectMessageSendPaging(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("message.selectMessageSendPaging",map);
	}

	@Override
	public List<MessageReceiveVo> selectMessageCheckPaging(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("message.selectMessageCheckPaging",map);
	}

	@Override
	public int deleteReceive(String str) {
		return sqlSessionTemplate.delete("message.deleteReceive", str);
	}

	@Override
	public int updateReceive(String str) {
		return sqlSessionTemplate.update("message.updateReceive", str);
	}

}
