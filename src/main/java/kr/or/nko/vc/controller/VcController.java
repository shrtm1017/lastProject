package kr.or.nko.vc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VcController {
	@RequestMapping(path="/vc", method=RequestMethod.GET)
	public String vc(){
		
		return "vc";
	}
		
}