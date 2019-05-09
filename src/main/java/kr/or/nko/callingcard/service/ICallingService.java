package kr.or.nko.callingcard.service;

import java.util.Map;

import kr.or.nko.callingcard.model.CallingCardVo;
import kr.or.nko.util.model.PageVo;

public interface ICallingService {
	// 명함 조회 메서드
	Map<String, Object> select_cardPagingList(PageVo pageVo);
	
	// 명함 등록 메서드
	int insert_callingcard(CallingCardVo callingCardVo);
	
	// 명함 삭제 메서드
	int delete_callingcard(int cal_sq);
	
	// 명함 수정 메서드
	int update_callingcard(CallingCardVo callingCardVo);
	
	// 명함 상세보기
	
	CallingCardVo select_callingcarddetail(int cal_sq);
	
	Map<String, Object> selectCallingCardPaging_Search(Map<String, Object> map, CallingCardVo callingCardVo);
}
