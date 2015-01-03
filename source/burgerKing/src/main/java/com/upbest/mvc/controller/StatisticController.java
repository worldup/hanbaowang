package com.upbest.mvc.controller;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.mvc.service.IStatisticService;
import com.upbest.mvc.vo.UserStatisticVO;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping("/statistic")
public class StatisticController {

	@Inject
	private IStatisticService service;

	@RequestMapping("/index")
	public String index(Model model) {
	    model.addAttribute("current", "statistic");
		return "statistic/statistic";
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageModel<UserStatisticVO> queryStatisticInfos(
			@RequestParam("page") int page, @RequestParam("rows") int size) {
		Pageable pageable = new PageRequest(page - 1, size);
		return service.queryStatisticResult(pageable);
	}


}
