package kr.or.nko.certificate.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.certificate.model.CertificateDivVo;
import kr.or.nko.certificate.model.CertificateVo;

public interface ICertificateService {
	
	// 검색한 증명서리스트 페이징
	public Map<String, Object> selectCertificatePaging_Search(Map<String, Object> map,CertificateVo certificateVo);

    // 증명서 상세보기(증명서 번호를 파라미터로)
	CertificateVo select_Crtdetail(int crt_sq);
	
	// 증명서 추가
	int insertCrt(CertificateVo certificateVo);
	
	// 증명서 업뎃
	int updateCrt(CertificateVo certificateVo);
	
	
	// 증명서 구분번호 리스트뽑기
	public List<CertificateDivVo> selectCrtDivList();
}
