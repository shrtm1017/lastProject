package kr.or.nko.notice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.util.model.PageVo;
@Repository("noticeDao")
public class NoticeDaoImpl implements INoticeDao{
@Resource(name="sqlSessionTemplate")
private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<NoticeVo> NoticeList(Map<String, Object> map) {
		List<NoticeVo> noticeList =sqlSessionTemplate.selectList("notice.getallNoticeList",map);
		return noticeList ;
	}
	@Override
	public int NoticeCnt(int ntc_div) {
		int noticeCnt = sqlSessionTemplate.selectOne("notice.NoticeListCnt",ntc_div);
		return noticeCnt;
	}
	@Override
	public List<NoticeVo> Notice_divNo() {
		List<NoticeVo>  Notice_divNo =sqlSessionTemplate.selectList("notice.notice_divNo");
		return Notice_divNo;
	}
	@Override
	public int Notice_Register(NoticeVo noticeVo) {
		int notice_Insert = sqlSessionTemplate.insert("notice.notice_Register",noticeVo);
		return notice_Insert;
	}
	@Override
	public int NoticeUpdate(NoticeVo noticeVo) {
		int notice_Update =sqlSessionTemplate.update("notice.updateNotice",noticeVo);
		return notice_Update;
	}
	@Override
	public int NoticeDelete(int ntc_Sq) {
		int notice_Delete = sqlSessionTemplate.delete("notice.deleteNotice",ntc_Sq);
		return notice_Delete;
	}
	@Override
	public NoticeVo NoticeSelect(int ntc_Sq) {
		NoticeVo notice_Select = sqlSessionTemplate.selectOne("notice.selectNotice",ntc_Sq);
		return notice_Select;
	}
	@Override
	public int NoticeHitUpdate(int ntc_Hits) {
		int NoticeHitUpdate = sqlSessionTemplate.update("notice.updateHits",ntc_Hits);
		return NoticeHitUpdate;
	}
	@Override
	public List<NoticeVo> Notice_Serch(Map<String, Object> map) {
		List<NoticeVo> noticeList =sqlSessionTemplate.selectList("notice.selectNoticeList",map);
		return noticeList ;
	}
	@Override
	public int NoticeSerchCnt(NoticeVo noticeVo) {
		int NoticeSerchCnt = sqlSessionTemplate.selectOne("notice.selectNoticeListCnt",noticeVo);
		return NoticeSerchCnt;
	}
	@Override
	public List<NoticeVo> selectMainNotice(int ntc_div) {
		List<NoticeVo> noticeList =sqlSessionTemplate.selectList("notice.selectMainNotice",ntc_div);
		return noticeList ;
	}

}
