package kr.or.nko.callingcard.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class CallingCardDaoTest extends LogicTestConfig{

	@Resource(name="callingDao")
	private ICallingDao callingDao;
	
	@Test
	public void test() {
		CallingCardVo vo = new CallingCardVo();
		
		vo.setCal_emp_sq(180902);
		vo.setCal_nm("신차장");
		vo.setCal_phone("010-1235-8795");
		vo.setCal_mail("dlstpdlem@naver.com");
		vo.setCal_com("THE NK");
		vo.setCal_com_phone("02-1563-7756");
		vo.setCal_grade("차장");
		int insert = callingDao.insert_callingcard(vo);
		
		assertEquals(1, insert);
		
	}

}
