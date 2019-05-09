package kr.or.nko.approval.dao;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.testconfig.LogicTestConfig;

public class ApprovalDaoTest extends LogicTestConfig {

	@Resource(name="approvalDao")
	private IApprovalDao approvalDao;
	
	@Test
	public void testSearchApvStartDate() {
		/***Given***/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<>();
		
		/***When***/
		map.put("apv_sign", 180901);
		map.put("start_date", "2019-04-08");
		map.put("end_date", "2019-04-11");
		map.put("apv_sign_chk", "1");
		List<ApprovalEleVo> apvList = approvalDao.searchApvStartDate(map);
		
		/***Then***/
		assertEquals(3, apvList.size());
	}
	
	@Test
	public void testSearchApvSignDate() {
		/***Given***/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<>();
		
		/***When***/
		map.put("apv_sign", 180901);
		map.put("start_date", "2019-04-08");
		map.put("end_date", "2019-04-11");
		map.put("apv_div_sq", "1");
		map.put("apv_nm", "신청서");
		List<ApprovalEleVo> apvList = approvalDao.searchApvSignDate(map);
		
		/***Then***/
		assertEquals(1, apvList.size());
	}

}
