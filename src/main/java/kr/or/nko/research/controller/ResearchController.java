package kr.or.nko.research.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.research.model.AnswerVo;
import kr.or.nko.research.model.AttendVo;
import kr.or.nko.research.model.QuestionVo;
import kr.or.nko.research.model.ResearchVo;
import kr.or.nko.research.service.IResearchService;
import kr.or.nko.util.model.PageVo;

@Controller
public class ResearchController {

	private Logger logger = LoggerFactory.getLogger(ResearchController.class);
	
	@Resource(name="researchService")
	private IResearchService researchService;
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@RequestMapping("/researchList")
	public String researchListView(@RequestParam(name="page", defaultValue="1")int page,
								   @RequestParam(name="pageSize", defaultValue="10")int pageSize,
								   Model model) {
		PageVo pageVo = new PageVo(page, pageSize);

		Map<String, Object> resultMap = researchService.selectResearchPaging(pageVo);
		List<ResearchVo> researchList = (List<ResearchVo>) resultMap.get("researchList");
		int researchCnt = (Integer) resultMap.get("researchCnt");
		
		model.addAttribute("researchList", researchList);
		model.addAttribute("researchCnt", researchCnt);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", page);
		
		return "researchList";
	}
	
	@RequestMapping(path="/researchInsert", method=RequestMethod.GET)
	public String researchInsertView() {
		return "researchInsert";
	}
	
	@RequestMapping(path="/researchInsert", method=RequestMethod.POST)
	public String researchInsertProcess(ResearchVo researchVo,
										@RequestParam("question")List<String> questionList,
										@RequestParam("option")List<String> optionList,
										HttpSession session,
										RedirectAttributes ra) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		researchVo.setRes_emp_sq(employeeVo.getEmp_sq());
		
		int researchCnt = researchService.insertResearch(researchVo);
		
		if (researchCnt == 1) {
			int res_sq = researchVo.getRes_sq();
			
			for (int i = 0; i < questionList.size(); i++) {
				QuestionVo questionVo = new QuestionVo();
				String que_num = questionList.get(i).split("_")[0];
				String que_con = questionList.get(i).split("_")[1];
				
				questionVo.setQue_res_sq(res_sq);
				questionVo.setQue_con(que_con);
				
				int questionCnt = researchService.insertQuestion(questionVo);
				
				if (questionCnt == 1) {
					int que_sq = questionVo.getQue_sq();
					
					for (int j = 0; j < optionList.size(); j++) {
						String[] option = optionList.get(j).split("_");
						
						if (que_num.equals(option[0])) {
							AnswerVo answerVo = new AnswerVo();
							answerVo.setAns_que_sq(que_sq);
							answerVo.setAns_con(option[1]);
							
							researchService.insertAnswer(answerVo);
						}
					}
				}
			}
			
			ra.addFlashAttribute("msg", "정상 등록되었습니다.");
			return "redirect:/researchList";
		}
		
		else {
			ra.addFlashAttribute("msg", "설문지가 등록되지 않았습니다. 다시 시도해 주세요.");
			return "redirect:/researchInsert";
		}
	}
	
	@RequestMapping(path="/researchUpdate", method=RequestMethod.GET)
	public String researchUpdateView(@RequestParam("res_sq")int res_sq,
									 Model model) {
		ResearchVo resVo = researchService.selectResearch(res_sq);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String end_dt = sdf.format(resVo.getRes_end_dt());
		
		List<QuestionVo> queList = researchService.selectQuestion(res_sq);
		List<AnswerVo> ansList = new ArrayList<AnswerVo>();
		
		for (int i = 0; i < queList.size(); i++) {
			List<AnswerVo> tempList = researchService.selectAnswer(queList.get(i).getQue_sq());
			
			for (int j = 0; j < tempList.size(); j++) {
				ansList.add(tempList.get(j));
			}
		}
		
		model.addAttribute("resVo", resVo);
		model.addAttribute("end_dt", end_dt);
		model.addAttribute("queList", queList);
		model.addAttribute("ansList", ansList);
		return "researchUpdate";
	}
	
	@RequestMapping(path="/researchUpdate", method=RequestMethod.POST)
	public String researchUpdateProcess(ResearchVo researchVo,
										@RequestParam(name="question", defaultValue="")List<String> questionList,
										@RequestParam(name="option", defaultValue="")List<String> optionList,
										HttpSession session,
										RedirectAttributes ra) {
		int researchCnt = researchService.updateResearch(researchVo);
		
		if (researchCnt == 1 && questionList != null && optionList != null) {
			for (int i = 0; i < questionList.size(); i++) {
				QuestionVo questionVo = new QuestionVo();
				String que_num = questionList.get(i).split("_")[0];
				String que_con = questionList.get(i).split("_")[1];
				
				questionVo.setQue_res_sq(researchVo.getRes_sq());
				questionVo.setQue_con(que_con);
				
				int questionCnt = researchService.insertQuestion(questionVo);
				
				if (questionCnt == 1) {
					int que_sq = questionVo.getQue_sq();
					
					for (int j = 0; j < optionList.size(); j++) {
						String[] option = optionList.get(j).split("_");
						
						if (que_num.equals(option[0])) {
							AnswerVo answerVo = new AnswerVo();
							answerVo.setAns_que_sq(que_sq);
							answerVo.setAns_con(option[1]);
							
							researchService.insertAnswer(answerVo);
						}
					}
				}
			}
			
			ra.addFlashAttribute("msg", "정상 수정되었습니다.");
			ra.addAttribute("res_sq", researchVo.getRes_sq());
			return "redirect:/researchDetail";
		}
		
		else {
			ra.addFlashAttribute("msg", "설문지가 수정되지 않았습니다. 다시 시도해 주세요.");
			ra.addAttribute("res_sq", researchVo.getRes_sq());
			return "redirect:/researchUpdate";
		}
	}
	
	@RequestMapping(path="/researchDetail", method=RequestMethod.GET)
	public String researchDetailView(@RequestParam("res_sq")int res_sq,
									 HttpSession session,
									 Model model) {
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		// 조회수 업데이트
		researchService.updateHits(res_sq);
		
		// 설문조사 정보 조회
		ResearchVo resVo = researchService.selectResearch(res_sq);
		EmployeeVo empVo = employeeService.selectEmployee(resVo.getRes_emp_sq());
		
		List<QuestionVo> queList = researchService.selectQuestion(res_sq);
		List<AnswerVo> ansList = new ArrayList<AnswerVo>();
		
		for (int i = 0; i < queList.size(); i++) {
			List<AnswerVo> tempList = researchService.selectAnswer(queList.get(i).getQue_sq());
			
			for (int j = 0; j < tempList.size(); j++) {
				ansList.add(tempList.get(j));
			}
		}
		
		// 설문조사 참여 여부 조회
		List<AttendVo> attendList = researchService.selectAttend(employeeVo.getEmp_sq());
		int attCnt = 0;
		if (attendList != null) {
			for (int i = 0; i < attendList.size(); i++) {
				for (int j = 0; j < queList.size(); j++) {
					if (attendList.get(i).getAtt_que_sq() == queList.get(j).getQue_sq()) {
						attCnt++;
					}
				}
			}
		}
		
		int attend = 0;
		if (attCnt > 0) {
			attend = 1;
		}
		
		List<AnswerVo> resultList = researchService.selectResult();
		
		logger.debug("resultList : {}", resultList);
		
		model.addAttribute("employeeVo", employeeVo);
		model.addAttribute("resVo", resVo);
		model.addAttribute("queList", queList);
		model.addAttribute("ansList", ansList);
		model.addAttribute("res_emp_nm", empVo.getEmp_nm());
		model.addAttribute("attend", attend);
		model.addAttribute("resultList", resultList);
		return "researchDetail";
	}
	
	@RequestMapping(path="/researchDetail", method=RequestMethod.POST)
	public String researchDetailProcess(@RequestParam("option")List<String> optionList,
										@RequestParam("res_sq")int res_sq,
										HttpSession session,
										RedirectAttributes ra) {
		EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		int cnt = 0;
		
		for (int i = 0; i < optionList.size(); i++) {
			int que_sq = Integer.parseInt(optionList.get(i).split("_")[0]);
			int ans_sq = Integer.parseInt(optionList.get(i).split("_")[1]);
			
			int updCnt = researchService.updatePeople(ans_sq);
			
			if (updCnt == 1) {
				AttendVo attendVo = new AttendVo();
				attendVo.setAtt_que_sq(que_sq);
				attendVo.setAtt_ans_sq(ans_sq);
				attendVo.setAtt_emp_sq(empVo.getEmp_sq());

				cnt += researchService.insertAttend(attendVo);
			}
		}

		// 정상 제출
		if (cnt == optionList.size()) {
			ra.addFlashAttribute("msg", "설문조사에 참여해 주셔서 감사합니다.");
			ra.addAttribute("res_sq", res_sq);
			return "redirect:/researchDetail";
		}
		
		else {
			ra.addFlashAttribute("msg", "설문지가 제출되지 않았습니다. 다시 시도해 주세요.");
			ra.addAttribute("res_sq", res_sq);
			return "redirect:/researchDetail";
		}
	}
	
	@RequestMapping(path="/researchDelete", method=RequestMethod.POST)
	public String researchDeleteProcess(@RequestParam("res_sq")int res_sq,
										RedirectAttributes ra) {
		// 질문 리스트
		List<QuestionVo> queList = researchService.selectQuestion(res_sq);

		// 참여여부, 답변 테이블 데이터 삭제
		for (int i = 0 ; i < queList.size(); i++) {
			int que_sq = queList.get(i).getQue_sq();
			researchService.deleteAttend(que_sq);
			researchService.deleteAnswer(que_sq);
		}
		
		int queCnt = researchService.deleteQuestion(res_sq);
		int resCnt = 0;
		
		if (queCnt == queList.size()) {
			resCnt = researchService.deleteResearch(res_sq);
		}
		
		if (resCnt == 1) {
			ra.addFlashAttribute("msg", "정상 삭제되었습니다.");
			return "redirect:/researchList";
		}
		else {
			ra.addFlashAttribute("msg", "설문지가 삭제되지 않았습니다. 다시 시도해 주세요.");
			ra.addAttribute("res_sq", res_sq);
			return "redirect:/researchDetail";
		}
	}
	
	@RequestMapping("/researchResubmit")
	public String researchResubmitProcess(@RequestParam("res_sq")int res_sq,
										  HttpSession session,
										  RedirectAttributes ra) {
		EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
		// 질문 리스트
		List<QuestionVo> queList = researchService.selectQuestion(res_sq);
		List<AttendVo> attList = researchService.selectAttend(empVo.getEmp_sq());
		
		// 참여여부, 답변 테이블 데이터 삭제
		AttendVo attendVo = new AttendVo();
		attendVo.setAtt_emp_sq(empVo.getEmp_sq());
		
		for (int i = 0 ; i < queList.size(); i++) {
			int que_sq = queList.get(i).getQue_sq();
			attendVo.setAtt_que_sq(que_sq);
			
			for (int j = 0; j < attList.size(); j++) {
				if (attList.get(j).getAtt_que_sq() == que_sq) {
					researchService.updPeopleResubmit(attList.get(j).getAtt_ans_sq());
				}
			}

			researchService.delAttendResubmit(attendVo);
		}
		
		ra.addAttribute("res_sq", res_sq);
		return "redirect:/researchDetail";
	}
	
	@RequestMapping("/researchSearch")
	public String researchSearchProcess(@RequestParam("condition")String condition,
										@RequestParam("keyword")String keyword,
										@RequestParam(name="page", defaultValue="1")int page,
										@RequestParam(name="pageSize", defaultValue="10")int pageSize,
										Model model) {
		PageVo pageVo = new PageVo(page, pageSize);
		
		if (condition.equals("t")) {
			pageVo.setRes_nm(keyword);
		} else if (condition.equals("s")) {
			pageVo.setRes_state(keyword);
		}

		Map<String, Object> resultMap = researchService.selectResearchPagingSearch(pageVo);
		List<ResearchVo> researchList = (List<ResearchVo>) resultMap.get("researchList");
		int researchCnt = (Integer) resultMap.get("researchCnt");
		
		model.addAttribute("researchList", researchList);
		model.addAttribute("researchCnt", researchCnt);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", page);
		
		model.addAttribute("condition", condition);
		model.addAttribute("keyword", keyword);
		
		return "researchList";
	}

	// 마감일 체크
	@Scheduled(cron="5 * * * * *")
	public void researchTask() {
		List<ResearchVo> resList = researchService.getAllResearch();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		String deadline = null;
		
		for (int i = 0; i < resList.size(); i++) {
			deadline = sdf.format(resList.get(i).getRes_end_dt());
			
			int compare = today.compareTo(deadline);
			
			if (compare > 0 && resList.get(i).getRes_state().equals("1")) {
				int cnt = researchService.updateResearchState(resList.get(i).getRes_sq());
				
				if (cnt == 1) {
					logger.debug("res_state update : {}", resList.get(i).getRes_sq());
				}
			}
		}
	}
	
}
