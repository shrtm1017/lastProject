package kr.or.nko.annual.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;

public interface AnnualDao {
	
	public int insertAnnual(GiveAnnualVo giveannualvo);
	
	public int insertGiveAnnual(GiveAnnualVo giveannualvo); 
	
	public int insert_UseAnnualDay(UseAnnualVo useAnnualVo);
	
	public int insert_UseAnnualDay_bancha(UseAnnualVo useAnnualVo);
	public int listAnnualCnt(int emp_sq);
	public int Use_AnnuaListCnt(int emp_sq);

	public List<GiveAnnualVo> listAnnual(Map<String, Object> map);
	public List<UseAnnualVo> Use_annualDayList(Map<String, Object> map);
	
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
	
	//허가체크
	public int select_UseAnnualApv(UseAnnualVo anu_emp_sq);
	
	//
	public int select_UseEmpsq(int anu_sq);
	public double selectGiveday(GiveAnnualVo giveannualvo);
	
	public String selelct_anu_div(int anu_sq);
	
	public int update_UseDayUpdate(UseAnnualVo anu_sq);
	
	public int SelectGiveYYYY (int anu_emp_sq);
	
}
