package kr.or.nko.data.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.nko.data.model.DataDatasVo;
import kr.or.nko.data.model.DataHisVo;
import kr.or.nko.data.model.DataVo;
import kr.or.nko.data.service.IDataService;
import kr.or.nko.employee.model.EmployeeVo;
import kr.or.nko.util.model.PageVo;

@Controller
public class DataController {
	
	private Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@Resource(name="dataService")
	private IDataService dataService;
	
	@RequestMapping("/datas")
	public String webhard(Model model,HttpSession session,
			@RequestParam(name="page", defaultValue="1")int page,
			@RequestParam(name="pageSize", defaultValue="10")int pageSize){
		
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		int memGrade = empvo.getEmp_grade();
		
		PageVo pageVo = new PageVo(page, pageSize);
		
		Map<String, Object> resultMap = dataService.selectPaging(pageVo);
		List<DataVo> dataList = (List<DataVo>) resultMap.get("dataList");
		int dataCnt = (Integer) resultMap.get("dataCnt");
		
		model.addAttribute("dataCnt", dataCnt);
	    model.addAttribute("dataList", dataList);
	    model.addAttribute("memGrade", memGrade);
	    model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", page);
	    
	    
		return "datas";
	}
	
	@RequestMapping("/dataSearch")
	public String researchSearchProcess(
										@RequestParam("keyword")String keyword,
										@RequestParam(name="page", defaultValue="1")int page,
										@RequestParam(name="pageSize", defaultValue="10")int pageSize,
										Model model) {
		
		PageVo pageVo = new PageVo(page, pageSize);
		pageVo.setData_man(keyword);
		pageVo.setData_nm(keyword);
		
		Map<String, Object> resultMap = dataService.selectSearchPaging(pageVo);
		List<DataVo> dataList = (List<DataVo>) resultMap.get("dataList");
//		List<DataVo> dataList = dataService.searchWantData(keyword);
		
		int dataCnt = (Integer) resultMap.get("dataCnt");
		
		logger.debug("keyword={}",keyword);
		logger.debug("dataList={}",dataList);
		logger.debug("dataCnt={}",dataCnt);
		
		model.addAttribute("dataList", dataList);
		model.addAttribute("dataCnt", dataCnt);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		
		return "datas";
	}
	
	@RequestMapping(path = "/insert", method = RequestMethod.POST)
	public String add(Model model,
			@RequestParam("dataTitle") String dataTitle, MultipartHttpServletRequest mhsq, HttpSession session) throws Exception {
		
		logger.debug("dataTitle = {}",dataTitle);
		logger.debug("mhsq = {}",mhsq);
		
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq();
		
		String realFolder = "d:/Upload/";
		
		// 파일이 없을 경우 생성
        File dir = new File(realFolder);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
 
        // 넘어온 파일을 리스트로 저장
        List<MultipartFile> mf = mhsq.getFiles("dataFile");
        if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {
             // 넘어온 파일이 없을경우
        	
        } else {
        	DataVo dvo = new DataVo();
        	dvo.setData_emp_sq(memId);
        	dvo.setData_nm(dataTitle);
        	dvo.setData_man(empvo.getEmp_nm());
        	dataService.insertData(dvo);
        	
            for (int i = 0; i < mf.size(); i++) {
                // 파일 중복명 처리
                String genId = UUID.randomUUID().toString();
                // 본래 파일명
                String originalfileName = mf.get(i).getOriginalFilename();
                // 저장되는 파일 이름
                String saveFileName = genId + "." + FilenameUtils.getExtension(originalfileName);
 
                String savePath = realFolder + saveFileName; // 저장 될 파일 경로
 
                long fileSize = mf.get(i).getSize(); // 파일 사이즈
 
                mf.get(i).transferTo(new File(savePath)); // 파일 저장
                
                // db 삽입
                DataDatasVo vo = new DataDatasVo();
                vo.setData_fl_nm(originalfileName);
                vo.setData_fl_path(savePath);
                vo.setData_fl_norenm(saveFileName);
                vo.setData_fl_size(fileSize);
                dataService.insertDataFile(vo);
            }
        }
        
        List<DataVo> dataList = dataService.selectData(Integer.toString(memId));
        model.addAttribute("dataList", dataList);
        
        return "redirect:/datas";
	}
	
	@RequestMapping("/download")
	public void download(@RequestParam("down") String down,HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException{
		
		logger.debug("**************");
		logger.debug("down = {}",down); // 시퀀스를 얻어옴
		
		List<DataDatasVo> dataList = dataService.selectDataDatas(down); // 얻어온 시퀀스를 이용해 다중파일리스트 가지고옴
		logger.debug("dataList = {}{}",dataList,dataList.size()); // 리스트 확인
		
		// 다운로드내역 회원 삽입(만약 다운받았다면 삽입x)
		EmployeeVo empvo = (EmployeeVo) session.getAttribute("employeeVo");
		int memId = empvo.getEmp_sq(); // 사원번호
		String name = empvo.getEmp_nm(); // 사원이름
		
		List<DataHisVo> datahisList = dataService.selectDataHis(down);
		int chk = 0;
		for(int i=0;i<datahisList.size();i++){
			if(memId == datahisList.get(i).getData_emp_sq()){
				chk=1;
			}
		}
		
		if(chk==0){
			DataHisVo hvo = new DataHisVo();
			hvo.setData_sq(Integer.parseInt(down));
			hvo.setData_emp_sq(memId);
			hvo.setData_nm(name);
			dataService.insetDataHis(hvo);
		}
		
		// 파일 다운로드 영역
		for(int i=0;i<dataList.size();i++){
			 String fd_file_name = dataList.get(i).getData_fl_nm();			// 진짜이름
			 String fd_file_noname = dataList.get(i).getData_fl_norenm();	// 가짜이름
			 String fd_file_path = dataList.get(i).getData_fl_path();		// 경로
			 long fd_file_size = dataList.get(i).getData_fl_size();     	// 사이즈
			        
			 response.setContentType("application/octet-stream; charset=UTF-8");
			 response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fd_file_name, "UTF-8")  + "\"");
			 response.setHeader("Content-Transfer-Encoding", "binary");

			 try {
				FileCopyUtils.copy(new FileInputStream(new File(fd_file_path)), response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}    
		} // for
		
	}
	
	@ResponseBody
	@RequestMapping("/downloadHis")
	public List<DataHisVo> downloadHis(@RequestBody String param,Model model){
		
		logger.debug("sq = {}",param); // 시퀀스값
		
		List<DataHisVo> datahisList = dataService.selectDataHis(param);
		
		logger.debug("datahisList = {}",datahisList);
		
		model.addAttribute("datahisList", datahisList);
		
		return datahisList;
	}
	
	@ResponseBody
	@RequestMapping("/searchEmp")
	public List<EmployeeVo> searchEmp(@RequestBody String param,Model model){
		String name = param.substring(1, param.length()-1); // json 이기 때문에 "이름" 식으로 나와 처리 
		logger.debug(name);
		List<EmployeeVo> emplist = dataService.searchEmp(name);
		
		logger.debug("emplist = {}",emplist);
		
		return emplist;
	}
	
	@RequestMapping("/delData")
	public String delData(@RequestParam("deldown") String deldown,Model model){
		
		dataService.delDatas(deldown);	// 다운 자료 삭제
		dataService.delHisData(deldown); // 다운 내역 삭제
		dataService.delData(deldown); // 다운 로드 삭제
		
		return "redirect:/datas";
	}
}
