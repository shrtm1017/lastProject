package kr.or.nko.certificate.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.certificate.model.CertificateDivVo;
import kr.or.nko.certificate.model.CertificateVo;

public interface ICertificateDao {
	
	
	
	// 검색한 증명서리스트 페이징
	public List<CertificateVo> selectCertificatePaging_Search(Map<String, Object> map);
	
	// 증명서 전체 수 조회
	int getCrtCnt();
		
	// 검색한 증명서 수
	public int getSearchCrtCnt(CertificateVo certificateVo);
	
    // 증명서 상세보기(증명서 번호를 파라미터로)
	CertificateVo select_Crtdetail(int crt_sq);
	
	// 증명서 추가
	int insertCrt(CertificateVo certificateVo);
	
	// 증명서 업뎃
	int updateCrt(CertificateVo certificateVo);
	
	// 증명서 삭제 (혹시몰라서 아직 안만듦)
	
	
	// 증명서 구분번호 리스트뽑기
	public List<CertificateDivVo> selectCrtDivList();
}
