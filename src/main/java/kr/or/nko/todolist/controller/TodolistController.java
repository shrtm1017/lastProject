package kr.or.nko.todolist.controller;

import java.util.List;

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

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.todolist.model.TodolistVo;
import kr.or.nko.todolist.service.ITodolistService;

@Controller
public class TodolistController {
	
	private Logger logger = LoggerFactory.getLogger(TodolistController.class);
	
	@Resource(name="todolistService")
	private ITodolistService todolistService;
	
	/**
	* Method : viewtoDoList
	* 작성자 : pc15
	* 변경이력 :
	* @param session
	* @param model
	* @return
	* Method 설명 : left 메뉴에서 클릭시 페이지 이동 페이지 이동시 전체회원 리스트 전달
	*/
	@RequestMapping("/toDoList")
	public String viewtoDoList(HttpSession session,Model model){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		return "todolist";
	}
	
	/**
	 * Method : viewtoDoList
	 * 작성자 : pc15
	 * 변경이력 :
	 * @param session
	 * @param model
	 * @return
	 * Method 설명 : left 메뉴에서 클릭시 페이지 이동 페이지 이동시 전체회원 리스트 전달
	 */
	@ResponseBody
	@RequestMapping("/countTodo")
	public int countTodo(HttpSession session,Model model){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		int todosize = 0;
		
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		for(int i=0;i<todolist.size();i++){
			if(todolist.get(i).getTdl_comp().equals("n")){
				todosize++;
			}
		}
		
		return todosize;
	}
	
	/**
	* Method : createTodo
	* 작성자 : pc15
	* 변경이력 :
	* @return
	* Method 설명 : 새로운 To do List 생성
	*/
	@RequestMapping("/createTodo")
	public String createTodo(HttpSession session,Model model,@RequestParam("todoplus")String todoplus){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_emp_sq(memId);
		tdvo.setTdl_con(todoplus);
		tdvo.setTdl_comp("n");
		
		todolistService.insertTodo(tdvo);
		
		// 추가 된 이후 전체 사원 리스트
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		return "todolist";
	}
	
	@RequestMapping("/modiTodo")
	public String modiTodo(HttpSession session,
						   Model model,
//						   @RequestParam()Map<String,Object> map,
						   @RequestParam("tdl_sq")int tdl_sq,
						   @RequestParam("tdl_con")String tdl_con){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
/*		String connum= (String) map.get("connum");
		int sqnum= (Integer) map.get("sqnum");
		
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_con(connum);
		tdvo.setTdl_sq(sqnum);
		
		logger.debug("modihid : {}",connum);
		logger.debug("modihsq : {}",sqnum);*/
		
		// 추가
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_sq(tdl_sq);
		tdvo.setTdl_con(tdl_con);
		
		todolistService.updateTodo(tdvo);
		
		// 수정 된 이후 전체 사원 리스트
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		return "todolist";
	}
	
	@RequestMapping("/deleTodo")
	public String deleTodo(HttpSession session,Model model,@RequestParam("sqnum")int delehsq){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_sq(delehsq);
		
		todolistService.deleteTodo(tdvo);
		
		// 삭제 된 이후 전체 사원 리스트
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		return "todolist";
	}
	
	@RequestMapping("/completeChk")
	public String completeChk(HttpSession session,Model model,@RequestParam("sqnum")int chk){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		// update y
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_sq(chk);
		tdvo.setTdl_comp("y");
		
		todolistService.updateTodochk(tdvo);
		
		// 삭제 된 이후 전체 사원 리스트
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		int todosize2 = 0;
		
		for(int i=0;i<todolist.size();i++){
			if(todolist.get(i).getTdl_comp().equals("n")){
				todosize2++;
			}
		}
		session.setAttribute("todoSize",todosize2);
		
		return "todolist";
	}
	
	@RequestMapping("/uncompleteChk")
	public String uncompleteChk(HttpSession session,Model model,@RequestParam("sqnum")int chk){
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		// update n
		TodolistVo tdvo = new TodolistVo();
		tdvo.setTdl_sq(chk);
		tdvo.setTdl_comp("n");
		
		todolistService.updateTodochk(tdvo);
		
		// 삭제 된 이후 전체 사원 리스트
		List<TodolistVo> todolist = todolistService.selectTodo(memId);
		
		int todosize = todolist.size();
		
		model.addAttribute("todolist",todolist); 
		model.addAttribute("todosize",todosize); 
		
		int todosize2 = 0;
		
		for(int i=0;i<todolist.size();i++){
			if(todolist.get(i).getTdl_comp().equals("n")){
				todosize2++;
			}
		}
		session.setAttribute("todoSize",todosize2);
		
		return "todolist";
	}
	
}
