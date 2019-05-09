package kr.or.nko.webhard.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nko.data.service.IDataService;

@Controller
public class WebhardController {
	
	private Logger logger = LoggerFactory.getLogger(WebhardController.class);
	
	@Resource(name="dataService")
	private IDataService dataService;
	
	@RequestMapping("/webhard")
	public String webhard(){
		
		return "webhard";
	}
	
	
}
