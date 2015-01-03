package com.upbest.mvc.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.constant.Constant.QuestionType;
import com.upbest.mvc.entity.BQuestion;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IBExBaseInfoService;
import com.upbest.mvc.service.IQuestionManageService;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping("/question")
public class QuestionManagerController {

	@Inject
	private IQuestionManageService service;
	
	@Inject
    private IBExBaseInfoService baseService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("current", "questionManager");
		return "questionManager/questionManager";
	}

	@RequestMapping("/addOrUpdatePage")
	public String addOrUpdatePage(@RequestParam(value = "id", required = false) Integer id, Model model) {
		if (id != null) {
		    QuestionVO question = service.queryQuestion(id).get(0);
			model.addAttribute("question", question);
		}

		return "questionManager/addQuestion";
	}

	@RequestMapping("/add")
	@ResponseBody
	public void addQuestion(BQuestion question, @RequestParam(value = "ids", required = false) Integer ids) {
	    question.setQmodule(ids);
		service.saveOrUpdateQuestion(question);
	}

	@RequestMapping("/del")
	@ResponseBody
	public void deleteQuestion(@RequestParam(value = "id", required = false) Integer questionId) {
		service.deleteQuestion(questionId);
	}

	@RequestMapping("/update")
	@ResponseBody
	public void updateQuestion(BQuestion question, @RequestParam(value = "ids", required = false) Integer ids) {
	    question.setQmodule(ids);
		service.saveOrUpdateQuestion(question);
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageModel<QuestionVO> queryQuestions(@RequestParam(value="quesType",required=false)Integer quesType,
			@RequestParam("page") int page, @RequestParam("rows") int size, 
			@RequestParam(value = "ids", required = false) Integer ids, 
			@RequestParam(value = "preids", required = false) Integer preids, 
			HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, size);
		return service.queryQuestions(quesType, ids, preids, pageable);
	}

	@RequestMapping("/detail")
	public String queryQuestion(@RequestParam("id") int id, Model model) {
	    QuestionVO question = service.queryQuestion(id).get(0);
	    model.addAttribute("question", question);
		return "/questionManager/getQuestion";
	}
	
	@ResponseBody
    @RequestMapping("/questionType")
    public List<SelectionVO> questionType(HttpServletResponse response, HttpSession session) {
        List<SelectionVO> questionTypeList = service.queryQuestionType();
        return questionTypeList;
    }
	
    /*@RequestMapping("/getTreeList")
    @ResponseBody
    public String getUserTreeList(@RequestParam(value = "role", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        String jsonString = new String();
        List<TreeVO> list = baseService.getUrVOList();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String regEx = "[''”“\"\"]";
        Pattern pat = Pattern.compile(regEx);
        if (!CollectionUtils.isEmpty(list)) {
            for (TreeVO tree : list) {
                sb.append("{");
                sb.append("\"id\":\"" + tree.getId() + "\"");
                sb.append(",\"name\":\"" + tree.getName() + "\"");
                sb.append(",\"pId\":\"" + tree.getPid() + "\",checked:false");
                sb.append(",open:true");
                sb.append("},\n");
            }
            StringBuffer sb2 = new StringBuffer();
            sb2.append(sb.toString().substring(0, sb.toString().length() - 2));
            sb2.append("\n]");
            jsonString = sb2.toString();
        }
        return jsonString;
//        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(jsonString, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
//        outPrint(json, response);
    }*/
	
	@RequestMapping("/listQuestionType")
	@ResponseBody
	public List<SelectionVO> queryQuestionType(){
		return service.queryQuestionType();
	}

}
