package kr.or.nko.approval.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nko.annual.model.GiveAnnualVo;
import kr.or.nko.annual.model.UseAnnualVo;
import kr.or.nko.annual.service.AnnualService;
import kr.or.nko.approval.model.ApprovalDivVo;
import kr.or.nko.approval.model.ApprovalEleVo;
import kr.or.nko.approval.model.ApprovalLineVo;
import kr.or.nko.approval.model.ApprovalRefVo;
import kr.or.nko.approval.model.ApprovalVo;
import kr.or.nko.approval.model.SignalVo;
import kr.or.nko.approval.service.IApprovalService;
import kr.or.nko.department.model.DepartmentVo;
import kr.or.nko.department.service.IDepartmentService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.employee.service.IEmployeeService;

@Controller
public class ApprovalController {

   private Logger logger = LoggerFactory.getLogger(ApprovalController.class);

   @Resource(name = "approvalService")
   private IApprovalService approvalService;
   @Resource(name = "employeeService")
   private IEmployeeService employeeService;
   @Resource(name = "departmentService")
   private IDepartmentService departmentService;
   @Resource(name = "annualService")
   private AnnualService annualService;

   // 조장 작업구역 시작

   @RequestMapping("/apvList")
   public String apvListView(Model model) {
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();

      model.addAttribute("apvDivList", apvDivList);
      return "apvList";
   }

   @RequestMapping(path = "/apvForm", method = RequestMethod.GET)
   public String apvFormView(@RequestParam("div_apv_sq") int div_apv_sq, 
                       HttpSession session, 
                       Model model) {
      // 문서 정보 (양식)
      ApprovalDivVo apvDivVo = approvalService.selectApvDiv(div_apv_sq);

      // 수신참조자 목록
      List<EmployeeVo> refList = approvalService.selectReference(div_apv_sq);

      // 전체 사원 목록 (시행자 선택 시 필요)
      List<EmployeeVo> empList = employeeService.getAllEmp();

      // 기안자 정보
      EmployeeVo employeeVo = (EmployeeVo) session.getAttribute("employeeVo");
      EmployeeVo empVo = employeeService.selectEmployee(employeeVo.getEmp_sq());
      DepartmentVo dptVo = departmentService.selectDepartment(employeeVo.getEmp_dpt());

      // 연차 정보
      if (div_apv_sq == 1) {
         Calendar calendar = new GregorianCalendar(Locale.KOREA);
         String year = Integer.toString(calendar.get(Calendar.YEAR));

         GiveAnnualVo giveAnuVo = new GiveAnnualVo();
         giveAnuVo.setAnu_emp_sq(employeeVo.getEmp_sq());
         giveAnuVo.setAnu_give_dt(year);

         giveAnuVo = annualService.selectGiveAnu(giveAnuVo);

         model.addAttribute("restAnu", giveAnuVo.getAnu_remains());
      }

      // 결재라인지정
      List<EmployeeVo> employeeList = employeeService.selectAllList();
      session.setAttribute("employeeList", employeeList);
      List<DepartmentVo> departmentList = departmentService.selectDepartNull();
      model.addAttribute("departmentList", departmentList);

      model.addAttribute("apvDivVo", apvDivVo);
      model.addAttribute("refList", refList);
      model.addAttribute("empList", empList);
      model.addAttribute("empVo", empVo);
      model.addAttribute("dptVo", dptVo);
      return "apvForm";
   }

   @RequestMapping(path = "/apvInsert", method = RequestMethod.POST)
   public String apvInsert(ApprovalEleVo apvEleVo, 
                     @RequestPart("apv_fl") MultipartFile apv_fl,
                     @RequestParam(name="apv_anu_time", defaultValue="") String apv_anu_time,
                     @RequestParam("apvLineFlag") String apvLineFlag,
                     @RequestParam(name="selectSign", defaultValue="") String signLine,
                     @RequestParam(name="selectRef", defaultValue="") String refLine, 
                     RedirectAttributes ra,
                     HttpSession session) throws IllegalStateException, IOException {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      // apvEleVo : 결재구분, 기안자, 시행자, 제목, 기안부서, 내용

      // 첨부파일 업로드
      String apv_fl_nm = "";
      String apv_fl_path = "";

      if (apv_fl.getSize() > 0) {
         apv_fl_nm = apv_fl.getOriginalFilename();

         apv_fl_path = "\\\\Sem-pc\\공유폴더\\최종프로젝트\\1조\\files\\" + UUID.randomUUID().toString();
         apv_fl.transferTo(new File(apv_fl_path));
      }

      apvEleVo.setApv_fl_nm(apv_fl_nm);
      apvEleVo.setApv_fl_path(apv_fl_path);

      // 근태신청서 양식
      if (apvEleVo.getApv_div_sq() == 1) {
         if (apvEleVo.getApv_anu_div().equals("2")) {
            apvEleVo.setApv_anu_str(apvEleVo.getApv_anu_str() + " " + apv_anu_time);
         }
      } else {
         apvEleVo.setApv_anu_str("");
         apvEleVo.setApv_anu_end("");
         apvEleVo.setApv_anu_div("");
      }

      int cnt = approvalService.insertApvEle(apvEleVo);

      if (cnt == 1) {
         int apv_sq = apvEleVo.getApv_sq();
         List<ApprovalLineVo> apvLineList = new ArrayList<>();
         List<ApprovalLineVo> apvRefLineList = new ArrayList<>();

         // 지정된 결재라인으로 상신
         logger.debug("결재라인지정 유무 : {}", apvLineFlag);

         if (apvLineFlag != null && apvLineFlag.equals("t")) {
            // 결재자 지정했을 때
            if (!signLine.equals("")) {
               String[] signLineArray = signLine.split(",");
               if (signLineArray.length > 0) {
                  for (int i = 0; i < signLineArray.length; i++) {
                     ApprovalLineVo apvLineTemp = new ApprovalLineVo();
                     apvLineTemp.setApa_emp(Integer.parseInt(signLineArray[i]));
                     apvLineList.add(apvLineTemp);
                  }
               }
            }

            // 수신참조자 지정했을 때
            if (!refLine.equals("")) {
               String[] refLineArray = refLine.split(",");
               if (refLineArray.length > 0) {
                  for (int i = 0; i < refLineArray.length; i++) {
                     ApprovalLineVo apvRefLineTemp = new ApprovalLineVo();
                     apvRefLineTemp.setApa_emp(Integer.parseInt(refLineArray[i]));
                     apvRefLineList.add(apvRefLineTemp);
                  }
               }
            }

            logger.debug("결재라인지정 : apvLineList : {}", apvLineList);
            logger.debug("결재라인지정 : apvRefLineList : {}", apvRefLineList);
         }

         // 기존 결재라인으로 상신
         else {
            ApprovalLineVo apvLineVo = new ApprovalLineVo();
            apvLineVo.setApa_div_apv(apvEleVo.getApv_div_sq());
            apvLineVo.setApa_code("1");
            // 결재자 목록
            apvLineList = approvalService.getAllApvLine(apvLineVo);

            // 수신참조자 목록
            apvLineVo.setApa_code("2");
            apvRefLineList = approvalService.getAllApvLine(apvLineVo);
         }

         ApprovalVo apvVo = new ApprovalVo();
         apvVo.setApv_sq(apv_sq);

         SignalVo signalVo = new SignalVo();
         signalVo.setSig_apv_sq(apv_sq);
         signalVo.setSig_div("1");

         // 결재자 목록이 존재할 때
         int apvCnt = 0;
         if (apvLineList.size() != 0 && apvLineList != null) { // 조건문 수정 예정
            for (int i = 0; i < apvLineList.size(); i++) {
               int apv_sign = apvLineList.get(i).getApa_emp();
               apvVo.setApv_sign(apv_sign);

               apvCnt += approvalService.insertApv(apvVo);

               signalVo.setSig_emp_sq(apv_sign);
               signalVo.setSig_con("[내가 받은 결재]" + apv_sq + "번 문서가 도착했습니다.");

               approvalService.insertSignal(signalVo);
            }

            if (apvCnt == apvLineList.size()) {
               logger.debug("Approval - 결재자 등록 완료");

               List<SignalVo> signalList = approvalService.selectSignal(empVo.getEmp_sq());
               session.setAttribute("signalSize", signalList.size());
               session.setAttribute("signalList", signalList);
            }
         }

         ApprovalRefVo apvRefVo = new ApprovalRefVo();
         apvRefVo.setApr_sq(apv_sq);

         signalVo.setSig_div("3");

         // 수신참조자 목록이 존재할 때
         int apvRefCnt = 0;
         if (apvRefLineList.size() != 0 && apvRefLineList != null) {
            for (int i = 0; i < apvRefLineList.size(); i++) {
               int apr_emp_sq = apvRefLineList.get(i).getApa_emp();
               apvRefVo.setApr_emp_sq(apr_emp_sq);

               apvRefCnt += approvalService.insertApvRef(apvRefVo);

               signalVo.setSig_emp_sq(apr_emp_sq);
               signalVo.setSig_con("[참조문서함]" + apv_sq + "번 문서가 도착했습니다.");

               approvalService.insertSignal(signalVo);
            }

            if (apvRefCnt == apvRefLineList.size()) {
               logger.debug("ApprovalRef - 수신참조자 등록 완료");

               List<SignalVo> signalList = approvalService.selectSignal(empVo.getEmp_sq());
               session.setAttribute("signalSize", signalList.size());
               session.setAttribute("signalList", signalList);
            }
         }

         ra.addFlashAttribute("msg", "상신되었습니다.");
         return "redirect:/apvList";
      }

      ra.addAttribute("div_apv_sq", apvEleVo.getApv_div_sq());
      return "redirect:/apvForm";
   }

   @RequestMapping(path = "/apvReceive", method = RequestMethod.GET)
   public String apvReceiveView(@RequestParam(name="page", defaultValue="1") int page,
                         @RequestParam(name="pageSize", defaultValue="10") int pageSize, 
                         HttpSession session, 
                         Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      // 전체 사원 목록
      List<EmployeeVo> empList = employeeService.selectAllList();
      // 전체 기안 문서 목록
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      // 전체 결재자 목록 - 직급 순
      List<ApprovalVo> approvalList = approvalService.getAllApv();
      // 내가 받은 결재 목록
      List<ApprovalVo> apvList = approvalService.selectApvList(empVo.getEmp_sq());
      // 내가 받은 결재 문서 목록
      List<ApprovalEleVo> apvEleTemp = approvalService.selectSignList(empVo.getEmp_sq());

      // 내가 받은 결재 목록
      for (int i = 0; i < apvList.size(); i++) {
         boolean flag = false; // 결재자 순으로 미승인, 반려 여부 체크
         for (int j = 0; j < approvalList.size(); j++) {
            if (approvalList.get(j).getApv_sq() == apvList.get(i).getApv_sq()) {
               if (approvalList.get(j).getApv_sign() != apvList.get(i).getApv_sign()) {
                  // 미승인 or 반려
                  if (approvalList.get(j).getApv_sign_chk().equals("0")
                        || approvalList.get(j).getApv_sign_chk().equals("2")) {
                     flag = true;
                     break;
                  }
               } else {
                  break;
               }
            }
         }

         if (flag) {
            for (int z = 0; z < apvEleTemp.size(); z++) {
               if (apvEleTemp.get(z).getApv_sq() == apvList.get(i).getApv_sq()) {
                  apvEleTemp.remove(z);
               }
            }
         }
      }

      // ========== 페이징리스트 ===========
      List<ApprovalEleVo> apvEleList = new ArrayList<>();

      for (int i = (page - 1) * pageSize; i < page * pageSize; i++) {
         if (i < apvEleTemp.size()) {
            apvEleList.add(apvEleTemp.get(i));
         }
      }

      model.addAttribute("empVo", empVo);
      model.addAttribute("empList", empList);
      model.addAttribute("approvalList", approvalList);
      model.addAttribute("apvDivList", apvDivList);
      model.addAttribute("apvEleList", apvEleList);

      model.addAttribute("apvEleCnt", apvEleTemp.size());
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvReceive";
   }

   @RequestMapping("/fileDownload")
   public void fileDownload(@RequestParam("apv_sq") int apv_sq, 
                      HttpSession session, 
                      HttpServletResponse response) throws Exception {
      ApprovalEleVo apvEleVo = approvalService.selectApvEle(apv_sq);
      String filename = apvEleVo.getApv_fl_nm();
      String filepath = apvEleVo.getApv_fl_path();

      File outputFile = new File(filepath);

      // 파일을 읽어와 저장할 버퍼를 임시로 만들고 버퍼의 용량은 업로드할 수 있는 파일 크기로 지정한다.
      byte[] temp = new byte[1024 * 1024 * 5];

      FileInputStream fis = new FileInputStream(outputFile);

      // 유형 확인 : 읽어올 경로의 파일 유형 -> 페이지 생성할 때 타입을 설정해야 한다.
      String mimeType = session.getServletContext().getMimeType(filepath);

      // 지정되지 않은 유형 예외처리
      if (mimeType == null) {
         mimeType = "application.octec-stream"; // 일련된 8bit 스트림 형식
      }

      // 파일 다운로드 시작
      response.setContentType(mimeType); // 유형 지정

      String encoding = new String(filename.getBytes("euc-kr"), "8859_1");

      String AA = "Content-Disposition";
      String BB = "attachment;filename=" + encoding;
      response.setHeader(AA, BB);

      // 브라우저에 쓰기
      ServletOutputStream sos = response.getOutputStream();

      int numRead = 0;
      while ((numRead = fis.read(temp, 0, temp.length)) != -1) {
         // 브라우저에 출력
         sos.write(temp, 0, numRead);
      }

      // 자원 해제
      sos.flush();
      sos.close();
      fis.close();
   }

   @RequestMapping(path="/apvStateUpdate", produces={ "application/json" })
   @ResponseBody
   public ApprovalVo apvStateUpdate(@RequestBody ApprovalVo approvalVo, 
                            HttpSession session, 
                            UseAnnualVo annualVo) {
      logger.debug("approvalVo : {}", approvalVo);

      int cnt = approvalService.updApvState(approvalVo);

      List<ApprovalVo> apvList = approvalService.getAllApvList(approvalVo);
      ApprovalVo apvVo = new ApprovalVo();
      ApprovalEleVo apvEleVo = new ApprovalEleVo();
      apvEleVo.setApv_sq(approvalVo.getApv_sq());

      // 승인 횟수
      int signCnt = 0;
      // 반려 횟수
      int returnCnt = 0;

      if (cnt == 1) {
         logger.debug("========== 결재 상태 변경 ==========");
         for (int i = 0; i < apvList.size(); i++) {
            if (apvList.get(i).getApv_sign_chk().equals("1")) {
               signCnt++;
            } else if (apvList.get(i).getApv_sign_chk().equals("2")) {
               returnCnt++;
            }
         }

         // 1. 대기 / 2. 진행 / 3. 종결 / 4. 반려

         if (returnCnt == 0) {
            // 승인하지 않은 결재자 존재 : 진행
            if (signCnt < apvList.size()) {
               apvEleVo.setApv_state("2");
            }
            // 결재자 모두 승인 : 종결
            else if (signCnt == apvList.size()) {
               // 업데이트 추가
               apvEleVo.setApv_state("3");
               approvalService.updApvEleState(apvEleVo);

               int apv_sq = approvalVo.getApv_sq();

               String apv_anu_div = annualService.selelct_anu_div(apv_sq);
               int apv_executer = annualService.select_UseEmpsq(apv_sq);

               // 추가 - 근태 구분이 존재할 때
               if (apv_anu_div != null) {
                  annualVo.setAnu_emp_sq(apv_executer);
                  annualVo.setAnu_sq(approvalVo.getApv_sq());
                  annualVo.setAnu_div(apv_anu_div);
                  int int_apv_anu_div = Integer.parseInt(apv_anu_div);
                  int apvCount = annualService.select_UseAnnualApv(annualVo);
                  if (int_apv_anu_div == 1) {
                     logger.debug("annualVo.getAnu_emp_sq() " + annualVo.getAnu_emp_sq());
                     logger.debug("apvCount " + apvCount);
                     annualService.insert_UseAnnualDay(annualVo);
                     annualService.update_UseDayUpdate(annualVo);

                  } else if (int_apv_anu_div == 2) {
                     annualService.insert_UseAnnualDay_bancha(annualVo);
                     annualService.update_UseDayUpdate(annualVo);
                  }
               }
            }
         }
         // 반려한 결재자 존재 : 반려
         else if (returnCnt > 0) {
            apvEleVo.setApv_state("4");
         }

         int eleUpdCnt = approvalService.updApvEleState(apvEleVo);

         if (eleUpdCnt == 1) {
            apvVo = approvalService.selectApv(approvalVo);

            if (apvEleVo.getApv_state().equals("3") || apvEleVo.getApv_state().equals("4")) {
               ApprovalEleVo tempVo = approvalService.selectApvEle(apvEleVo.getApv_sq());

               SignalVo signalVo = new SignalVo();
               signalVo.setSig_apv_sq(tempVo.getApv_sq());
               signalVo.setSig_emp_sq(tempVo.getApv_emp_sq());
               signalVo.setSig_div("2");

               if (apvEleVo.getApv_state().equals("3")) {
                  signalVo.setSig_con("[내가 올린 결재]" + tempVo.getApv_sq() + "번 문서가 종결되었습니다.");
               } else {
                  signalVo.setSig_con("[내가 올린 결재]" + tempVo.getApv_sq() + "번 문서가 반려되었습니다.");
               }

               approvalService.insertSignal(signalVo);

               List<SignalVo> signalList = approvalService.selectSignal(tempVo.getApv_emp_sq());
               session.setAttribute("signalSize", signalList.size());
               session.setAttribute("signalList", signalList);
            }
         }
      }

      return apvVo;
   }

   @RequestMapping(path="/apvSignCheck", produces={ "application/json" })
   @ResponseBody
   public boolean apvSignCheck(@RequestBody ApprovalVo approvalVo) {
      logger.debug("approvalVo : {}", approvalVo);

      EmployeeVo empVo = employeeService.selectEmployee(approvalVo.getApv_sign());

      if (empVo.getEmp_sign() != null) {
         return true;
      } else
         return false;
   }

   @RequestMapping(path="/apvReceiveSearch", method=RequestMethod.GET)
   public String apvReceiveSearchView(@RequestParam(name = "selDt", defaultValue = "1") String selDt,
                               @RequestParam("apv_str_dt") String apv_str_dt, 
                               @RequestParam("apv_end_dt") String apv_end_dt,
                               @RequestParam(name="apv_sign_chk", defaultValue="100") String apv_sign_chk,
                               @RequestParam(name="apv_div_sq", defaultValue="0") String apv_div_sq,
                               @RequestParam("apv_nm") String apv_nm, 
                               @RequestParam(name="page", defaultValue="1") int page,
                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, 
                               HttpSession session, 
                               Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      // 전체 회원 목록
      List<EmployeeVo> empList = employeeService.selectAllList();
      // 전체 기안 문서 목록
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      // 전체 결재자 목록 - 직급 순
      List<ApprovalVo> approvalList = approvalService.getAllApv();
      // 내가 받은 결재 목록
      List<ApprovalVo> apvList = approvalService.selectApvList(empVo.getEmp_sq());

      Map<String, Object> map = new HashMap<>();
      map.put("apv_sign", empVo.getEmp_sq());

      // 검색 결과
      List<ApprovalEleVo> apvEleTemp = null;

      model.addAttribute("selDt", selDt);
      model.addAttribute("apv_str_dt", apv_str_dt);
      model.addAttribute("apv_end_dt", apv_end_dt);
      model.addAttribute("apv_sign_chk", apv_sign_chk);
      model.addAttribute("apv_div_sq", apv_div_sq);
      model.addAttribute("apv_nm", apv_nm);

      // Map에 검색 조건 추가
      if (!apv_str_dt.equals("") && !apv_end_dt.equals("")) {
         map.put("start_date", apv_str_dt.replaceAll("/", "-"));
         map.put("end_date", apv_end_dt.replaceAll("/", "-"));
      }
      if (!apv_sign_chk.equals("100")) {
         map.put("apv_sign_chk", apv_sign_chk);
      }
      if (!apv_div_sq.equals("0")) {
         map.put("apv_div_sq", apv_div_sq);
      }
      if (!apv_nm.equals("")) {
         map.put("apv_nm", apv_nm);
      }

      // 기안일 검색
      if (selDt.equals("1")) {
         apvEleTemp = approvalService.searchApvStartDate(map);
      }
      // 결재일 검색
      else if (selDt.equals("2")) {
         apvEleTemp = approvalService.searchApvSignDate(map);
      }

      // 내가 받은 결재 목록
      for (int i = 0; i < apvList.size(); i++) {
         boolean flag = false; // 결재자 순으로 미승인, 반려 여부 체크
         for (int j = 0; j < approvalList.size(); j++) {
            if (approvalList.get(j).getApv_sq() == apvList.get(i).getApv_sq()) {
               if (approvalList.get(j).getApv_sign() != apvList.get(i).getApv_sign()) {
                  // 미승인 or 반려
                  if (approvalList.get(j).getApv_sign_chk().equals("0")
                        || approvalList.get(j).getApv_sign_chk().equals("2")) {
                     flag = true;
                     break;
                  }
               } else {
                  break;
               }
            }
         }

         if (flag) {
            for (int z = 0; z < apvEleTemp.size(); z++) {
               if (apvEleTemp.get(z).getApv_sq() == apvList.get(i).getApv_sq()) {
                  apvEleTemp.remove(z);
               }
            }
         }
      }

      // ========== 페이징리스트 ===========
      List<ApprovalEleVo> apvEleList = new ArrayList<>();

      for (int i = (page - 1) * pageSize; i < page * pageSize; i++) {
         if (i < apvEleTemp.size()) {
            apvEleList.add(apvEleTemp.get(i));
         }
      }

      model.addAttribute("empVo", empVo);
      model.addAttribute("empList", empList);
      model.addAttribute("approvalList", approvalList);
      model.addAttribute("apvDivList", apvDivList);
      model.addAttribute("apvEleList", apvEleList);

      model.addAttribute("apvEleCnt", apvEleTemp.size());
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvReceive";
   }

   @RequestMapping(path="/apvReference", method=RequestMethod.GET)
   public String apvReferenceView(@RequestParam(name = "page", defaultValue = "1") int page,
                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, 
                           HttpSession session, 
                           Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("apr_emp_sq", empVo.getEmp_sq());
      map.put("page", page);
      map.put("pageSize", pageSize);

      List<ApprovalEleVo> apvEleList = approvalService.selectApvRef(map);
      int apvEleCnt = approvalService.getApvRefCnt(empVo.getEmp_sq());

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      List<ApprovalVo> approvalList = approvalService.getAllApv();

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);

      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvReference";
   }

   @RequestMapping(path="/apvReferenceSearch", method=RequestMethod.GET)
   public String apvReferenceSearchView(@RequestParam(name = "selDt", defaultValue = "1") String selDt,
                                @RequestParam("apv_str_dt") String apv_str_dt, 
                                @RequestParam("apv_end_dt") String apv_end_dt,
                                @RequestParam(name = "apv_state", defaultValue = "100") String apv_state,
                                @RequestParam(name = "apv_div_sq", defaultValue = "0") String apv_div_sq,
                                @RequestParam("apv_nm") String apv_nm, 
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, 
                                HttpSession session, 
                                Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("apr_emp_sq", empVo.getEmp_sq());
      map.put("page", page);
      map.put("pageSize", pageSize);

      // 검색 결과
      List<ApprovalEleVo> apvEleList = null;

      model.addAttribute("selDt", selDt);
      model.addAttribute("apv_str_dt", apv_str_dt);
      model.addAttribute("apv_end_dt", apv_end_dt);
      model.addAttribute("apv_state", apv_state);
      model.addAttribute("apv_div_sq", apv_div_sq);
      model.addAttribute("apv_nm", apv_nm);

      // Map에 검색 조건 추가
      if (!apv_str_dt.equals("") && !apv_end_dt.equals("")) {
         map.put("start_date", apv_str_dt.replaceAll("/", "-"));
         map.put("end_date", apv_end_dt.replaceAll("/", "-"));
      }
      if (!apv_state.equals("100")) {
         map.put("apv_state", apv_state);
      }
      if (!apv_div_sq.equals("0")) {
         map.put("apv_div_sq", apv_div_sq);
      }
      if (!apv_nm.equals("")) {
         map.put("apv_nm", apv_nm);
      }

      int apvEleCnt = 0;

      // 기안일 검색
      if (selDt.equals("1")) {
         apvEleList = approvalService.searchApvRefStartDate(map);
         apvEleCnt = approvalService.getApvRefCntStartDate(map);
      }
      // 최종결재일 검색
      else if (selDt.equals("2")) {
         apvEleList = approvalService.searchApvRefEndDate(map);
         apvEleCnt = approvalService.getApvRefCntEndDate(map);
      }

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      List<ApprovalVo> approvalList = approvalService.getAllApv();

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);

      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvReference";
   }

   @RequestMapping(path="/apvRefHitUpdate", produces={ "application/json" })
   @ResponseBody
   public int apvRefHitUpdate(@RequestBody ApprovalRefVo apvRefVo, HttpSession session) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
      apvRefVo.setApr_emp_sq(empVo.getEmp_sq());

      approvalService.updateApvRefHit(apvRefVo);

      return 0;
   }

   @RequestMapping(path="/apvSend", method=RequestMethod.GET)
   public String apvSendView(@RequestParam(name="page", defaultValue="1") int page,
                       @RequestParam(name="pageSize", defaultValue="10") int pageSize, 
                         HttpSession session, 
                       Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("apv_emp_sq", empVo.getEmp_sq());
      map.put("page", page);
      map.put("pageSize", pageSize);

      List<ApprovalEleVo> apvEleList = approvalService.selectApvEleList(map);
      int apvEleCnt = approvalService.getApvEleCnt(empVo.getEmp_sq());

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      List<ApprovalVo> approvalList = approvalService.getAllApv();

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);
      model.addAttribute("approvalList", approvalList);

      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvSend";
   }

   @RequestMapping(path="/apvSendSearch", method=RequestMethod.GET)
   public String apvSendSearchView(@RequestParam(name="selDt", defaultValue="1") String selDt,
                           @RequestParam("apv_str_dt") String apv_str_dt, 
                           @RequestParam("apv_end_dt") String apv_end_dt,
                           @RequestParam(name="apv_state", defaultValue="100") String apv_state,
                           @RequestParam(name="apv_div_sq", defaultValue="0") String apv_div_sq,
                           @RequestParam("apv_nm") String apv_nm, 
                           @RequestParam(name="page", defaultValue="1") int page,
                           @RequestParam(name="pageSize", defaultValue="10") int pageSize, 
                           HttpSession session, 
                           Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("apv_emp_sq", empVo.getEmp_sq());
      map.put("page", page);
      map.put("pageSize", pageSize);

      // 검색 결과
      List<ApprovalEleVo> apvEleList = null;

      model.addAttribute("selDt", selDt);
      model.addAttribute("apv_str_dt", apv_str_dt);
      model.addAttribute("apv_end_dt", apv_end_dt);
      model.addAttribute("apv_state", apv_state);
      model.addAttribute("apv_div_sq", apv_div_sq);
      model.addAttribute("apv_nm", apv_nm);

      // Map에 검색 조건 추가
      if (!apv_str_dt.equals("") && !apv_end_dt.equals("")) {
         map.put("start_date", apv_str_dt.replaceAll("/", "-"));
         map.put("end_date", apv_end_dt.replaceAll("/", "-"));
      }
      if (!apv_state.equals("100")) {
         map.put("apv_state", apv_state);
      }
      if (!apv_div_sq.equals("0")) {
         map.put("apv_div_sq", apv_div_sq);
      }
      if (!apv_nm.equals("")) {
         map.put("apv_nm", apv_nm);
      }

      int apvEleCnt = 0;

      // 기안일 검색
      if (selDt.equals("1")) {
         apvEleList = approvalService.searchApvEleStartDate(map);
         apvEleCnt = approvalService.getApvEleCntStartDate(map);
      }
      // 최종결재일 검색
      else if (selDt.equals("2")) {
         apvEleList = approvalService.searchApvEleEndDate(map);
         apvEleCnt = approvalService.getApvEleCntEndDate(map);
      }

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      List<ApprovalVo> approvalList = approvalService.getAllApv();

      // 수신참조자 정보 출력
      List<ApprovalRefVo> allApvRefList = approvalService.getAllApvRef();
      List<ApprovalRefVo> apvRefList = new ArrayList<>();
      for (int i = 0; i < apvEleList.size(); i++) {
         for (int j = 0; j < allApvRefList.size(); j++) {
            if (apvEleList.get(i).getApv_sq() == allApvRefList.get(j).getApr_sq()) {
               apvRefList.add(allApvRefList.get(j));
            }
         }
      }

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);
      model.addAttribute("approvalList", approvalList);

      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "apvSend";
   }

   // 반려 문서 재상신
   @RequestMapping(path="/apvResubmit", produces={ "application/json" })
   @ResponseBody
   public String apvResubmit(@RequestBody ApprovalEleVo apvEleVo,
                       HttpSession session) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");
      
      // 기존 문서의 결재자, 수신참조자 정보 가져올 때 사용
      int re_apv_sq = apvEleVo.getApv_sq();

      apvEleVo = approvalService.selectApvEle(apvEleVo.getApv_sq());

      if (apvEleVo.getApv_fl_nm() == null || apvEleVo.getApv_fl_path() == null) {
         apvEleVo.setApv_fl_nm("");
         apvEleVo.setApv_fl_path("");
      }

      if (apvEleVo.getApv_anu_str() == null || apvEleVo.getApv_anu_str() == null
            || apvEleVo.getApv_anu_div() == null) {
         apvEleVo.setApv_anu_str("");
         apvEleVo.setApv_anu_end("");
         apvEleVo.setApv_anu_div("");
      }

      int cnt = approvalService.insertApvEle(apvEleVo);

      if (cnt == 1) {
         int apv_sq = apvEleVo.getApv_sq();

         ApprovalVo apvTempVo = new ApprovalVo();
         apvTempVo.setApv_sq(re_apv_sq);

         // 반려 문서의 결재자 목록
         List<ApprovalVo> apvList = approvalService.getAllApvList(apvTempVo);
         // 반려 문서의 수신참조자 목록
         List<ApprovalRefVo> apvRefList = approvalService.getApvRef(re_apv_sq);

         SignalVo signalVo = new SignalVo();
         signalVo.setSig_apv_sq(apv_sq);
         signalVo.setSig_div("1");

         // 결재자 목록이 존재할 때
         int apvCnt = 0;
         if (apvList.size() != 0 && apvList != null) { // 조건문 수정 예정
            for (int i = 0; i < apvList.size(); i++) {
               ApprovalVo apvVo = apvList.get(i);
               apvVo.setApv_sq(apv_sq);

               apvCnt += approvalService.insertApv(apvVo);
               
               signalVo.setSig_emp_sq(apvVo.getApv_sign());
               signalVo.setSig_con("[내가 받은 결재]" + apv_sq + "번 문서가 도착했습니다.");
               
               approvalService.insertSignal(signalVo);
            }

            if (apvCnt == apvList.size()) {
               logger.debug("Approval - 결재자 등록 완료");

               List<SignalVo> signalList = approvalService.selectSignal(empVo.getEmp_sq());
               session.setAttribute("signalSize", signalList.size());
               session.setAttribute("signalList", signalList);
            }
         }

         signalVo.setSig_div("3");
         
         // 수신참조자 목록이 존재할 때
         int apvRefCnt = 0;
         if (apvRefList.size() != 0 && apvRefList != null) {
            for (int i = 0; i < apvRefList.size(); i++) {
               ApprovalRefVo apvRefVo = apvRefList.get(i);
               apvRefVo.setApr_sq(apv_sq);

               apvRefCnt += approvalService.insertApvRef(apvRefVo);

               signalVo.setSig_emp_sq(apvRefVo.getApr_emp_sq());
               signalVo.setSig_con("[참조문서함]" + apv_sq + "번 문서가 도착했습니다.");

               approvalService.insertSignal(signalVo);
            }

            if (apvRefCnt == apvRefList.size()) {
               logger.debug("ApprovalRef - 수신참조자 등록 완료");

               List<SignalVo> signalList = approvalService.selectSignal(empVo.getEmp_sq());
               session.setAttribute("signalSize", signalList.size());
               session.setAttribute("signalList", signalList);
            }
         }

         return "1";
      }

      return "0";
   }

   @RequestMapping(path="/apvSignal", method=RequestMethod.GET)
   public String apvSignalView(@RequestParam("sig_sq") int sig_sq, 
                        HttpSession session, 
                        Model model) {
      SignalVo signalVo = approvalService.selectSignalSq(sig_sq);

      int cnt = approvalService.deleteSignal(signalVo.getSig_sq());

      if (cnt == 1) {
         List<SignalVo> signalList = approvalService.selectSignal(signalVo.getSig_emp_sq());
         session.setAttribute("signalSize", signalList.size());
         session.setAttribute("signalList", signalList);
      }

      // 내가 받은 결재 알림
      if (signalVo.getSig_div().equals("1")) {
         return "redirect:/apvReceive";
      }
      // 내가 올린 결재 알림
      else if (signalVo.getSig_div().equals("2")) {
         return "redirect:/apvSend";
      }
      // 참조문서함 알림
      else {
         return "redirect:/apvReference";
      }
   }

   @RequestMapping(path="/apvDetail", produces={ "application/json" })
   @ResponseBody
   public Map<String, Object> apvDetail(@RequestBody ApprovalVo approvalVo, 
                               HttpSession session) {
      logger.debug("디테일 모달 테스트 : {}", approvalVo);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      ApprovalEleVo apvEleVo = approvalService.selectApvEle(approvalVo.getApv_sq());
      ApprovalDivVo apvDivVo = approvalService.selectApvDiv(apvEleVo.getApv_div_sq());
      EmployeeVo apvEmpVo = employeeService.selectEmployee(apvEleVo.getApv_emp_sq());
      EmployeeVo apvExeVo = employeeService.selectEmployee(apvEleVo.getApv_executer());
      DepartmentVo apvDptVo = departmentService.selectDepartment(apvEmpVo.getEmp_dpt());
      List<EmployeeVo> apvRefEmpList = approvalService.selectApvRefEmp(apvEleVo.getApv_sq());
      List<ApprovalRefVo> apvRefList = approvalService.getApvRef(apvEleVo.getApv_sq());

      ApprovalVo tempVo = new ApprovalVo();
      tempVo.setApv_sq(apvEleVo.getApv_sq());

      // 결재자 목록
      List<ApprovalVo> apvList = approvalService.getAllApvList(tempVo);
      List<EmployeeVo> apvEmpList = approvalService.getAllApvEmpList(apvEleVo.getApv_sq());

      Map<String, Object> map = new HashMap<>();
      map.put("apv_div_nm", apvDivVo.getDiv_apv_nm());
      map.put("apv_sq", apvEleVo.getApv_sq());
      map.put("apv_dt", sdf.format(apvEleVo.getApv_dt()));
      map.put("apv_dpt_nm", apvDptVo.getDpt_nm());
      map.put("apv_emp_nm", apvEmpVo.getEmp_nm());
      map.put("apv_exe_nm", apvExeVo.getEmp_nm());
      map.put("apv_nm", apvEleVo.getApv_nm());
      map.put("apv_con", apvEleVo.getApv_con());
      map.put("apv_fl_nm", apvEleVo.getApv_fl_nm());
      map.put("apv_state", apvEleVo.getApv_state());
      map.put("apv_ref_emp", apvRefEmpList);
      map.put("apv_ref", apvRefList);
      map.put("apv_list", apvList);
      map.put("apv_emp_list", apvEmpList);

      List<String> apvDateList = new ArrayList<>();
      for (int i = 0; i < apvList.size(); i++) {
         if (apvList.get(i).getApv_date() != null) {
            apvDateList.add(sdf.format(apvList.get(i).getApv_date()));
         } else {
            apvDateList.add("");
         }
      }
      map.put("apv_date_list", apvDateList);

      // 반려 문서 : 사유
      if (apvEleVo.getApv_state().equals("4")) {
         for (int i = 0; i < apvList.size(); i++) {
            if (apvList.get(i).getApv_sign_chk().equals("2")) {
               map.put("apv_refu", apvList.get(i).getApv_refu());
               break;
            }
         }
      }

      // 근태신청서 : 기간, 구분
      if (apvEleVo.getApv_div_sq() == 1) {
         String term = apvEleVo.getApv_anu_str() + " ~ " + apvEleVo.getApv_anu_end();
         map.put("apv_term", term);

         if (apvEleVo.getApv_anu_div().equals("1")) {
            map.put("apv_anu_div", "연차");
         } else if (apvEleVo.getApv_anu_div().equals("2")) {
            map.put("apv_anu_div", "반차");
         } else if (apvEleVo.getApv_anu_div().equals("3")) {
            map.put("apv_anu_div", "외출");
         } else if (apvEleVo.getApv_anu_div().equals("4")) {
            map.put("apv_anu_div", "조퇴");
         } else if (apvEleVo.getApv_anu_div().equals("5")) {
            map.put("apv_anu_div", "경조");
         } else if (apvEleVo.getApv_anu_div().equals("6")) {
            map.put("apv_anu_div", "교육");
         } else {
            map.put("apv_anu_div", "기타");
         }
      }

      return map;
   }

   // 조장 작업구역 끝

   // 조원 한상호 작업구역
   // 결재라인 관리
   @RequestMapping(path="/approvalSignLine", method=RequestMethod.GET)
   public String approvalSignLineView(Model model) {

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<DepartmentVo> dptList = departmentService.selectDepartNull();
      List<DepartmentVo> dptdownList = departmentService.getAllDepartment();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();

      model.addAttribute("empList", empList);
      model.addAttribute("dptList", dptList);
      model.addAttribute("dptdownList", dptdownList);
      model.addAttribute("apvDivList", apvDivList);

      return "approvalSignLine";
   }

   @RequestMapping(path="/approvalSignLine", method=RequestMethod.POST)
   public String approvalSignLine_post(Model model) {

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<DepartmentVo> dptList = departmentService.selectDepartNull();
      List<DepartmentVo> dptdownList = departmentService.getAllDepartment();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();

      model.addAttribute("empList", empList);
      model.addAttribute("dptList", dptList);
      model.addAttribute("dptdownList", dptdownList);
      model.addAttribute("apvDivList", apvDivList);

      return "approvalSignLine";
   }

   @RequestMapping(path="/ajaxSignLine", method=RequestMethod.POST)
   @ResponseBody
   public List<Map<String, Object>> approvalSignLineSearch_post(@RequestBody Map<String, Object> map) {
      // 검색에 따라 테이블 나오게

      List<Map<String, Object>> searchList = approvalService.select_Searchresult((String) map.get("inputText"));

      return searchList;
   }

   // 전체에헤이~
   @RequestMapping(path="/ajaxSelectList", method=RequestMethod.POST)
   @ResponseBody
   public List<EmployeeVo> approvalSelectList_post(@RequestBody String hr_sq) {
      int sq = Integer.parseInt(hr_sq);
      logger.debug("번호 들어오냐...   : {}", sq);

      List<EmployeeVo> AllList = approvalService.selectEmpAllList(sq);
      logger.debug("배열에 있냐   : {}  {}", AllList.get(0).getEmp_nm(), AllList.get(0).getDpt_nm());

      return AllList;
   }

   @RequestMapping(path="/insertApprovalLine", method=RequestMethod.POST)
   public String insertLine(Model model, 
	                        @RequestParam("p_Approval_emp_sq") List<Integer> p_Approval_emp_sq,
	                        @RequestParam("p_Reference_emp_sq") List<Integer> p_Reference_emp_sq,
	                        @RequestParam("param_apv_div_sq") int param_apv_div_sq) {
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();
      List<EmployeeVo> empList = employeeService.selectAllList();
      List<DepartmentVo> dptList = departmentService.selectDepartNull();
      List<DepartmentVo> dptdownList = departmentService.getAllDepartment();

      model.addAttribute("empList", empList);
      model.addAttribute("dptList", dptList);
      model.addAttribute("dptdownList", dptdownList);
      model.addAttribute("apvDivList", apvDivList);

      // 삭제 후 insert 예정
      int delete = approvalService.deleteApvLine(param_apv_div_sq);

      ApprovalLineVo lineVo = new ApprovalLineVo();
      lineVo.setApa_div_apv(param_apv_div_sq);

      int insert = 0;
      for (int i = 0; i < p_Approval_emp_sq.size(); i++) {
         logger.debug("p_Approval_emp_sq : {}", p_Approval_emp_sq.get(i));
         lineVo.setApa_code("1");
         lineVo.setApa_div_apv(param_apv_div_sq);
         lineVo.setApa_emp(p_Approval_emp_sq.get(i));
         insert += approvalService.insertApvLine(lineVo);
      }

      for (int i = 0; i < p_Reference_emp_sq.size(); i++) {
         logger.debug("p_Reference_emp_sq : {}", p_Reference_emp_sq.get(i));
         lineVo.setApa_code("2");
         lineVo.setApa_div_apv(param_apv_div_sq);
         lineVo.setApa_emp(p_Reference_emp_sq.get(i));
         insert += approvalService.insertApvLine(lineVo);
      }

      return "approvalSignLine";
   }

   @RequestMapping(path="/ajaxSelectval", method=RequestMethod.POST)
   @ResponseBody
   public List<ApprovalLineVo> approvalSignLineSelect_post(@RequestBody String selvalue, Model model) {

      List<ApprovalLineVo> selectListVo = approvalService.selectAllApvLine(Integer.parseInt(selvalue));
      model.addAttribute("selectListVo", selectListVo);

      return selectListVo;

   }

   // 전체문서함

   @RequestMapping(path="/apvAllList", method=RequestMethod.GET)
   public String apvAllListView(@RequestParam(name="page", defaultValue="1") int page,
                          @RequestParam(name="pageSize", defaultValue="10") int pageSize, 
                          HttpSession session, 
                         Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("page", page);
      map.put("pageSize", pageSize);
      
      List<ApprovalEleVo> apvEleList = approvalService.getAllApvEleList(map);
      int apvEleCnt = approvalService.getAllApvEleCnt();

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);
      
      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "approvalAllList";
   }

   @RequestMapping(path="/apvAllListSearch", method=RequestMethod.GET)
   public String apvAllListSearchView(@RequestParam(name="selDt", defaultValue="1") String selDt,
                              @RequestParam("apv_str_dt") String apv_str_dt, 
                              @RequestParam("apv_end_dt") String apv_end_dt,
                              @RequestParam(name="apv_state", defaultValue="100") String apv_state,
                              @RequestParam(name="apv_div_sq", defaultValue="0") String apv_div_sq,
                              @RequestParam("apv_nm") String apv_nm, 
                              @RequestParam(name="page", defaultValue="1") int page,
                              @RequestParam(name="pageSize", defaultValue="10") int pageSize, 
                              HttpSession session,
                              Model model) {
      EmployeeVo empVo = (EmployeeVo) session.getAttribute("employeeVo");

      Map<String, Object> map = new HashMap<>();
      map.put("apv_emp_sq", empVo.getEmp_sq());
      map.put("page", page);
      map.put("pageSize", pageSize);
      
      // 검색 결과
      List<ApprovalEleVo> apvEleList = null;

      model.addAttribute("selDt", selDt);
      model.addAttribute("apv_str_dt", apv_str_dt);
      model.addAttribute("apv_end_dt", apv_end_dt);
      model.addAttribute("apv_state", apv_state);
      model.addAttribute("apv_div_sq", apv_div_sq);
      model.addAttribute("apv_nm", apv_nm);
      
      // Map에 검색 조건 추가
      if (!apv_str_dt.equals("") && !apv_end_dt.equals("")) {
         map.put("start_date", apv_str_dt.replaceAll("/", "-"));
         map.put("end_date", apv_end_dt.replaceAll("/", "-"));
      }
      if (!apv_state.equals("100")) {
         map.put("apv_state", apv_state);
      }
      if (!apv_div_sq.equals("0")) {
         map.put("apv_div_sq", apv_div_sq);
      }
      if (!apv_nm.equals("")) {
         map.put("apv_nm", apv_nm);
      }

      int apvEleCnt = 0;
      
      // 기안일 검색
      if (selDt.equals("1")) {
         apvEleList = approvalService.searchAllApvEleStartDate(map);
         apvEleCnt = approvalService.searchAllApvEleCntStartDate(map);
      }
      // 최종결재일 검색
      else if (selDt.equals("2")) {
         apvEleList = approvalService.searchAllApvEleEndDate(map);
         apvEleCnt = approvalService.searchAllApvEleCntEndDate(map);
      }

      List<EmployeeVo> empList = employeeService.selectAllList();
      List<ApprovalDivVo> apvDivList = approvalService.getAllApvDiv();

      model.addAttribute("empList", empList);
      model.addAttribute("apvEleList", apvEleList);
      model.addAttribute("apvDivList", apvDivList);
      
      model.addAttribute("apvEleCnt", apvEleCnt);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("page", page);
      return "approvalAllList";
   }

   // 여기까지

}