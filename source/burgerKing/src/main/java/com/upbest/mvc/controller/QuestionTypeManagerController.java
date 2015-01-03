package com.upbest.mvc.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.mvc.entity.BExHeading;
import com.upbest.mvc.service.IQuestionTypeManageService;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping("/questionType")
public class QuestionTypeManagerController {

    @Inject
    private IQuestionTypeManageService service;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("current", "questionTypeManager");
        return "questionTypeManager/questionTypeManager";
    }

    @RequestMapping("/addOrUpdatePage")
    public String addOrUpdatePage(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            BExHeading question = service.queryQuestion(id);
            model.addAttribute("question", question);
        }
        return "questionTypeManager/addQuestionType";
    }

    @RequestMapping("/add")
    @ResponseBody
    public void addQuestion(BExHeading question) {
        service.saveOrUpdateQuestion(question);
    }

    @RequestMapping("/del")
    @ResponseBody
    public void deleteQuestion(@RequestParam(value = "id", required = false) Integer questionId) {
        service.deleteQuestion(questionId);
    }

    @RequestMapping("/update")
    @ResponseBody
    public void updateQuestion(BExHeading question) {
        service.saveOrUpdateQuestion(question);
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageModel<BExHeading> queryQuestions(@RequestParam(value = "quesType", required = false) Integer quesType, @RequestParam("page") int page, @RequestParam("rows") int size,
            @RequestParam(value = "hValue", required = false) String hValue, HttpServletResponse response) {
        Pageable pageable = new PageRequest(page - 1, size);
        return service.queryQuestions(quesType,hValue, pageable);
    }
}
