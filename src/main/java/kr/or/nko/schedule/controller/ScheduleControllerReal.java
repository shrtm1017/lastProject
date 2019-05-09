package kr.or.nko.schedule.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nko.admin.model.AuthorityVo;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.schedule.model.ScheduleVo;
import kr.or.nko.schedule.service.IScheduleService;

@Controller
public class ScheduleControllerReal {

	private Logger logger = LoggerFactory.getLogger(ScheduleControllerReal.class);
	int scd_sq;
	
	@Resource(name="scheduleService")
	private IScheduleService service;
	
	@Resource(name="scheduleService")
	private IScheduleService scheduleService;

  /**
* Method : Schedule
* 작성자 : pc15
* 변경이력 :
* @param scd_sq
* @param model
* @throws Exception
* Method 설명 : 개인 일정 관리
*/
@RequestMapping("/scheduleList")
  public void Schedule(@RequestParam String scd_sq, Model model,HttpSession session) throws Exception {
    
	EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
	int memId = empvo.getEmp_sq();
	
	String str = Integer.toString(memId);
	
	List<AuthorityVo> authlist = scheduleService.authoSearch(str);
    
    try {
      model.addAttribute("scd_sq", scd_sq);
      model.addAttribute("login_admn_sq", authlist);
    } catch(Exception e) {
      e.printStackTrace();
    }

  }

  /**
* Method : selectSchedule
* 작성자 : pc15
* 변경이력 :
* @param params
* @param model
* @return
* @throws Exception
* Method 설명 : 달력에 출력
*/
  @ResponseBody // ajax 이용할 경우 데이터 전송시 필수사항
  @RequestMapping("/selectSchedule")
  public List<Map<String, Object>> selectSchedule(@RequestBody String params, Model model,HttpSession session) throws Exception {
    // @RequestBody ajax값을 받을때 이용 주로 Map 이용 (이유 Map을 이용하면 한번에 모든 파라미터를 map에 담아 이용가능하고, 형변환이 자동적으로 실행)
	  
	EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
	int memId = empvo.getEmp_sq();
    List<ScheduleVo> result = null;
    
    // 로그인했을 때 부서번호, 사원번호 (jsp에서 일정 구분번호는 params에 담겨있다)	
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("scd_div_sq", params);
    param.put("dpt_sq", empvo.getEmp_dpt());
    param.put("stf_sq", memId);
    
    logger.debug("*******************");
    logger.debug("{}",param.get("scd_div_sq"));
    logger.debug("{}",param.get("dpt_sq"));
    logger.debug("{}",param.get("stf_sq"));
    logger.debug("*******************");
    
    try {
      
      // 회사일정에 등록된 모든 스케쥴을 DB에서 조회한다.
      result = service.selectSchedule(param);

    } catch(Exception e) {
      e.printStackTrace();
    }
    
    List<Map<String, Object>> resultList = new ArrayList<>();
    
    for(ScheduleVo svo : result){
    	Map<String, Object> map = new HashMap<>();
    	map.put("title", svo.getScd_title());
    	map.put("start", svo.getScd_str_dt());
    	map.put("end", svo.getScd_end_dt());
    	map.put("id", svo.getScd_sq());
    	map.put("allDay", true);
    	map.put("className", svo.getScd_select());
    	map.put("scd_emp_sq",svo.getScd_emp_sq());
    	
    	resultList.add(map);
    }
    
    return resultList;
  }

  @RequestMapping("/insertSchedule")
  public String insertSchedule(@RequestParam Map<String,Object> param,HttpSession session,Model model) throws Exception {
    
    // 로그인했을 때 사번
    EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
	int memId = empvo.getEmp_sq(); // 사원 번호
	int memDpt = empvo.getEmp_dpt();
	
	
	logger.debug("{}",memId);
	logger.debug("{}",param.get("scd_div_sq"));
	logger.debug("{}",param.get("scd_str_dt"));
	logger.debug("{}",param.get("scd_end_dt"));
	logger.debug("{}",param.get("scd_sel"));
	logger.debug("{}",param.get("scd_con"));
	
	ScheduleVo vo = new ScheduleVo();
	System.out.println("test1");
	
	String str_date = (String)param.get("scd_str_dt"); // 시작 날짜
	String end_date = (String)param.get("scd_end_dt"); // 종료 날짜
	String scd_div = (String)param.get("scd_div_sq"); // 일정 항목
	
	logger.debug("{}",str_date);
	logger.debug("{}",end_date);
	System.out.println("test2");
	
	vo.setScd_emp_sq(memId);
    vo.setScd_div_sq((String)param.get("scd_div_sq"));
    vo.setScd_title((String)param.get("scd_title"));
    vo.setScd_str_dt((String)param.get("scd_str_dt"));
    vo.setScd_end_dt((String)param.get("scd_end_dt"));
    vo.setScd_select((String)param.get("scd_sel"));
    vo.setScd_con((String)param.get("scd_con"));
    vo.setScd_con(vo.getScd_con().replaceAll("\n", "<br>"));
   
    logger.debug("{}",vo);
    
    try {
      service.insertSchedule(vo);
    } catch(Exception e) {
      e.printStackTrace();
    }

    model.addAttribute("scd_div_sq",vo.getScd_div_sq());
    return "calendar";
  }
  
  /**
* Method : readSchedule
* 작성자 : pc15
* 변경이력 :
* @param params
* @return
* @throws Exception
* Method 설명 : 클릭시 모달창 정보출력
*/
@ResponseBody
  @RequestMapping("/readSchedule")
  public ScheduleVo readSchedule(@RequestBody Map<String, Object> params) throws Exception {
    String result = null;
    logger.debug("test111");
  
    Map<String,Object> map = new HashMap<>();
    map.put("scd_sq", params.get("id"));
    logger.debug("test222");
    logger.debug("id={}",params.get("id"));
    
    ScheduleVo svo = service.readSchedule(map);
    result = svo.getScd_con();
    logger.debug("con={}",svo.getScd_con());
    
    return svo;
  }
  
  @ResponseBody
  @RequestMapping("/updateSchedule")
  public int updateSchedule(@RequestBody Map<String, String> params,Model model) throws Exception {
    
    int result = 0;
    
    logger.debug("{}",params.get("scd_str_dt"));
    logger.debug("{}",params.get("scd_end_dt"));
    
    params.put("scd_con", params.get("scd_con").replaceAll("\n", "<br>"));
    
    try {
      result = service.updateSchedule(params);
      
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    
//    model.addAttribute("scd_div_sq", params.get("scd_div_sq"));
//    model.addAttribute("scd_emp_sq", params.get("scd_emp_sq"));

    
    return result;
  }
  
  
  @ResponseBody
  @RequestMapping("/deleteSchedule")
  public int deleteSchedule(@RequestBody Map<String, Object> params,Model model) throws Exception {
    
    int result = 0;
    
    logger.debug("{}",params.size());
    logger.debug("{}",params.size());
    logger.debug("{}",params.size());
    logger.debug("{}",params.get("bs_scd_div_sq_up"));
    
    try {
      result = service.deleteSchedule(params);
      
    } catch(Exception e) {
      e.printStackTrace();
    }
    
//    model.addAttribute("scd_div_sq", params.get("scd_div_sq"));
//    model.addAttribute("scd_emp_sq", params.get("scd_emp_sq"));

    return result;
  }
}