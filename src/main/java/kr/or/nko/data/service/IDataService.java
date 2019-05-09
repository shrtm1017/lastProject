package kr.or.nko.data.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.data.model.DataDatasVo;
import kr.or.nko.data.model.DataHisVo;
import kr.or.nko.data.model.DataVo;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.model.PageVo;

public interface IDataService {
	
	public int insertData(DataVo vo);
	public int insertDataFile(DataDatasVo vo);
	public int insetDataHis(DataHisVo vo);
	
	public List<DataVo> selectData(String str);
	public List<DataDatasVo> selectDataDatas(String str);
	public List<DataHisVo> selectDataHis(String str);
	
	public List<EmployeeVo> searchEmp(String str);
	
	public int delHisData(String str);
	public int delDatas(String str);
	public int delData(String str);
	
	public Map<String, Object> selectPaging(PageVo vo);
	public Map<String, Object> selectSearchPaging(PageVo vo);
	public int getDatasCnt();
	public int getDataCntSearch(PageVo vo);
	
	public List<DataVo> searchWantData(String str);
}
