package kr.or.nko.work.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nko.admin.service.IAuthorityService;
import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.util.UtilNKO;
import kr.or.nko.util.model.PageVo;
import kr.or.nko.work.model.ProjectMemVo;
import kr.or.nko.work.model.ProjectVo;
import kr.or.nko.work.model.WorkFileVo;
import kr.or.nko.work.model.WorkHistoryVo;
import kr.or.nko.work.model.WorkRelationVo;
import kr.or.nko.work.model.WorkVo;
import kr.or.nko.work.service.IWorkService;

@Controller
public class WorkController {
	
	private Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	@Resource(name="employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="departmentService")
	private IDepartmentService departmentService;
	
	@Resource(name="workService")
	private IWorkService workService;
	
	@Resource(name="authorityService")
	private IAuthorityService authorityService;
	
	@Resource(name="utilNKO")
	private UtilNKO utilNKO;
	
	@RequestMapping(path="/workManage", method=RequestMethod.GET)
	public String workManageView(Model model, HttpSession session, PageVo pageVo){
		//전체사원 조회후 session에 세팅(담당자 부여할때 필요)
		List<EmployeeVo> employeeList = employeeService.selectAllList();
		session.setAttribute("employeeList", employeeList);
		
		//tabCode 1번은 프로젝트tab
		model.addAttribute("tabCode", "1");
		//tabCode 1번은 프로젝트pane
		model.addAttribute("tabpaneCode", "1");
		
		//권한 확인
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		
		//권한 확인하여 프로젝트 리스트, 작업내역리스트를 세션에 추가
		utilNKO.projectCheckAuthority(employeeVo, session);
		
		//조건검색할 값을 parameter map에 세팅
		//검색조건이 담긴 paramMap을 session에서 찾은후 있으면 사용하고 없으면 진행중을 나타내는 상태를 넣어 검색
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(session.getAttribute("paramMap") == null){
			paramMap.put("selectState", 1);
		}else{
			paramMap = (Map<String, Object>) session.getAttribute("paramMap");
		}
		
		//조건검색에 따른 업무 페이징 리스트
		utilNKO.workPaging(paramMap, pageVo, session);
		
		return "workManage";
	}
	
	@RequestMapping(path="/projectInsert", method=RequestMethod.GET)
	public String projectInsertView(Model model){
		//부서 조회후(dpt_hr_sq가 null인 부서) model 객체에 추가
		List<DepartmentVo> departmentList = departmentService.selectDepartNull();
		model.addAttribute("departmentList", departmentList);
		
		return "projectInsert";
	}
	
	@RequestMapping(path="/projectInsert", method=RequestMethod.POST)
	public String projectInsert(@RequestParam Map<String, Object> map, Model model){
		//map으로 해당태그들의 name을 키값으로 값을 받아옴
		String projectName = (String) map.get("inputProjectName");
		String projectExplain = (String) map.get("inputExplain");
		String projectOpen = (String) map.get("inputOpen");
		//다수의 값은 ,로 구분되는 String형태로 오기때문에 ,로 분리해줘야함
		String projectMember = (String) map.get("selectMember");
		String[] projectMemberArray = projectMember.split(",");
		
		logger.debug("*******************************");
		logger.debug("/projectInsert -> projectName : {}", projectName);
		logger.debug("/projectInsert -> projectExplain : {}", projectExplain);
		logger.debug("/projectInsert -> projectOpen : {}", projectOpen);
		logger.debug("/projectInsert -> projectMember : {}", projectMember);
		logger.debug("/projectInsert -> projectMemberArray.length : {}", projectMemberArray.length);
		logger.debug("******************************");
		
		//등록할 프로젝트 정보 세팅후 insert
		ProjectVo projectVo = new ProjectVo();
		projectVo.setPro_nm(projectName);
		projectVo.setPro_exp(projectExplain);
		projectVo.setPro_open(projectOpen);
		
		int projectInsert = workService.insertProject(projectVo);
		
		//정상적으로 등록시 프로젝트멤버도 등록
		if(projectInsert == 1){
			int pro_sq = projectVo.getPro_sq();
			
			int result = 0;
			ProjectMemVo projectMemVo = new ProjectMemVo();
			//프로젝트 멤버등록
			for(String pro_mem : projectMemberArray){
				projectMemVo.setPro_sq(pro_sq);
				projectMemVo.setPro_mem(Integer.parseInt(pro_mem));
				
				result += workService.insertMember(projectMemVo);
			}
		}
		
		return "redirect:/workManage"; //리다이렉트시 ContextPathListener에 의해 contextPath주소가 자동으로 붙여짐 
	}
	
	@RequestMapping(path="/workSelect", method=RequestMethod.GET)
	public String workSelect(Model model){
		
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		return "workDetail";
	}
	
	@RequestMapping(path="/conditionSelect", method=RequestMethod.GET)
	public String conditionSelect(@RequestParam Map<String, Object> map, Model model, HttpSession session){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabpaneCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//검색조건
		String selectCondition = (String) map.get("selectCondition");
		
		//session에서 검색조건리스트(selConditionList)를 받아온후 있는지 없는지 체크한후
		//검색조건(selCondition) 추가후 session에 추가
		List<String> selConditionList = (List<String>) session.getAttribute("selConditionList");
		
		boolean isExist = false;
		if(selConditionList != null){
			for(int i=0; i<selConditionList.size(); i++){
				//새로고침시 계속 추가되는 것때문에 체크해줘야함(동일한 selCondition이 존재하지 않을경우 - isExist가 false일경우에만 추가)
				if(selConditionList.get(i).equals(selectCondition)){
					isExist = true;
				}
			}
			
			//selConditionList를 다 조회후 없을경우에만 추가
			if(!isExist){
				selConditionList.add(selectCondition);
			}
		}else{
			//검색조건리스트가 null이면 그냥 add
			selConditionList = new ArrayList<String>();
			selConditionList.add(selectCondition);
		}
		session.setAttribute("selConditionList", selConditionList);
		
		return "workManage";
	}
	
	@RequestMapping(path="/conditionWorkSelect", method=RequestMethod.GET)
	public String conditionWorkSelect(@RequestParam Map<String, Object> map, Model model, PageVo pageVo, HttpSession session){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//1-진행중, 2-완료됨, 3-모두
		String selectState = (String) map.get("selectState");
		
		//1-이다, 2-아니다
		String selectProjectState = (String) map.get("selectProjectState");
		//프로젝트 번호
		String pro_sq = (String) map.get("selectProject");
		
		//1-이다, 2-아니다
		String selectTypeState = (String) map.get("selectTypeState");
		//1-새기능, 2-결함, 3-공지, 4-지원
		String wk_type = (String) map.get("selectType");
		
		//1-이다, 2-아니다
		String selectChargerState = (String) map.get("selectChargerState");
		//담당자 사원번호
		String wk_emp_sq = (String) map.get("selectCharger");
		
		//검색조건 세션에 추가
		session.setAttribute("selectState", selectState);
		session.setAttribute("selectProjectState", selectProjectState);
		session.setAttribute("pro_sq", pro_sq);
		session.setAttribute("selectTypeState", selectTypeState);
		session.setAttribute("wk_type", wk_type);
		session.setAttribute("selectChargerState", selectChargerState);
		session.setAttribute("wk_emp_sq", wk_emp_sq);
		
		//조건검색할 값을 parameter map에 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("selectState", selectState);
		paramMap.put("selectProjectState", selectProjectState);
		paramMap.put("pro_sq", pro_sq);
		paramMap.put("selectTypeState", selectTypeState);
		paramMap.put("wk_type", wk_type);
		paramMap.put("selectChargerState", selectChargerState);
		paramMap.put("wk_emp_sq", wk_emp_sq);
		
		//조건검색 값 유지하기위해 session에 paramMap 세팅
		logger.debug("/conditionWorkSelect -> paramMap : {}", paramMap);
		session.setAttribute("paramMap", paramMap);
		
		//조건검색에 따른 업무 페이징 리스트
		utilNKO.workPaging(paramMap, pageVo, session);
		
		return "workManage";
	}
	
	@RequestMapping(path="/conditionDelete", method=RequestMethod.GET)
	public String conditionDelete(Model model, HttpSession session){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//session에서 검색조건리스트(selConditionList) 삭제
		session.removeAttribute("selConditionList");
		
		//검색조건 세션에서 삭제
		session.removeAttribute("selectState");
		session.removeAttribute("selectProjectState");
		session.removeAttribute("pro_sq");
		session.removeAttribute("selectTypeState");
		session.removeAttribute("wk_type");
		session.removeAttribute("selectChargerState");
		session.removeAttribute("wk_emp_sq");
		
		//조건검색할 값을 parameter map에 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("selectState", 1);
		
		//조건검색에 따른 업무 페이징 리스트
		utilNKO.workPaging(paramMap, new PageVo(), session);
		
		return "workManage";
	}
	
	@RequestMapping(path="/workInsert", method=RequestMethod.GET)
	public String workInsertView(HttpSession session){
		return "workInsert";
	}
	
	//ajax로 프로젝트 selectbox 선택시 해당프로젝트의 멤버 조회후 반환
	@RequestMapping(path="/ajaxRequestBody/projectMemberSelect", produces={"application/json"})
	@ResponseBody
	public List<EmployeeVo> projectMemberSelect(@RequestBody ProjectVo projectVo){
		logger.debug("/ajaxRequestBody/projectMemberSelect -> projectVo : {}", projectVo);
		
		List<EmployeeVo> projectMemList = workService.selectMemberList(projectVo.getPro_sq());
		
		return projectMemList;
	}
	
	//ajax로 상위일감 검색시 해당프로젝트의 상위일감 조회후 반환
	@RequestMapping(path="/ajaxRequestBody/projectWorkSelect", produces={"application/json"})
	@ResponseBody
	public List<WorkVo> projectWorkSelect(@RequestBody Map<String, Object> map){
		logger.debug("/ajaxRequestBody/projectWorkSelect -> map : {}", map);
		logger.debug("/ajaxRequestBody/projectWorkSelect -> map : {}", map.get("pro_sq"));
		logger.debug("/ajaxRequestBody/projectWorkSelect -> map : {}", map.get("wk_sq"));
		
		//map에 담긴 프로젝트번호로 해당업무를 조회하며 업무번호가 있을시 자신을포함한 하위업무는 조회되지 않음
		List<WorkVo> workList = workService.selectWorkList(map);
		
		logger.debug("/ajaxRequestBody/projectWorkSelect -> workList : {}", workList);
		return workList;
	}
	
	@RequestMapping(path="/workInsert", method=RequestMethod.POST)
	public String workInsert(@RequestParam Map<String, Object> map, @RequestPart("attach")MultipartFile attach, HttpSession session) throws Exception{
		//insert할때 들어갈 정보
		//업무관련사원테이블에 업무번호와 책임코드, 담당자명으로 사원번호 조회후 insert 
		//프로젝트정보(담을 공간 필요함), 업무구분번호, 제목, 내용, 상태, 상위일감, 담당자, 시작시간, 완료기한, 진척도, 첨부파일
		logger.debug("/workInsert -> map : {}", map);
		
		String pro_sq = (String) map.get("selectProject");
		String wk_type = (String) map.get("selectType");
		String wk_nm = (String) map.get("inputTitle");
		String wk_con = (String) map.get("smarteditor");
		String wk_state = (String) map.get("selectState");
		String wk_emp_sq = (String) map.get("selectCharger");
		String wk_str_dt = (String) map.get("dateStart");
		String wk_end_dt = (String) map.get("dateEnd");
		String wk_progress = (String) map.get("selectProgress");
		
		//상위일감은 업무번호+업무제목으로 넘어오기때문에 split으로 구분해줘야함(넘어올때 값이 없으면 ""로 넘어오기때문에 체크해줘야함)
		String wk_hr_sq = (((String) map.get("inputWork")).equals("")) ? null : (String) map.get("inputWork");
		
		int wk_parent_sq = 0;
		if(wk_hr_sq != null){
			//0번째는 상위업무번호, 1번째는 상위업무제목
			String[] workArr = wk_hr_sq.split(":");
			wk_parent_sq = Integer.parseInt(workArr[0]);
		}
		
		//시작날짜와 종료예정날짜 형식 바꾸기(db에 넣기위해)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(wk_str_dt);
		Date endDate = sdf.parse(wk_end_dt);
		
		logger.debug("**************************************");
		logger.debug("/workInsert -> startDate : {}", startDate);
		logger.debug("/workInsert -> endDate : {}", endDate);
		logger.debug("**************************************");
		
		//업무세팅
		WorkVo workVo = new WorkVo(wk_parent_sq, wk_nm, wk_con, startDate, endDate,
						wk_type, wk_state, Integer.parseInt(wk_progress), Integer.parseInt(pro_sq));
		//업무등록결과
		int workResult = workService.insertWork(workVo);
		
		//업무사원관련등록결과
		int relationResult = 0;
		//업무작업내역 등록결과
		int historyResult = 0;
		//업무가 성공적으로 등록된 경우 업무관련사원, 업무작업내역 등록
		if(workResult == 1){
			//업무관련사원 세팅
			WorkRelationVo workRelationVo = new WorkRelationVo();
			workRelationVo.setWk_sq(workVo.getWk_sq()); //insert된 시퀀스 값가져와야함
			workRelationVo.setWk_emp_sq(Integer.parseInt(wk_emp_sq));
			workRelationVo.setWk_code("01");
			relationResult = workService.insertRelation(workRelationVo);
			
			//세션에서 사원정보 가져오기
			EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
			
			//업무작업내역 세팅
			WorkHistoryVo workHistoryVo = new WorkHistoryVo();
			workHistoryVo.setHis_wk_sq(workVo.getWk_sq());
			workHistoryVo.setHis_emp_sq(empVo.getEmp_sq()); //로그인한 사원번호 세팅
			workHistoryVo.setHis_nm(workVo.getWk_nm());
			workHistoryVo.setHis_con(workVo.getWk_con());
			historyResult = workService.insertHistory(workHistoryVo);
		}
		
		//파일등록결과
		int fileResult = 0;
		
		//파일이름과 저장경로 초기화
		String wk_fl_nm = "";
		String wk_fl_path = "";
		
		//첨부파일을 등록한 경우 & 업무가 성공적으로 등록된 경우
		if(attach.getSize() > 0 && workResult == 1){
			wk_fl_nm = attach.getOriginalFilename();
			wk_fl_path = "d:\\picture\\work\\" + UUID.randomUUID().toString();
			//wk_fl_path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			
			attach.transferTo(new File(wk_fl_path));
			
			//파일명, uuid(저장경로) 세팅
			WorkFileVo workFileVo = new WorkFileVo();
			workFileVo.setWk_fl_nm(wk_fl_nm);
			workFileVo.setWk_fl_path(wk_fl_path);
			workFileVo.setWk_sq(workVo.getWk_sq()); //insert된 시퀀스 값가져와야함
			
			fileResult += workService.insertFile(workFileVo);
		}
		
		//첨부파일을 올리지 않은 경우 filename, realFilename 모두 공백(위에서 초기화한값)
		logger.debug("/workInsert -> fileResult : {}", fileResult);
		logger.debug("/workInsert -> relationResult : {}", relationResult);
		logger.debug("/workInsert -> historyResult : {}", historyResult);
		
		//정상 입력(성공)
		if(workResult == 1){
			//db에서 데이터를 조작하는 로직을 처리할때는 forward가 아니라 redirect를 사용해야함(새로고침시 최초요청 url로 다시 이동하기때문에)
			//redirect는 ContextPath를 써줘야하며 redirect는 get방식임
			//루트path 지정해야하지만 contextPath에 관한 리스너 등록시 자동으로 붙여진다!!!
			return "redirect:" + "/workManage";
		}
		//정상 입력(실패)
		else{
			return "workInsert";
		}
	}
	
	@RequestMapping(path="/workDetail", method=RequestMethod.GET)
	public String workDetail(@RequestParam Map<String, Object> map, Model model){
		//업무번호 파라미터
		String wk_sq = (String) map.get("wk_sq");
		
		//업무번호로 해당업무와 업무첨부파일, 작업내역 조회
		Map<String, Object> workMap = workService.selectWork(Integer.parseInt(wk_sq));
		List<WorkFileVo> workFileList = workService.selectFileList(Integer.parseInt(wk_sq));
		List<Map<String, Object>> historyList = workService.selectHistoryList(Integer.parseInt(wk_sq));
		
		//모델에 업무, 첨부파일 추가
		model.addAttribute("workMap", workMap);
		model.addAttribute("workFileList", workFileList);
		model.addAttribute("historyList", historyList);
		
		logger.debug("/workDetail -> workMap : {}", workMap);
		logger.debug("/workDetail -> workMap_WK_CON : {}", workMap.get("WK_CON"));
		logger.debug("/workDetail -> workFileList : {}", workFileList);
		logger.debug("/workDetail -> historyList : {}", historyList);
		
		return "workDetail";
	}
	
	@RequestMapping(path="/workFile", method=RequestMethod.GET)
	public String workFile(@RequestParam Map<String, Object> map, Model model){
		String wk_fl_sq = (String) map.get("wk_fl_sq");
		model.addAttribute("wk_fl_sq", wk_fl_sq);
		return "workFileView";
	}
	
	@RequestMapping(path="/workUpdate", method=RequestMethod.POST)
	public String workUpdate(@RequestParam Map<String, Object> map, Model model, @RequestPart("attach")MultipartFile attach, HttpSession session) throws Exception{
		logger.debug("/workUpdate -> map : {}", map);
		
		String pro_sq = (String) map.get("selectProject");
		String wk_type = (String) map.get("selectType");
		String wk_nm = (String) map.get("inputTitle");
		String wk_con = (String) map.get("smarteditor");
		String wk_state = (String) map.get("selectState");
		String wk_emp_sq = (String) map.get("selectCharger");
		String wk_str_dt = (String) map.get("dateStart");
		String wk_end_dt = (String) map.get("dateEnd");
		String wk_progress = (String) map.get("selectProgress");
		
		//상위일감은 업무번호+업무제목으로 넘어오기때문에 split으로 구분해줘야함(넘어올때 값이 없으면 ""로 넘어오기때문에 체크해줘야함)
		String wk_hr_sq = (((String) map.get("inputWork")).equals("")) ? null : (String) map.get("inputWork");
		
		int wk_parent_sq = 0;
		if(wk_hr_sq != null){
			//0번째는 상위업무번호, 1번째는 상위업무제목
			String[] workArr = wk_hr_sq.split(":");
			wk_parent_sq = Integer.parseInt(workArr[0]);
		}
		
		//시작날짜와 종료예정날짜 형식 바꾸기(db에 넣기위해)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(wk_str_dt);
		Date endDate = sdf.parse(wk_end_dt);
		
		logger.debug("**************************************");
		logger.debug("/workUpdate -> startDate : {}", startDate);
		logger.debug("/workUpdate -> endDate : {}", endDate);
		logger.debug("**************************************");
		
		//업무번호 파라미터로 받기
		String wk_sq = (String) map.get("wk_sq");
		
		//업무세팅
		WorkVo workVo = new WorkVo(wk_parent_sq, wk_nm, wk_con, startDate, endDate,
						wk_type, wk_state, Integer.parseInt(wk_progress), Integer.parseInt(pro_sq));
		workVo.setWk_sq(Integer.parseInt(wk_sq));
		//업무 수정결과
		int workResult = workService.updateWork(workVo);
		
		//업무사원관련 수정결과
		int relationResult = 0;
		//업무작업내역 등록결과
		int historyResult = 0;
		//업무가 성공적으로 수정된 경우 업무관련사원 수정, 업무작업내역 등록
		if(workResult == 1){
			//업무관련사원 세팅
			WorkRelationVo workRelationVo = new WorkRelationVo();
			workRelationVo.setWk_sq(workVo.getWk_sq());
			workRelationVo.setWk_emp_sq(Integer.parseInt(wk_emp_sq));
			workRelationVo.setWk_code("01");
			relationResult = workService.updateRelation(workRelationVo);
			
			//세션에서 사원정보 가져오기
			EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
			
			//업무작업내역 세팅
			WorkHistoryVo workHistoryVo = new WorkHistoryVo();
			workHistoryVo.setHis_wk_sq(workVo.getWk_sq());
			workHistoryVo.setHis_emp_sq(empVo.getEmp_sq()); //로그인한 사원번호 세팅
			workHistoryVo.setHis_nm(workVo.getWk_nm());
			workHistoryVo.setHis_con(workVo.getWk_con());
			historyResult = workService.insertHistory(workHistoryVo);
		}
		
		//파일등록결과
		int fileResult = 0;
		
		//파일이름과 저장경로 초기화
		String wk_fl_nm = "";
		String wk_fl_path = "";
		
		//첨부파일을 등록한 경우 & 업무가 성공적으로 수정된 경우
		if(attach.getSize() > 0 && workResult == 1){
			wk_fl_nm = attach.getOriginalFilename();
			wk_fl_path = "d:\\picture\\work\\" + UUID.randomUUID().toString();
			//wk_fl_path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\images\\" + UUID.randomUUID().toString();
			
			attach.transferTo(new File(wk_fl_path));
			
			//파일명, uuid(저장경로) 세팅
			WorkFileVo workFileVo = new WorkFileVo();
			workFileVo.setWk_fl_nm(wk_fl_nm);
			workFileVo.setWk_fl_path(wk_fl_path);
			workFileVo.setWk_sq(workVo.getWk_sq());
			
			fileResult += workService.insertFile(workFileVo);
		}
		
		//첨부파일을 올리지 않은 경우 filename, realFilename 모두 공백(위에서 초기화한값)
		logger.debug("/workUpdate -> fileResult : {}", fileResult);
		logger.debug("/workUpdate -> relationResult : {}", relationResult);
		logger.debug("/workUpdate -> historyResult : {}", historyResult);
		
		//정상적으로 수정이 되든 안되든 원래화면으로
		//db에서 데이터를 조작하는 로직을 처리할때는 forward가 아니라 redirect를 사용해야함(새로고침시 최초요청 url로 다시 이동하기때문에)
		//redirect는 ContextPath를 써줘야하며 redirect는 get방식임
		//루트path 지정해야하지만 contextPath에 관한 리스너 등록시 자동으로 붙여진다!!!
		return "redirect:/workDetail?wk_sq=" + workVo.getWk_sq();
	}
	
	@RequestMapping(path="/workFileDelete", method=RequestMethod.GET)
	public String workFileDelete(@RequestParam Map<String, Object> map){
		//파일번호 파라미터
		String wk_fl_sq = (String) map.get("wk_fl_sq");
		//업무번호 파라미터
		String wk_sq = (String) map.get("wk_sq");
		
		int fileResult = workService.deleteFile(Integer.parseInt(wk_fl_sq));
		
		logger.debug("/workFileDelete -> fileResult : {}", fileResult);
		
		//정상적으로 삭제가 되든 안되든 원래화면으로
		return "redirect:/workDetail?wk_sq=" + Integer.parseInt(wk_sq);
	}
	
	@RequestMapping(path="/workDelete", method=RequestMethod.POST)
	public String workDelete(@RequestParam Map<String, Object> map){
		//업무번호 파라미터
		String wk_sq_str = (String) map.get("wk_sq");
		int wk_sq = Integer.parseInt(wk_sq_str);
		
		//업무삭제시 업무관련 -> 파일 -> 작업내역 -> 하위일감 -> 순으로 삭제
		int relationDelete = workService.deleteAllRelation(wk_sq);
		int fileDelete = workService.deleteAllFile(wk_sq);
		int historyDelete = workService.deleteAllHistory(wk_sq);
		int workDelete = workService.deleteAllWork(wk_sq);
		
		logger.debug("/workDelete -> relationDelete : {}", relationDelete);
		logger.debug("/workDelete -> relationDelete : {}", fileDelete);
		logger.debug("/workDelete -> relationDelete : {}", historyDelete);
		logger.debug("/workDelete -> relationDelete : {}", workDelete);
		logger.debug("/workDelete -> relationDelete : {}", relationDelete);
		
		return "redirect:/workManage";
	}
	
	@RequestMapping(path="/projectDetail", method=RequestMethod.GET)
	public String projectDetail(@RequestParam Map<String, Object> map, Model model, HttpSession session, PageVo pageVo){
		//프로젝트 번호를 받은후
		String pro_sq = (String) map.get("pro_sq");
		
		//프로젝트 조회, 타입*유형별 업무수 조회
		ProjectVo projectVo = workService.selectProject(Integer.parseInt(pro_sq));
		Map<String, Object> countMap = workService.selectCount(Integer.parseInt(pro_sq));
		
		//세션에 프로젝트 정보
		session.setAttribute("projectVo", projectVo);
		//세션에 업무수 추가
		session.setAttribute("countMap", countMap);
		
		//tabCode 1번은 개요tab
		model.addAttribute("tabCode", "1");
		//tabpaneCode 1번은 개요pane
		model.addAttribute("tabpaneCode", "1");
		
		//list를 생성하여 project번호를 담기 
		List<Integer> prosqList = new ArrayList<>();
		prosqList.add(projectVo.getPro_sq());
		
		//map에 project 번호를 가진 list 담기
		Map<String, Object> prosqMap = new HashMap<String, Object>();
		prosqMap.put("projectList", prosqList);
		prosqMap.put("his_wk_sq", 0);
		
		//작업내역조회
		List<Map<String, Object>> workHistoryMapList= workService.selectHistoryMapList(prosqMap);
		
		//세션에 작업내역리스트 추가
		session.setAttribute("workHistoryMapList2", workHistoryMapList);
		
		//조건검색할 값을 parameter map에 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("selectProjectState", 1);
		paramMap.put("pro_sq", pro_sq);
		
		//해당 프로젝트 기본적인 업무 페이징 리스트
		utilNKO.workPaging(paramMap, pageVo, session);
		
		return "projectDetail";
	}
	
	@RequestMapping(path="/projectConditionSelect", method=RequestMethod.GET)
	public String projectConditionSelect(@RequestParam Map<String, Object> map, Model model, HttpSession session){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabpaneCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//검색조건
		String selectCondition = (String) map.get("selectCondition");
		
		//session에서 검색조건리스트(selConditionList)를 받아온후 있는지 없는지 체크한후
		//검색조건(selCondition) 추가후 session에 추가
		List<String> selConditionList = (List<String>) session.getAttribute("selConditionList2");
		
		boolean isExist = false;
		if(selConditionList != null){
			for(int i=0; i<selConditionList.size(); i++){
				//새로고침시 계속 추가되는 것때문에 체크해줘야함(동일한 selCondition이 존재하지 않을경우 - isExist가 false일경우에만 추가)
				if(selConditionList.get(i).equals(selectCondition)){
					isExist = true;
				}
			}
			
			//selConditionList를 다 조회후 없을경우에만 추가
			if(!isExist){
				selConditionList.add(selectCondition);
			}
		}else{
			//검색조건리스트가 null이면 그냥 add
			selConditionList = new ArrayList<String>();
			selConditionList.add(selectCondition);
		}
		session.setAttribute("selConditionList2", selConditionList);
		
		return "projectDetail";
	}
	
	@RequestMapping(path="/projectConditionWorkSelect", method=RequestMethod.GET)
	public String projectConditionWorkSelect(@RequestParam Map<String, Object> map, Model model, PageVo pageVo, HttpSession session){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//1-진행중, 2-완료됨, 3-모두
		String selectState = (String) map.get("selectState");
		
		//1-이다, 2-아니다
		String selectProjectState = (String) map.get("selectProjectState");
		if(selectProjectState == null){
			selectProjectState = "1";
		}
		
		//프로젝트 번호
		String pro_sq = (String) map.get("pro_sq");
		
		//1-이다, 2-아니다
		String selectTypeState = (String) map.get("selectTypeState");
		//1-새기능, 2-결함, 3-공지, 4-지원
		String wk_type = (String) map.get("selectType");
		
		//1-이다, 2-아니다
		String selectChargerState = (String) map.get("selectChargerState");
		//담당자 사원번호
		String wk_emp_sq = (String) map.get("selectCharger");
		
		//검색조건 세션에 추가
		session.setAttribute("selectState2", selectState);
		session.setAttribute("selectProjectState2", selectProjectState);
		session.setAttribute("pro_sq2", pro_sq);
		session.setAttribute("selectTypeState2", selectTypeState);
		session.setAttribute("wk_type2", wk_type);
		session.setAttribute("selectChargerState2", selectChargerState);
		session.setAttribute("wk_emp_sq2", wk_emp_sq);

		//조건검색할 값을 parameter map에 세팅
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("selectState", selectState);
		paramMap2.put("selectProjectState", selectProjectState);
		paramMap2.put("pro_sq", pro_sq);
		paramMap2.put("selectTypeState", selectTypeState);
		paramMap2.put("wk_type", wk_type);
		paramMap2.put("selectChargerState", selectChargerState);
		paramMap2.put("wk_emp_sq", wk_emp_sq);
		
		//조건검색 값 유지하기위해 session에 paramMap2 세팅
		logger.debug("/projectConditionWorkSelect -> paramMap2 : {}", paramMap2);
		session.setAttribute("paramMap2", paramMap2);
		
		logger.debug("/projectConditionWorkSelect -> workHistoryMapList2 : {}", session.getAttribute("workHistoryMapList2"));
		logger.debug("/projectConditionWorkSelect -> pageVo: {}", pageVo);
		
		//해당 프로젝트 조건검색에 따른 업무 페이징 리스트
		utilNKO.workPaging(paramMap2, pageVo, session);
		
		return "projectDetail";
	}
	
	@RequestMapping(path="/projectConditionDelete", method=RequestMethod.GET)
	public String projectConditionDelete(@RequestParam Map<String, Object> map, Model model, HttpSession session, PageVo pageVo){
		//tabCode 3번은 일감tab
		model.addAttribute("tabCode", "3");
		//tabCode 3번은 일감pane
		model.addAttribute("tabpaneCode", "3");
		
		//session에서 검색조건리스트(selConditionList) 삭제
		session.removeAttribute("selConditionList2");
		
		//검색조건 세션에서 삭제
		session.removeAttribute("selectState2");
		session.removeAttribute("selectProjectState2");
		session.removeAttribute("pro_sq2");
		session.removeAttribute("selectTypeState2");
		session.removeAttribute("wk_type2");
		session.removeAttribute("selectChargerState2");
		session.removeAttribute("wk_emp_sq2");
		
		//조건검색할 값을 parameter map에 세팅
		//검색조건이 담긴 paramMap을 session에서 찾은후 있으면 사용하고 없으면 진행중을 나타내는 상태를 넣어 검색
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("selectState", 1);
		paramMap2.put("selectProjectState", 1);
		paramMap2.put("pro_sq", map.get("pro_sq"));
		
		//해당 프로젝트 조건검색에 따른 업무 페이징 리스트
		utilNKO.workPaging(paramMap2, new PageVo(), session);
		
		return "projectDetail";
	}
	
	@RequestMapping(path="/projectWorkInsert", method=RequestMethod.GET)
	public String projectWorkInsertView(HttpSession session){
		return "projectWorkInsert";
	}
	
	@RequestMapping(path="/ganttChart", method=RequestMethod.GET)
	public String ganttChartView(Model model, HttpSession session){
		//전체사원 조회후 session에 세팅(담당자 부여할때 필요)
		List<EmployeeVo> employeeList = employeeService.selectAllList();
		session.setAttribute("employeeList", employeeList);
		
		//로그인 유저정보 얻어서 권한 확인하고 프로젝트리스트 정보를 세션에 저장
		EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
		utilNKO.projectCheckAuthority(employeeVo, session);
		
		//간트차트에 표시될 데이터 가져오기(기본적으로는 진행중인 업무이며 사원에게 권한이 있는 프로젝트만 검색)
		Map<String, Object> ganttMap = new HashMap<String, Object>();
		ganttMap.put("selectState", 1);
		ganttMap.put("projectList", session.getAttribute("prosqList"));
		
		//프로젝트가 없는 사원일 경우 not in 쿼리 실행하기 위해 notIn 객체를 세션에서 가져와서 파라미터 맵에 추가
		if(session.getAttribute("notIn") != null){
			ganttMap.put("notIn", "notIn");
		}
		
		List<Map<String, Object>> ganttMapList = workService.selectGanttMap(ganttMap);
		logger.debug("/projectWorkInsert -> ganttMapList : {}", ganttMapList);
		
		//model에 데이터 세팅
		model.addAttribute("ganttMapList", ganttMapList);
		
		//날짜구하기
		utilNKO.dateSeek(model);
		
		return "ganttChart";
	}
	
	@RequestMapping(path="/ganttConditionSelect", method=RequestMethod.GET)
	public String ganttConditionSelect(@RequestParam Map<String, Object> map, Model model, HttpSession session){
		//검색조건
		String selectCondition = (String) map.get("selectCondition");
		
		//session에서 검색조건리스트(selConditionList)를 받아온후 있는지 없는지 체크한후
		//검색조건(selCondition) 추가후 session에 추가
		List<String> selConditionList = (List<String>) session.getAttribute("selConditionList");
		
		boolean isExist = false;
		if(selConditionList != null){
			for(int i=0; i<selConditionList.size(); i++){
				//새로고침시 계속 추가되는 것때문에 체크해줘야함(동일한 selCondition이 존재하지 않을경우 - isExist가 false일경우에만 추가)
				if(selConditionList.get(i).equals(selectCondition)){
					isExist = true;
				}
			}
			
			//selConditionList를 다 조회후 없을경우에만 추가
			if(!isExist){
				selConditionList.add(selectCondition);
			}
		}else{
			//검색조건리스트가 null이면 그냥 add
			selConditionList = new ArrayList<String>();
			selConditionList.add(selectCondition);
		}
		session.setAttribute("selConditionList", selConditionList);
		
		return "ganttChart";
	}
	
	@RequestMapping(path="/ganttConditionWorkSelect", method=RequestMethod.GET)
	public String ganttConditionWorkSelect(@RequestParam Map<String, Object> map, Model model, PageVo pageVo, HttpSession session){
		//1-진행중, 2-완료됨, 3-모두
		String selectState = (String) map.get("selectState");
		
		//1-이다, 2-아니다
		String selectProjectState = (String) map.get("selectProjectState");
		//프로젝트 번호
		String pro_sq = (String) map.get("selectProject");
		
		//1-이다, 2-아니다
		String selectTypeState = (String) map.get("selectTypeState");
		//1-새기능, 2-결함, 3-공지, 4-지원
		String wk_type = (String) map.get("selectType");
		
		//1-이다, 2-아니다
		String selectChargerState = (String) map.get("selectChargerState");
		//담당자 사원번호
		String wk_emp_sq = (String) map.get("selectCharger");
		
		//검색조건 세션에 추가
		session.setAttribute("selectState", selectState);
		session.setAttribute("selectProjectState", selectProjectState);
		session.setAttribute("pro_sq", pro_sq);
		session.setAttribute("selectTypeState", selectTypeState);
		session.setAttribute("wk_type", wk_type);
		session.setAttribute("selectChargerState", selectChargerState);
		session.setAttribute("wk_emp_sq", wk_emp_sq);
		
		//조건검색할 값을 parameter map에 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("selectState", selectState);
		paramMap.put("selectProjectState", selectProjectState);
		paramMap.put("pro_sq", pro_sq);
		paramMap.put("selectTypeState", selectTypeState);
		paramMap.put("wk_type", wk_type);
		paramMap.put("selectChargerState", selectChargerState);
		paramMap.put("wk_emp_sq", wk_emp_sq);
		
		//조건검색 값 유지하기위해 session에 paramMap 세팅
		logger.debug("/ganttConditionWorkSelect -> paramMap : {}", paramMap);
		session.setAttribute("paramMap", paramMap);
		
		//프로젝트가 없는 사원일 경우 not in 쿼리 실행하기 위해 notIn 객체를 세션에서 가져와서 파라미터 맵에 추가
		if(session.getAttribute("notIn") != null){
			paramMap.put("notIn", "notIn");
		}
		
		//기본적으로 사원에게 권한이 있는 프로젝트만 검색
		paramMap.put("projectList", session.getAttribute("prosqList"));
		List<Map<String, Object>> ganttMapList = workService.selectGanttMap(paramMap);
		logger.debug("/ganttConditionWorkSelect -> ganttMapList : {}", ganttMapList);
		
		//model에 데이터 세팅
		model.addAttribute("ganttMapList", ganttMapList);
		
		//날짜구하기
		utilNKO.dateSeek(model);
		
		return "ganttChart";
	}
	
	@RequestMapping(path="/ganttConditionDelete", method=RequestMethod.GET)
	public String ganttConditionDelete(Model model, HttpSession session){
		//session에서 검색조건리스트(selConditionList) 삭제
		session.removeAttribute("selConditionList");
		
		//검색조건 세션에서 삭제
		session.removeAttribute("selectState");
		session.removeAttribute("selectProjectState");
		session.removeAttribute("pro_sq");
		session.removeAttribute("selectTypeState");
		session.removeAttribute("wk_type");
		session.removeAttribute("selectChargerState");
		session.removeAttribute("wk_emp_sq");
		
		//간트차트에 표시될 데이터 가져오기(기본적으로는 진행중인 업무이며 사원에게 권한이 있는 프로젝트만 검색)
		Map<String, Object> ganttMap = new HashMap<String, Object>();
		ganttMap.put("selectState", 1);
		ganttMap.put("projectList", session.getAttribute("prosqList"));
		
		//프로젝트가 없는 사원일 경우 not in 쿼리 실행하기 위해 notIn 객체를 세션에서 가져와서 파라미터 맵에 추가
		if(session.getAttribute("notIn") != null){
			ganttMap.put("notIn", "notIn");
		}
		
		List<Map<String, Object>> ganttMapList = workService.selectGanttMap(ganttMap);
		logger.debug("/projectWorkInsert -> ganttMapList : {}", ganttMapList);
		
		//model에 데이터 세팅
		model.addAttribute("ganttMapList", ganttMapList);
		
		//날짜구하기
		utilNKO.dateSeek(model);
		
		return "ganttChart";
	}

}