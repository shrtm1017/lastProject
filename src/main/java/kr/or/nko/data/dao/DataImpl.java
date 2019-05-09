package kr.or.nko.data.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.data.model.DataDatasVo;
import kr.or.nko.data.model.DataHisVo;
import kr.or.nko.data.model.DataVo;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.model.PageVo;

@Repository("datadDao")
public class DataImpl implements IDataDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertData(DataVo vo) {
		return sqlSessionTemplate.insert("data.insertData", vo);
	}

	@Override
	public int insertDataFile(DataDatasVo vo) {
		return sqlSessionTemplate.insert("data.insertDataFile", vo);
	}

	@Override
	public List<DataVo> selectData(String str) {
		return sqlSessionTemplate.selectList("data.selectData", str);
	}

	@Override
	public List<DataDatasVo> selectDataDatas(String str) {
		return sqlSessionTemplate.selectList("data.selectDataDatas", str);
	}

	@Override
	public int insetDataHis(DataHisVo vo) {
		return sqlSessionTemplate.insert("data.insetDataHis", vo);
	}

	@Override
	public List<DataHisVo> selectDataHis(String str) {
		return sqlSessionTemplate.selectList("data.selectDataHis", str);
	}

	@Override
	public List<EmployeeVo> searchEmp(String str) {
		return sqlSessionTemplate.selectList("data.searchEmp", str);
	}

	@Override
	public int delHisData(String str) {
		return sqlSessionTemplate.delete("data.delHisData", str);
	}

	@Override
	public int delDatas(String str) {
		return sqlSessionTemplate.delete("data.delDatas", str);
	}

	@Override
	public int delData(String str) {
		return sqlSessionTemplate.delete("data.delData", str);
	}

	@Override
	public List<DataVo> selectPaging(PageVo vo) {
		return sqlSessionTemplate.selectList("data.selectPaging", vo);
	}

	@Override
	public List<DataVo> selectSearchPaging(PageVo vo) {
		return sqlSessionTemplate.selectList("data.selectSearchPaging", vo);
	}

	@Override
	public int getDatasCnt() {
		return sqlSessionTemplate.selectOne("data.getDatasCnt");
	}

	@Override
	public int getDataCntSearch(PageVo vo) {
		return sqlSessionTemplate.selectOne("data.getDataCntSearch",vo);
	}

	@Override
	public List<DataVo> searchWantData(String str) {
		return sqlSessionTemplate.selectList("data.searchWantData", str);
	}
	
}
