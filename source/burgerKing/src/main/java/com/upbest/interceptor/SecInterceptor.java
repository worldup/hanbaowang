package com.upbest.interceptor;

 
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.upbest.mvc.entity.Buser;
import com.upbest.utils.SystemConstants;

 

/** 
 * @author qgan
 * @version 2014年2月20日 上午11:05:26
 */
public class SecInterceptor extends ConfigurableInterceptor {

 

    private static final Logger log = LoggerFactory.getLogger(SecInterceptor.class);

	@Override
	public boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Buser admin = (Buser) request.getSession().getAttribute(SystemConstants.CLIENT_SESSION);
		log.debug("token =  {}", admin);
        if(admin  == null) {
            forbidden(request, response);
            return false;
        }
 
		return true;
	}

    private void forbidden(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.sendRedirect("/toLogin");
    	//        log.info("user is unauthorized");
//        jsonView.setUpdateContentLength(true);
//        jsonView.render(null,request, response);
     }

    public void internalPostHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // CurrentUser.clearUser();
    }

}
