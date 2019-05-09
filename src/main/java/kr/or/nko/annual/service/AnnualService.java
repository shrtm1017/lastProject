package kr.or.nko.annual.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;
import kr.or.nko.util.model.PageVo;

public interface AnnualService {
	
	public int insertAnnual(GiveAnnualVo giveannualvo); 
	
	public int insertGiveAnnual(GiveAnnualVo giveannualvo); 
	
	public int insert_UseAnnualDay(UseAnnualVo useAnnualVo);
	
	public Map<String,Object> annualList(GiveAnnualVo giveannualvo,PageVo pagevo);
	public Map<String,Object> Use_annualDayList(UseAnnualVo useAnnualVo,PageVo pagevo);
	
	public List<GiveAnnualVo> insertList();

	/**
	 * Method : selectUseAnu
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param anu_emp_sq
	 * @return
	 * Method 설명 : 연차 사용 일수 조회
	 */
	public double selectUseAnu(int anu_emp_sq);
	
	/**
	 * Method : selectGiveAnu
	 * 작성자 : PC07
	 * 변경이력 :
	 * @param giveAnuVo
	 * @return
	 * Method 설명 : 연차 지급 일수 조회
	 */
	public GiveAnnualVo selectGiveAnu(GiveAnnualVo giveAnuVo);
	
	public int select_UseAnnualApv(UseAnnualVo anu_emp_sq); 
	
	public int select_UseEmpsq(int anu_sq);
	
	public int update_UseDayUpdate(UseAnnualVo anu_sq);
	
	public int insert_UseAnnualDay_bancha(UseAnnualVo useAnnualVo);
	
	public double selectGiveday(GiveAnnualVo giveannualvo);
	
	public String selelct_anu_div(int anu_sq);
	
	public int SelectGiveYYYY (int anu_emp_sq);
	
}
