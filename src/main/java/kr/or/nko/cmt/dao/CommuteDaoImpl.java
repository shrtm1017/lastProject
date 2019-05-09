package kr.or.nko.cmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.cmt.model.CmtVo;
@Repository("cmtDao")
public class CommuteDaoImpl implements ICommuteDao {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int cmtInsert(CmtVo cmtVo) {
		return sqlSessionTemplate.insert("commute.gotoWorkCommute",cmtVo);
	}

	@Override
	public int cmtUpdate(CmtVo cmtVo) {
		return sqlSessionTemplate.update("commute.gotoHomeCommute",cmtVo);
	}

	@Override
	public List<CmtVo> selectCmtPageList(Map<String, Object> map) {
		List<CmtVo> selectCmtPageList = sqlSessionTemplate.selectList("commute.CommuteSelectPagingList",map);
		return selectCmtPageList;
	}

	@Override
	public int cmtCnt(int cmtCnt) {
		int cmtpageCnt =sqlSessionTemplate.selectOne("commute.CommuteSelectCnt",cmtCnt);
		return cmtpageCnt;
	}

	@Override
	public List<CmtVo> selectCmtCheckList(CmtVo cmtVo) {
		List<CmtVo> selectCmtCheckList = sqlSessionTemplate.selectList("commute.CommuteCheckList",cmtVo);
		return selectCmtCheckList;
	}

	@Override
	public CmtVo selectCmtCheck(CmtVo cmtVo) {
		return sqlSessionTemplate.selectOne("commute.CommuteCheck",cmtVo);
	}

	@Override
	public  List<CmtVo>  cmtSearch(Map<String, Object> map) {
		List<CmtVo> cmtSearchList =sqlSessionTemplate.selectList("commute.CommuteSearch",map);
		return cmtSearchList;
	}

	@Override
	public int cmtSearchCnt(CmtVo cmtCnt) {
		int cmtSearchCnt =sqlSessionTemplate.selectOne("commute.CommuteSearchCnt",cmtCnt);
		return cmtSearchCnt;
	}



}
