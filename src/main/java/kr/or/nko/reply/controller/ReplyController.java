package kr.or.nko.reply.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.reply.model.ReplyVo;
import kr.or.nko.reply.service.IReplyService;

@Controller
public class ReplyController {
	
	private Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Resource(name="replyService")
	private IReplyService replyService;
	//게시글 댓글 등록
	@RequestMapping(path={"/replyInsert"} ,method={RequestMethod.POST})
	public String reply_insert(ReplyVo replyVo,HttpSession session){
		NoticeVo noticeVo= (NoticeVo) session.getAttribute("NoticeVoSelect");
		
		logger.debug(replyVo.getRpy_con());
		EmployeeVo emp =(EmployeeVo) session.getAttribute("employeeVo");
		replyVo.setRpy_emp_sq(emp.getEmp_sq());
		int reply_insert =replyService.reply_insert(replyVo);
		return "redirect:detail?ntc_sq="+noticeVo.getNtc_sq()+"&ntc_div="+noticeVo.getNtc_div();
	}
	//게시글 댓글에 대한 답글 등록
	@RequestMapping(path={"/reply_comment"} ,method={RequestMethod.POST})
	public String reply_comment_insert(ReplyVo replyVo,HttpSession session,@RequestParam("rpy_comment")String rpy_comment){
		NoticeVo noticeVo= (NoticeVo) session.getAttribute("NoticeVoSelect");
		
		EmployeeVo emp =(EmployeeVo) session.getAttribute("employeeVo");
		replyVo.setRpy_emp_sq(emp.getEmp_sq());
		replyVo.setRpy_parent_sq(replyVo.getRpy_sq());
		logger.debug(rpy_comment+"@@");
		replyVo.setRpy_con(rpy_comment);
		int reply_insert =replyService.reply_insert(replyVo);
		return "redirect:detail?ntc_sq="+noticeVo.getNtc_sq()+"&ntc_div="+noticeVo.getNtc_div();
	}
	//게시글 삭제
	@RequestMapping(path={"/delete_reply"},method={RequestMethod.POST})
	public String delete_reply(ReplyVo replyVo, HttpSession session){
		
		NoticeVo noticeVo= (NoticeVo) session.getAttribute("NoticeVoSelect");
		
		int delete_reply =replyService.delete_reply(replyVo);
		if(delete_reply>0){
			
			return "redirect:detail?ntc_sq="+noticeVo.getNtc_sq()+"&ntc_div="+noticeVo.getNtc_div();
		}
		return "main";
		
	}
	
	

}
