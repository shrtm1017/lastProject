package kr.or.nko.notice.service;

import java.util.List;
import java.util.Map;

import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.util.model.PageVo;

public interface INoticeService {
	
	public Map<String,Object> getAllNoticeList(NoticeVo noticeVo,PageVo pagevo);
	public Map<String,Object> NoticeSerch(NoticeVo noticeVo,PageVo pagevo);
	public List<NoticeVo> Notice_divNo();
	public List<NoticeVo> selectMainNotice(int ntc_div);
	public int Notice_Register(NoticeVo noticeVo);
	public int NoticeUpdate(NoticeVo noticeVo);
	public int NoticeDelete(int ntc_Sq);
	public int NoticeHitUpdate(int ntc_Sq);
	public NoticeVo NoticeSelect(int ntc_Sq);
}
