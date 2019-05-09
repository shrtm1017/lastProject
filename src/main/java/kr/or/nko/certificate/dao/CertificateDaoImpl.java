package kr.or.nko.certificate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.certificate.model.CertificateDivVo;
import kr.or.nko.certificate.model.CertificateVo;

@Repository("certificateDao")
public class CertificateDaoImpl implements ICertificateDao{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<CertificateVo> selectCertificatePaging_Search(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("certificate.selectCertificatePaging_Search", map);
	}

	@Override
	public int getCrtCnt() {
		int cnt = sqlSessionTemplate.selectOne("certificate.getCrtCnt");
		return cnt;
	}

	@Override
	public int getSearchCrtCnt(CertificateVo certificateVo) {
		int SearchCnt = sqlSessionTemplate.selectOne("certificate.getSearchCrtCnt",certificateVo);
		return SearchCnt;
	}

	@Override
	public CertificateVo select_Crtdetail(int crt_sq) {
		CertificateVo certificateVo = sqlSessionTemplate.selectOne("certificate.selectCrtDetail", crt_sq);
		return certificateVo;
	}

	@Override
	public int insertCrt(CertificateVo certificateVo) {
		int insert = sqlSessionTemplate.insert("certificate.insertCertificate",certificateVo);
		return insert;
	}

	@Override
	public int updateCrt(CertificateVo certificateVo) {
		int update = sqlSessionTemplate.update("certificate.updateCertificate", certificateVo);
		return update;
	}

	@Override
	public List<CertificateDivVo> selectCrtDivList() {
			List<CertificateDivVo> crtDivList = sqlSessionTemplate.selectList("certificate.selectCrtDivList");
		return crtDivList;
	}
	
}
