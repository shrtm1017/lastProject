package kr.or.nko.annual.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;

@Repository("annualDao")
public class AnnualDaoImpl implements AnnualDao {
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertAnnual(GiveAnnualVo giveannualvo) {
		return sqlSessionTemplate.insert("annual.insertAnnual",giveannualvo);
	}

	@Override
	public List<GiveAnnualVo> listAnnual(Map<String, Object> map) {
		List<GiveAnnualVo> listAnnual =sqlSessionTemplate.selectList("annual.getallAnnuaList",map);
		return listAnnual;
	}

	@Override
	public int listAnnualCnt(int emp_sq) {
		int listAnnualCnt = sqlSessionTemplate.selectOne("annual.AnnuaListCnt",emp_sq);
		return listAnnualCnt ;
	}

	@Override
	public List<GiveAnnualVo> insertList() {
		List<GiveAnnualVo> insertList = sqlSessionTemplate.selectList("annual.insertList");
		return insertList;
	}
	
	@Override
	public double selectUseAnu(int anu_emp_sq) {
		return sqlSessionTemplate.selectOne("annual.selectUseAnu", anu_emp_sq);
	}

	@Override
	public GiveAnnualVo selectGiveAnu(GiveAnnualVo giveAnuVo) {
		return sqlSessionTemplate.selectOne("annual.selectGiveAnu", giveAnuVo);
	}

	@Override
	public int insertGiveAnnual(GiveAnnualVo giveannualvo) {
		return  sqlSessionTemplate.insert("annual.insertGiveAnnual",giveannualvo);
	}

	@Override
	public int insert_UseAnnualDay(UseAnnualVo useAnnualVo) {
		return sqlSessionTemplate.insert("annual.insert_UseAnnualDay", useAnnualVo);
	}

	@Override
	public int select_UseAnnualApv(UseAnnualVo anu_emp_sq) {
		int select_UseAnnaulApvCount = sqlSessionTemplate.selectOne("annual.select_UseAnnualApv",anu_emp_sq);
		
		return select_UseAnnaulApvCount;
	}

	@Override
	public int Use_AnnuaListCnt(int emp_sq) {
		int Use_AnnuaListCnt = sqlSessionTemplate.selectOne("annual.Use_AnnuaListCnt",emp_sq);
		return Use_AnnuaListCnt ;
	}

	@Override
	public List<UseAnnualVo> Use_annualDayList(Map<String, Object> map) {
		List<UseAnnualVo> Use_annualList =sqlSessionTemplate.selectList("annual.Use_annualDayList",map);
		return Use_annualList;
	}

	@Override
	public int select_UseEmpsq(int anu_sq) {
		int select_UseEmpsq = sqlSessionTemplate.selectOne("annual.select_UseEmpsq",anu_sq);
		return select_UseEmpsq;
	}

	@Override
	public int update_UseDayUpdate(UseAnnualVo anu_sq) {
		return sqlSessionTemplate.update("annual.Use_annual_Update",anu_sq);
	}

	@Override
	public int insert_UseAnnualDay_bancha(UseAnnualVo useAnnualVo) {
		return sqlSessionTemplate.insert("annual.insert_UseAnnualDay_bancha",useAnnualVo);
	}

	@Override
	public String selelct_anu_div(int anu_sq) {
		String selelct_anu_div = sqlSessionTemplate.selectOne("annual.selelct_anu_div",anu_sq);
		return selelct_anu_div;
	}

	@Override
	public double selectGiveday(GiveAnnualVo giveannualvo) {
		double selectGiveday = sqlSessionTemplate.selectOne("annual.SelectGiveday",giveannualvo);
		return selectGiveday;
	}

	@Override
	public int SelectGiveYYYY(int anu_emp_sq) {
		int SelectGiveYYYY = sqlSessionTemplate.selectOne("annual.SelectGiveYYYY",anu_emp_sq);
		return SelectGiveYYYY;
	}

}
