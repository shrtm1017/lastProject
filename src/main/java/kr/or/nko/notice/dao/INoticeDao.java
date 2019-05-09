package kr.or.nko.notice.dao;

import java.util.List;
import java.util.Map;

import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.util.model.PageVo;

public interface INoticeDao {
	public List<NoticeVo> NoticeList(Map<String, Object> map);
	public List<NoticeVo> Notice_divNo();
	public List<NoticeVo> Notice_Serch(Map<String, Object> map);
	public List<NoticeVo> selectMainNotice(int ntc_div);
	public int Notice_Register(NoticeVo noticeVo);
	public int NoticeCnt(int ntc_div);
	public int NoticeSerchCnt(NoticeVo noticeVo);
	public int NoticeUpdate(NoticeVo noticeVo);
	public int NoticeDelete(int ntc_Sq);
	public int NoticeHitUpdate(int ntc_Sq);
	public NoticeVo NoticeSelect(int ntc_Hits);
}
