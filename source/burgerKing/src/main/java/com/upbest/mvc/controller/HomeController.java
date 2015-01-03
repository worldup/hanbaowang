package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IBuserService;
import com.upbest.utils.PasswordUtil;
import com.upbest.utils.SystemConstants;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    protected static final int DEFAULT_PAGE_NUM = 1;
    protected static final int DEFAULT_PAGE_SIZE = 4;
    @Inject
    protected IBuserService buserService;
    @RequestMapping(value = "/login")
    @ResponseBody
    public void login(@RequestParam("name") String name, HttpSession session, @RequestParam("pwd") String pwd, HttpServletResponse response, Model model, HttpServletRequest request) {
         Buser buser = buserService.findByName(name);
        if (buser == null) {
            // 用户名或者密码错误
            outPrint("0", response);
        } else if (!PasswordUtil.genPassword(pwd).equalsIgnoreCase(buser.getPwd())) {
            outPrint("1", response);
        } else {
            session.setAttribute("buser", buser);
            outPrint("2", response);
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        session.setAttribute("buser", null);
        return "login";

    }
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        return "index";
    }
    private void outPrint(String str, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(str);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
