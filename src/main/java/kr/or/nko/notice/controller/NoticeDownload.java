package kr.or.nko.notice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.View;

import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.notice.service.INoticeService;

public class NoticeDownload implements View{
	
	@Resource(name="noticeService")
	private INoticeService noticeService;

	@Override
	public String getContentType() {
		return "image";
	}

	@Override
	public void render(Map<String, ?> arg0, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/octet-stram");
		
		HttpSession session = req.getSession();
		NoticeVo noticeVo =(NoticeVo) session.getAttribute("NoticeVoSelect");
		resp.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(noticeVo.getNtc_fl_nm(), "utf-8"));
		
		FileInputStream fis;
		//File.separator는 \, /같은 파일의 경로를 분리해주는 메소드
		String path = noticeVo.getNtc_fl_path()+File.separator;
		
		fis = new FileInputStream(new File(path));
		ServletOutputStream sos = resp.getOutputStream();
		byte[] buff = new byte[512]; //버퍼
		int len = 0; //읽은 갯수
		
		while((len = fis.read(buff)) > -1){
			sos.write(buff);
		}
		
		sos.close();
		fis.close();
		
	}


}
