package kr.or.nko.message.controller;

import java.util.ArrayList;
import java.util.List;
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

import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;
import kr.or.nko.message.model.MessageManyVo;
import kr.or.nko.message.model.MessageReceiveVo;
import kr.or.nko.message.model.MessageSendVo;
import kr.or.nko.message.service.IMessageService;

@Controller
public class MessageController {

	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	String code = "0";

	@Resource(name = "messageService")
	private IMessageService messageService;

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;

	@RequestMapping("/messageList")
	public String messageList(
			Model model, HttpSession session) {

		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();

		String strId = Integer.toString(empvo.getEmp_sq());

		// 받은 쪽지 => receive 쪽지
		List<MessageReceiveVo> recelist = messageService.selectRec(strId);
		// 내게쓴 쪽지 => send 쪽지,나에게쓴
		List<MessageReceiveVo> myrecelist = messageService.myselectRec(strId);
		// 내가 보낸 쪽지 => send 쪽지
		List<MessageSendVo> sendList = messageService.selectSend(strId);
		// 쪽지 보관함 => 중요하다고 체크한 쪽지
		List<MessageReceiveVo> receimplist = messageService.selectimpRec(strId);
		// 내가 보낸 쪽지 => Map으로 처리
		List<Map<String, Object>> sendMap = messageService.selectSends(strId);
		
		model.addAttribute("sendMap", sendMap);
		model.addAttribute("recelist", recelist);
		model.addAttribute("myrecelist", myrecelist);
		model.addAttribute("sendList", sendList);
		model.addAttribute("receimplist", receimplist);
		model.addAttribute("number", code); 

		return "messageList";
	}
	
	@RequestMapping("/messageLists")
	public String messageLists(@RequestBody String check, Model model, HttpSession session) {
		
		logger.debug("check={}",check); // check가 2이면 처리
		String number = check.substring(1,check.length()-1);
		logger.debug("number={}",number); // check가 2이면 처리
		
		
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();

		String strId = Integer.toString(empvo.getEmp_sq());

		// 받은 쪽지 => receive 쪽지
		List<MessageReceiveVo> recelist = messageService.selectRec(strId);
		// 내게쓴 쪽지 => send 쪽지,나에게쓴
		List<MessageReceiveVo> myrecelist = messageService.myselectRec(strId);
		// 내가 보낸 쪽지 => send 쪽지
		List<MessageSendVo> sendList = messageService.selectSend(strId);
		// 쪽지 보관함 => 중요하다고 체크한 쪽지
		List<MessageReceiveVo> receimplist = messageService.selectimpRec(strId);

		List<Map<String, Object>> sendMap = messageService.selectSends(strId);

		model.addAttribute("sendMap", sendMap);
		model.addAttribute("recelist", recelist); // 
		model.addAttribute("myrecelist", myrecelist); // 
		model.addAttribute("sendList", sendList); // 
		model.addAttribute("receimplist", receimplist); // 
		model.addAttribute("number", number); 

		return "jsonView";
	}

	@RequestMapping("/messageInsert")
	public String messageInsert(Model model, HttpSession session) {

		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();

		List<EmployeeVo> elist = employeeService.selectAllList();

		model.addAttribute("emplist", elist);
		model.addAttribute("memId", memId);

		return "messageInsert";
	}

	@RequestMapping("/messageInsertrecall")
	public String messageInsertrecall(@RequestParam String recallins, Model model, HttpSession session) {

		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();

		List<EmployeeVo> elist = employeeService.selectAllList();
		EmployeeVo evo = employeeService.selectEmployee(Integer.parseInt(recallins));
		String name = evo.getEmp_nm();
		String result = name + "(" + recallins + ")";

		model.addAttribute("recallins", result);
		model.addAttribute("emplist", elist);
		model.addAttribute("memId", memId);

		return "messageInsert";
	}

	@RequestMapping("/messageInserts")
	public String messageInserts(Model model, HttpSession session, @RequestParam Map<String, Object> map) {

		int flag = 1; // 나에게 보내는 쪽지인지 확인
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();

		String receive = (String) map.get("res_nm");
		String content = (String) map.get("contentarea");

		List<Integer> receList = new ArrayList<>();
		if (receive.length() > 8) {
			flag = 0;
			String arr[] = receive.split(",");

			for (String ar : arr) {
				receList.add(Integer.parseInt(ar.substring(ar.length() - 7, ar.length() - 1)));
			}
		}

		MessageSendVo svo = new MessageSendVo();
		MessageManyVo mvo = new MessageManyVo();
		MessageReceiveVo rvo = new MessageReceiveVo();

		// send 테이블값 추가
		svo.setMsg_emp_send(memId);
		svo.setMsg_con(content);
		svo.setMsg_nm("제목");

		messageService.insertMessageSend(svo);

		// many 테이블값 추가
		List<MessageSendVo> mslist = messageService.selectSeq(Integer.toString(empvo.getEmp_sq()));

		mvo.setMn_msg_send_sq(mslist.get(0).getMsg_rec_sq());

		if (flag == 1) {
			mvo.setMn_emp_rec_sq(Integer.parseInt(receive));
			messageService.insertMessageMany(mvo);
		} else if (flag == 0) {
			for (int i = 0; i < receList.size(); i++) {
				mvo.setMn_emp_rec_sq(receList.get(i));
				messageService.insertMessageMany(mvo);
			}
		}

		// receive 테이블값 추가
		rvo.setMsg_emp_send(memId);
		rvo.setMsg_con(content);
		if (flag == 1) {
			rvo.setMsg_emp_rec(Integer.parseInt(receive));
			messageService.insertMessageReceive(rvo);
		} else if (flag == 0) {
			for (int i = 0; i < receList.size(); i++) {
				rvo.setMsg_emp_rec(receList.get(i));
				messageService.insertMessageReceive(rvo);
			}
		}

		return "redirect:/messageList";
	}

	@RequestMapping("/messageDelete")
	public String messageDelete(@RequestBody Map delmess, Model model, HttpSession session) {
		
		String result =(String) delmess.get("params");
		
		String arr[] = result.split(", ");
		
		logger.debug("delmess={}",result);
		
		for(int i=0;i<arr.length;i++){
			messageService.deleteReceive(arr[i]);
		}
		
		return "redirect:/messageList";
	}

	@RequestMapping("/messageSave")
	public String messageSave(@RequestBody Map savemess, Model model, HttpSession session) {
		
		String result =(String) savemess.get("params");
		
		String arr[] = result.split(", ");
		
		logger.debug("savemess={}",result);
		
		for(int i=0;i<arr.length;i++){
			messageService.updateReceive(arr[i]);
		}
		
		return "redirect:/messageList";
	}

}