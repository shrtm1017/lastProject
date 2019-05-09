package kr.or.nko.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.notice.service.INoticeService;
import kr.or.nko.reply.model.ReplyVo;
import kr.or.nko.reply.service.IReplyService;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;

@Controller
public class NoticeController {
	private Logger logger = LoggerFactory.getLogger(NoticeController.class);
	@Resource(name = "noticeService")
	private INoticeService noticeService;
	@Resource(name = "replyService")
	private IReplyService replyService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	

	@RequestMapping(path = { "/noticeBoard" }, method = { RequestMethod.GET })
	public String noticeListView(Model model, NoticeVo noticeVo, PageVo pageVo, HttpSession session) {
		System.out.println(noticeVo.getNtc_div() + "@@@@@");
		session.setAttribute("board_div", noticeVo.getNtc_div());
		
		Map<String, Object> resultMap = noticeService.getAllNoticeList(noticeVo,pageVo);
		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int noticeCnt = (int) resultMap.get("NoticeCnt");
		int lastPage = (noticeCnt / pageVo.getPageSize()) + (noticeCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("board_div",noticeVo.getNtc_div());
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		return "noticeBoard";

	}
	@RequestMapping(path = { "/notice_Serch" }, method = { RequestMethod.POST })
	public String notice_SerchView(Model model, 
								   @RequestParam("ntc_info")String ntc_info, 
								   PageVo pageVo, 
								   String selCode) {
		NoticeVo noticeVO = new NoticeVo();
		logger.debug(selCode);

		if(selCode.equals("1")){
			noticeVO.setNtc_nm(ntc_info);
		}else if(selCode.equals("2")){
		boolean check=	utilNKO.checkStrToInt(ntc_info);
		if(check){
			int Ntc_emp_sq=Integer.parseInt(ntc_info);
			noticeVO.setNtc_emp_sq(Ntc_emp_sq);
			
		}
		}
		Map<String, Object> resultMap = noticeService.NoticeSerch(noticeVO,pageVo);
		model.addAllAttributes(resultMap);

		int Page = pageVo.getPage();
		int noticeCnt = (int) resultMap.get("NoticeSerchCnt");
		int lastPage = (noticeCnt / pageVo.getPageSize()) + (noticeCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		return "noticeBoard";

	}
	//게시글 뷰
	@RequestMapping(path = { "/detail" }, method = { RequestMethod.GET })
	public String detailView(Model model, NoticeVo noticeVo, HttpSession session, PageVo pageVo, ReplyVo replyVo) {

		int result = 0;
		int rpy_ntc_sq = noticeVo.getNtc_sq();
		Integer rpy_ntc_num = noticeVo.getNtc_sq();
		//조회수
		noticeService.NoticeHitUpdate(noticeVo.getNtc_sq());
		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		List<ReplyVo> replyAllList = replyService.replyAllList(rpy_ntc_num);
		for (ReplyVo reply : replyAllList) {
			String level = reply.getRpy_level();
			logger.debug("level1 : {}", level);
			reply.setRpy_level(level);
			logger.debug("level2 : {}", reply.getRpy_level());
			result += replyService.updateLevel(reply);
		}
		

		NoticeVo NoticeVo = noticeService.NoticeSelect(noticeVo.getNtc_sq());
		Map<String, Object> map = replyService.replyPageingList(rpy_ntc_sq, pageVo);
		session.setAttribute("NoticeVoSelect", NoticeVo);
		
		logger.debug("@@##"+NoticeVo.getNtc_emp_sq());

		List<ReplyVo> replyList = (List<ReplyVo>) map.get("replyList");
		System.out.println();
		int replyCnt = (int) map.get("replyCnt");
		int Page = pageVo.getPage();
		int lastPage = (replyCnt / pageVo.getPageSize()) + (replyCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("NoticeVo", NoticeVo);
		model.addAttribute("emp_sq", EmployeeVo.getEmp_sq());
		model.addAttribute("replyList", replyList);
		model.addAttribute("rpy_ntc_sq", rpy_ntc_sq);
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);

		return "detail";

	}
	//게시글 및 파일 삭제
	@RequestMapping(path = { "/delete" }, method = { RequestMethod.GET })
	public String deleteView(Model model, NoticeVo noticeVo, HttpSession session, PageVo pageVo) {
		int ntc_div = (int) session.getAttribute("board_div");
		Map<String, Object> resultMap = noticeService.getAllNoticeList(noticeVo,pageVo);
		model.addAllAttributes(resultMap);
		int Page = pageVo.getPage();
		int noticeCnt = (int) resultMap.get("NoticeCnt");
		int lastPage = (noticeCnt / pageVo.getPageSize()) + (noticeCnt % pageVo.getPageSize() > 0 ? 1 : 0);
		int lastPageStartPage = ((lastPage - 1) / 10) * 10 + 1;
		int startPage = ((Page - 1) / 10) * 10 + 1;
		int endPage = startPage + 10 - 1;
		model.addAttribute("page", pageVo.getPage());
		model.addAttribute("pageSize", pageVo.getPageSize());
		model.addAttribute("lastPageStartPage", lastPageStartPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastpage", lastPage);
		String path = noticeVo.getNtc_fl_path();
		File file = new File(path);
		if (file.exists() == true) {
			file.delete();

		}
		int clear_reply = replyService.clear_reply(noticeVo.getNtc_sq());
		int NoticeVo = noticeService.NoticeDelete(noticeVo.getNtc_sq());

		if (NoticeVo > 0) {

			return "redirect:" + "/noticeBoard?ntc_div=" + ntc_div;
		}

		return "detail";
		// session.setAttribute("NoticeVoSelect", NoticeVo);

	}
	//게시글 수정 뷰
	@RequestMapping(path = { "/modify" }, method = { RequestMethod.GET })
	public String modifyView(Model model, NoticeVo noticeVo, HttpSession session) {
		NoticeVo NoticeVo = noticeService.NoticeSelect(noticeVo.getNtc_sq());
		model.addAttribute("noticeModify", NoticeVo);
		return "noticemodify";

	}
	//게시글 수정 로직
	@RequestMapping(path = { "/modify" }, method = { RequestMethod.POST })
	public String notice_modify(Model model, NoticeVo noticeVo, HttpSession session,
			@RequestPart("notice_file") MultipartFile file) throws Exception, IOException {

		int ntc_div = (int) session.getAttribute("board_div");
		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		String notice_realpath = "";
		String notice_nm = "";

		noticeVo.setNtc_div(ntc_div);
		noticeVo.setNtc_emp_sq(EmployeeVo.getEmp_sq());
		System.out.println("file.getSize() " + file.getSize());
		//게시글 수정시 파일 삭제뒤 생성
		if (file.getSize() > 0) {
			if (noticeVo.getNtc_fl_path() != null)
				notice_realpath = noticeVo.getNtc_fl_path();
			File file_add = new File(notice_realpath);
			if (file_add.exists()) {
				file_add.delete();
			}
			notice_nm = file.getOriginalFilename();
			notice_realpath = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			file.transferTo(new File(notice_realpath));
			noticeVo.setNtc_fl_nm(notice_nm);
			noticeVo.setNtc_fl_path(notice_realpath);

		} else if (file.getSize() == 0) {
			notice_nm = null;
			notice_realpath = null;
			noticeVo.setNtc_fl_nm(notice_nm);
			noticeVo.setNtc_fl_path(notice_realpath);
			System.out.println("else if" + notice_realpath);
		}

		noticeService.NoticeUpdate(noticeVo);
		return "redirect:" + "detail?ntc_sq=" + noticeVo.getNtc_sq();

	}

	@RequestMapping(path = { "/notice_register" }, method = { RequestMethod.GET })
	public String notice_registerView(Model model, NoticeVo noticeVo, HttpSession session) {
		session.setAttribute("ntc_div", noticeVo.getNtc_div());
		logger.debug(""+noticeVo.getNtc_div());
		return "noticeRegister";

	}

	// 게시글 등록 로직
	@RequestMapping(path = { "/notice_register" }, method = { RequestMethod.POST })
	public String notice_Register(NoticeVo noticeVo, HttpServletRequest req, HttpSession session, Model model,
			@RequestPart("notice_file") MultipartFile file) throws Exception {
		int ntc_div = (int) session.getAttribute("ntc_div");
		EmployeeVo EmployeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		String notice_realpath = "";
		String notice_nm = "";

		noticeVo.setNtc_div(ntc_div);
		noticeVo.setNtc_emp_sq(EmployeeVo.getEmp_sq());
		// System.out.println("");

		if (file.getSize() > 0) {
			notice_nm = file.getOriginalFilename();
			notice_realpath = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			file.transferTo(new File(notice_realpath));
		}

		noticeVo.setNtc_fl_nm(file.getOriginalFilename());
		noticeVo.setNtc_fl_path(notice_realpath);
		int notice_insert = noticeService.Notice_Register(noticeVo);

		if (notice_insert > 0) {
			return "redirect:" + "/noticeBoard?ntc_div=" + ntc_div;
		}
		return "main";

	}


	@RequestMapping("/NoticeDownload")
	public String noticeDowload() {
		return "NoticeDownload";

	}

	@RequestMapping("/cancle")
	public String cancle(Model model, NoticeVo noticeVo, HttpSession session) {
		NoticeVo NoticeVo = noticeService.NoticeSelect(noticeVo.getNtc_sq());
		model.addAttribute("NoticeVo", NoticeVo);
		return "detail";

	}
}
