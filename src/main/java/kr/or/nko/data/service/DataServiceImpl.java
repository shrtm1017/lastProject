package kr.or.nko.data.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.data.dao.IDataDao;
import kr.or.nko.data.model.DataDatasVo;
import kr.or.nko.data.model.DataHisVo;
import kr.or.nko.data.model.DataVo;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.model.PageVo;

@Service("dataService")
public class DataServiceImpl implements IDataService {

	@Resource(name="datadDao")
	private IDataDao dataDao;
	
	@Override
	public int insertData(DataVo vo) {
		return dataDao.insertData(vo);
	}

	@Override
	public int insertDataFile(DataDatasVo vo) {
		return dataDao.insertDataFile(vo);
	}

	@Override
	public List<DataVo> selectData(String str) {
		return dataDao.selectData(str);
	}

	@Override
	public List<DataDatasVo> selectDataDatas(String str) {
		return dataDao.selectDataDatas(str);
	}

	@Override
	public int insetDataHis(DataHisVo vo) {
		return dataDao.insetDataHis(vo);
	}

	@Override
	public List<DataHisVo> selectDataHis(String str) {
		return dataDao.selectDataHis(str);
	}

	@Override
	public List<EmployeeVo> searchEmp(String str) {
		return dataDao.searchEmp(str);
	}

	@Override
	public int delHisData(String str) {
		return dataDao.delHisData(str);
	}

	@Override
	public int delDatas(String str) {
		return dataDao.delDatas(str);
	}

	@Override
	public int delData(String str) {
		return dataDao.delData(str);
	}

	@Override
	public Map<String, Object> selectPaging(PageVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("dataList", dataDao.selectPaging(vo));
		resultMap.put("dataCnt", dataDao.getDatasCnt());
		
		return resultMap;
	}

	@Override
	public Map<String, Object> selectSearchPaging(PageVo vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("dataList", dataDao.selectSearchPaging(vo));
		resultMap.put("dataCnt", dataDao.getDataCntSearch(vo));
		
		return resultMap;
	}

	@Override
	public int getDatasCnt() {
		return dataDao.getDatasCnt();
	}

	@Override
	public int getDataCntSearch(PageVo vo) {
		return dataDao.getDataCntSearch(vo);
	}

	@Override
	public List<DataVo> searchWantData(String str) {
		return dataDao.searchWantData(str);
	}

}
