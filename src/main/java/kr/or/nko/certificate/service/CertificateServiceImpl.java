package kr.or.nko.certificate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.nko.certificate.dao.ICertificateDao;
import kr.or.nko.certificate.model.CertificateDivVo;
import kr.or.nko.certificate.model.CertificateVo;

@Service("certificateService")
public class CertificateServiceImpl implements ICertificateService{
	
	@Resource(name="certificateDao")
	private ICertificateDao certificateDao;

	@Override
	public Map<String, Object> selectCertificatePaging_Search(Map<String, Object> map,CertificateVo certificateVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("CrtPagingList", certificateDao.selectCertificatePaging_Search(map));
		resultMap.put("CrtCnt",certificateDao.getCrtCnt());
		resultMap.put("SearchCrtCnt", certificateDao.getSearchCrtCnt(certificateVo));
		
		return resultMap;
	}

	@Override
	public CertificateVo select_Crtdetail(int crt_sq) {
		CertificateVo certificateVo = certificateDao.select_Crtdetail(crt_sq);
		return certificateVo;
	}

	@Override
	public int insertCrt(CertificateVo certificateVo) {
		int insert = certificateDao.insertCrt(certificateVo);
		return insert;
	}

	@Override
	public int updateCrt(CertificateVo certificateVo) {
		int update = certificateDao.updateCrt(certificateVo);
		return update;
	}

	@Override
	public List<CertificateDivVo> selectCrtDivList() {
		List<CertificateDivVo> crtDivList = certificateDao.selectCrtDivList();
		return crtDivList;
	}
}
