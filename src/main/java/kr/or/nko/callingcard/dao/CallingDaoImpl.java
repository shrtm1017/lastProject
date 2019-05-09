package kr.or.nko.callingcard.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.util.model.PageVo;

@Repository("callingDao")
public class CallingDaoImpl implements ICallingDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlsessionTemplate;

	@Override
	public List<CallingCardVo> select_cardPagingList(PageVo pageVo) {
		List<CallingCardVo> list = sqlsessionTemplate.selectList("callingcard.selectCallingCardPagingList",pageVo);
		
		return list;
	}

	@Override
	public int insert_callingcard(CallingCardVo callingCardVo) {
		int insert = sqlsessionTemplate.insert("callingcard.insertCallingCard",callingCardVo);
		return insert;
	}

	@Override
	public int delete_callingcard(int cal_sq) {
		int delete = sqlsessionTemplate.delete("callingcard.deleteCallingCard",cal_sq);
		return delete;
	}

	@Override
	public int update_callingcard(CallingCardVo callingCardVo) {
		int update = sqlsessionTemplate.update("callingcard.updateCallingCard",callingCardVo); 
		return update;
	}

	@Override
	public CallingCardVo select_callingcarddetail(int cal_sq) {
		CallingCardVo callingCardVo = sqlsessionTemplate.selectOne("callingcard.selectCallingCard",cal_sq);
		return callingCardVo;
	}

	@Override
	public int getCardCnt() {
		int cardcnt = sqlsessionTemplate.selectOne("callingcard.getCardCnt");
		return cardcnt;
	}

	@Override
	public List<CallingCardVo> selectCallingCardPaging_Search(Map<String, Object> map) {
		
		return sqlsessionTemplate.selectList("callingcard.selectCallingCardPaging_Search",map);
	}

	@Override
	public int getSearchCardCnt(CallingCardVo callingCardVo) {
		
		return sqlsessionTemplate.selectOne("callingcard.getSearchCardCnt",callingCardVo);
	}
	
}
