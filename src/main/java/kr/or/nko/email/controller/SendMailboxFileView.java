package kr.or.nko.email.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import kr.or.nko.email.model.EmlFileVo;
import kr.or.nko.email.service.IEmailService;
import kr.or.nko.work.model.WorkFileVo;
import kr.or.nko.work.service.IWorkService;

public class SendMailboxFileView implements View{
	
	@Resource(name="emailService")
	private IEmailService emailService;

	@Override
	public String getContentType() {
		return "image";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//첨부파일 다운로드 받기위해선 설정해줘야함 - 필수!!!
		response.setHeader("Content-Disposition", "attachment; filename=file.png");
		response.setContentType("application/octet-stream");
		response.setContentType("image");
		
		//1.첨부파일번호 파라미터 확인
		String eml_fl_sq_str = (String) model.get("eml_fl_sq");
		Integer eml_fl_sq = Integer.parseInt(eml_fl_sq_str);
		
		//2.해당 첨부파일 번호로 첨부파일 정보 조회
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("eml_fl_sq", eml_fl_sq);
		EmlFileVo emlFileVo = emailService.selectFileList(map).get(0);
		
		//3-1.realFilename이 존재할 경우
		//3-1-1.해당 경로의 파일을 FileInputStream으로 읽는다
		FileInputStream fis;
		if(emlFileVo != null && emlFileVo.getEml_fl_path() != null){
			fis = new FileInputStream(new File(emlFileVo.getEml_fl_path()));
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(emlFileVo.getEml_fl_nm(), "UTF-8")); //URLEncoder.encode -> (다운로드시 한글설정)
		}
		
		//3-2.realFilename이 존재하지 않을 경우
		//3-2-1. /images/noimg.png (application.getRealPath())
		else{
			ServletContext application = request.getServletContext();
			String noimgPath = application.getRealPath("/images/noimg.png");
			fis = new FileInputStream(new File(noimgPath));
		}
		
		//4.FileInputStream을 response객체의 outputStream 객체에 write
		ServletOutputStream sos = response.getOutputStream();
		byte[] buff = new byte[512];
		int len = 0;
		while((len = fis.read(buff)) > -1){
			sos.write(buff);
		}
		
		sos.close();
		fis.close();
	}

}