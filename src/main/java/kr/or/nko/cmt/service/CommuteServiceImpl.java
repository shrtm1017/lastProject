package kr.or.nko.cmt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.nko.cmt.dao.ICommuteDao;
import kr.or.nko.cmt.model.CmtVo;
import kr.or.nko.util.model.PageVo;

@Service("cmtService")
public class CommuteServiceImpl implements ICommuteService{
	@Resource(name="cmtDao")
	private ICommuteDao commuteDao;
	private Logger logger = LoggerFactory.getLogger(CommuteServiceImpl.class);
	@Override
	public int cmtInsert(CmtVo cmtVo) {
		return commuteDao.cmtInsert(cmtVo);
	}

	@Override
	public int cmtUpdate(CmtVo cmtVo) {
		return commuteDao.cmtUpdate(cmtVo);
	}

	@Override
	public Map<String, Object> selectCmtPageList(CmtVo CmtVo, PageVo pagevo) {
		Map<String, Object> selectCmtPageList = new HashMap<String, Object>();
		selectCmtPageList.put("page", pagevo.getPage());
		selectCmtPageList.put("pageSize", pagevo.getPageSize());
		selectCmtPageList.put("cmt_emp_sq", CmtVo.getCmt_emp_sq());
		logger.debug(CmtVo.getCmt_emp_sq()+"@@@");
		selectCmtPageList.put("cmtCnt", commuteDao.cmtCnt(CmtVo.getCmt_emp_sq()));
		selectCmtPageList.put("selectCmtPageList", commuteDao.selectCmtPageList(selectCmtPageList));
		return selectCmtPageList;
	}

	@Override
	public List<CmtVo> selectCmtCheckList(CmtVo cmtVo) {
		List<CmtVo>selectCmtCheckList = commuteDao.selectCmtCheckList(cmtVo);
		return selectCmtCheckList;
	}

	@Override
	public CmtVo selectCmtCheck(CmtVo cmtVo) {
		return commuteDao.selectCmtCheck(cmtVo);
	}

	@Override
	public Map<String, Object> selectCmtSearchList(CmtVo CmtVo, PageVo pagevo) {
		logger.debug("controller"+CmtVo.getCmt_end_tm() );
		logger.debug("controller"+CmtVo.getCmt_srt_tm());
		Map<String, Object> selectCmtSearchList = new HashMap<String, Object>();
		selectCmtSearchList.put("page", pagevo.getPage());
		selectCmtSearchList.put("pageSize", pagevo.getPageSize());
		selectCmtSearchList.put("cmt_emp_sq", CmtVo.getCmt_emp_sq());
		selectCmtSearchList.put("cmt_srt_tm", CmtVo.getCmt_srt_tm());
		selectCmtSearchList.put("cmt_end_tm", CmtVo.getCmt_end_tm());
		
		
		selectCmtSearchList.put("cmtSearchCnt", commuteDao.cmtSearchCnt(CmtVo));
		selectCmtSearchList.put("selectCmtPageList", commuteDao.cmtSearch(selectCmtSearchList));
		logger.debug(selectCmtSearchList.size()+"@@");
		return selectCmtSearchList;
	}


	

}
