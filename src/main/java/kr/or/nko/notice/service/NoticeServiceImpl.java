package kr.or.nko.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.nko.notice.dao.INoticeDao;
import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.util.model.PageVo;

@Service("noticeService")
public class NoticeServiceImpl implements INoticeService {
	@Resource(name = "noticeDao")
	private INoticeDao noticeDao;
private Logger logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	@Override
	public Map<String, Object> getAllNoticeList(NoticeVo noticeVo,PageVo pagevo) {
		Map<String, Object> getAllNoticeList = new HashMap<String, Object>();
		int ntc_div =pagevo.getNtc_div();
		getAllNoticeList.put("ntc_div", pagevo.getNtc_div());
		getAllNoticeList.put("page", pagevo.getPage());
		getAllNoticeList.put("pageSize", pagevo.getPageSize());
		getAllNoticeList.put("NoticeCnt", noticeDao.NoticeCnt(ntc_div));
		
		logger.debug("noticeDiv : {}", pagevo.getNtc_div());
		logger.debug("noticeCnt : {}", noticeDao.NoticeCnt(ntc_div));
		logger.debug("pageSize : {}", pagevo.getPageSize());
		
		getAllNoticeList.put("NoticeList", noticeDao.NoticeList(getAllNoticeList));
		return getAllNoticeList;
	}

	@Override
	public List<NoticeVo> Notice_divNo() {
		List<NoticeVo> Notice_divNo = noticeDao.Notice_divNo();
		return Notice_divNo;
	}

	@Override
	public int Notice_Register(NoticeVo noticeVo) {
		int notice_insert = noticeDao.Notice_Register(noticeVo);
		return notice_insert;
	}

	@Override
	public int NoticeUpdate(NoticeVo noticeVo) {
		int notice_Update = noticeDao.NoticeUpdate(noticeVo);
		return notice_Update;
	}

	@Override
	public int NoticeDelete(int ntc_Sq) {
		int notice_Delete = noticeDao.NoticeDelete(ntc_Sq);
		return notice_Delete;
	}

	@Override
	public NoticeVo NoticeSelect(int ntc_Sq) {
		NoticeVo notice_Select = noticeDao.NoticeSelect(ntc_Sq);
		return notice_Select;
	}

	@Override
	public int NoticeHitUpdate(int ntc_Sq) {
		int NoticeHitUpdate = noticeDao.NoticeHitUpdate(ntc_Sq);
		return NoticeHitUpdate;
	}

	@Override
	public Map<String, Object> NoticeSerch(NoticeVo noticeVo, PageVo pagevo) {
		Map<String, Object> NoticeSerchList = new HashMap<String, Object>();
		NoticeSerchList.put("page", pagevo.getPage());
		NoticeSerchList.put("pageSize", pagevo.getPageSize());
		NoticeSerchList.put("ntc_nm", noticeVo.getNtc_nm());
		NoticeSerchList.put("ntc_emp_sq", noticeVo.getNtc_emp_sq());
		logger.debug(noticeVo.getNtc_emp_sq()+"@@");
		NoticeSerchList.put("ntc_div", pagevo.getNtc_div());
		NoticeSerchList.put("NoticeSerchCnt", noticeDao.NoticeSerchCnt(noticeVo));
		NoticeSerchList.put("NoticeList", noticeDao.Notice_Serch(NoticeSerchList));
		
		return NoticeSerchList;
	}

	@Override
	public List<NoticeVo> selectMainNotice(int ntc_div) {
		List<NoticeVo> selectMainNotice = noticeDao.selectMainNotice(ntc_div);
		return selectMainNotice;
	}

}
