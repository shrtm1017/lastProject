package kr.or.nko.main;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.service.AnnualService;
import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.approval.service.IApprovalService;
import kr.or.nko.cmt.model.CmtVo;
import kr.or.nko.cmt.service.ICommuteService;
import kr.or.nko.email.model.EmlReceiveVo;
import kr.or.nko.email.service.IEmailService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.notice.model.NoticeVo;
import kr.or.nko.notice.service.INoticeService;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.research.service.IResearchService;

@Controller
public class MainController {
	
	@Resource(name = "emailService")
	private IEmailService emailService;
	@Resource(name = "cmtService")
	private ICommuteService commuteService;
	@Resource(name = "approvalService")
	private IApprovalService approvalService;
	@Resource(name = "annualService")
	private AnnualService annualService;
	@Resource(name = "noticeService")
	private INoticeService noticeService;
	@Resource(name = "researchService")
	private IResearchService researchService;
	
	@RequestMapping("/main")
	public String viewMain(HttpSession session,CmtVo cmtVo,Model model) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		// 받은 메일 (읽지 않은 메일 모델에 추가)
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("eml_emp_rec_sq", employeeVo.getEmp_sq());
		paramMap.put("eml_rec_chk", "N");
		List<EmlReceiveVo> emlReceiveList = emailService.selectReceiveList(paramMap);
		model.addAttribute("emlReceiveList", emlReceiveList);
		
		// 근태
		cmtVo.setCmt_emp_sq(employeeVo.getEmp_sq());
		model.addAttribute("commuteCheck", commuteService.selectCmtCheck(cmtVo));
		
		// 사용일, 남은일 추가
		GiveAnnualVo vo = new GiveAnnualVo();
		int emp_sq = employeeVo.getEmp_sq();
		int anu_sqMax =annualService.SelectGiveYYYY(emp_sq);
		
		vo.setAnu_emp_sq(emp_sq);
		vo.setAnu_sq(anu_sqMax);
		
		
		double useDay = annualService.selectUseAnu(emp_sq);
		double  remainsDay= annualService.selectGiveday(vo);
		
		model.addAttribute("useDay", useDay);	
		model.addAttribute("remainsDay", remainsDay);	
		
		// 공지사항 추가
		int ntc_div = 1;
		List<NoticeVo>noticeList =noticeService.selectMainNotice(ntc_div);
		model.addAttribute("noticeList", noticeList);
		session.setAttribute("board_div", ntc_div);
		
		// todayDate 추가
		Date today = new Date();
		model.addAttribute("today", today);
		
		// 결재현황
		ApprovalEleVo apvEleVo = new ApprovalEleVo();
		apvEleVo.setApv_emp_sq(employeeVo.getEmp_sq());
		
		apvEleVo.setApv_state("1");
		int waitCnt = approvalService.getApvStateCnt(apvEleVo);

		apvEleVo.setApv_state("3");
		int endCnt = approvalService.getApvStateCnt(apvEleVo);
		
		apvEleVo.setApv_state("4");
		int returnCnt = approvalService.getApvStateCnt(apvEleVo);
		
		model.addAttribute("waitCnt", waitCnt);		// 대기문서
		model.addAttribute("endCnt", endCnt);		// 종결문서
		model.addAttribute("returnCnt", returnCnt);	// 반려문서
		
		// 전자결재 알림 수 조회
		List<SignalVo> signalList = approvalService.selectSignal(employeeVo.getEmp_sq());
		session.setAttribute("signalSize", signalList.size());
		session.setAttribute("signalList", signalList);
		
		// 진행 중인 설문
		List<ResearchVo> resList = researchService.getOngoingResearch();
		model.addAttribute("resList", resList);
		
		return "main";
	}
	
}
