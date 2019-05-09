package kr.or.nko.email.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.email.dao.IEmailDao;
import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.model.EmlSendVo;
import kr.or.nko.email.model.EmlTempFileVo;
import kr.or.nko.email.model.EmlTempVo;

@Service("emailService")
public class EmailServiceImpl implements IEmailService{
	
	@Resource(name="emailDao")
	private IEmailDao emailDao;

	@Override
	public int insertTemp(EmlTempVo emlTempVo) {
		return emailDao.insertTemp(emlTempVo);
	}

	@Override
	public int insertTempFile(EmlTempFileVo emlTempFileVo) {
		return emailDao.insertTempFile(emlTempFileVo);
	}

	@Override
	public List<EmlTempFileVo> selectTempFileList(int eml_temp_sq) {
		return emailDao.selectTempFileList(eml_temp_sq);
	}
	
	@Override
	public int deleteTemp(int eml_temp_sq) {
		return emailDao.deleteTemp(eml_temp_sq);
	}
	
	@Override
	public int deleteTempFiles(int eml_temp_sq) {
		return emailDao.deleteTempFiles(eml_temp_sq);
	}

	@Override
	public int deleteTempFile(int eml_temp_fl_sq) {
		return emailDao.deleteTempFile(eml_temp_fl_sq);
	}

	@Override
	public EmlTempFileVo selectTempFile(int eml_temp_fl_sq) {
		return emailDao.selectTempFile(eml_temp_fl_sq);
	}

	@Override
	public List<EmlTempVo> selectTempList(EmlTempVo emlTempVo) {
		return emailDao.selectTempList(emlTempVo);
	}

	@Override
	public int updateTemp(EmlTempVo emlTempVo) {
		return emailDao.updateTemp(emlTempVo);
	}

	@Override
	public int insertSend(EmlSendVo emlSendVo) {
		return emailDao.insertSend(emlSendVo);
	}
	
	@Override
	public int insertFile(EmlFileVo emlFileVo) {
		return emailDao.insertFile(emlFileVo);
	}

	@Override
	public int insertReceive(EmlReceiveVo emlReceiveVo) {
		return emailDao.insertReceive(emlReceiveVo);
	}

	@Override
	public List<EmlSendVo> selectSendList(Map<String, Object> map) {
		return emailDao.selectSendList(map);
	}

	@Override
	public List<EmlFileVo> selectFileList(Map<String, Object> map) {
		return emailDao.selectFileList(map);
	}

	@Override
	public List<EmlReceiveVo> selectReceiveList(Map<String, Object> map) {
		return emailDao.selectReceiveList(map);
	}

	@Override
	public int updateSend(EmlSendVo emlSendVo) {
		return emailDao.updateSend(emlSendVo);
	}

	@Override
	public int updateReceive(EmlReceiveVo emlReceiveVo) {
		return emailDao.updateReceive(emlReceiveVo);
	}

}