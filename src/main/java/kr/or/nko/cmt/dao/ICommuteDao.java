package kr.or.nko.cmt.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.cmt.model.CmtVo;

public interface ICommuteDao {
 public int cmtInsert (CmtVo cmtVo); 
 public int cmtUpdate(CmtVo cmtVo); 
 public int cmtCnt(int cmtCnt); 
 public int cmtSearchCnt(CmtVo cmtCnt); 
 public List<CmtVo> selectCmtPageList(Map<String, Object> map);
 public List<CmtVo> selectCmtCheckList(CmtVo cmtVo);
 public CmtVo selectCmtCheck(CmtVo cmtVo);
 public  List<CmtVo>  cmtSearch(Map<String, Object> map);


}
