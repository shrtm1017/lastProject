package kr.or.nko.cmt.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.cmt.model.CmtVo;
import kr.or.nko.util.model.PageVo;

public interface ICommuteService {
public	int cmtInsert (CmtVo cmtVo); 
public int cmtUpdate(CmtVo cmtVo); 
public Map<String,Object> selectCmtPageList(CmtVo CmtVo,PageVo pagevo);
public Map<String,Object> selectCmtSearchList(CmtVo CmtVo,PageVo pagevo);
public List<CmtVo> selectCmtCheckList(CmtVo cmtVo);
public CmtVo selectCmtCheck(CmtVo cmtVo);

}
