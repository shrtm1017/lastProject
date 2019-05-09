package kr.or.nko.callingcard.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.util.model.PageVo;

public interface ICallingDao {
	
	// 명함 조회 메서드
	List<CallingCardVo> select_cardPagingList(PageVo pageVo);
	
	// 명함 등록 메서드
	int insert_callingcard(CallingCardVo callingCardVo);
	
	// 명함 삭제 메서드
	int delete_callingcard(int cal_sq);
	
	// 명함 수정 메서드
	int update_callingcard(CallingCardVo callingCardVo);
	
	// 명함 상세보기
	
	CallingCardVo select_callingcarddetail(int cal_sq);
	
	// 명함 전체 수 조회
	int getCardCnt();
	
	// 검색한 명함리스트 페이징
	public List<CallingCardVo> selectCallingCardPaging_Search(Map<String, Object> map);
	
	// 검색한 명함 수
	public int getSearchCardCnt(CallingCardVo callingCardVo);
}
