package kr.or.nko.message.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.nko.message.model.MessageSendVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class MessageDaoTest extends LogicTestConfig {
	
	private Logger logger = LoggerFactory.getLogger(MessageDaoTest.class);
	
	@Resource(name="messageDao")
	private IMessageDao messageDao;
	
	@Test
	public void testInsert() {
		MessageSendVo svo = new MessageSendVo();
		svo.setMsg_emp_send(180903);
		svo.setMsg_con("메롱");
		svo.setMsg_nm("제목");
		
		int insertResult = messageDao.insertMessageSend(svo);
		
		logger.debug("insertResult : {}", insertResult);
	}

}