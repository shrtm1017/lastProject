package kr.or.nko.annual.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.nko.annual.dao.AnnualDao;
import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;
import kr.or.nko.util.model.PageVo;

@Service("annualService")
public class AnnualServiceImpl implements AnnualService {
	
	@Resource(name="annualDao")
	private AnnualDao annualDao;
	
	private Logger logger = LoggerFactory.getLogger(AnnualServiceImpl.class);

	@Override
	public int insertAnnual(GiveAnnualVo giveannualvo) {
		return annualDao.insertAnnual(giveannualvo);
	}

	@Override
	public Map<String, Object> annualList(GiveAnnualVo giveannualvo, PageVo pagevo) {
		Map<String, Object> annualList = new HashMap<String, Object>();
		
		logger.debug("emp_sq "+ giveannualvo.getAnu_emp_sq());
		annualList.put("anu_emp_sq", giveannualvo.getAnu_emp_sq());
		annualList.put("page", pagevo.getPage());
		annualList.put("pageSize", pagevo.getPageSize());
		annualList.put("listAnnualCnt", annualDao.listAnnualCnt(giveannualvo.getAnu_emp_sq()));
		annualList.put("listAnnual", annualDao.listAnnual(annualList));
		return annualList;
	}

	@Override
	public List<GiveAnnualVo> insertList() {
		List<GiveAnnualVo> insertList = annualDao.insertList();
		return insertList;
	}
	
	@Override
	public double selectUseAnu(int anu_emp_sq) {
		return annualDao.selectUseAnu(anu_emp_sq);
	}

	@Override
	public GiveAnnualVo selectGiveAnu(GiveAnnualVo giveAnuVo) {
		return annualDao.selectGiveAnu(giveAnuVo);
	}

	@Override
	public int insertGiveAnnual(GiveAnnualVo giveannualvo) {
		return annualDao.insertGiveAnnual(giveannualvo);
	}

	@Override
	public int insert_UseAnnualDay(UseAnnualVo useAnnualVo) {
		return annualDao.insert_UseAnnualDay(useAnnualVo);
	}

	@Override
	public int select_UseAnnualApv(UseAnnualVo anu_emp_sq) {
		return annualDao.select_UseAnnualApv(anu_emp_sq);
	}

	@Override
	public Map<String, Object> Use_annualDayList(UseAnnualVo useAnnualVo, PageVo pagevo) {
	Map<String, Object> Use_annualDayList = new HashMap<String, Object>();
		
		logger.debug("emp_sq "+ useAnnualVo.getAnu_emp_sq());
		Use_annualDayList.put("anu_emp_sq", useAnnualVo.getAnu_emp_sq());
		Use_annualDayList.put("page", pagevo.getPage());
		Use_annualDayList.put("pageSize", pagevo.getPageSize());
		Use_annualDayList.put("Use_annualDayListCnt", annualDao.Use_AnnuaListCnt(useAnnualVo.getAnu_emp_sq()));
		Use_annualDayList.put("Use_annualDayList", annualDao.Use_annualDayList(Use_annualDayList));
		return Use_annualDayList;
	}

	@Override
	public int select_UseEmpsq(int anu_sq) {
		return annualDao.select_UseEmpsq(anu_sq);
	}

	@Override
	public int update_UseDayUpdate(UseAnnualVo anu_sq) {
		return annualDao.update_UseDayUpdate(anu_sq);
	}

	@Override
	public int insert_UseAnnualDay_bancha(UseAnnualVo useAnnualVo) {
		return annualDao.insert_UseAnnualDay_bancha(useAnnualVo);
	}

	@Override
	public String selelct_anu_div(int anu_sq) {
		return annualDao.selelct_anu_div(anu_sq);
	}

	@Override
	public double selectGiveday(GiveAnnualVo giveannualvo) {
		return annualDao.selectGiveday(giveannualvo);
	}

	@Override
	public int SelectGiveYYYY(int anu_emp_sq) {
		
		return annualDao.SelectGiveYYYY(anu_emp_sq);
	}

}
