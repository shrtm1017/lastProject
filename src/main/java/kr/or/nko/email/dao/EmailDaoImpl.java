package kr.or.nko.email.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.model.EmlSendVo;
import kr.or.nko.email.model.EmlTempFileVo;
import kr.or.nko.email.model.EmlTempVo;

@Repository("emailDao")
public class EmailDaoImpl implements IEmailDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertTemp(EmlTempVo emlTempVo) {
		return sqlSessionTemplate.insert("email.insertTemp", emlTempVo);
	}

	@Override
	public int insertTempFile(EmlTempFileVo emlTempFileVo) {
		return sqlSessionTemplate.insert("email.insertTempFile", emlTempFileVo);
	}

	@Override
	public List<EmlTempFileVo> selectTempFileList(int eml_temp_sq) {
		return sqlSessionTemplate.selectList("email.selectTempFileList", eml_temp_sq);
	}
	
	@Override
	public int deleteTemp(int eml_temp_sq) {
		return sqlSessionTemplate.delete("email.deleteTemp", eml_temp_sq);
	}
	
	@Override
	public int deleteTempFiles(int eml_temp_sq) {
		return sqlSessionTemplate.delete("email.deleteTempFiles", eml_temp_sq);
	}

	@Override
	public int deleteTempFile(int eml_temp_fl_sq) {
		return sqlSessionTemplate.delete("email.deleteTempFile", eml_temp_fl_sq);
	}

	@Override
	public EmlTempFileVo selectTempFile(int eml_temp_fl_sq) {
		return sqlSessionTemplate.selectOne("email.selectTempFile", eml_temp_fl_sq);
	}

	@Override
	public List<EmlTempVo> selectTempList(EmlTempVo emlTempVo) {
		return sqlSessionTemplate.selectList("email.selectTempList", emlTempVo);
	}

	@Override
	public int updateTemp(EmlTempVo emlTempVo) {
		return sqlSessionTemplate.update("email.updateTemp", emlTempVo);
	}

	@Override
	public int insertSend(EmlSendVo emlSendVo) {
		return sqlSessionTemplate.insert("email.insertSend", emlSendVo);
	}

	@Override
	public int insertFile(EmlFileVo emlFileVo) {
		return sqlSessionTemplate.insert("email.insertFile", emlFileVo);
	}

	@Override
	public int insertReceive(EmlReceiveVo emlReceiveVo) {
		return sqlSessionTemplate.insert("email.insertReceive", emlReceiveVo);
	}

	@Override
	public List<EmlSendVo> selectSendList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("email.selectSendList", map);
	}

	@Override
	public List<EmlFileVo> selectFileList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("email.selectFileList", map);
	}

	@Override
	public List<EmlReceiveVo> selectReceiveList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("email.selectReceiveList", map);
	}

	@Override
	public int updateSend(EmlSendVo emlSendVo) {
		return sqlSessionTemplate.update("email.updateSend", emlSendVo);
	}

	@Override
	public int updateReceive(EmlReceiveVo emlReceiveVo) {
		return sqlSessionTemplate.update("email.updateReceive", emlReceiveVo);
	}

}