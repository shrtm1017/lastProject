package kr.or.nko.callingcard.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.callingcard.dao.CallingDaoImpl;
import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.util.model.PageVo;

@Service("callingService")
public class CallingServiceImpl implements ICallingService{
	
	
	@Resource(name="callingDao")
	private CallingDaoImpl callingDao;
	
	public CallingServiceImpl() {
	}
	
	
	@Override
	public Map<String, Object> select_cardPagingList(PageVo pageVo) {
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		resultmap.put("cardpaginglist",callingDao.select_cardPagingList(pageVo));
		resultmap.put("cardCnt", callingDao.getCardCnt());
		return resultmap;
	}
	

	@Override
	public int insert_callingcard(CallingCardVo callingCardVo) {
		int insert = callingDao.insert_callingcard(callingCardVo);
		return insert;
	}

	@Override
	public int delete_callingcard(int cal_sq) {
		int delete = callingDao.delete_callingcard(cal_sq);
		return delete;
	}

	@Override
	public int update_callingcard(CallingCardVo callingCardVo) {
		int update = callingDao.update_callingcard(callingCardVo);
		return update;
	}

	@Override
	public CallingCardVo select_callingcarddetail(int cal_sq) {
		CallingCardVo callingCardVo = callingDao.select_callingcarddetail(cal_sq);
		
		return callingCardVo;
	}


	@Override
	public Map<String, Object> selectCallingCardPaging_Search(Map<String, Object> map, CallingCardVo callingCardVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("cardpaginglist", callingDao.selectCallingCardPaging_Search(map));
		resultMap.put("cardCnt", callingDao.getCardCnt());
		resultMap.put("SearchcardCnt", callingDao.getSearchCardCnt(callingCardVo));
		
		return resultMap;
	}

	
	
}
