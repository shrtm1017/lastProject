package kr.or.nko.email.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.model.EmlManyVo;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.model.EmlSendVo;
import kr.or.nko.email.model.EmlTempFileVo;
import kr.or.nko.email.model.EmlTempVo;

public interface IEmailDao {
	
	/**
	 * Method : insertTemp
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlTempVo
	 * @return
	 * Method 설명 : 임시메일 등록
	 */
	public int insertTemp(EmlTempVo emlTempVo);
	
	/**
	 * Method : insertTempFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlTempFileVo
	 * @return
	 * Method 설명 : 임시메일파일 등록
	 */
	public int insertTempFile(EmlTempFileVo emlTempFileVo);
	
	/**
	 * Method : selectTempFileList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param eml_temp_sq
	 * @return
	 * Method 설명 : 해당임시메일 임시파일 조회
	 */
	public List<EmlTempFileVo> selectTempFileList(int eml_temp_sq);
	
	/**
	 * Method : deleteTemp
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param eml_temp_sq
	 * @return
	 * Method 설명 : 임시메일 삭제
	 */
	public int deleteTemp(int eml_temp_sq);
	
	/**
	 * Method : deleteTempFiles
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param eml_temp_sq
	 * @return
	 * Method 설명 : 임시메일 해당 임시파일 삭제
	 */
	public int deleteTempFiles(int eml_temp_sq);
	
	/**
	 * Method : deleteTempFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param eml_temp_fl_sq
	 * @return
	 * Method 설명 : 임시파일 삭제
	 */
	public int deleteTempFile(int eml_temp_fl_sq);
	
	/**
	 * Method : selectTempFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param eml_temp_fl_sq
	 * @return
	 * Method 설명 : 임시파일번호로 업무첨부파일 조회
	 */
	public EmlTempFileVo selectTempFile(int eml_temp_fl_sq);
	
	/**
	 * Method : selectTempList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlTempVo
	 * @return
	 * Method 설명 : 임시메일 조회
	 */
	public List<EmlTempVo> selectTempList(EmlTempVo emlTempVo);

	/**
	 * Method : updateTemp
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlTempVo
	 * @return
	 * Method 설명 : 임시메일 수정
	 */
	public int updateTemp(EmlTempVo emlTempVo);
	
	/**
	 * Method : insertSend
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlSendVo
	 * @return
	 * Method 설명 : 보낸메일 등록
	 */
	public int insertSend(EmlSendVo emlSendVo);
	
	/**
	 * Method : insertFile
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlFileVo
	 * @return
	 * Method 설명 : 파일함 등록
	 */
	public int insertFile(EmlFileVo emlFileVo);
	
	/**
	 * Method : insertReceive
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlReceiveVo
	 * @return
	 * Method 설명 : 받은메일 등록
	 */
	public int insertReceive(EmlReceiveVo emlReceiveVo);
	
	/**
	 * Method : selectSendList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 보낸메일리스트 조회
	 */
	public List<EmlSendVo> selectSendList(Map<String, Object> map);
	
	/**
	 * Method : selectFileList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 파일함 리스트 조회
	 */
	public List<EmlFileVo> selectFileList(Map<String, Object> map);
	
	/**
	 * Method : selectReceiveList
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param map
	 * @return
	 * Method 설명 : 받은메일 리스트 조회
	 */
	public List<EmlReceiveVo> selectReceiveList(Map<String, Object> map);
	
	/**
	 * Method : updateSend
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlSendVo
	 * @return
	 * Method 설명 : 보낸메일 중요표시, 삭제여부 수정
	 */
	public int updateSend(EmlSendVo emlSendVo);
	
	/**
	 * Method : updateReceive
	 * 작성자 : pc11
	 * 변경이력 :
	 * @param emlReceiveVo
	 * @return
	 * Method 설명 : 받은메일 중요표시, 읽은표시, 삭제여부 수정
	 */
	public int updateReceive(EmlReceiveVo emlReceiveVo);
}